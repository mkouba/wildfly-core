/*
 * JBoss, Home of Professional Open Source.
 * Copyright 2015, Red Hat, Inc., and individual contributors
 * as indicated by the @author tags. See the copyright.txt file in the
 * distribution for a full listing of individual contributors.
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
package org.jboss.as.cli.handlers.trycatch;

import org.jboss.as.cli.CommandContext;
import org.jboss.as.cli.CommandLineException;
import org.jboss.as.cli.handlers.CommandHandlerWithHelp;

/**
 *
 * @author Alexey Loubyansky
 */
public class CatchHandler extends CommandHandlerWithHelp {

    public CatchHandler() {
        super("catch", true);
    }

    @Override
    public boolean isAvailable(CommandContext ctx) {
        final TryCatchFinallyControlFlow flow = TryCatchFinallyControlFlow.get(ctx);
        return flow != null && flow.isInTry();
    }

    /* (non-Javadoc)
     * @see org.jboss.as.cli.handlers.CommandHandlerWithHelp#doHandle(org.jboss.as.cli.CommandContext)
     */
    @Override
    protected void doHandle(CommandContext ctx) throws CommandLineException {
        final TryCatchFinallyControlFlow flow = TryCatchFinallyControlFlow.get(ctx);
        if(flow == null) {
            throw new CommandLineException("catch is available only in try-catch-finally control flow");
        }
        if(flow.isInTry()) {
            flow.moveToCatch();
        } else {
            throw new CommandLineException("catch may appear only once after try and before finally");
        }
    }
}
