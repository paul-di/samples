package com.github.paul_di.samples.mr;

import org.apache.hadoop.io.Text;
import org.apache.orc.mapred.OrcStruct;
import org.junit.Test;

import static org.junit.Assert.*;

public class SimpleMapperTest {
  private SimpleMRMain.SimpleMapper mapper = new SimpleMRMain.SimpleMapper();

  @Test(expected=IllegalArgumentException.class)
  public void csvLineNotEnoughElements() throws Exception {
    mapper.genOutputRow(new Text("alex,1.0"));
  }

  @Test(expected=IllegalArgumentException.class)
  public void csvLineTooManyElements() throws Exception {
    mapper.genOutputRow(new Text("alex,1.0,1,bonus string"));
  }

  @Test(expected=NumberFormatException.class)
  public void illegalBalanceFormat() throws Exception {
    mapper.genOutputRow(new Text("alex,1+0,1"));
  }

  @Test(expected=NumberFormatException.class)
  public void illegalIsActiveFormat() throws Exception {
    mapper.genOutputRow(new Text("alex,0.0,1.0"));
  }

  @Test
  public void csvEscapingSupport() throws Exception {
    OrcStruct row = mapper.genOutputRow(new Text("\"Alic\\\"e\",0.0,1"));
    assertEquals(row.getFieldValue("name").toString(), "Alic\"e");
  }

  @Test
  public void validResReturn() throws Exception {
    OrcStruct row = mapper.genOutputRow(new Text("Bob,123456789.987654321,0"));
    assertEquals(row.getFieldValue("name").toString(), "Bob");
    assertEquals(row.getFieldValue("balance").toString(), "123456789.987654321");
    assertEquals(row.getFieldValue("isActive").toString(), "0");
  }
}