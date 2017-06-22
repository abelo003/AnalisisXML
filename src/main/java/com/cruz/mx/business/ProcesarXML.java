/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cruz.mx.business;

import com.cruz.mx.beans.Concepto;
import com.cruz.mx.beans.Retencion;
import com.cruz.mx.beans.Traslado;
import com.cruz.mx.views.Principal;
import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import org.apache.log4j.Logger;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

/**
 *
 * @author acruzb
 */
public class ProcesarXML {
    
    private static final Logger LOGGER = Logger.getLogger(ProcesarXML.class);
    private Principal principal;
    public String cadenaXML;

    public ProcesarXML(String cadenaXML, Principal principal) {
        this.cadenaXML = cadenaXML;
        this.principal = principal;
    }
    
    public void procesarXML(){
        try {
            DocumentBuilderFactory domFactory = DocumentBuilderFactory.newInstance();
            domFactory.setNamespaceAware(true);
            DocumentBuilder builder = domFactory.newDocumentBuilder();
            Document doc = builder.parse(new InputSource(new StringReader(cadenaXML)));

            Node comprobante = doc.getElementsByTagName(Constantes.COMPROBANTE).item(0);
            agregarGenerales(comprobante);
            NodeList conceptos = ((Element) comprobante).getElementsByTagName(Constantes.CONCEPTOS).item(0).getChildNodes();
            agregarConceptos(conceptos);
            Node impuestos = doc.getElementsByTagName(Constantes.IMPUESTOS).item(0);
            agregarImpuestos(impuestos);
        } catch (IOException | ParserConfigurationException | SAXException ex) {}
    }
    
    private void agregarGenerales(Node node){
        if (node.getNodeType() == Node.ELEMENT_NODE) {
            Element elemento = (Element) node;
            principal.setLabelVersion(elemento.getAttribute("version"));
            principal.setTextMoneda(elemento.getAttribute("Moneda"));
            principal.setTextTipoCambio(elemento.getAttribute("TipoCambio"));
            principal.setTextMontoFolioFiscalOrig(elemento.getAttribute("MontoFolioFiscalOrig"));
            principal.setTextFechaFolioFiscalOrig(elemento.getAttribute("FechaFolioFiscalOrig"));
            principal.setTextSerieFolioFiscalOrig(elemento.getAttribute("SerieFolioFiscalOrig"));
            principal.setTextFolioFiscalOrig(elemento.getAttribute("FolioFiscalOrig"));
            principal.setTextLugarExpedicion(elemento.getAttribute("LugarExpedicion"));
            principal.setTextmetodoDePago(elemento.getAttribute("metodoDePago"));
            principal.setTexttipoDeComprobante(elemento.getAttribute("tipoDeComprobante"));
            principal.setTexttotal(elemento.getAttribute("total"));
            principal.setTextdescuento(elemento.getAttribute("descuento"));
            principal.setTextsubTotal(elemento.getAttribute("subTotal"));
            principal.setTextcondicionesDePago(elemento.getAttribute("condicionesDePago"));
            principal.setTextformaDePago(elemento.getAttribute("formaDePago"));
            principal.setTextfecha(elemento.getAttribute("fecha"));
            principal.setTextfolio(elemento.getAttribute("folio"));
            principal.setTextserie(elemento.getAttribute("serie"));
        }
    }
    
    public void agregarConceptos(NodeList lista){
        LOGGER.info("Se inicia el agregado de conceptos.");
        ArrayList<Concepto> listCon = new ArrayList<>();
        for (int i = 0; i < lista.getLength(); i++) {
            Node valor = (Node) lista.item(i);
            if(valor.getNodeType() == Node.ELEMENT_NODE){
                double importe = Double.parseDouble(((Element) valor).getAttribute("importe"));
                double valorUnitario = Double.parseDouble(((Element) valor).getAttribute("valorUnitario"));
                String descripcion = ((Element) valor).getAttribute("descripcion");
                String noIdentificacion = ((Element) valor).getAttribute("noIdentificacion");
                String unidad = ((Element) valor).getAttribute("unidad");
                double cantidad = Double.parseDouble(((Element) valor).getAttribute("cantidad"));
                listCon.add(new Concepto(importe, valorUnitario, descripcion, noIdentificacion, unidad, cantidad));
            }
        }
        principal.addDataConceptos(listCon);
    }
    
    public void agregarImpuestos(Node impuestos){
        //Zona de impuestos
        String totalTra = ((Element) impuestos).getAttribute("totalImpuestosTrasladados");
        String totalRet = ((Element) impuestos).getAttribute("totalImpuestosRetenidos");
        double totalTraslados = Double.parseDouble("".equals(totalTra) ? "0": totalTra);
        double totalRetenciones = Double.parseDouble("".equals(totalRet) ? "0": totalRet);
        LOGGER.info(totalRetenciones);
        LOGGER.info(totalTraslados);
        NodeList traslados = ((Element) impuestos).getElementsByTagName(Constantes.TRASLADOS).item(0).getChildNodes();
        agregarTraslados(traslados);
        NodeList retenciones = ((Element) impuestos).getElementsByTagName(Constantes.RETENCIONES).item(0).getChildNodes();
        agregarRetenciones(retenciones);
    }
    
    public void agregarRetenciones(NodeList lista){
        LOGGER.info("Se inicia el agregado de las retenciones.");
        ArrayList<Retencion> listRet = new ArrayList<>();
        for (int i = 0; i < lista.getLength(); i++) {
            Node valor = (Node) lista.item(i);
            if(valor.getNodeType() == Node.ELEMENT_NODE){
                double importe = Double.parseDouble(((Element) valor).getAttribute("importe"));
                String impuesto = ((Element) valor).getAttribute("impuesto");
                listRet.add(new Retencion(importe, impuesto));
            }
        }
        principal.addDataRetenciones(listRet);
    }
    
    public void agregarTraslados(NodeList lista){
        LOGGER.info("Se inicia el agregado de las retenciones.");
        ArrayList<Traslado> listTra = new ArrayList<>();
        for (int i = 0; i < lista.getLength(); i++) {
            Node valor = (Node) lista.item(i);
            if(valor.getNodeType() == Node.ELEMENT_NODE){
                double importe = Double.parseDouble(((Element) valor).getAttribute("importe"));
                double tasa = Double.parseDouble(((Element) valor).getAttribute("tasa"));
                String impuesto = ((Element) valor).getAttribute("impuesto");
                listTra.add(new Traslado(importe, tasa, impuesto));
            }
        }
        principal.addDataTraslados(listTra);
    }
    
    
}
