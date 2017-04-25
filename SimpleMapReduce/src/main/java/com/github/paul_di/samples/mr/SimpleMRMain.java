package com.github.paul_di.samples.mr;

import com.opencsv.CSVParser;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.hive.serde2.io.HiveDecimalWritable;
import org.apache.hadoop.io.*;
import org.apache.hadoop.conf.*;
import org.apache.hadoop.mapreduce.*;
import org.apache.hadoop.mapreduce.lib.input.TextInputFormat;
import org.apache.orc.TypeDescription;
import org.apache.orc.mapreduce.OrcOutputFormat;
import org.apache.orc.mapred.OrcStruct;
import org.apache.log4j.Logger;
import java.io.IOException;

/*
create external table TableName (
  name string,
  balance decimal(38,12),
  isActive int
) stored as orc
  LOCATION '/pathToFolder/withOrc'
  tblproperties ("orc.compress"="SNAPPY");
*/

public class SimpleMRMain {
  private static String OutputScheme = "struct<name:string,balance:decimal(38,12),isActive:int>";

  public static void main(String[] args) throws Exception {
    Configuration conf = new Configuration();
    conf.set("orc.mapred.output.schema", OutputScheme);

    Job job = Job.getInstance(conf, "Simple MR");

    job.setJarByClass(SimpleMRMain.class);
    job.setMapperClass(SimpleMapper.class);
    job.setNumReduceTasks(0);//disable reducer

    job.setMapOutputKeyClass(NullWritable.class);
    job.setMapOutputValueClass(Writable.class);
    job.setOutputKeyClass(NullWritable.class);
    job.setOutputValueClass(Writable.class);

    if(args.length != 2) {
      System.err.println("Usage: [inputPath] [outputPath]");
      System.exit(-1);
    }

    TextInputFormat.addInputPath(job, new Path(args[0]));
    OrcOutputFormat.setOutputPath(job, new Path(args[1]));

    job.setInputFormatClass(TextInputFormat.class);
    job.setOutputFormatClass(OrcOutputFormat.class);
    System.exit(job.waitForCompletion(true) ? 0 : 1);
  }

  protected static class SimpleMapper extends Mapper<LongWritable, Text, NullWritable, OrcStruct> {
    private Logger logger = Logger.getLogger(SimpleMapper.class);

    private final CSVParser csvParser = new CSVParser();

    private final TypeDescription schema = TypeDescription.fromString(OutputScheme);
    private final OrcStruct orcRow = (OrcStruct) OrcStruct.createValue(schema);

    protected void map(LongWritable key, Text value, Context context) throws IOException, InterruptedException {
      try {
        context.write(NullWritable.get(), genOutputRow(value));
      } catch(Exception e) {
        //logging of parsing errors is not not typical for bigdata apps. Wrong input data should be saved separately
        //or ignored (kafka appender can be used for saving when percent of errors isn't big)
        logger.info("Can't parse line: {" + value.toString() + "}", e);
      }
    }

    protected OrcStruct genOutputRow(Text value) throws IOException {
      String[] parsed = csvParser.parseLine(value.toString());
      if (parsed.length != 3) {
        throw new IllegalArgumentException("Csv line has {" + parsed.length + "} elements. Expected: 3");
      }

      Text name = new Text(parsed[0]);
      HiveDecimalWritable balance = new HiveDecimalWritable(parsed[1]);
      IntWritable isActive = new IntWritable(Integer.valueOf(parsed[2]));

      if(!balance.isSet()) {
        throw new NumberFormatException("Can't parse balance {" + parsed[1] + "} to BigDecimal");
      }

      orcRow.setFieldValue(0, name);
      orcRow.setFieldValue(1, balance );
      orcRow.setFieldValue(2, isActive);
      return orcRow;
    }
  }
}