package com.example.lint;


import schemacrawler.schema.Table;
import schemacrawler.tools.analysis.lint.BaseLinter;

public class AdditionalLinter
  extends BaseLinter
{
  
  @Override
  public String convertLintValueToString(final Object value)
  {
    return value.toString();
  }

  @Override
  public String getDescription()
  {
    return getSummary();
  }

  @Override
  public String getSummary()
  {
    return "table names should start with FOO_";
  }

  @Override
  public void lint(final Table table)
  {
    if (table != null)
    {
      if (!table.getName().startsWith("FOO_"))
      {
        addLint(table, getSummary(), table.getFullName());
      }
    }
  }

}