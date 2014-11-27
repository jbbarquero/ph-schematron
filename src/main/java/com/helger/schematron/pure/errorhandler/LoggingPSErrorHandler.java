/**
 * Copyright (C) 2014 Philip Helger (www.helger.com)
 * philip[at]helger[dot]com
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.helger.schematron.pure.errorhandler;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.helger.commons.error.EErrorLevel;
import com.helger.commons.io.IReadableResource;
import com.helger.commons.lang.CGStringHelper;
import com.helger.commons.log.LogUtils;
import com.helger.commons.string.StringHelper;
import com.helger.schematron.pure.model.IPSElement;
import com.helger.schematron.pure.model.IPSHasID;

/**
 * An implementation if {@link IPSErrorHandler} that logs to an SLF4J logger.
 *
 * @author Philip Helger
 */
public class LoggingPSErrorHandler extends AbstractPSErrorHandler
{
  private static final Logger s_aLogger = LoggerFactory.getLogger (LoggingPSErrorHandler.class);

  public LoggingPSErrorHandler ()
  {
    super ();
  }

  public LoggingPSErrorHandler (@Nullable final IPSErrorHandler aNestedErrorHandler)
  {
    super (aNestedErrorHandler);
  }

  @Nonnull
  public static String getLogMessage (@Nullable final IReadableResource aRes,
                                      @Nonnull final IPSElement aSourceElement,
                                      @Nonnull final String sMessage)
  {
    return StringHelper.getImplodedNonEmpty (" - ",
                                             aRes == null ? null : aRes.getPath (),
                                             CGStringHelper.getClassLocalName (aSourceElement),
                                             aSourceElement instanceof IPSHasID && ((IPSHasID) aSourceElement).hasID () ? "ID " +
                                                                                                                          ((IPSHasID) aSourceElement).getID ()
                                                                                                                       : null,
                                             sMessage);
  }

  @Override
  protected void handle (@Nullable final IReadableResource aRes,
                         @Nonnull final EErrorLevel eErrorLevel,
                         @Nonnull final IPSElement aSourceElement,
                         @Nonnull final String sMessage,
                         @Nullable final Throwable t)
  {
    LogUtils.log (s_aLogger, eErrorLevel, getLogMessage (aRes, aSourceElement, sMessage), t);
  }
}
