/*
 * Copyright (c) 2016 Kevin Herron
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * and Eclipse Distribution License v1.0 which accompany this distribution.
 *
 * The Eclipse Public License is available at
 *   http://www.eclipse.org/legal/epl-v10.html
 * and the Eclipse Distribution License is available at
 *   http://www.eclipse.org/org/documents/edl-v10.html.
 */

package org.eclipse.milo.opcua.sdk.server.nodes;

import java.util.Optional;
import javax.annotation.Nullable;

import org.eclipse.milo.opcua.sdk.core.NumericRange;
import org.eclipse.milo.opcua.sdk.server.NamespaceManager;
import org.eclipse.milo.opcua.sdk.server.api.nodes.Node;
import org.eclipse.milo.opcua.sdk.server.util.AttributeReader;
import org.eclipse.milo.opcua.sdk.server.util.AttributeWriter;
import org.eclipse.milo.opcua.stack.core.AttributeId;
import org.eclipse.milo.opcua.stack.core.StatusCodes;
import org.eclipse.milo.opcua.stack.core.UaException;
import org.eclipse.milo.opcua.stack.core.types.builtin.DataValue;
import org.eclipse.milo.opcua.stack.core.types.builtin.unsigned.UInteger;
import org.eclipse.milo.opcua.stack.core.types.enumerated.TimestampsToReturn;

public interface ServerNode extends Node {

    /**
     * Read the specified attribute.
     * <p>
     * If the attribute is not specified on this node, a value with status {@link StatusCodes#Bad_AttributeIdInvalid}
     * will be returned.
     *
     * @param attribute the id of the attribute to read.
     * @return the value of the specified attribute.
     */
    default DataValue readAttribute(AttributeContext context, UInteger attribute) {
        return readAttribute(context, attribute.intValue());
    }

    /**
     * Read the specified attribute.
     * <p>
     * If the attribute is not specified on this node, a value with status {@link StatusCodes#Bad_AttributeIdInvalid}
     * will be returned.
     *
     * @param attribute the id of the attribute to read.
     * @return the value of the specified attribute.
     */
    default DataValue readAttribute(AttributeContext context, int attribute) {
        return readAttribute(context, attribute, null, null);
    }

    /**
     * Read the specified attribute.
     * <p>
     * If the attribute is not specified on this node, a value with status {@link StatusCodes#Bad_AttributeIdInvalid}
     * will be returned.
     *
     * @param attribute  the id of the attribute to read.
     * @param timestamps the {@link TimestampsToReturn}.
     * @param indexRange the index range to read. Must be a parseable by {@link NumericRange}.
     * @return the value of the specified attribute.
     */
    default DataValue readAttribute(
        AttributeContext context,
        int attribute,
        @Nullable TimestampsToReturn timestamps,
        @Nullable String indexRange) {

        return AttributeId.from(attribute)
            .map(attributeId -> readAttribute(context, attributeId, timestamps, indexRange))
            .orElse(new DataValue(StatusCodes.Bad_AttributeIdInvalid));
    }

    /**
     * Read the specified attribute.
     * <p>
     * If the attribute is not specified on this node, a value with status {@link StatusCodes#Bad_AttributeIdInvalid}
     * will be returned.
     *
     * @param attributeId the id of the attribute to read.
     * @return the value of the specified attribute.
     */
    default DataValue readAttribute(
        AttributeContext context,
        AttributeId attributeId) {

        return readAttribute(context, attributeId, null, null);
    }

    /**
     * Read the specified attribute, applying {@code timestamps} and {@code indexRange} if specified.
     * <p>
     * If the attribute is not specified on this node, a value with status {@link StatusCodes#Bad_AttributeIdInvalid}
     * will be returned.
     *
     * @param attributeId the id of the attribute to read.
     * @param timestamps  the {@link TimestampsToReturn}.
     * @param indexRange  the index range to read. Must be a parseable by {@link NumericRange}.
     * @return the value of the specified attribute.
     */
    default DataValue readAttribute(
        AttributeContext context,
        AttributeId attributeId,
        @Nullable TimestampsToReturn timestamps,
        @Nullable String indexRange) {

        return AttributeReader.readAttribute(context, this, attributeId, timestamps, indexRange);
    }

    /**
     * Write to the specified attribute.
     *
     * @param context    the {@link NamespaceManager}.
     * @param attribute  the id of the attribute to write.
     * @param value      the {@link DataValue} write.
     * @param indexRange the index range to write. Must be a parseable by {@link NumericRange}.
     * @throws UaException if writing to the attribute fails.
     */
    default void writeAttribute(
        AttributeContext context,
        UInteger attribute,
        DataValue value,
        String indexRange) throws UaException {

        writeAttribute(context, attribute.intValue(), value, indexRange);
    }

    /**
     * Write to the specified attribute.
     *
     * @param context    the {@link NamespaceManager}.
     * @param attribute  the id of the attribute to write.
     * @param value      the {@link DataValue} write.
     * @param indexRange the index range to write. Must be a parseable by {@link NumericRange}.
     * @throws UaException if writing to the attribute fails.
     */
    default void writeAttribute(
        AttributeContext context,
        int attribute,
        DataValue value,
        String indexRange) throws UaException {

        Optional<AttributeId> attributeId = AttributeId.from(attribute);

        if (attributeId.isPresent()) {
            writeAttribute(context, attributeId.get(), value, indexRange);
        } else {
            throw new UaException(StatusCodes.Bad_AttributeIdInvalid);
        }
    }

    /**
     * Write to the specified attribute.
     *
     * @param context     the {@link NamespaceManager}.
     * @param attributeId the {@link AttributeId} of the attribute to write.
     * @param value       the {@link DataValue} write.
     * @param indexRange  the index range to write. Must be a parseable by {@link NumericRange}.
     * @throws UaException if writing to the attribute fails.
     */
    default void writeAttribute(
        AttributeContext context,
        AttributeId attributeId,
        DataValue value,
        String indexRange) throws UaException {

        AttributeWriter.writeAttribute(context, this, attributeId, value, indexRange);
    }

    DataValue getAttribute(AttributeContext context, AttributeId attributeId);

    void setAttribute(AttributeContext context, AttributeId attributeId, DataValue value) throws UaException;

}
