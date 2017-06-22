/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cruz.mx;

import java.io.StringReader;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

/**
 *
 * @author acruzb
 */
public class Test {
    
    public static void main(String[] args) {
        String xml = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n" +
        "<cfdi:Comprobante xmlns:cfdi=\"http://www.sat.gob.mx/cfd/3\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" Moneda=\"MXN\" TipoCambio=\"1.0000\" MontoFolioFiscalOrig=\"2646.54\" FechaFolioFiscalOrig=\"2017-06-21T08:42:54\" SerieFolioFiscalOrig=\"DAS\" FolioFiscalOrig=\"53556\" LugarExpedicion=\"Ciudad de México\" metodoDePago=\"99\" tipoDeComprobante=\"ingreso\" total=\"3069.99\" descuento=\"0.00\" subTotal=\"2646.54\" condicionesDePago=\"Contado Unidad\" certificado=\"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\" noCertificado=\"00001000000403735412\" formaDePago=\"PAGO EN UNA SOLA EXHIBICION\" sello=\"TZIndz1N3fjmtv5z6KuT71UbQSHKoCNdSRfHaTYBI3ndyMAlHrYhyXldDCoRI8w0LsXtYGhWqL6HPj3MDr2pdhKwZV2X5tShD1S41Y7GCYO09V1/4KorCVbSyz64NCoYQhEesmL5ROdTZHZ79BFWQS++jVcNG28z1/db2PXhUqWzfUsStXJujG8hE3Hv0x7YkcL5169juvexyIh1lYK1G4FZ2EKejthm6p3U0ou2V8trpSVakPnM0rT5Mrs1ub8d2jtG/vlxGD3kHKH67hvPfBxf+4w+/9ihSB07o8iPQ5iolz5+JiI5ihbexDQN9lGZjK3WcVyjHL1/gL4srdvkEQ==\" fecha=\"2017-06-21T08:42:54\" folio=\"53556\" serie=\"DAS\" xsi:schemaLocation=\"http://www.sat.gob.mx/cfd/3 http://www.sat.gob.mx/sitio_internet/cfd/3/cfdv32.xsd\" version=\"3.2\">\n" +
        "   <cfdi:Emisor nombre=\"AUTOMOTRIZ SANJE, S.A. DE C.V.\" rfc=\"ASA850513KC2\">\n" +
        "      <cfdi:DomicilioFiscal codigoPostal=\"01090\" pais=\"México\" estado=\"Ciudad de México\" municipio=\"ÁLVARO OBREGÓN\" referencia=\"(0155) 5683-7833\" colonia=\"LA OTRA BANDA\" noExterior=\"630\" calle=\"AV. SAN JERÓNIMO\" />\n" +
        "      <cfdi:RegimenFiscal Regimen=\"Régimen General de las personas morales, Título II LISR\" />\n" +
        "   </cfdi:Emisor>\n" +
        "   <cfdi:Receptor nombre=\"ADMINISTRACION VENTURA SA DE CV\" rfc=\"AVE1402128N3\">\n" +
        "      <cfdi:Domicilio codigoPostal=\"10200\" pais=\"Mexico\" estado=\"Ciudad de M‚xico\" colonia=\"SAN JERONIMO LIDICE\" calle=\"AVENIDA CONTRERAS 246 302\" localidad=\"LA MAGDALENA CONTRERAS\" />\n" +
        "   </cfdi:Receptor>\n" +
        "   <cfdi:Conceptos>\n" +
        "      <cfdi:Concepto importe=\"234.52\" valorUnitario=\"58.63\" descripcion=\"ACEITE AC MT\" noIdentificacion=\"TAMBOF422BDD\" unidad=\"pza\" cantidad=\"4.00\" />\n" +
        "      <cfdi:Concepto importe=\"56.98\" valorUnitario=\"56.98\" descripcion=\"FILTRO DE ACEITE\" noIdentificacion=\"15208F4301\" unidad=\"pza\" cantidad=\"1.00\" />\n" +
        "      <cfdi:Concepto importe=\"12.89\" valorUnitario=\"12.89\" descripcion=\"ARANDELA CAR\" noIdentificacion=\"11026JA00A\" unidad=\"pza\" cantidad=\"1.00\" />\n" +
        "      <cfdi:Concepto importe=\"110.32\" valorUnitario=\"27.58\" descripcion=\"BUJIA MOTOR\" noIdentificacion=\"2240155Y15\" unidad=\"pza\" cantidad=\"4.00\" />\n" +
        "      <cfdi:Concepto importe=\"102.00\" valorUnitario=\"102.00\" descripcion=\"FILTRO AIRE\" noIdentificacion=\"16546F41X1\" unidad=\"pza\" cantidad=\"1.00\" />\n" +
        "      <cfdi:Concepto importe=\"80.00\" valorUnitario=\"10.00\" descripcion=\"PLOMOS\" noIdentificacion=\"0000R00001\" unidad=\"pza\" cantidad=\"8.00\" />\n" +
        "      <cfdi:Concepto importe=\"68.57\" valorUnitario=\"68.57\" descripcion=\"LIMPIADOR DE CAMARA\" noIdentificacion=\"NIMEXU03Q2DD\" unidad=\"pza\" cantidad=\"1.00\" />\n" +
        "      <cfdi:Concepto importe=\"400.00\" valorUnitario=\"100.00\" descripcion=\"ALINEACION Y BALANCEO\" noIdentificacion=\"MAN01\" unidad=\"Servicio\" cantidad=\"4.00\" />\n" +
        "      <cfdi:Concepto importe=\"114.00\" valorUnitario=\"100.00\" descripcion=\"CARGA DE NITROGENO\" noIdentificacion=\"MAN01\" unidad=\"Servicio\" cantidad=\"1.14\" />\n" +
        "      <cfdi:Concepto importe=\"390.80\" valorUnitario=\"217.11\" descripcion=\"SERVICIO DE 20,000 KM TP\" noIdentificacion=\"MAN01\" unidad=\"Servicio\" cantidad=\"1.80\" />\n" +
        "      <cfdi:Concepto importe=\"217.11\" valorUnitario=\"217.11\" descripcion=\"LUBRICACION\" noIdentificacion=\"MAN01\" unidad=\"Servicio\" cantidad=\"1.00\" />\n" +
        "      <cfdi:Concepto importe=\"499.35\" valorUnitario=\"217.11\" descripcion=\"LIMPIEZA CAMARA DE ACELERACION\" noIdentificacion=\"MAN01\" unidad=\"Servicio\" cantidad=\"2.30\" />\n" +
        "      <cfdi:Concepto importe=\"360.00\" valorUnitario=\"360.00\" descripcion=\"LAVADO DE MOTOR EN SECO\" noIdentificacion=\"TOTM\" unidad=\"Servicio\" cantidad=\"1.00\" />\n" +
        "   </cfdi:Conceptos>\n" +
        "   <cfdi:Impuestos totalImpuestosTrasladados=\"423.45\">\n" +
        "      <cfdi:Traslados>\n" +
        "         <cfdi:Traslado importe=\"423.45\" tasa=\"16.00\" impuesto=\"IVA\" />\n" +
        "      </cfdi:Traslados>\n" +
        "   </cfdi:Impuestos>\n" +
        "   <cfdi:Complemento>\n" +
        "      <tfd:TimbreFiscalDigital xmlns:tfd=\"http://www.sat.gob.mx/TimbreFiscalDigital\" xsi:schemaLocation=\"http://www.sat.gob.mx/TimbreFiscalDigital http://www.sat.gob.mx/TimbreFiscalDigital/TimbreFiscalDigital.xsd\" version=\"1.0\" selloSAT=\"IUnlOfUACNeyezAsNUtdocUQICnSmrtWoU8RUVhI3dBY5KpXqusfokwot6evdmxXN4olosrpHBq7U16gFgVJihuESMl9QrrBCchK8sad01TEKlMd7mQmceRmmj2AInjTB0V4TOCbMIhqyZ20Pq6KBD/9jEBpqFzByWYxpPK6Z4kByQfTe07H/R/A8AOBDRB+wkWVBB+ojzYkgp933Z7WhgV9NfiT7v4svAGZKgpQIrE81aOTuFLqC523O6Jq1kX68HGIZ5Vx3mbhOOiC1jwnxc3bta8oLBNPYLmAc09b2v8q/GC1Qj9zAN8r2c6Z0YdyIKbRj8YYJPt+2rbDsFPHvg==\" selloCFD=\"TZIndz1N3fjmtv5z6KuT71UbQSHKoCNdSRfHaTYBI3ndyMAlHrYhyXldDCoRI8w0LsXtYGhWqL6HPj3MDr2pdhKwZV2X5tShD1S41Y7GCYO09V1/4KorCVbSyz64NCoYQhEesmL5ROdTZHZ79BFWQS++jVcNG28z1/db2PXhUqWzfUsStXJujG8hE3Hv0x7YkcL5169juvexyIh1lYK1G4FZ2EKejthm6p3U0ou2V8trpSVakPnM0rT5Mrs1ub8d2jtG/vlxGD3kHKH67hvPfBxf+4w+/9ihSB07o8iPQ5iolz5+JiI5ihbexDQN9lGZjK3WcVyjHL1/gL4srdvkEQ==\" noCertificadoSAT=\"00001000000404512308\" UUID=\"9FB49A08-06E2-43F7-A1BF-3D0F52BC8FDA\" FechaTimbrado=\"2017-06-21T08:43:09\" />\n" +
        "   </cfdi:Complemento>\n" +
        "</cfdi:Comprobante>";
        try {
            DocumentBuilderFactory domFactory = DocumentBuilderFactory.newInstance();
            domFactory.setNamespaceAware(true);
            DocumentBuilder builder = domFactory.newDocumentBuilder();
            Document doc = builder.parse(new InputSource(new StringReader(xml)));

//            Node comprobante = doc.getElementsByTagName("cfdi:Comprobante").item(0);
//            if (comprobante.getNodeType() == Node.ELEMENT_NODE) {
//                System.out.println("Yesy");
//                Element elementoComprobante = (Element) comprobante;
//                System.out.println(elementoComprobante.getAttribute("version"));
//                System.out.println(elementoComprobante.getAttribute("subTotal"));
//                NodeList lista = elementoComprobante.getElementsByTagName("cfdi:Conceptos").item(0).getChildNodes();
//                for (int i = 0; i < lista.getLength(); i++) {
//                    Node valor = (Node) lista.item(i);
//                    if(valor.getNodeType() == Node.ELEMENT_NODE){
//                        System.out.println(((Element) valor).getAttribute("importe"));
//                    }
//                }
//            }

            Node comprobante = doc.getElementsByTagName("cfdi:Complemento").item(0);
            if (comprobante.getNodeType() == Node.ELEMENT_NODE) {
                Element elementoComprobante = (Element) comprobante;
//                System.out.println(elementoComprobante.getAttribute("totalImpuestosTrasladados"));
                Node timbreFiscalD = elementoComprobante.getElementsByTagName("tfd:TimbreFiscalDigital").item(0);
                System.out.println(((Element) timbreFiscalD).getAttribute("version"));
//                NodeList lista = elementoComprobante.getElementsByTagName("cfdi:Traslados").item(0).getChildNodes();
//                for (int i = 0; i < lista.getLength(); i++) {
//                    Node valor = (Node) lista.item(i);
//                    if(valor.getNodeType() == Node.ELEMENT_NODE){
//                        System.out.print(((Element) valor).getAttribute("importe") + " ");
//                        System.out.println(((Element) valor).getAttribute("impuesto"));
//                    }
//                }
            }
        } catch (Exception ex) {
            System.out.println(ex);
        }
    }
    
}
