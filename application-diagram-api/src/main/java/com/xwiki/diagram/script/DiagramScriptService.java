/*
 * See the NOTICE file distributed with this work for additional
 * information regarding copyright ownership.
 *
 * This is free software; you can redistribute it and/or modify it
 * under the terms of the GNU Lesser General Public License as
 * published by the Free Software Foundation; either version 2.1 of
 * the License, or (at your option) any later version.
 *
 * This software is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this software; if not, write to the Free
 * Software Foundation, Inc., 51 Franklin St, Fifth Floor, Boston, MA
 * 02110-1301 USA, or see the FSF site: http://www.fsf.org.
 */
package com.xwiki.diagram.script;

import javax.inject.Inject;
import javax.inject.Named;
import javax.inject.Singleton;

import org.slf4j.Logger;
import org.xwiki.component.annotation.Component;
import org.xwiki.script.service.ScriptService;
import org.xwiki.script.service.ScriptServiceManager;
import org.xwiki.stability.Unstable;

import com.xwiki.diagram.internal.DiagramImporter;

/**
 * Script services for the Diagram application.
 * 
 * @version $Id$
 * @since 1.14
 */
@Component
@Named(DiagramScriptService.ROLEHINT)
@Singleton
@Unstable
public class DiagramScriptService implements ScriptService
{
    /**
     * The role hint of this component.
     */
    public static final String ROLEHINT = "diagram";

    @Inject
    private ScriptServiceManager scriptServiceManager;

    @Inject
    private DiagramImporter diagramImporter;

    @Inject
    private Logger logger;

    /**
     * @param <S> the type of the {@link ScriptService}
     * @param serviceName the name of the sub {@link ScriptService}
     * @return the {@link ScriptService} or null of none could be found
     */
    @SuppressWarnings("unchecked")
    public <S extends ScriptService> S get(String serviceName)
    {
        return (S) this.scriptServiceManager.get(DiagramScriptService.ROLEHINT + '.' + serviceName);
    }

    /**
     * Attempts to convert the given diagram from a third party format to the draw.io format.
     * 
     * @param diagram the diagram content, using a third-party format
     * @param fileName the diagram file name, used to detect the diagram type
     * @return the diagram XML in draw.io format, or {@code null} if the diagram format is not recognized or unsupported
     * @since 1.14
     */
    @Unstable
    public String importDiagram(String diagram, String fileName)
    {
        try {
            return this.diagramImporter.importDiagram(diagram, fileName);
        } catch (Exception e) {
            logger.error("Cannot import diagram.", e);
            return null;
        }
    }
}
