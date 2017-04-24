package com.github.paul_di.samples.mr;

import com.opencsv.CSVParser;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.hive.serde2.io.HiveDecimalWritable;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.NullWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.conf.*;
import org.apache.hadoop.mapreduce.*;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.input.TextInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
import org.apache.orc.TypeDescription;
import org.apache.orc.mapreduce.OrcOutputFormat;
import org.apache.orc.mapred.OrcStruct;
import org.apache.log4j.Logger;

import java.io.IOException;

public class SimpleMRMain {
  public static void main(String[] args) throws Exception {
    Configuration conf = new Configuration();
    conf.set("mapred.job.tracker", "local");
    conf.set("fs.default.name", "local");
    Job job = Job.getInstance(conf, "Simple MR");

    job.setJarByClass(SimpleMRMain.class);

    job.setMapperClass(SimpleMapper.class);
    job.setNumReduceTasks(0);//disable reducer

    job.setMapOutputKeyClass(NullWritable.class);
    job.setMapOutputValueClass(OrcStruct.class);
    job.setOutputKeyClass(NullWritable.class);
    job.setOutputValueClass(OrcStruct.class);

    if(args.length != 2) {
      System.err.println("Usage: [inputPath] [outputPath]");
      System.exit(-1);
    }
    FileInputFormat.addInputPath(job, new Path(args[0]));
    FileOutputFormat.setOutputPath(job, new Path(args[1]));

    job.setInputFormatClass(TextInputFormat.class);
    job.setOutputFormatClass(OrcOutputFormat.class);
    System.exit(job.waitForCompletion(true) ? 0 : 1);
  }

  public static class SimpleMapper extends Mapper<LongWritable, Text, NullWritable, OrcStruct> {
    private Logger logger = Logger.getLogger(SimpleMapper.class);

    private final CSVParser csvParser = new CSVParser();

    private final TypeDescription schema = TypeDescription.fromString("struct<name:string,balance:decimal,isActive:int>");
    private final OrcStruct orcRow = (OrcStruct) OrcStruct.createValue(schema);

    protected void map(LongWritable key, Text value, Context context) throws IOException, InterruptedException {

      try {
        String[] parsed = csvParser.parseLine(value.toString());
        if (parsed.length != 3) {
          throw new Exception("Csv line has {" + parsed.length + "} elements. Expected: 3");
        }

        Text name = new Text(parsed[0]);
        HiveDecimalWritable balance = new HiveDecimalWritable(parsed[1]);
        IntWritable isActive = new IntWritable(Integer.valueOf(parsed[2]));

        orcRow.setFieldValue(0, name);
        orcRow.setFieldValue(1, balance );
        orcRow.setFieldValue(2, isActive);

      } catch(Exception e) {
        //logging of parsing errors is not not typical for bigdata apps. Wrong input data should be saved separately
        //or ignored (kafka logger appender can be used for saving when percent of errors isn't big)
        logger.info("Can't parse line: {" + value.toString() + "}", e);
      }

      context.write(NullWritable.get(), orcRow);
    }
  }
}

/*
create external table Addresses (
  name string,
  balance decimal(38,12),
  isActive int
) stored as orc
  LOCATION '/path'
  tblproperties ("orc.compress"="SNAPPY");
*/
