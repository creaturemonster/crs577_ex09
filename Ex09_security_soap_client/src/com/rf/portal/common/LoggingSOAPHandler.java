package com.rf.portal.common;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.xml.namespace.QName;
import javax.xml.soap.SOAPMessage;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.ws.BindingProvider;
import javax.xml.ws.handler.Handler;
import javax.xml.ws.handler.MessageContext;
import javax.xml.ws.handler.soap.SOAPHandler;
import javax.xml.ws.handler.soap.SOAPMessageContext;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Logs SOAP messages using SLF4J. You can configure logging by modifying logback.xml.
 * 
 * Add LoggingSOAPHandler to a service proxy's binding handler chain as follows:
 * <pre>
 *  List<Handler> handlerChain = ((BindingProvider) servicePort).getBinding().getHandlerChain();
 *    if (handlerChain == null){
 *        handlerChain = new ArrayList<Handler>();
 *    }
 *    handlerChain.add(new LoggingSOAPHandler());
 *    ((BindingProvider) servicePort).getBinding().setHandlerChain(handlerChain);
 * </pre>
 * OR using the convenience method of this class:
 * <pre>
 * LoggingSOAPHandler.addToHandlerChain(servicePort);
 * </pre>
 * 
 * @author v.lakshmanan
 *
 */
public class LoggingSOAPHandler implements SOAPHandler<SOAPMessageContext>{
    private static final Logger logger = LoggerFactory.getLogger(LoggingSOAPHandler.class);

    private void handle(SOAPMessage message, String type){
        try {
            // prettify the output before writing
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            Transformer transformer = TransformerFactory.newInstance().newTransformer();
            transformer.setOutputProperty(OutputKeys.INDENT, "yes");
            transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "4");
            transformer.transform(new DOMSource(message.getSOAPPart()), new StreamResult(outputStream));
            
            logger.debug("\n" + type + ":\n" + outputStream.toString());
        } 
        catch (Exception e) {
            logger.error("Problem logging SOAP message", e);
        }
    }
    
    @Override
    public Set<QName> getHeaders() {
        return null;
    }
    
    @Override
    public void close(MessageContext arg0) {    
    }
    
    @Override
    public boolean handleFault(SOAPMessageContext ctx) {
        handle(ctx.getMessage(), "FAULT");
        return true;
    }

    @Override
    public boolean handleMessage(SOAPMessageContext ctx) {
        printHttpHeaders(ctx);
        boolean isOutbound = (boolean) ctx.get(SOAPMessageContext.MESSAGE_OUTBOUND_PROPERTY);
        handle(ctx.getMessage(), (isOutbound ? "REQUEST" : "RESPONSE") );
        return true;
    }

    private void printHttpHeaders(SOAPMessageContext context) {
        @SuppressWarnings("unchecked")
        Map<String, List<String>> headers = (Map<String, List<String>>) context
                .get(MessageContext.HTTP_REQUEST_HEADERS);
        String headerString = "";
        if (headers != null) {
            for (String keyString : headers.keySet()) {
                headerString += keyString + ": " + headers.get(keyString) + "\n";
            }
            logger.debug(headerString);
        }
    }

    /**
     * Convenience method to add logging to a port
     */
    @SuppressWarnings("rawtypes")
    public static void addToHandlerChain(Object proxy){
        if (! (proxy instanceof BindingProvider)) {
            throw new IllegalArgumentException("Argument must be a JAX-WS service proxy");
        }
        BindingProvider port = (BindingProvider) proxy;
        List<Handler> handlerChain = port.getBinding().getHandlerChain();
        if (handlerChain == null){
            handlerChain = new ArrayList<Handler>();
        }
        handlerChain.add(new LoggingSOAPHandler());
        port.getBinding().setHandlerChain(handlerChain);
    }
}
