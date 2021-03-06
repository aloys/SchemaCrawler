/*
========================================================================
SchemaCrawler
http://www.schemacrawler.com
Copyright (c) 2000-2018, Sualeh Fatehi <sualeh@hotmail.com>.
All rights reserved.
------------------------------------------------------------------------

SchemaCrawler is distributed in the hope that it will be useful, but
WITHOUT ANY WARRANTY; without even the implied warranty of
MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.

SchemaCrawler and the accompanying materials are made available under
the terms of the Eclipse Public License v1.0, GNU General Public License
v3 or GNU Lesser General Public License v3.

You may elect to redistribute this code under any of these licenses.

The Eclipse Public License is available at:
http://www.eclipse.org/legal/epl-v10.html

The GNU General Public License v3 and the GNU Lesser General Public
License v3 are available at:
http://www.gnu.org/licenses/

========================================================================
*/

package schemacrawler.tools.integration.thymeleaf;


import java.io.Writer;
import java.nio.charset.Charset;

import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;
import org.thymeleaf.templateresolver.AbstractConfigurableTemplateResolver;
import org.thymeleaf.templateresolver.ClassLoaderTemplateResolver;
import org.thymeleaf.templateresolver.FileTemplateResolver;
import org.thymeleaf.templateresolver.ITemplateResolver;
import org.thymeleaf.templateresolver.UrlTemplateResolver;

import schemacrawler.tools.executable.BaseSchemaCrawlerCommand;
import schemacrawler.tools.integration.serialization.SerializationCommand;
import sf.util.SchemaCrawlerLogger;

/**
 * Main executor for the Thymeleaf integration.
 *
 * @author Sualeh Fatehi
 */
public final class ThymeleafRenderer
  extends BaseSchemaCrawlerCommand
{

  private static final SchemaCrawlerLogger LOGGER = SchemaCrawlerLogger
    .getLogger(SerializationCommand.class.getName());

  static final String COMMAND = "thymeleaf";

  public ThymeleafRenderer()
  {
    super(COMMAND);
  }

  @Override
  public void checkAvailibility()
    throws Exception
  {
    // Nothing to check at this point. The Command should be available
    // after the class is loaded, and imports are resolved.
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public final void execute()
    throws Exception
  {
    checkCatalog();

    final Context context = new Context();
    context.setVariable("catalog", catalog);
    context.setVariable("identifiers", identifiers);

    final TemplateEngine templateEngine = new TemplateEngine();
    final Charset inputCharset = outputOptions.getInputCharset();

    final FileTemplateResolver fileResolver = new FileTemplateResolver();
    fileResolver.setCheckExistence(true);
    templateEngine.addTemplateResolver(configure(fileResolver, inputCharset));

    final ClassLoaderTemplateResolver classpathResolver = new ClassLoaderTemplateResolver();
    classpathResolver.setCheckExistence(true);
    templateEngine
      .addTemplateResolver(configure(classpathResolver, inputCharset));

    final UrlTemplateResolver urlResolver = new UrlTemplateResolver();
    urlResolver.setCheckExistence(true);
    templateEngine.addTemplateResolver(configure(urlResolver, inputCharset));

    final String templateLocation = outputOptions.getOutputFormatValue();
    try (final Writer writer = outputOptions.openNewOutputWriter();)
    {
      templateEngine.process(templateLocation, context, writer);
    }
  }

  private ITemplateResolver configure(final AbstractConfigurableTemplateResolver templateResolver,
                                      final Charset inputEncoding)
  {
    templateResolver.setCharacterEncoding(inputEncoding.name());
    templateResolver.setTemplateMode("HTML5");
    return templateResolver;
  }

}
