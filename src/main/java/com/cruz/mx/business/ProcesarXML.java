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
import javax.swing.JOptionPane;
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
    private final Principal principal;
    public String cadenaXML;

    public ProcesarXML(String cadenaXML, Principal principal) {
        this.cadenaXML = cadenaXML.trim().replaceAll("\\p{C}", "");
        this.principal = principal;
    }
    
    public void procesarXML(){
        try {
            DocumentBuilderFactory domFactory = DocumentBuilderFactory.newInstance();
            domFactory.setNamespaceAware(true);
            DocumentBuilder builder = domFactory.newDocumentBuilder();
            Document doc = builder.parse(new InputSource(new StringReader(cadenaXML.trim())));

            Node comprobante = doc.getElementsByTagName(Constantes.COMPROBANTE).item(0);
            agregarGenerales(comprobante);
            Node nodeConceptos = ((Element) comprobante).getElementsByTagName(Constantes.CONCEPTOS).item(0);
            agregarConceptos(nodeConceptos);
            Node impuestos = doc.getElementsByTagName(Constantes.IMPUESTOS).item(0);
            agregarImpuestos(impuestos);
            Node complemento = doc.getElementsByTagName(Constantes.COMPLEMENTO).item(0);
            agregarComplementos(complemento);
            Node receptor = doc.getElementsByTagName(Constantes.RECEPTOR).item(0);
            agregarDatosReceptor(receptor);
            Node emisor = doc.getElementsByTagName(Constantes.EMISOR).item(0);
            agregarDatosEmisor(emisor);
        } catch (Exception ex) {
            LOGGER.info("Falló la lectura de las propiedades del xml.", ex);
            JOptionPane.showMessageDialog(principal, "No se pudo realizar la lectura del archivo.", "Formato incorrecto", JOptionPane.WARNING_MESSAGE);
        }
    }
    
    private void agregarGenerales(Node node){
        LOGGER.info("Se inicia el agregado de los datos generales.");
        if (null != node && node.getNodeType() == Node.ELEMENT_NODE) {
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
    
    private void agregarConceptos(Node node){
        if(null != node && node.getNodeType() == Node.ELEMENT_NODE){
            NodeList lista = node.getChildNodes();
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
    }
    
    private void agregarImpuestos(Node impuestos){
        //Zona de impuestos
        if (null != impuestos && impuestos.getNodeType() == Node.ELEMENT_NODE) {
            LOGGER.info("Se inicia el agregado de los impuestos.");
            String totalTra = ((Element) impuestos).getAttribute("totalImpuestosTrasladados");
            String totalRet = ((Element) impuestos).getAttribute("totalImpuestosRetenidos");
            principal.setTextTotalRetenciones(totalRet);
            principal.setTextTotalTraslados(totalTra);
            Node nodeTrastlados = ((Element) impuestos).getElementsByTagName(Constantes.TRASLADOS).item(0);
            if (null != nodeTrastlados && nodeTrastlados.getNodeType() == Node.ELEMENT_NODE) {
                agregarTraslados(nodeTrastlados.getChildNodes());
            }
            Node nodeRetenciones = ((Element) impuestos).getElementsByTagName(Constantes.RETENCIONES).item(0);
            if (null != nodeRetenciones && nodeRetenciones.getNodeType() == Node.ELEMENT_NODE) {
                agregarRetenciones(nodeRetenciones.getChildNodes());
            }
        }
    }
    
    private void agregarRetenciones(NodeList lista){
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
    
    private void agregarTraslados(NodeList lista){
        LOGGER.info("Se inicia el agregado de las traslados.");
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
    
    private void agregarComplementos(Node node){
        LOGGER.info("Se agrega la información de complemento.");
        if (null != node && node.getNodeType() == Node.ELEMENT_NODE) {
            Element elemento = (Element) node;
            Element timbreFiscalD = (Element)elemento.getElementsByTagName("tfd:TimbreFiscalDigital").item(0);
            if (null != timbreFiscalD && timbreFiscalD.getNodeType() == Node.ELEMENT_NODE) {
                String uuid = timbreFiscalD.getAttribute("UUID");
                String fechaTimbrado = timbreFiscalD.getAttribute("FechaTimbrado");
                String selloCFD = timbreFiscalD.getAttribute("selloCFD");
                String certificadoSAT = timbreFiscalD.getAttribute("noCertificadoSAT");
                String selloSAT = timbreFiscalD.getAttribute("selloSAT");
                principal.setTextSelloCFD(selloCFD);
                principal.setTextSelloSAT(selloSAT);
                principal.setTextCertSAT(certificadoSAT);
                principal.setTextUuid(uuid);
                principal.setTextFechaTimbrado(fechaTimbrado);
            }
        }
    }
    
    private void agregarDatosReceptor(Node node){
        if (null != node && node.getNodeType() == Node.ELEMENT_NODE) {
            Element elementoComprobante = (Element) node;
            String nombre = elementoComprobante.getAttribute("nombre");
            String rfc = elementoComprobante.getAttribute("rfc");
            principal.setTextNombreReceptor(nombre);
            principal.setTextRFCReceptor(rfc);
            Node domicilio = elementoComprobante.getElementsByTagName("cfdi:Domicilio").item(0);
            if (null != domicilio && domicilio.getNodeType() == Node.ELEMENT_NODE) {
                Element domi = ((Element) domicilio);
                String calle = domi.getAttribute("calle");
                String numExt = domi.getAttribute("noExterior");
                String colonia = domi.getAttribute("colonia");
                String localidad = domi.getAttribute("localidad");
                String municipio = domi.getAttribute("municipio");
                String cp = domi.getAttribute("codigoPostal");
                String estado = domi.getAttribute("estado");
                String pais = domi.getAttribute("pais");
                principal.setTextCalleR(calle);
                principal.setTextNumExtR(numExt);
                principal.setTextColoniaR(colonia);
                principal.setTextLocalidadR(localidad);
                principal.setTextMunicipioR(municipio);
                principal.setTextCPR(cp);
                principal.setTextEstadoR(estado);
                principal.setTextPaisR(pais);
            }
        }
    }
    
    private void agregarDatosEmisor(Node node){
        if (node.getNodeType() == Node.ELEMENT_NODE) {
            Element elementoComprobante = (Element) node;
            String nombre = elementoComprobante.getAttribute("nombre");
            String rfc = elementoComprobante.getAttribute("rfc");
            principal.setTextNombreEmisor(nombre);
            principal.setTextRFCEmisor(rfc);
            Node domicilio = elementoComprobante.getElementsByTagName("cfdi:DomicilioFiscal").item(0);
            if (null != domicilio && domicilio.getNodeType() == Node.ELEMENT_NODE) {
                Element domi = ((Element) domicilio);
                String calle = domi.getAttribute("calle");
                String numExt = domi.getAttribute("noExterior");
                String colonia = domi.getAttribute("colonia");
                String localidad = domi.getAttribute("localidad");
                String municipio = domi.getAttribute("municipio");
                String cp = domi.getAttribute("codigoPostal");
                String estado = domi.getAttribute("estado");
                String pais = domi.getAttribute("pais");
                principal.setTextCalleF(calle);
                principal.setTextNumExtF(numExt);
                principal.setTextColoniaF(colonia);
                principal.setTextLocalidadF(localidad);
                principal.setTextMunicipioF(municipio);
                principal.setTextCPF(cp);
                principal.setTextEstadoF(estado);
                principal.setTextPaisF(pais);
            }
            Node regimenFiscal = elementoComprobante.getElementsByTagName("cfdi:RegimenFiscal").item(0);
            if (null != regimenFiscal && regimenFiscal.getNodeType() == Node.ELEMENT_NODE) {
                Element reg = ((Element) regimenFiscal);
                String regimen = reg.getAttribute("Regimen");
                principal.setTextRegimenEmisor(regimen);
            }
            Node expedicion = elementoComprobante.getElementsByTagName("cfdi:ExpedidoEn").item(0);
            if (null != expedicion && expedicion.getNodeType() == Node.ELEMENT_NODE) {
                Element domi = ((Element) expedicion);
                String calle = domi.getAttribute("calle");
                String numExt = domi.getAttribute("noExterior");
                String colonia = domi.getAttribute("colonia");
                String localidad = domi.getAttribute("localidad");
                String municipio = domi.getAttribute("municipio");
                String cp = domi.getAttribute("codigoPostal");
                String estado = domi.getAttribute("estado");
                String pais = domi.getAttribute("pais");
                principal.setTextCalleEx(calle);
                principal.setTextNumExtEx(numExt);
                principal.setTextColoniaEx(colonia);
                principal.setTextLocalidadEx(localidad);
                principal.setTextMunicipioEx(municipio);
                principal.setTextCPEx(cp);
                principal.setTextEstadoEx(estado);
                principal.setTextPaisEx(pais);
            }
        }
    }
}
