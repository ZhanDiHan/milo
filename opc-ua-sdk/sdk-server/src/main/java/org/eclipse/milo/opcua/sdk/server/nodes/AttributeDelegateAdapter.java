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

import org.eclipse.milo.opcua.sdk.server.api.nodes.DataTypeNode;
import org.eclipse.milo.opcua.sdk.server.api.nodes.MethodNode;
import org.eclipse.milo.opcua.sdk.server.api.nodes.Node;
import org.eclipse.milo.opcua.sdk.server.api.nodes.ObjectNode;
import org.eclipse.milo.opcua.sdk.server.api.nodes.ObjectTypeNode;
import org.eclipse.milo.opcua.sdk.server.api.nodes.ReferenceTypeNode;
import org.eclipse.milo.opcua.sdk.server.api.nodes.VariableNode;
import org.eclipse.milo.opcua.sdk.server.api.nodes.VariableTypeNode;
import org.eclipse.milo.opcua.sdk.server.api.nodes.ViewNode;
import org.eclipse.milo.opcua.stack.core.AttributeId;
import org.eclipse.milo.opcua.stack.core.StatusCodes;
import org.eclipse.milo.opcua.stack.core.UaException;
import org.eclipse.milo.opcua.stack.core.types.builtin.DataValue;
import org.eclipse.milo.opcua.stack.core.types.builtin.Variant;

public class AttributeDelegateAdapter implements AttributeDelegate {

    @Override
    public DataValue getAttribute(UaNode node, AttributeId attributeId) throws UaException {
        return null;
    }

    @Override
    public void setAttribute(UaNode node, AttributeId attributeId, DataValue value) throws UaException {
        switch (node.getNodeClass()) {
            case DataType:
                setDataTypeAttribute((DataTypeNode) node, attributeId, value);
                break;
            case Method:
                setMethodAttribute((MethodNode) node, attributeId, value);
                break;
            case Object:
                setObjectAttribute((ObjectNode) node, attributeId, value);
                break;
            case ObjectType:
                setObjectTypeAttribute((ObjectTypeNode) node, attributeId, value);
                break;
            case ReferenceType:
                setReferenceTypeAttribute((ReferenceTypeNode) node, attributeId, value);
                break;
            case Variable:
                setVariableAttribute((VariableNode) node, attributeId, value);
                break;
            case VariableType:
                setVariableTypeAttribute((VariableTypeNode) node, attributeId, value);
                break;
            case View:
                setViewAttribute((ViewNode) node, attributeId, value);
                break;

            default:
                throw new UaException(StatusCodes.Bad_NodeClassInvalid);
        }
    }

    protected void setBaseAttribute(Node node, AttributeId attributeId, DataValue value) throws UaException {
        switch (attributeId) {
            case NodeId:
                node.setNodeId(extract(value));
                break;
            case NodeClass:
                node.setNodeClass(extract(value));
                break;
            case BrowseName:
                node.setBrowseName(extract(value));
                break;
            case DisplayName:
                node.setDisplayName(extract(value));
                break;
            case Description:
                node.setDescription(extract(value));
                break;
            case WriteMask:
                node.setWriteMask(extract(value));
                break;
            case UserWriteMask:
                node.setUserWriteMask(extract(value));
                break;

            default:
                throw new UaException(StatusCodes.Bad_AttributeIdInvalid);
        }
    }

    protected void setDataTypeAttribute(
        DataTypeNode node,
        AttributeId attributeId,
        DataValue value) throws UaException {

        switch (attributeId) {
            case IsAbstract:
                node.setIsAbstract(extract(value));
                break;

            default:
                setBaseAttribute(node, attributeId, value);
        }
    }

    protected void setMethodAttribute(
        MethodNode node,
        AttributeId attributeId,
        DataValue value) throws UaException {

        switch (attributeId) {
            case Executable:
                node.setExecutable(extract(value));
                break;
            case UserExecutable:
                node.setUserExecutable(extract(value));
                break;

            default:
                setBaseAttribute(node, attributeId, value);
        }
    }

    protected void setObjectAttribute(
        ObjectNode node,
        AttributeId attributeId,
        DataValue value) throws UaException {

        switch (attributeId) {
            case EventNotifier:
                node.setEventNotifier(extract(value));
                break;

            default:
                setBaseAttribute(node, attributeId, value);
        }
    }

    protected void setObjectTypeAttribute(
        ObjectTypeNode node,
        AttributeId attributeId,
        DataValue value) throws UaException {

        switch (attributeId) {
            case IsAbstract:
                node.setIsAbstract(extract(value));
                break;

            default:
                setBaseAttribute(node, attributeId, value);
        }
    }

    protected void setReferenceTypeAttribute(
        ReferenceTypeNode node,
        AttributeId attributeId,
        DataValue value) throws UaException {

        switch (attributeId) {
            case IsAbstract:
                node.setIsAbstract(extract(value));
                break;
            case Symmetric:
                node.setSymmetric(extract(value));
                break;
            case InverseName:
                node.setInverseName(extract(value));
                break;

            default:
                setBaseAttribute(node, attributeId, value);
        }
    }

    protected void setVariableAttribute(
        VariableNode node,
        AttributeId attributeId,
        DataValue value) throws UaException {

        switch (attributeId) {
            case Value:
                node.setValue(value);
                break;
            case DataType:
                node.setDataType(extract(value));
                break;
            case ValueRank:
                node.setValueRank(extract(value));
                break;
            case ArrayDimensions:
                node.setArrayDimensions(Optional.ofNullable(extract(value)));
                break;
            case AccessLevel:
                node.setAccessLevel(extract(value));
                break;
            case UserAccessLevel:
                node.setUserAccessLevel(extract(value));
                break;
            case MinimumSamplingInterval:
                node.setMinimumSamplingInterval(extract(value));
                break;
            case Historizing:
                node.setHistorizing(extract(value));
                break;

            default:
                setBaseAttribute(node, attributeId, value);
        }
    }

    protected void setVariableTypeAttribute(
        VariableTypeNode node,
        AttributeId attributeId,
        DataValue value) throws UaException {

        switch (attributeId) {
            case Value:
                node.setValue(Optional.of(value));
                break;
            case DataType:
                node.setDataType(extract(value));
                break;
            case ValueRank:
                node.setValueRank(extract(value));
                break;
            case ArrayDimensions:
                node.setArrayDimensions(extract(value));
                break;
            case IsAbstract:
                node.setIsAbstract(extract(value));
                break;

            default:
                setBaseAttribute(node, attributeId, value);
        }
    }

    protected void setViewAttribute(
        ViewNode node,
        AttributeId attributeId,
        DataValue value) throws UaException {

        switch (attributeId) {
            case ContainsNoLoops:
                node.setContainsNoLoops(extract(value));
                break;
            case EventNotifier:
                node.setEventNotifier(extract(value));
                break;

            default:
                setBaseAttribute(node, attributeId, value);
        }
    }

    @SuppressWarnings("unchecked")
    private static <T> T extract(DataValue value) throws UaException {
        Variant variant = value.getValue();
        if (variant == null) return null;

        Object o = variant.getValue();
        if (o == null) return null;

        try {
            return (T) o;
        } catch (ClassCastException e) {
            throw new UaException(StatusCodes.Bad_TypeMismatch);
        }
    }

}