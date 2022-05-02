package com.abaco.ageneral;

import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.abaco.entidad.EMensaje;
import com.abaco.entidad.EUsuario;
import com.abaco.ageneral.EOperacionSolicitud;
import com.abaco.negocio.util.UConstante.UArea;
import com.abaco.negocio.util.UConstante.UIndicadorDigitalizacion;
import com.abaco.negocio.util.UConstante.UIndicadorTipoReasignar;
import com.abaco.negocio.util.UFuncionesGenerales;
import com.abaco.negocio.util.UManejadorLog;
import com.abaco.persistencia.acceso.InstanciaAcceso;
import com.abaco.persistencia.interfaces.IConexion;

public class DAOOperacion extends InstanciaAcceso{
	private static final String SP_ABACOINLEGAL_INS_OPERACION_DOCUMENTOGARANTIA = "{ CALL GESTIONDOC.SP_ABACOINLEGAL_INS_OPERACION_DOCUMENTOGARANTIA("+parametrosSP(14)+") }";
	private static final String SP_ABACOINLEGAL_UPD_OPERACION_DOCUMENTOGARANTIA = "{ CALL GESTIONDOC.SP_ABACOINLEGAL_UPD_OPERACION_DOCUMENTOGARANTIA("+parametrosSP(15)+") }";
	private static final String SP_ABACOINLEGAL_UPD_OPERACION_DOCUMENTOFIRMAGARANTIA = "{ CALL GESTIONDOC.SP_ABACOINLEGAL_UPD_OPERACION_DOCUMENTOFIRMAGARANTIA("+parametrosSP(8)+") }";
	private static final String SP_ABACOINLEGAL_SEL_OPERACION_DOCUMENTOGARANTIA="{ CALL GESTIONDOC.SP_ABACOINLEGAL_SEL_OPERACION_DOCUMENTOGARANTIA("+parametrosSP(2)+") }";
	
	//EVALUACION LEVANTAMIENTO GARANTIA
	private static final String SP_ABACOINLEGAL_INS_OPERACION_LEVANTAMIENTOGARANTIA = "{ CALL GESTIONDOC.SP_ABACOINLEGAL_INS_OPERACION_LEVANTAMIENTOGARANTIA("+parametrosSP(11)+") }";
	private static final String SP_ABACOINLEGAL_UPD_OPERACION_LEVANTAMIENTOGARANTIA = "{ CALL GESTIONDOC.SP_ABACOINLEGAL_UPD_OPERACION_LEVANTAMIENTOGARANTIA("+parametrosSP(12)+") }";
	private static final String SP_ABACOINLEGAL_SEL_OPERACION_LEVANTAMIENTOGARANTIA_MENSAJE = "{ CALL GESTIONDOC.SP_ABACOINLEGAL_SEL_OPERACION_LEVANTAMIENTOGARANTIA_MENSAJE("+parametrosSP(3)+") }";
	private static final String SP_ABACOINLEGAL_SEL_OPERACION_LEVANTAMIENTOGARANTIA_LEGAL = "{ CALL GESTIONDOC.SP_ABACOINLEGAL_SEL_OPERACION_LEVANTAMIENTOGARANTIA_LEGAL("+parametrosSP(3)+") }";
	private static final String SP_ABACOINLEGAL_SEL_OPERACION_LEVANTAMIENTOGARANTIA_OTROS = "{ CALL GESTIONDOC.SP_ABACOINLEGAL_SEL_OPERACION_LEVANTAMIENTOGARANTIA_OTROS("+parametrosSP(3)+") }";
	
	//EVALUACION CLIENTE
	private static final String SP_ABACOINLEGAL_INS_UPD_OPERACION_CLIENTE = "{ CALL GESTIONDOC.SP_ABACOINLEGAL_INS_UPD_OPERACION_CLIENTE("+parametrosSP(10)+") }";
	private static final String SP_ABACOINLEGAL_INS_OPERACION_CLIENTE_DOCUMENTO = "{ CALL GESTIONDOC.SP_ABACOINLEGAL_INS_OPERACION_CLIENTE_DOCUMENTO("+parametrosSP(12)+") }";
	private static final String SP_ABACOINLEGAL_UPD_OPERACION_CLIENTE="{ CALL GESTIONDOC.SP_ABACOINLEGAL_UPD_OPERACION_CLIENTE("+parametrosSP(60)+") }";
	private static final String SP_ABACOINLEGAL_SEL_OPERACION_CLIENTE="{ CALL GESTIONDOC.SP_ABACOINLEGAL_SEL_OPERACION_CLIENTE("+parametrosSP(2)+") }";
	private static final String SP_ABACOINLEGAL_SEL_OPERACION_CLIENTE_DOCUMENTO="{ CALL GESTIONDOC.SP_ABACOINLEGAL_SEL_OPERACION_CLIENTE_DOCUMENTO("+parametrosSP(3)+") }";
	private static final String SP_ABACOINLEGAL_BUS_OPERACION_CLIENTE="{ CALL GESTIONDOC.SP_ABACOINLEGAL_BUS_OPERACION_CLIENTE("+parametrosSP(3)+") }";
	
	//EVALUACION SOLICITUD CREDITO
	private static final String SP_ABACOINLEGAL_INS_OPERACION_SOLICITUDCREDITO = "{ CALL GESTIONDOC.SP_ABACOINLEGAL_INS_OPERACION_SOLICITUDCREDITO("+parametrosSP(2)+") }";
	private static final String SP_ABACOINLEGAL_INS_OPERACION_SOLICITUDCREDITO_DOCUMENTO = "{ CALL GESTIONDOC.SP_ABACOINLEGAL_INS_OPERACION_SOLICITUDCREDITO_DOCUMENTO("+parametrosSP(13)+") }";
	private static final String SP_ABACOINLEGAL_INS_OPERACION_SOLICITUDCREDITO_DOCUMENTOREVISION = "{ CALL GESTIONDOC.SP_ABACOINLEGAL_INS_OPERACION_SOLICITUDCREDITO_DOCUMENTOREVISION("+parametrosSP(13)+") }";
	private static final String SP_ABACOINLEGAL_INS_OPERACION_SOLICITUDCREDITO_DOCUMENTOPORASIGNAR = "{ CALL GESTIONDOC.SP_ABACOINLEGAL_INS_OPERACION_SOLICITUDCREDITO_DOCUMENTOPORASIGNAR("+parametrosSP(12)+") }";
	private static final String SP_ABACOINLEGAL_INS_OPERACION_SOLICITUDCREDITO_DOCUMENTOASIGNADO = "{ CALL GESTIONDOC.SP_ABACOINLEGAL_INS_OPERACION_SOLICITUDCREDITO_DOCUMENTOASIGNADO("+parametrosSP(12)+") }";
	private static final String SP_ABACOINLEGAL_UPD_OPERACION_SOLICITUDCREDITO="{ CALL GESTIONDOC.SP_ABACOINLEGAL_UPD_OPERACION_SOLICITUDCREDITO("+parametrosSP(60)+") }";
	private static final String SP_ABACOINLEGAL_INS_UPD_INFORMELEGALADICIONAL="{ CALL GESTIONDOC.SP_ABACOINLEGAL_INS_UPD_INFORMELEGALADICIONAL("+parametrosSP(50)+") }";
	private static final String SP_ABACOINLEGAL_DEL_OPERACION_SOLICITUDCREDITO_DOCUMENTOREVISION = "{ CALL GESTIONDOC.SP_ABACOINLEGAL_DEL_OPERACION_SOLICITUDCREDITO_DOCUMENTOREVISION("+parametrosSP(5)+") }";
	private static final String SP_ABACOINLEGAL_DEL_OPERACION_SOLICITUDCREDITO_DOCUMENTOPORASIGNAR = "{ CALL GESTIONDOC.SP_ABACOINLEGAL_DEL_OPERACION_SOLICITUDCREDITO_DOCUMENTOPORASIGNAR("+parametrosSP(5)+") }";
	private static final String SP_ABACOINLEGAL_SEL_OPERACION_SOLICITUDCREDITO="{ CALL GESTIONDOC.SP_ABACOINLEGAL_SEL_OPERACION_SOLICITUDCREDITO("+parametrosSP(6)+") }";
	private static final String SP_ABACOINLEGAL_SEL_OPERACION_SOLICITUDCREDITO_OTROS="{ CALL GESTIONDOC.SP_ABACOINLEGAL_SEL_OPERACION_SOLICITUDCREDITO_OTROS("+parametrosSP(6)+") }";
	private static final String SP_ABACOINLEGAL_SEL_OPERACION_SOLICITUDCREDITO_PORAUTORIZAR="{ CALL GESTIONDOC.SP_ABACOINLEGAL_SEL_OPERACION_SOLICITUDCREDITO_PORAUTORIZAR("+parametrosSP(6)+") }";
	private static final String SP_ABACOINLEGAL_SEL_OPERACION_SOLICITUDCREDITO_MENSAJE = "{ CALL GESTIONDOC.SP_ABACOINLEGAL_SEL_OPERACION_SOLICITUDCREDITO_MENSAJE("+parametrosSP(3)+") }";
	private static final String SP_ABACOINLEGAL_SEL_OPERACION_SOLICITUDCREDITO_DOCUMENTO="{ CALL GESTIONDOC.SP_ABACOINLEGAL_SEL_OPERACION_SOLICITUDCREDITO_DOCUMENTO("+parametrosSP(3)+") }";
	private static final String SP_ABACOINLEGAL_SEL_OPERACION_SOLICITUDCREDITO_DOCUMENTOREVISION="{ CALL GESTIONDOC.SP_ABACOINLEGAL_SEL_OPERACION_SOLICITUDCREDITO_DOCUMENTOREVISION("+parametrosSP(3)+") }";
	private static final String SP_ABACOINLEGAL_SEL_OPERACION_SOLICITUDCREDITO_DOCUMENTOPORASIGNAR="{ CALL GESTIONDOC.SP_ABACOINLEGAL_SEL_OPERACION_SOLICITUDCREDITO_DOCUMENTOPORASIGNAR("+parametrosSP(3)+") }";
	private static final String SP_ABACOINLEGAL_SEL_OPERACION_SOLICITUDCREDITO_DOCUMENTOASIGNADO="{ CALL GESTIONDOC.SP_ABACOINLEGAL_SEL_OPERACION_SOLICITUDCREDITO_DOCUMENTOASIGNADO("+parametrosSP(3)+") }";
	private static final String SP_ABACOINLEGAL_SEL_OPERACION_SOLICITUDCREDITO_DOCUMENTOREQUERIDO ="{ CALL GESTIONDOC.SP_ABACOINLEGAL_SEL_OPERACION_SOLICITUDCREDITO_DOCUMENTOREQUERIDO() }";
	private static final String SP_ABACOINLEGAL_BUS_OPERACION_SOLICITUDCREDITO="{ CALL GESTIONDOC.SP_ABACOINLEGAL_BUS_OPERACION_SOLICITUDCREDITO("+parametrosSP(3)+") }";
	
	
	private static String parametrosSP(int numeroDeParametros) {
        StringBuilder cadena = new StringBuilder();

        for (int i = 1; i <= numeroDeParametros; i++) {
            cadena.append("?,");
        }

        int longitudCadena = cadena.length();
        StringBuilder removeUltimoCaracter = new StringBuilder(cadena);
        StringBuilder cadenaFinal = removeUltimoCaracter.deleteCharAt(longitudCadena - 1);
        return cadenaFinal.toString();
    }
	
	public DAOOperacion(IConexion objConexion) {
		super(objConexion);
	}
	
	public EMensaje agregarDocumentoGarantia(EGarantia eOGarantia, EDocumentoCarga eDocumentoCarga) {
		EMensaje mensaje = new EMensaje();
		List<Object> lstParametrosEntrada;
		SimpleDateFormat formato = new SimpleDateFormat("HH:mm:ss");
		eOGarantia.setFechaRegistro(new Date());
		try {
			lstParametrosEntrada = new ArrayList<Object>();
			lstParametrosEntrada.add(eOGarantia.getCodigoGarantia());
			lstParametrosEntrada.add(eDocumentoCarga.getCodigoLaserFiche());
			lstParametrosEntrada.add(eDocumentoCarga.getNombre());
			lstParametrosEntrada.add(eDocumentoCarga.getNombreLaserFiche());
			lstParametrosEntrada.add(eDocumentoCarga.getNombreOriginal());
			lstParametrosEntrada.add(eOGarantia.getAreaEmisora());
			lstParametrosEntrada.add(eOGarantia.getAreaReceptora());
			lstParametrosEntrada.add(eOGarantia.getDescripcionDocumento());
			lstParametrosEntrada.add(eOGarantia.getFirmaDocumento());
			lstParametrosEntrada.add(eOGarantia.getEstadoDocumento());
			lstParametrosEntrada.add(eOGarantia.getObservacionDocumento());
			lstParametrosEntrada.add(eOGarantia.getUsuarioRegistro().getNombreUsuarioSIAF());
			
			mensaje = objConexion.ejecutaTransaccion(SP_ABACOINLEGAL_INS_OPERACION_DOCUMENTOGARANTIA, lstParametrosEntrada);
		} catch(Exception objEx) {
			mensaje.setIdMensaje(-1);
			mensaje.setDescMensaje(objEx.getMessage());
			UManejadorLog.error("Acceso: Problemas al insertar datos del Documento en DB2.", objEx);
		}
		return mensaje;
	}
	
	public EMensaje modificarDocumentoGarantia(EGarantia eOGarantia, EOperacionDocumento eOperacionDocumento) {
		EMensaje mensaje = new EMensaje();
		List<Object> lstParametrosEntrada;
		try {
			lstParametrosEntrada = new ArrayList<Object>();
			lstParametrosEntrada.add(eOperacionDocumento.getCodigoSolicitud());
			lstParametrosEntrada.add(eOGarantia.getCodigoGarantia());
			lstParametrosEntrada.add(eOperacionDocumento.getCodigoDocumentoLaserFiche());
			lstParametrosEntrada.add(eOperacionDocumento.getNombreDocumento());
			lstParametrosEntrada.add(eOperacionDocumento.getNombreDocumentoLaserFiche());
			lstParametrosEntrada.add(eOperacionDocumento.getNombreDocumentoOriginal());
			lstParametrosEntrada.add(eOGarantia.getAreaEmisora());
			lstParametrosEntrada.add(eOGarantia.getAreaReceptora());
			lstParametrosEntrada.add(eOGarantia.getDescripcionDocumento());
			lstParametrosEntrada.add(eOGarantia.getFirmaDocumento());
			lstParametrosEntrada.add(eOGarantia.getEstadoDocumento());
			lstParametrosEntrada.add(eOGarantia.getObservacionDocumento());
			lstParametrosEntrada.add(eOGarantia.getUsuarioRegistro().getNombreUsuarioSIAF());
			mensaje = objConexion.ejecutaTransaccion(SP_ABACOINLEGAL_UPD_OPERACION_DOCUMENTOGARANTIA, lstParametrosEntrada);
		} catch(Exception objEx) {
			mensaje.setIdMensaje(-1);
			mensaje.setDescMensaje(objEx.getMessage());
			UManejadorLog.error("Acceso: Problemas al modificar.", objEx);
		}
		return mensaje;
	}
	
	public EMensaje modificarFirmaDocumentoGarantia(EOperacionDocumento eOperacionDocumento) {
		EMensaje mensaje = new EMensaje();
		List<Object> lstParametrosEntrada;
		try {
			lstParametrosEntrada = new ArrayList<Object>();
			lstParametrosEntrada.add(eOperacionDocumento.getCodigoSolicitud());
			lstParametrosEntrada.add(eOperacionDocumento.getCodigoGarantia());
			lstParametrosEntrada.add(eOperacionDocumento.getCodigoDocumentoLaserFiche());
			lstParametrosEntrada.add(eOperacionDocumento.getFirmaDocumento());
			lstParametrosEntrada.add(eOperacionDocumento.getEstadoDocumento());
			lstParametrosEntrada.add(eOperacionDocumento.getUsuarioRegistro().getNombreUsuarioSIAF());
			mensaje = objConexion.ejecutaTransaccion(SP_ABACOINLEGAL_UPD_OPERACION_DOCUMENTOFIRMAGARANTIA, lstParametrosEntrada);
		} catch(Exception objEx) {
			mensaje.setIdMensaje(-1);
			mensaje.setDescMensaje(objEx.getMessage());
			UManejadorLog.error("Acceso: Problemas al modificar.", objEx);
		}
		return mensaje;
	}
	
	public List<EOperacionDocumento> listarDocumentoGarantia(EGarantia eOGarantia,int indicador) {
		List<Object> lstParametrosEntrada;
		ResultSet oResultSet = null;
		EOperacionDocumento oEOperacionDocumento= null;
		List<EOperacionDocumento> lstOperacionDocumento = null;
		
		try {	
			lstParametrosEntrada = new ArrayList<Object>();
			lstParametrosEntrada.add(indicador);
			lstParametrosEntrada.add(eOGarantia.getCodigoGarantia());
			 
			oResultSet = objConexion.ejecutaConsulta(SP_ABACOINLEGAL_SEL_OPERACION_DOCUMENTOGARANTIA, lstParametrosEntrada, null);
			if (oResultSet != null) {
				lstOperacionDocumento = new ArrayList<EOperacionDocumento>();
				while (oResultSet.next()) {
					oEOperacionDocumento=new EOperacionDocumento();
					oEOperacionDocumento.setCodigoSolicitud(oResultSet.getInt("NROSOL"));
					oEOperacionDocumento.setCodigoGarantia(oResultSet.getInt("GARANT"));
					oEOperacionDocumento.setCodigoDocumento(oResultSet.getInt("CODDOC"));
					oEOperacionDocumento.setCodigoDocumentoLaserFiche(oResultSet.getString("CODDOCLSF"));
					oEOperacionDocumento.setNombreDocumento(oResultSet.getString("NOMDOC"));
					oEOperacionDocumento.setNombreDocumentoLaserFiche(oResultSet.getString("NOMDOCLSF"));
					oEOperacionDocumento.setNombreDocumentoOriginal(oResultSet.getString("NOMDOCORI"));
					oEOperacionDocumento.setAreaEmisora(oResultSet.getString("AREAEMI"));
					oEOperacionDocumento.setAreaReceptora(oResultSet.getString("AREAREC"));
					oEOperacionDocumento.setDescripcionDocumento(oResultSet.getString("DESCRI"));
					oEOperacionDocumento.setFirmaDocumento(oResultSet.getString("FIRMA"));
					oEOperacionDocumento.setEstadoDocumento(oResultSet.getInt("ESTADO"));
					oEOperacionDocumento.setDescripcionEstadoDocumento(oResultSet.getString("DESCEST"));
					oEOperacionDocumento.setObervacionDocumento(oResultSet.getString("OBSERV"));
					oEOperacionDocumento.setUsuarioRegist(oResultSet.getString("USUREG"));
					oEOperacionDocumento.setFechaRegistro(oResultSet.getDate("FECREG"));
					oEOperacionDocumento.setHoraRegistro(UFuncionesGenerales.convertirEnteroATime(oResultSet.getInt("HORREG")));
					oEOperacionDocumento.setUsuarioModif(oResultSet.getString("USUREG"));
					oEOperacionDocumento.setFechaModificacion(oResultSet.getDate("FECREG"));
					oEOperacionDocumento.setHoraModificacion(UFuncionesGenerales.convertirEnteroATime(oResultSet.getInt("HORREG")));
					
					lstOperacionDocumento.add(oEOperacionDocumento);
				}								
			}						
			
		} catch(Exception objEx) {
			UManejadorLog.error("Acceso: Problemas al obtener.", objEx);
		}
		return lstOperacionDocumento;
	}
	
	public EMensaje agregarEvaluacionLevantamientoGarantia(EOperacionLevantamientoGarantia eOperacionLevantamientoGarantia) {
		EMensaje mensaje = new EMensaje();
		List<Object> lstParametrosEntrada;
		SimpleDateFormat formato = new SimpleDateFormat("HH:mm:ss");
		
		try {			
			lstParametrosEntrada = new ArrayList<Object>();
			lstParametrosEntrada.add(eOperacionLevantamientoGarantia.getCodigoCliente());
			lstParametrosEntrada.add(eOperacionLevantamientoGarantia.getCodigoServicio());
			lstParametrosEntrada.add(eOperacionLevantamientoGarantia.getCodigoGarantia());
			lstParametrosEntrada.add(eOperacionLevantamientoGarantia.getCodigoMoneda());
			
			lstParametrosEntrada.add(eOperacionLevantamientoGarantia.getCodigoEstadoSolicitud());
			lstParametrosEntrada.add(eOperacionLevantamientoGarantia.getDescripcionMensaje());
			//lstParametrosEntrada.add(eEvaluacionSolicitudCreditoLegal.getUsuarioRegistro().getCodigoArea());
			lstParametrosEntrada.add(eOperacionLevantamientoGarantia.getUsuarioRegistro().getNombreUsuario());
			lstParametrosEntrada.add(eOperacionLevantamientoGarantia.getFechaRegistro());
			lstParametrosEntrada.add(formato.format(eOperacionLevantamientoGarantia.getFechaRegistro()));
			
			mensaje = objConexion.ejecutaTransaccion(SP_ABACOINLEGAL_INS_OPERACION_LEVANTAMIENTOGARANTIA, lstParametrosEntrada);
		} catch(Exception objEx) {
			mensaje.setIdMensaje(-1);
			mensaje.setDescMensaje(objEx.getMessage());
			UManejadorLog.error("Acceso: Problemas al agregar.", objEx);
		}
		return mensaje;
	}
	
	public EMensaje modificarEvaluacionLevantamientoGarantia(EOperacionLevantamientoGarantia eOperacionLevantamientoGarantia) {
		EMensaje mensaje = new EMensaje();
		List<Object> lstParametrosEntrada;
		SimpleDateFormat formato = new SimpleDateFormat("HH:mm:ss");
		
		try {			
			lstParametrosEntrada = new ArrayList<Object>();
			lstParametrosEntrada.add(eOperacionLevantamientoGarantia.getCodigoCliente());
			lstParametrosEntrada.add(eOperacionLevantamientoGarantia.getCodigoServicio());
			lstParametrosEntrada.add(eOperacionLevantamientoGarantia.getCodigoGarantia());
			lstParametrosEntrada.add(eOperacionLevantamientoGarantia.getCodigoMoneda());
			
			lstParametrosEntrada.add(eOperacionLevantamientoGarantia.getCodigoEstadoSolicitud());
			lstParametrosEntrada.add(eOperacionLevantamientoGarantia.getCodigoEstadoDocumento());
			lstParametrosEntrada.add(eOperacionLevantamientoGarantia.getDescripcionMensaje());
			//lstParametrosEntrada.add(eEvaluacionSolicitudCreditoLegal.getUsuarioRegistro().getCodigoArea());
			lstParametrosEntrada.add(eOperacionLevantamientoGarantia.getUsuarioRegistro().getNombreUsuario());
			lstParametrosEntrada.add(eOperacionLevantamientoGarantia.getFechaRegistro());
			lstParametrosEntrada.add(formato.format(eOperacionLevantamientoGarantia.getFechaRegistro()));
			
			mensaje = objConexion.ejecutaTransaccion(SP_ABACOINLEGAL_UPD_OPERACION_LEVANTAMIENTOGARANTIA, lstParametrosEntrada);
		} catch(Exception objEx) {
			mensaje.setIdMensaje(-1);
			mensaje.setDescMensaje(objEx.getMessage());
			UManejadorLog.error("Acceso: Problemas al agregar.", objEx);
		}
		return mensaje;
	}
	
	public List<EOperacionLevantamientoGarantiaMensaje> listarMensajeLevantamientoGarantia(int codigoServicio, long codigoGarantia, int codigoMoneda) {
		List<Object> lstParametrosEntrada;
		ResultSet oResultSet = null;
		EOperacionLevantamientoGarantiaMensaje oEOperacionLevantamientoGarantiaMensaje= null;
		List<EOperacionLevantamientoGarantiaMensaje> lstResultado = null;
		
		try {
			lstParametrosEntrada = new ArrayList<Object>();
			lstParametrosEntrada.add(codigoServicio);
			lstParametrosEntrada.add(codigoGarantia);
			lstParametrosEntrada.add(codigoMoneda);
			
			oResultSet = objConexion.ejecutaConsulta(SP_ABACOINLEGAL_SEL_OPERACION_LEVANTAMIENTOGARANTIA_MENSAJE, lstParametrosEntrada, null);
				
			if (oResultSet != null) {
				lstResultado = new ArrayList<EOperacionLevantamientoGarantiaMensaje>();
				while (oResultSet.next()) {
					oEOperacionLevantamientoGarantiaMensaje = new EOperacionLevantamientoGarantiaMensaje();
					oEOperacionLevantamientoGarantiaMensaje.setCodigoCliente(oResultSet.getInt("CODCLI"));
					oEOperacionLevantamientoGarantiaMensaje.setCodigoServicio(oResultSet.getInt("SERVIC"));
					oEOperacionLevantamientoGarantiaMensaje.setCodigoGarantia(oResultSet.getInt("GARANT"));
					oEOperacionLevantamientoGarantiaMensaje.setCodigoMoneda(oResultSet.getInt("MONEDA"));
					oEOperacionLevantamientoGarantiaMensaje.setCodigoMensaje(oResultSet.getInt("CODMSJ"));
					oEOperacionLevantamientoGarantiaMensaje.setDescripcionMensaje(oResultSet.getString("DESCMSJ"));
					oEOperacionLevantamientoGarantiaMensaje.setNombreUsuarioEnvio(oResultSet.getString("USUENV"));
					oEOperacionLevantamientoGarantiaMensaje.setFechaEnvio(oResultSet.getDate("FECENV"));
					oEOperacionLevantamientoGarantiaMensaje.setHoraEnvio(oResultSet.getString("HORENV"));
					
					lstResultado.add(oEOperacionLevantamientoGarantiaMensaje);
				}								
			}						
			
		} catch(Exception objEx) {
			UManejadorLog.error("Acceso: Problemas al obtener.", objEx);
		}
		return lstResultado;
	}
	
	public List<EGarantiaSolicitud> listarEvaluacionLevantamientoGarantia(int codigo, String descripcion, EUsuario eUsuario, int indicadorConsulta) {
		List<Object> lstParametrosEntrada;
		ResultSet oResultSet = null;
		EGarantiaSolicitud oEGarantia= null;
		List<EGarantiaSolicitud> lstGarantia = null;
		
		try {
			lstParametrosEntrada = new ArrayList<Object>();
			lstParametrosEntrada.add(codigo);
			lstParametrosEntrada.add(descripcion);
			lstParametrosEntrada.add(eUsuario.getNombreUsuario());
			
			if(indicadorConsulta == 1){
				oResultSet = objConexion.ejecutaConsulta(SP_ABACOINLEGAL_SEL_OPERACION_LEVANTAMIENTOGARANTIA_LEGAL, lstParametrosEntrada, null);
			}else if(indicadorConsulta == 2){
				oResultSet = objConexion.ejecutaConsulta(SP_ABACOINLEGAL_SEL_OPERACION_LEVANTAMIENTOGARANTIA_OTROS, lstParametrosEntrada, null);
			}
			if (oResultSet != null) {
				lstGarantia = new ArrayList<EGarantiaSolicitud>();
				while (oResultSet.next()) {
					oEGarantia = new EGarantiaSolicitud();
					oEGarantia.setCodigoCliente(oResultSet.getInt("CODCLI"));
					oEGarantia.setNombreLargo(oResultSet.getString("NOMBCL"));
					//oEGarantia.setCodigoServicioGarantia(oResultSet.getInt("SERVIC"));
					oEGarantia.setCodigoGarantia(oResultSet.getInt("GARANT"));
					oEGarantia.setMontoGarantia(oResultSet.getDouble("MTOGAR"));
					oEGarantia.setDescripcionTipoGarantiaReal(oResultSet.getString("DESGAR"));
					//oEGarantia.setDescripcionCreditoRelacionado(oResultSet.getString("DESCOPE"));
					oEGarantia.setCodigoEstadoLevantamiento(oResultSet.getInt("CODESTSOL"));
					oEGarantia.setDescripcionEstadoLevantamiento(oResultSet.getString("DESCESTSOL"));
					oEGarantia.setCodigoEstadoDocumento(oResultSet.getInt("CODESTDOC"));
					oEGarantia.setDescripcionEstadoDocumento(oResultSet.getString("DESCESTDOC"));
					oEGarantia.setAbreviacionMonedaGarantia(UFuncionesGenerales.revisaCadena(oResultSet.getString("ABRVMON")));
					oEGarantia.setIndicadorRevisar(oResultSet.getInt("INDREV"));
					
					lstGarantia.add(oEGarantia);
				}								
			}						
			
		} catch(Exception objEx) {
			UManejadorLog.error("Acceso: Problemas al obtener.", objEx);
		}
		return lstGarantia;
	}
	
	public EMensaje agregarModificarEvaluacionCliente(EOperacionCliente eOperacionCliente) {
		EMensaje mensaje = new EMensaje();
		List<Object> lstParametrosEntrada;
		SimpleDateFormat formato = new SimpleDateFormat("HH:mm:ss");
		
		try {			
			lstParametrosEntrada = new ArrayList<Object>();
			lstParametrosEntrada.add(eOperacionCliente.getCodigoTipoCliente());
			lstParametrosEntrada.add(eOperacionCliente.getCodigoCliente());
			lstParametrosEntrada.add(eOperacionCliente.getCodigoTipoPersona());
			lstParametrosEntrada.add(eOperacionCliente.getNombreLargo());
			lstParametrosEntrada.add(eOperacionCliente.getNombreCorto());
			//lstParametrosEntrada.add(eEvaluacionSolicitudCreditoLegal.getUsuarioRegistro().getCodigoArea());
			lstParametrosEntrada.add(eOperacionCliente.getUsuarioRegistro().getNombreUsuario());
			lstParametrosEntrada.add(eOperacionCliente.getFechaRegistro());
			lstParametrosEntrada.add(formato.format(eOperacionCliente.getFechaRegistro()));
			
			mensaje = objConexion.ejecutaTransaccion(SP_ABACOINLEGAL_INS_UPD_OPERACION_CLIENTE, lstParametrosEntrada);
		} catch(Exception objEx) {
			mensaje.setIdMensaje(-1);
			mensaje.setDescMensaje(objEx.getMessage());
			UManejadorLog.error("Acceso: Problemas al agregar.", objEx);
		}
		return mensaje;
	}
	
	public EMensaje agregarModificarInformeLegalAdicional2(EOperacionCliente eOperacionCliente, EInformeLegalAdicional eEInformeLegalAdicional) {
		EMensaje mensaje = new EMensaje();
		List<Object> lstParametrosEntrada;
		SimpleDateFormat formato = new SimpleDateFormat("HH:mm:ss");
		
		try {		
			lstParametrosEntrada = new ArrayList<Object>();
			lstParametrosEntrada.add(eEInformeLegalAdicional.getNumeroSolicitud());
			lstParametrosEntrada.add(eEInformeLegalAdicional.getCodigoCliente());
			lstParametrosEntrada.add(eEInformeLegalAdicional.getCodigoTipoCliente());
			
			lstParametrosEntrada.add(eOperacionCliente.getCodigoTipoDocumento());
			lstParametrosEntrada.add(eOperacionCliente.getNumeroDocumento());
			lstParametrosEntrada.add(eOperacionCliente.getCodigoTipoPersonaJuridica());
			lstParametrosEntrada.add(eOperacionCliente.getMontoCapitalSocialRegistroPublicos());
			lstParametrosEntrada.add(eOperacionCliente.getMontoCapitalSocialActual());
			lstParametrosEntrada.add(eOperacionCliente.getCodigoFacultadOperar());
			lstParametrosEntrada.add(eOperacionCliente.getCodigoTipoSuscripcionPago());
			lstParametrosEntrada.add(eOperacionCliente.getNumeroSuscripcionPago());
			lstParametrosEntrada.add(eOperacionCliente.getNumeroAcciones());
			lstParametrosEntrada.add(eOperacionCliente.getIndicadorAvalarTercero());
			lstParametrosEntrada.add(eOperacionCliente.getIndicadorGrabarBien());
			lstParametrosEntrada.add(eOperacionCliente.getDescripcionAvalarTercero());
			lstParametrosEntrada.add(UFuncionesGenerales.obtieneDescripcionPorSaltoLinea(eOperacionCliente.getObservacionAvalarTercero(),0));
			lstParametrosEntrada.add(UFuncionesGenerales.obtieneDescripcionPorSaltoLinea(eOperacionCliente.getObservacionAvalarTercero(),1));
			lstParametrosEntrada.add(UFuncionesGenerales.obtieneDescripcionPorSaltoLinea(eOperacionCliente.getObservacionAvalarTercero(),2));
			lstParametrosEntrada.add(UFuncionesGenerales.obtieneDescripcionPorSaltoLinea(eOperacionCliente.getObservacionGrabarBien(),0));
			lstParametrosEntrada.add(UFuncionesGenerales.obtieneDescripcionPorSaltoLinea(eOperacionCliente.getObservacionGrabarBien(),1));
			lstParametrosEntrada.add(UFuncionesGenerales.obtieneDescripcionPorSaltoLinea(eOperacionCliente.getObservacionGrabarBien(),2));
			lstParametrosEntrada.add(UFuncionesGenerales.obtieneDescripcionPorSaltoLinea(eOperacionCliente.getObservacionConstanciaSocial(),0));
			lstParametrosEntrada.add(UFuncionesGenerales.obtieneDescripcionPorSaltoLinea(eOperacionCliente.getObservacionConstanciaSocial(),1));
			lstParametrosEntrada.add(UFuncionesGenerales.obtieneDescripcionPorSaltoLinea(eOperacionCliente.getObservacionConstanciaSocial(),2));
			lstParametrosEntrada.add(UFuncionesGenerales.obtieneDescripcionPorSaltoLinea(eOperacionCliente.getObservacionConstanciaSocial(),3));
			lstParametrosEntrada.add(UFuncionesGenerales.obtieneDescripcionPorSaltoLinea(eOperacionCliente.getObservacionConstanciaSocial(),4));
			
			lstParametrosEntrada.add(eEInformeLegalAdicional.getCodigoEstadoCivil());
			lstParametrosEntrada.add(eEInformeLegalAdicional.getNumeroPartida());
			lstParametrosEntrada.add(eEInformeLegalAdicional.getOficinaRegistral());
			lstParametrosEntrada.add(eEInformeLegalAdicional.getCodigoTipoDuracionPartida());
			lstParametrosEntrada.add(eEInformeLegalAdicional.getRegistroPartida());
			lstParametrosEntrada.add(eEInformeLegalAdicional.getDescripcionConstitucion());
			lstParametrosEntrada.add(eEInformeLegalAdicional.getFechaConstitucion());
			lstParametrosEntrada.add(eEInformeLegalAdicional.getCodigoNotario());
			lstParametrosEntrada.add(eEInformeLegalAdicional.getDescripcionNotario());
			lstParametrosEntrada.add(eEInformeLegalAdicional.getNumeroAcciones());
			lstParametrosEntrada.add(eEInformeLegalAdicional.getCodigoTipoValorSuscripcion());
			lstParametrosEntrada.add(eEInformeLegalAdicional.getMontoValorNominal());
			lstParametrosEntrada.add(eEInformeLegalAdicional.getEstatuto());
			lstParametrosEntrada.add(eEInformeLegalAdicional.getDescripcionPatrimonio());
			lstParametrosEntrada.add(eEInformeLegalAdicional.getCodigoTipoNumeracionEstatuto());
			lstParametrosEntrada.add(eEInformeLegalAdicional.getNumeracionEstatuto());
			lstParametrosEntrada.add(eEInformeLegalAdicional.getAsiento());
			lstParametrosEntrada.add(eEInformeLegalAdicional.getFechaPeriodoInicio());
			lstParametrosEntrada.add(eEInformeLegalAdicional.getFechaPeriodoVencimiento());
			lstParametrosEntrada.add(eEInformeLegalAdicional.getUsuarioRegistro().getNombreUsuario());
			lstParametrosEntrada.add(eEInformeLegalAdicional.getFechaRegistro());
			lstParametrosEntrada.add(formato.format(eEInformeLegalAdicional.getFechaRegistro()));
			
			mensaje = objConexion.ejecutaTransaccion(SP_ABACOINLEGAL_INS_UPD_INFORMELEGALADICIONAL, lstParametrosEntrada);
		} catch(Exception objEx) {
			mensaje.setIdMensaje(-1);
			mensaje.setDescMensaje(objEx.getMessage());
			UManejadorLog.error("Acceso: Problemas al modificar.", objEx);
		}
		return mensaje;
	}
	
	public EMensaje agregarEvaluacionClienteDocumento(EOperacionCliente eOperacionCliente, EDocumentoCarga eDocumentoCarga) {
		EMensaje mensaje = new EMensaje();
		List<Object> lstParametrosEntrada;
		SimpleDateFormat formato = new SimpleDateFormat("HH:mm:ss");
		
		try {
			lstParametrosEntrada = new ArrayList<Object>();
			lstParametrosEntrada.add(eOperacionCliente.getNumeroSolicitud());
			lstParametrosEntrada.add(eOperacionCliente.getCodigoTipoCliente());
			lstParametrosEntrada.add(eOperacionCliente.getCodigoCliente());
			lstParametrosEntrada.add(eDocumentoCarga.getCodigoLaserFiche());
			lstParametrosEntrada.add(eDocumentoCarga.getNombre());
			lstParametrosEntrada.add(eDocumentoCarga.getNombreLaserFiche());
			lstParametrosEntrada.add(eDocumentoCarga.getNombreOriginal());
			lstParametrosEntrada.add(eOperacionCliente.getUsuarioRegistro().getIdUsuario());
			lstParametrosEntrada.add(eOperacionCliente.getFechaRegistro());
			lstParametrosEntrada.add(formato.format(eOperacionCliente.getFechaRegistro()));
			
			mensaje = objConexion.ejecutaTransaccion(SP_ABACOINLEGAL_INS_OPERACION_CLIENTE_DOCUMENTO, lstParametrosEntrada);
		} catch(Exception objEx) {
			mensaje.setIdMensaje(-1);
			mensaje.setDescMensaje(objEx.getMessage());
			UManejadorLog.error("Acceso: Problemas al agregar.", objEx);
		}
		return mensaje;
	}
	
	public EMensaje modificarEvaluacionCliente(EOperacionCliente eOperacionCliente) {
		EMensaje mensaje = new EMensaje();
		List<Object> lstParametrosEntrada;
		SimpleDateFormat formato = new SimpleDateFormat("HH:mm:ss");
		
		try {			
			lstParametrosEntrada = new ArrayList<Object>();
			lstParametrosEntrada.add(eOperacionCliente.getNumeroSolicitud());
			lstParametrosEntrada.add(eOperacionCliente.getCodigoTipoCliente());
			lstParametrosEntrada.add(eOperacionCliente.getCodigoCliente());
			
			lstParametrosEntrada.add(eOperacionCliente.getCodigoEstadoSolicitud());
			lstParametrosEntrada.add(eOperacionCliente.getObservacionLegal());
			lstParametrosEntrada.add(eOperacionCliente.getObservacionNegocio());
			
			lstParametrosEntrada.add(eOperacionCliente.getCodigoTipoPersona());
			lstParametrosEntrada.add(eOperacionCliente.getCodigoTipoDocumento());
			lstParametrosEntrada.add(eOperacionCliente.getNumeroDocumento());
			lstParametrosEntrada.add(eOperacionCliente.getRuc());
			lstParametrosEntrada.add(eOperacionCliente.getApellidoPaterno());
			lstParametrosEntrada.add(eOperacionCliente.getApellidoMaterno());
			lstParametrosEntrada.add(eOperacionCliente.getNombre());
			lstParametrosEntrada.add(eOperacionCliente.getNombreLargo());
			lstParametrosEntrada.add(eOperacionCliente.getDireccionReal());
			lstParametrosEntrada.add(eOperacionCliente.getCodigoUbigeoReal());
			lstParametrosEntrada.add(eOperacionCliente.getDireccionContractual());
			lstParametrosEntrada.add(eOperacionCliente.getCodigoUbigeoContractual());
			
			lstParametrosEntrada.add(eOperacionCliente.getCodigoTipoDocumentoConyugue());
			lstParametrosEntrada.add(eOperacionCliente.getDocumentoConyugue());
			lstParametrosEntrada.add(eOperacionCliente.getApellidoPaternoConyugue());
			lstParametrosEntrada.add(eOperacionCliente.getApellidoMaternoConyugue());
			lstParametrosEntrada.add(eOperacionCliente.getNombreConyugue());
			lstParametrosEntrada.add(eOperacionCliente.getNombreLargoConyugue());
			
			lstParametrosEntrada.add(eOperacionCliente.getCodigoTipoPersonaJuridica());
			lstParametrosEntrada.add(eOperacionCliente.getMontoCapitalSocialRegistroPublicos());
			lstParametrosEntrada.add(eOperacionCliente.getMontoCapitalSocialActual());
			lstParametrosEntrada.add(eOperacionCliente.getCodigoFacultadOperar());
			lstParametrosEntrada.add(eOperacionCliente.getCodigoTipoSuscripcionPago());
			lstParametrosEntrada.add(eOperacionCliente.getNumeroSuscripcionPago());
			lstParametrosEntrada.add(eOperacionCliente.getNumeroAcciones());
			lstParametrosEntrada.add(eOperacionCliente.getIndicadorAvalarTercero());
			lstParametrosEntrada.add(eOperacionCliente.getIndicadorGrabarBien());
			lstParametrosEntrada.add(eOperacionCliente.getDescripcionAvalarTercero());
			
			lstParametrosEntrada.add(UFuncionesGenerales.obtieneDescripcionPorSaltoLinea(eOperacionCliente.getObservacionAvalarTercero(),0));
			lstParametrosEntrada.add(UFuncionesGenerales.obtieneDescripcionPorSaltoLinea(eOperacionCliente.getObservacionAvalarTercero(),1));
			lstParametrosEntrada.add(UFuncionesGenerales.obtieneDescripcionPorSaltoLinea(eOperacionCliente.getObservacionAvalarTercero(),2));
			
			lstParametrosEntrada.add(UFuncionesGenerales.obtieneDescripcionPorSaltoLinea(eOperacionCliente.getObservacionGrabarBien(),0));
			lstParametrosEntrada.add(UFuncionesGenerales.obtieneDescripcionPorSaltoLinea(eOperacionCliente.getObservacionGrabarBien(),1));
			lstParametrosEntrada.add(UFuncionesGenerales.obtieneDescripcionPorSaltoLinea(eOperacionCliente.getObservacionGrabarBien(),2));
			
			lstParametrosEntrada.add(UFuncionesGenerales.obtieneDescripcionPorSaltoLinea(eOperacionCliente.getObservacionConstanciaSocial(),0));
			lstParametrosEntrada.add(UFuncionesGenerales.obtieneDescripcionPorSaltoLinea(eOperacionCliente.getObservacionConstanciaSocial(),1));
			lstParametrosEntrada.add(UFuncionesGenerales.obtieneDescripcionPorSaltoLinea(eOperacionCliente.getObservacionConstanciaSocial(),2));
			lstParametrosEntrada.add(UFuncionesGenerales.obtieneDescripcionPorSaltoLinea(eOperacionCliente.getObservacionConstanciaSocial(),3));
			lstParametrosEntrada.add(UFuncionesGenerales.obtieneDescripcionPorSaltoLinea(eOperacionCliente.getObservacionConstanciaSocial(),4));
			
			lstParametrosEntrada.add(UFuncionesGenerales.obtieneDescripcionPorSaltoLinea(eOperacionCliente.getObservacionSolicitud(),0));
			lstParametrosEntrada.add(UFuncionesGenerales.obtieneDescripcionPorSaltoLinea(eOperacionCliente.getObservacionSolicitud(),1));
			lstParametrosEntrada.add(UFuncionesGenerales.obtieneDescripcionPorSaltoLinea(eOperacionCliente.getObservacionSolicitud(),2));
			lstParametrosEntrada.add(UFuncionesGenerales.obtieneDescripcionPorSaltoLinea(eOperacionCliente.getObservacionSolicitud(),3));
			lstParametrosEntrada.add(UFuncionesGenerales.obtieneDescripcionPorSaltoLinea(eOperacionCliente.getObservacionSolicitud(),4));
			lstParametrosEntrada.add(UFuncionesGenerales.obtieneDescripcionPorSaltoLinea(eOperacionCliente.getObservacionSolicitud(),5));
			lstParametrosEntrada.add(UFuncionesGenerales.obtieneDescripcionPorSaltoLinea(eOperacionCliente.getObservacionSolicitud(),6));
			lstParametrosEntrada.add(UFuncionesGenerales.obtieneDescripcionPorSaltoLinea(eOperacionCliente.getObservacionSolicitud(),7));
			lstParametrosEntrada.add(UFuncionesGenerales.obtieneDescripcionPorSaltoLinea(eOperacionCliente.getObservacionSolicitud(),8));
			lstParametrosEntrada.add(UFuncionesGenerales.obtieneDescripcionPorSaltoLinea(eOperacionCliente.getObservacionSolicitud(),9));
			
			lstParametrosEntrada.add(eOperacionCliente.getUsuarioRegistro().getNombreUsuario());
			lstParametrosEntrada.add(eOperacionCliente.getFechaRegistro());
			lstParametrosEntrada.add(formato.format(eOperacionCliente.getFechaRegistro()));
			
			mensaje = objConexion.ejecutaTransaccion(SP_ABACOINLEGAL_UPD_OPERACION_CLIENTE, lstParametrosEntrada);
		} catch(Exception objEx) {
			mensaje.setIdMensaje(-1);
			mensaje.setDescMensaje(objEx.getMessage());
			UManejadorLog.error("Acceso: Problemas al modificar.", objEx);
		}
		return mensaje;
	}
	
	public List<EOperacionCliente> listarEvaluacionCliente(int codigo, String descripcion, EUsuario eUsuario) {
		List<Object> lstParametrosEntrada;
		ResultSet oResultSet = null;
		EOperacionCliente oEOperacionCliente= null;
		List<EOperacionCliente> lstOperacionCliente = null;
		
		try {
			lstParametrosEntrada = new ArrayList<Object>();
			lstParametrosEntrada.add(codigo);
			lstParametrosEntrada.add(descripcion);
			//lstParametrosEntrada.add(eUsuario.getNombreUsuario());
			
			oResultSet = objConexion.ejecutaConsulta(SP_ABACOINLEGAL_SEL_OPERACION_CLIENTE, lstParametrosEntrada, null);
			if (oResultSet != null) {
				lstOperacionCliente = new ArrayList<EOperacionCliente>();
				while (oResultSet.next()) {
					oEOperacionCliente = new EOperacionCliente();
					oEOperacionCliente.setNumeroSolicitud(oResultSet.getInt("NROSOL"));
					oEOperacionCliente.setCodigoEstadoSolicitud(oResultSet.getInt("CODESTSOL"));
					oEOperacionCliente.setDescripcionEstadoSolicitud(UFuncionesGenerales.revisaCadena(oResultSet.getString("DESCESTSOL")));
					
					oEOperacionCliente.setCodigoCliente(oResultSet.getInt("CODCLI"));
					oEOperacionCliente.setCodigoTipoCliente(oResultSet.getInt("TIPCLI"));
					oEOperacionCliente.setCodigoTipoPersona(UFuncionesGenerales.revisaCadena(oResultSet.getString("PERSON")));
					oEOperacionCliente.setCodigoTipoDocumento(UFuncionesGenerales.revisaCadena(oResultSet.getString("TIPDOC")));
					oEOperacionCliente.setNumeroDocumento(UFuncionesGenerales.revisaCadena(oResultSet.getString("DOCUME")));
					oEOperacionCliente.setNombreLargo(UFuncionesGenerales.revisaCadena(oResultSet.getString("NOMBCL")));
					oEOperacionCliente.setNombreCorto(UFuncionesGenerales.revisaCadena(oResultSet.getString("NOMABV")));
					oEOperacionCliente.setFechaPrimeraEvaluacion(oResultSet.getDate("FECIEV"));
					oEOperacionCliente.setHoraPrimeraEvaluacion(UFuncionesGenerales.convertirEnteroATime(oResultSet.getInt("HOAIEV")));
					oEOperacionCliente.setUsuarioPrimeraEvaluacion(UFuncionesGenerales.revisaCadena(oResultSet.getString("USUIEV")));
					oEOperacionCliente.setFechaRegistroLegal(oResultSet.getDate("FECREGL"));
					oEOperacionCliente.setHoraRegistroLegal(UFuncionesGenerales.convertirEnteroATime(oResultSet.getInt("HOREGL")));
					oEOperacionCliente.setUsuarioRegistroLegal(UFuncionesGenerales.revisaCadena(oResultSet.getString("USREGL")));
					oEOperacionCliente.setFechaUltimaRevision(oResultSet.getDate("FECUREV"));
					oEOperacionCliente.setHoraUltimaRevision(UFuncionesGenerales.convertirEnteroATime(oResultSet.getInt("HOUREV")));
					oEOperacionCliente.setUsuarioUltimaRevision(UFuncionesGenerales.revisaCadena(oResultSet.getString("USUREV")));
					
					oEOperacionCliente.setDescripcionTipoCliente(UFuncionesGenerales.revisaCadena(oResultSet.getString("DESCTIPCLI")));
					oEOperacionCliente.setAbreviacionTipoPersona(UFuncionesGenerales.revisaCadena(oResultSet.getString("ABRVPERSON")));
					
					lstOperacionCliente.add(oEOperacionCliente);
				}								
			}						
			
		} catch(Exception objEx) {
			UManejadorLog.error("Acceso: Problemas al obtener.", objEx);
		}
		return lstOperacionCliente;
	}
	
	public List<EOperacionClienteDocumento> listarEvaluacionClienteDocumento(long numeroSolicitud, int codigoTipoCliente, int codigoCliente) {
		List<Object> lstParametrosEntrada;
		ResultSet oResultSet = null;
		EOperacionClienteDocumento oEOperacionClienteDocumento= null;
		List<EOperacionClienteDocumento> lstOperacionClienteDocumento = null;
		
		try {	
			lstParametrosEntrada = new ArrayList<Object>();
			lstParametrosEntrada.add(numeroSolicitud);
			lstParametrosEntrada.add(codigoTipoCliente);
			lstParametrosEntrada.add(codigoCliente);
			 
			oResultSet = objConexion.ejecutaConsulta(SP_ABACOINLEGAL_SEL_OPERACION_CLIENTE_DOCUMENTO, lstParametrosEntrada, null);
			if (oResultSet != null) {
				lstOperacionClienteDocumento = new ArrayList<EOperacionClienteDocumento>();
				while (oResultSet.next()) {
					oEOperacionClienteDocumento=new EOperacionClienteDocumento();
					oEOperacionClienteDocumento.setNumeroSolicitud(oResultSet.getInt("NROSOL"));
					oEOperacionClienteDocumento.setCodigoTipoCliente(oResultSet.getInt("TIPCLI"));
					oEOperacionClienteDocumento.setCodigoCliente(oResultSet.getInt("CODCLI"));
					oEOperacionClienteDocumento.setCodigoDocumento(oResultSet.getInt("CODDOC"));
					oEOperacionClienteDocumento.setCodigoDocumentoLaserFiche(oResultSet.getString("CODDOCLSF"));
					oEOperacionClienteDocumento.setNombreDocumento(oResultSet.getString("NOMDOC"));
					oEOperacionClienteDocumento.setNombreDocumentoLaserFiche(oResultSet.getString("NOMDOCLSF"));
					oEOperacionClienteDocumento.setNombreDocumentoOriginal(oResultSet.getString("NOMDOCORI"));
					
					lstOperacionClienteDocumento.add(oEOperacionClienteDocumento);
				}								
			}						
			
		} catch(Exception objEx) {
			UManejadorLog.error("Acceso: Problemas al obtener.", objEx);
		}
		return lstOperacionClienteDocumento;
	}
	
	public EOperacionCliente buscarEvaluacionCliente(long numeroSolicitud, int codigoTipoCliente, int codigoCliente) {
		List<Object> lstParametrosEntrada;
		ResultSet oResultSet = null;
		EOperacionCliente oEOperacionCliente= null;
		SimpleDateFormat formato = new SimpleDateFormat("HH:mm:ss");
		
		try {
			lstParametrosEntrada = new ArrayList<Object>();
			lstParametrosEntrada.add(numeroSolicitud);
			lstParametrosEntrada.add(codigoTipoCliente);
			lstParametrosEntrada.add(codigoCliente);
			
			oResultSet = objConexion.ejecutaConsulta(SP_ABACOINLEGAL_BUS_OPERACION_CLIENTE, lstParametrosEntrada, null);
			if (oResultSet != null) {
				while (oResultSet.next()) {
					oEOperacionCliente=new EOperacionCliente();
					oEOperacionCliente.setNumeroSolicitud(oResultSet.getInt("NROSOL"));
					oEOperacionCliente.setCodigoEstadoSolicitud(oResultSet.getInt("CODESTSOL"));
					//oEOperacionCliente.setDescripcionEstadoActual(UFuncionesGenerales.revisaCadena(oResultSet.getString("DESCSTALEG")));
					oEOperacionCliente.setObservacionLegal(oResultSet.getString("OBSLEG"));
					oEOperacionCliente.setObservacionNegocio(oResultSet.getString("OBSNEG"));
					
					oEOperacionCliente.setCodigoCliente(oResultSet.getInt("CODCLI"));
					oEOperacionCliente.setCodigoTipoCliente(oResultSet.getInt("TIPCLI"));
					oEOperacionCliente.setDescripcionTipoCliente(UFuncionesGenerales.revisaCadena(oResultSet.getString("DESCTIPCLI")));
					oEOperacionCliente.setCodigoTipoPersona(UFuncionesGenerales.revisaCadena(oResultSet.getString("PERSON")));
					oEOperacionCliente.setAbreviacionTipoPersona(UFuncionesGenerales.revisaCadena(oResultSet.getString("ABRVPERSON")));
					oEOperacionCliente.setCodigoTipoDocumento(UFuncionesGenerales.revisaCadena(oResultSet.getString("TIPDOC")));
					oEOperacionCliente.setNumeroDocumento(UFuncionesGenerales.revisaCadena(oResultSet.getString("DOCUME")));
					oEOperacionCliente.setRuc(UFuncionesGenerales.revisaCadena(oResultSet.getString("NRORUC")));
					
					oEOperacionCliente.setNombreLargo(UFuncionesGenerales.revisaCadena(oResultSet.getString("NOMBCL")));
					oEOperacionCliente.setNombreCorto(UFuncionesGenerales.revisaCadena(oResultSet.getString("NOMABV")));
					
					oEOperacionCliente.setCodigoTipoDocumentoConyugue(UFuncionesGenerales.revisaCadena(oResultSet.getString("TDOCON")));
					oEOperacionCliente.setDocumentoConyugue(UFuncionesGenerales.revisaCadena(oResultSet.getString("DOCCON")));
					oEOperacionCliente.setNombreLargoConyugue(UFuncionesGenerales.revisaCadena(oResultSet.getString("NOMCON")));
					
					oEOperacionCliente.setCodigoUbigeoReal(oResultSet.getInt("CCIUDA"));
					oEOperacionCliente.setDireccionReal(UFuncionesGenerales.revisaCadena(oResultSet.getString("DIRECC")));
					oEOperacionCliente.setCodigoUbigeoContractual(oResultSet.getInt("CCIUDP"));
					oEOperacionCliente.setDireccionContractual(UFuncionesGenerales.revisaCadena(oResultSet.getString("DIRECP")));
					
					oEOperacionCliente.setCodigoTipoPersonaJuridica(UFuncionesGenerales.revisaCadena(oResultSet.getString("TIPOPJ")));
					oEOperacionCliente.setMontoCapitalSocialRegistroPublicos(oResultSet.getDouble("CAPSRP"));
					oEOperacionCliente.setMontoCapitalSocialActual(oResultSet.getDouble("CAPSAC"));
					oEOperacionCliente.setCodigoFacultadOperar(UFuncionesGenerales.revisaCadena(oResultSet.getString("FACUOP")));
					oEOperacionCliente.setCodigoTipoSuscripcionPago(UFuncionesGenerales.revisaCadena(oResultSet.getString("TIPSPG")));
					oEOperacionCliente.setNumeroSuscripcionPago(oResultSet.getInt("SUSPAG"));
					oEOperacionCliente.setNumeroAcciones(oResultSet.getInt("NROACC"));
					oEOperacionCliente.setIndicadorAvalarTercero(UFuncionesGenerales.revisaCadena(oResultSet.getString("AVATER")));
					oEOperacionCliente.setIndicadorGrabarBien(UFuncionesGenerales.revisaCadena(oResultSet.getString("GRABIE")));
					oEOperacionCliente.setDescripcionAvalarTercero(UFuncionesGenerales.revisaCadena(oResultSet.getString("AVATFI")));
					oEOperacionCliente.setObservacionAvalarTercero(UFuncionesGenerales.revisaCadenaSaltoLinea(UFuncionesGenerales.revisaCadena(oResultSet.getString("OB1AVT")))+
																	UFuncionesGenerales.revisaCadenaSaltoLinea(UFuncionesGenerales.revisaCadena(oResultSet.getString("OB2AVT")))+
																	UFuncionesGenerales.revisaCadena(oResultSet.getString("OB3AVT")));
					oEOperacionCliente.setObservacionGrabarBien(UFuncionesGenerales.revisaCadenaSaltoLinea(UFuncionesGenerales.revisaCadena(oResultSet.getString("OB1GRA")))+
																UFuncionesGenerales.revisaCadenaSaltoLinea(UFuncionesGenerales.revisaCadena(oResultSet.getString("OB2GRA")))+
																UFuncionesGenerales.revisaCadena(oResultSet.getString("OB3GRA")));
					oEOperacionCliente.setObservacionConstanciaSocial(UFuncionesGenerales.revisaCadenaSaltoLinea(UFuncionesGenerales.revisaCadena(oResultSet.getString("DAT1PJ")))+
																		UFuncionesGenerales.revisaCadenaSaltoLinea(UFuncionesGenerales.revisaCadena(oResultSet.getString("DAT2PJ")))+
																		UFuncionesGenerales.revisaCadenaSaltoLinea(UFuncionesGenerales.revisaCadena(oResultSet.getString("DAT3PJ")))+
																		UFuncionesGenerales.revisaCadenaSaltoLinea(UFuncionesGenerales.revisaCadena(oResultSet.getString("DAT4PJ")))+
																		UFuncionesGenerales.revisaCadena(oResultSet.getString("DAT5PJ")));
					oEOperacionCliente.setObservacionSolicitud(UFuncionesGenerales.revisaCadenaSaltoLinea(UFuncionesGenerales.revisaCadena(oResultSet.getString("OBEL01")))+
																UFuncionesGenerales.revisaCadenaSaltoLinea(UFuncionesGenerales.revisaCadena(oResultSet.getString("OBEL02")))+
																UFuncionesGenerales.revisaCadenaSaltoLinea(UFuncionesGenerales.revisaCadena(oResultSet.getString("OBEL03")))+
																UFuncionesGenerales.revisaCadenaSaltoLinea(UFuncionesGenerales.revisaCadena(oResultSet.getString("OBEL04")))+
																UFuncionesGenerales.revisaCadenaSaltoLinea(UFuncionesGenerales.revisaCadena(oResultSet.getString("OBEL05")))+
																UFuncionesGenerales.revisaCadenaSaltoLinea(UFuncionesGenerales.revisaCadena(oResultSet.getString("OBEL06")))+
																UFuncionesGenerales.revisaCadenaSaltoLinea(UFuncionesGenerales.revisaCadena(oResultSet.getString("OBEL07")))+
																UFuncionesGenerales.revisaCadenaSaltoLinea(UFuncionesGenerales.revisaCadena(oResultSet.getString("OBEL08")))+
																UFuncionesGenerales.revisaCadenaSaltoLinea(UFuncionesGenerales.revisaCadena(oResultSet.getString("OBEL09")))+
																UFuncionesGenerales.revisaCadena(oResultSet.getString("OBEL10")));
					
					//oEOperacionCliente.setUsuarioEvaluacionLegal(UFuncionesGenerales.revisaCadena(oResultSet.getString("NOMUSUEVALG")));
					oEOperacionCliente.setFechaRegistroLegal(oResultSet.getDate("FECREGL"));
					oEOperacionCliente.setHoraRegistroLegal(UFuncionesGenerales.convertirEnteroATime(oResultSet.getInt("HOREGL")));
					oEOperacionCliente.setUsuarioRegistroLegal(UFuncionesGenerales.revisaCadena(oResultSet.getString("USREGL")));
					oEOperacionCliente.setFechaUltimaRevision(oResultSet.getDate("FECUREV"));
					oEOperacionCliente.setHoraUltimaRevision(UFuncionesGenerales.convertirEnteroATime(oResultSet.getInt("HOUREV")));
					oEOperacionCliente.setUsuarioUltimaRevision(UFuncionesGenerales.revisaCadena(oResultSet.getString("USUREV")));
				}						
			}						
			
		} catch(Exception objEx) {
			UManejadorLog.error("Acceso: Problemas al obtener.", objEx);
		}
		return oEOperacionCliente;
	}
	
	public EMensaje agregarEvaluacionSolicitudCredito() {
		EMensaje mensaje = new EMensaje();		
		List<Object> lstParametrosEntrada;
		try {	
			lstParametrosEntrada = new ArrayList<Object>();
			
			mensaje = objConexion.ejecutaTransaccion(SP_ABACOINLEGAL_INS_OPERACION_SOLICITUDCREDITO, lstParametrosEntrada);						
		} catch(Exception objEx) {
			UManejadorLog.error("Acceso: Problemas al agregar.", objEx);
		}
		return mensaje;
	}
	
	public EMensaje agregarEvaluacionSolicitudCreditoDocumento(EOperacionSolicitudCredito eOperacionSolicitudCredito, EDocumentoCarga eDocumentoCarga) {
		EMensaje mensaje = new EMensaje();
		List<Object> lstParametrosEntrada;
		SimpleDateFormat formato = new SimpleDateFormat("HH:mm:ss");
		
		try {
			lstParametrosEntrada = new ArrayList<Object>();			
			lstParametrosEntrada.add(eOperacionSolicitudCredito.getNumeroSolicitud());
			lstParametrosEntrada.add(eOperacionSolicitudCredito.getCodigoTipoCliente());
			lstParametrosEntrada.add(eOperacionSolicitudCredito.getCodigoCliente());
			lstParametrosEntrada.add(eOperacionSolicitudCredito.getNumeroMensaje());
			lstParametrosEntrada.add(eDocumentoCarga.getCodigoLaserFiche());
			lstParametrosEntrada.add(eDocumentoCarga.getNombre());
			lstParametrosEntrada.add(eDocumentoCarga.getNombreLaserFiche());
			lstParametrosEntrada.add(eDocumentoCarga.getNombreOriginal());
			lstParametrosEntrada.add(eOperacionSolicitudCredito.getUsuarioRegistro().getNombreUsuario());
			lstParametrosEntrada.add(eOperacionSolicitudCredito.getFechaRegistro());
			lstParametrosEntrada.add(formato.format(eOperacionSolicitudCredito.getFechaRegistro()));
			
			mensaje = objConexion.ejecutaTransaccion(SP_ABACOINLEGAL_INS_OPERACION_SOLICITUDCREDITO_DOCUMENTO, lstParametrosEntrada);
		} catch(Exception objEx) {
			mensaje.setIdMensaje(-1);
			mensaje.setDescMensaje(objEx.getMessage());
			UManejadorLog.error("Acceso: Problemas al agregar.", objEx);
		}
		return mensaje;
	}
	
	public EMensaje agregarEvaluacionSolicitudCreditoDocumentoRevision(EOperacionSolicitudCredito eOperacionSolicitudCredito, EOperacionSolicitudCreditoDocumentoRevision eOperacionSolicitudCreditoDocumentoRevision) {
		EMensaje mensaje = new EMensaje();
		List<Object> lstParametrosEntrada;
		SimpleDateFormat formato = new SimpleDateFormat("HH:mm:ss");
		
		try {
			lstParametrosEntrada = new ArrayList<Object>();
			lstParametrosEntrada.add(eOperacionSolicitudCredito.getNumeroSolicitud());
			lstParametrosEntrada.add(eOperacionSolicitudCredito.getCodigoTipoCliente());
			lstParametrosEntrada.add(eOperacionSolicitudCredito.getCodigoCliente());
			lstParametrosEntrada.add(eOperacionSolicitudCreditoDocumentoRevision.getCodigoDocumentoRequerido());
			lstParametrosEntrada.add(eOperacionSolicitudCreditoDocumentoRevision.getCodigoDocumentoLaserFiche());
			lstParametrosEntrada.add(eOperacionSolicitudCreditoDocumentoRevision.getNombreDocumento());
			lstParametrosEntrada.add(eOperacionSolicitudCreditoDocumentoRevision.getNombreDocumentoLaserFiche());
			lstParametrosEntrada.add(eOperacionSolicitudCreditoDocumentoRevision.getNombreDocumentoOriginal());
			lstParametrosEntrada.add(eOperacionSolicitudCredito.getUsuarioRegistro().getNombreUsuario());
			lstParametrosEntrada.add(eOperacionSolicitudCredito.getFechaRegistro());
			lstParametrosEntrada.add(formato.format(eOperacionSolicitudCredito.getFechaRegistro()));
			
			mensaje = objConexion.ejecutaTransaccion(SP_ABACOINLEGAL_INS_OPERACION_SOLICITUDCREDITO_DOCUMENTOREVISION, lstParametrosEntrada);
		} catch(Exception objEx) {
			mensaje.setIdMensaje(-1);
			mensaje.setDescMensaje(objEx.getMessage());
			UManejadorLog.error("Acceso: Problemas al agregar.", objEx);
		}
		return mensaje;
	}
	
	public EMensaje agregarEvaluacionSolicitudCreditoDocumentoPorAsignar(EOperacionSolicitudCredito eOperacionSolicitudCredito, ERevisionDocumento eRevisionDocumento) {
		EMensaje mensaje = new EMensaje();
		List<Object> lstParametrosEntrada;
		SimpleDateFormat formato = new SimpleDateFormat("HH:mm:ss");
		
		try {
			lstParametrosEntrada = new ArrayList<Object>();
			lstParametrosEntrada.add(eOperacionSolicitudCredito.getNumeroSolicitud());
			lstParametrosEntrada.add(eOperacionSolicitudCredito.getCodigoTipoCliente());
			lstParametrosEntrada.add(eOperacionSolicitudCredito.getCodigoCliente());
			lstParametrosEntrada.add(eRevisionDocumento.getCodigoSolicitud());
			lstParametrosEntrada.add(eRevisionDocumento.getCodigoMensaje());
			lstParametrosEntrada.add(eRevisionDocumento.getCodigoDocumento());
			lstParametrosEntrada.add(eRevisionDocumento.getCodigoDocumentoLaserFiche());
			lstParametrosEntrada.add(eOperacionSolicitudCredito.getUsuarioRegistro().getNombreUsuario());
			lstParametrosEntrada.add(eOperacionSolicitudCredito.getFechaRegistro());
			lstParametrosEntrada.add(formato.format(eOperacionSolicitudCredito.getFechaRegistro()));
			
			mensaje = objConexion.ejecutaTransaccion(SP_ABACOINLEGAL_INS_OPERACION_SOLICITUDCREDITO_DOCUMENTOPORASIGNAR, lstParametrosEntrada);
		} catch(Exception objEx) {
			mensaje.setIdMensaje(-1);
			mensaje.setDescMensaje(objEx.getMessage());
			UManejadorLog.error("Acceso: Problemas al agregar.", objEx);
		}
		return mensaje;
	}
	
	public EMensaje agregarEvaluacionSolicitudCreditoDocumentoAsignado(EOperacionSolicitudCredito eOperacionSolicitudCredito, ERevisionDocumento eRevisionDocumento) {
		EMensaje mensaje = new EMensaje();
		List<Object> lstParametrosEntrada;
		SimpleDateFormat formato = new SimpleDateFormat("HH:mm:ss");
		
		try {
			lstParametrosEntrada = new ArrayList<Object>();
			lstParametrosEntrada.add(eOperacionSolicitudCredito.getNumeroSolicitud());
			lstParametrosEntrada.add(eOperacionSolicitudCredito.getCodigoTipoCliente());
			lstParametrosEntrada.add(eOperacionSolicitudCredito.getCodigoCliente());
			lstParametrosEntrada.add(eRevisionDocumento.getCodigoSolicitud());
			lstParametrosEntrada.add(eRevisionDocumento.getCodigoMensaje());
			lstParametrosEntrada.add(eRevisionDocumento.getCodigoDocumento());
			lstParametrosEntrada.add(eRevisionDocumento.getCodigoDocumentoLaserFiche());
			lstParametrosEntrada.add(eOperacionSolicitudCredito.getUsuarioRegistro().getNombreUsuario());
			lstParametrosEntrada.add(eOperacionSolicitudCredito.getFechaRegistro());
			lstParametrosEntrada.add(formato.format(eOperacionSolicitudCredito.getFechaRegistro()));
			
			mensaje = objConexion.ejecutaTransaccion(SP_ABACOINLEGAL_INS_OPERACION_SOLICITUDCREDITO_DOCUMENTOASIGNADO, lstParametrosEntrada);
		} catch(Exception objEx) {
			mensaje.setIdMensaje(-1);
			mensaje.setDescMensaje(objEx.getMessage());
			UManejadorLog.error("Acceso: Problemas al agregar.", objEx);
		}
		return mensaje;
	}
	
	public EMensaje modificarEvaluacionSolicitudCredito(EOperacionSolicitudCredito eOperacionSolicitudCredito) {
		EMensaje mensaje = new EMensaje();
		List<Object> lstParametrosEntrada;
		SimpleDateFormat formato = new SimpleDateFormat("HH:mm:ss");
		
		try {			
			lstParametrosEntrada = new ArrayList<Object>();
			lstParametrosEntrada.add(eOperacionSolicitudCredito.getNumeroSolicitud());
			lstParametrosEntrada.add(eOperacionSolicitudCredito.getCodigoTipoCliente());
			lstParametrosEntrada.add(eOperacionSolicitudCredito.getCodigoCliente());
			
			lstParametrosEntrada.add(eOperacionSolicitudCredito.getCodigoEstadoActual());
			lstParametrosEntrada.add(eOperacionSolicitudCredito.getCodigoAutorizacion());
			lstParametrosEntrada.add(eOperacionSolicitudCredito.getDescripcionMensaje());
			
			lstParametrosEntrada.add(eOperacionSolicitudCredito.getCodigoTipoPersona());
			lstParametrosEntrada.add(eOperacionSolicitudCredito.getCodigoTipoDocumento());
			lstParametrosEntrada.add(eOperacionSolicitudCredito.getNumeroDocumento());
			lstParametrosEntrada.add(eOperacionSolicitudCredito.getRuc());
			lstParametrosEntrada.add(eOperacionSolicitudCredito.getApellidoPaterno());
			lstParametrosEntrada.add(eOperacionSolicitudCredito.getApellidoMaterno());
			lstParametrosEntrada.add(eOperacionSolicitudCredito.getNombre());
			lstParametrosEntrada.add(eOperacionSolicitudCredito.getNombreLargo());
			lstParametrosEntrada.add(eOperacionSolicitudCredito.getDireccionReal());
			lstParametrosEntrada.add(eOperacionSolicitudCredito.getCodigoUbigeoReal());
			lstParametrosEntrada.add(eOperacionSolicitudCredito.getDireccionContractual());
			lstParametrosEntrada.add(eOperacionSolicitudCredito.getCodigoUbigeoContractual());
			
			lstParametrosEntrada.add(eOperacionSolicitudCredito.getCodigoTipoDocumentoConyugue());
			lstParametrosEntrada.add(eOperacionSolicitudCredito.getDocumentoConyugue());
			lstParametrosEntrada.add(eOperacionSolicitudCredito.getApellidoPaternoConyugue());
			lstParametrosEntrada.add(eOperacionSolicitudCredito.getApellidoMaternoConyugue());
			lstParametrosEntrada.add(eOperacionSolicitudCredito.getNombreConyugue());
			lstParametrosEntrada.add(eOperacionSolicitudCredito.getNombreLargoConyugue());
			
			lstParametrosEntrada.add(eOperacionSolicitudCredito.getCodigoTipoPersonaJuridica());
			lstParametrosEntrada.add(eOperacionSolicitudCredito.getMontoCapitalSocialRegistroPublicos());
			lstParametrosEntrada.add(eOperacionSolicitudCredito.getMontoCapitalSocialActual());
			lstParametrosEntrada.add(eOperacionSolicitudCredito.getCodigoFacultadOperar());
			lstParametrosEntrada.add(eOperacionSolicitudCredito.getCodigoTipoSuscripcionPago());
			lstParametrosEntrada.add(eOperacionSolicitudCredito.getNumeroSuscripcionPago());
			lstParametrosEntrada.add(eOperacionSolicitudCredito.getNumeroAcciones());
			lstParametrosEntrada.add(eOperacionSolicitudCredito.getIndicadorAvalarTercero());
			lstParametrosEntrada.add(eOperacionSolicitudCredito.getIndicadorGrabarBien());
			lstParametrosEntrada.add(eOperacionSolicitudCredito.getDescripcionAvalarTercero());
			
			lstParametrosEntrada.add(UFuncionesGenerales.obtieneDescripcionPorSaltoLinea(eOperacionSolicitudCredito.getObservacionAvalarTercero(),0));
			lstParametrosEntrada.add(UFuncionesGenerales.obtieneDescripcionPorSaltoLinea(eOperacionSolicitudCredito.getObservacionAvalarTercero(),1));
			lstParametrosEntrada.add(UFuncionesGenerales.obtieneDescripcionPorSaltoLinea(eOperacionSolicitudCredito.getObservacionAvalarTercero(),2));
			
			lstParametrosEntrada.add(UFuncionesGenerales.obtieneDescripcionPorSaltoLinea(eOperacionSolicitudCredito.getObservacionGrabarBien(),0));
			lstParametrosEntrada.add(UFuncionesGenerales.obtieneDescripcionPorSaltoLinea(eOperacionSolicitudCredito.getObservacionGrabarBien(),1));
			lstParametrosEntrada.add(UFuncionesGenerales.obtieneDescripcionPorSaltoLinea(eOperacionSolicitudCredito.getObservacionGrabarBien(),2));
			
			lstParametrosEntrada.add(UFuncionesGenerales.obtieneDescripcionPorSaltoLinea(eOperacionSolicitudCredito.getObservacionConstanciaSocial(),0));
			lstParametrosEntrada.add(UFuncionesGenerales.obtieneDescripcionPorSaltoLinea(eOperacionSolicitudCredito.getObservacionConstanciaSocial(),1));
			lstParametrosEntrada.add(UFuncionesGenerales.obtieneDescripcionPorSaltoLinea(eOperacionSolicitudCredito.getObservacionConstanciaSocial(),2));
			lstParametrosEntrada.add(UFuncionesGenerales.obtieneDescripcionPorSaltoLinea(eOperacionSolicitudCredito.getObservacionConstanciaSocial(),3));
			lstParametrosEntrada.add(UFuncionesGenerales.obtieneDescripcionPorSaltoLinea(eOperacionSolicitudCredito.getObservacionConstanciaSocial(),4));
			
			lstParametrosEntrada.add(UFuncionesGenerales.obtieneDescripcionPorSaltoLinea(eOperacionSolicitudCredito.getObservacionSolicitud(),0));
			lstParametrosEntrada.add(UFuncionesGenerales.obtieneDescripcionPorSaltoLinea(eOperacionSolicitudCredito.getObservacionSolicitud(),1));
			lstParametrosEntrada.add(UFuncionesGenerales.obtieneDescripcionPorSaltoLinea(eOperacionSolicitudCredito.getObservacionSolicitud(),2));
			lstParametrosEntrada.add(UFuncionesGenerales.obtieneDescripcionPorSaltoLinea(eOperacionSolicitudCredito.getObservacionSolicitud(),3));
			lstParametrosEntrada.add(UFuncionesGenerales.obtieneDescripcionPorSaltoLinea(eOperacionSolicitudCredito.getObservacionSolicitud(),4));
			lstParametrosEntrada.add(UFuncionesGenerales.obtieneDescripcionPorSaltoLinea(eOperacionSolicitudCredito.getObservacionSolicitud(),5));
			lstParametrosEntrada.add(UFuncionesGenerales.obtieneDescripcionPorSaltoLinea(eOperacionSolicitudCredito.getObservacionSolicitud(),6));
			lstParametrosEntrada.add(UFuncionesGenerales.obtieneDescripcionPorSaltoLinea(eOperacionSolicitudCredito.getObservacionSolicitud(),7));
			lstParametrosEntrada.add(UFuncionesGenerales.obtieneDescripcionPorSaltoLinea(eOperacionSolicitudCredito.getObservacionSolicitud(),8));
			lstParametrosEntrada.add(UFuncionesGenerales.obtieneDescripcionPorSaltoLinea(eOperacionSolicitudCredito.getObservacionSolicitud(),9));
			
			lstParametrosEntrada.add(eOperacionSolicitudCredito.getUsuarioRegistro().getNombreUsuario());
			lstParametrosEntrada.add(eOperacionSolicitudCredito.getFechaRegistro());
			lstParametrosEntrada.add(formato.format(eOperacionSolicitudCredito.getFechaRegistro()));
			
			mensaje = objConexion.ejecutaTransaccion(SP_ABACOINLEGAL_UPD_OPERACION_SOLICITUDCREDITO, lstParametrosEntrada);
		} catch(Exception objEx) {
			mensaje.setIdMensaje(-1);
			mensaje.setDescMensaje(objEx.getMessage());
			UManejadorLog.error("Acceso: Problemas al modificar.", objEx);
		}
		return mensaje;
	}
	
	public EMensaje agregarModificarInformeLegalAdicional(EOperacionSolicitudCredito eOperacionSolicitudCredito, EInformeLegalAdicional eEInformeLegalAdicional) {
		EMensaje mensaje = new EMensaje();
		List<Object> lstParametrosEntrada;
		SimpleDateFormat formato = new SimpleDateFormat("HH:mm:ss");
		
		try {		
			lstParametrosEntrada = new ArrayList<Object>();
			lstParametrosEntrada.add(eEInformeLegalAdicional.getNumeroSolicitud());
			lstParametrosEntrada.add(eEInformeLegalAdicional.getCodigoCliente());
			lstParametrosEntrada.add(eEInformeLegalAdicional.getCodigoTipoCliente());
			
			lstParametrosEntrada.add(eOperacionSolicitudCredito.getCodigoTipoDocumento());
			lstParametrosEntrada.add(eOperacionSolicitudCredito.getNumeroDocumento());
			lstParametrosEntrada.add(eOperacionSolicitudCredito.getCodigoTipoPersonaJuridica());
			lstParametrosEntrada.add(eOperacionSolicitudCredito.getMontoCapitalSocialRegistroPublicos());
			lstParametrosEntrada.add(eOperacionSolicitudCredito.getMontoCapitalSocialActual());
			lstParametrosEntrada.add(eOperacionSolicitudCredito.getCodigoFacultadOperar());
			lstParametrosEntrada.add(eOperacionSolicitudCredito.getCodigoTipoSuscripcionPago());
			lstParametrosEntrada.add(eOperacionSolicitudCredito.getNumeroSuscripcionPago());
			lstParametrosEntrada.add(eOperacionSolicitudCredito.getNumeroAcciones());
			lstParametrosEntrada.add(eOperacionSolicitudCredito.getIndicadorAvalarTercero());
			lstParametrosEntrada.add(eOperacionSolicitudCredito.getIndicadorGrabarBien());
			lstParametrosEntrada.add(eOperacionSolicitudCredito.getDescripcionAvalarTercero());
			lstParametrosEntrada.add(UFuncionesGenerales.obtieneDescripcionPorSaltoLinea(eOperacionSolicitudCredito.getObservacionAvalarTercero(),0));
			lstParametrosEntrada.add(UFuncionesGenerales.obtieneDescripcionPorSaltoLinea(eOperacionSolicitudCredito.getObservacionAvalarTercero(),1));
			lstParametrosEntrada.add(UFuncionesGenerales.obtieneDescripcionPorSaltoLinea(eOperacionSolicitudCredito.getObservacionAvalarTercero(),2));
			lstParametrosEntrada.add(UFuncionesGenerales.obtieneDescripcionPorSaltoLinea(eOperacionSolicitudCredito.getObservacionGrabarBien(),0));
			lstParametrosEntrada.add(UFuncionesGenerales.obtieneDescripcionPorSaltoLinea(eOperacionSolicitudCredito.getObservacionGrabarBien(),1));
			lstParametrosEntrada.add(UFuncionesGenerales.obtieneDescripcionPorSaltoLinea(eOperacionSolicitudCredito.getObservacionGrabarBien(),2));
			lstParametrosEntrada.add(UFuncionesGenerales.obtieneDescripcionPorSaltoLinea(eOperacionSolicitudCredito.getObservacionConstanciaSocial(),0));
			lstParametrosEntrada.add(UFuncionesGenerales.obtieneDescripcionPorSaltoLinea(eOperacionSolicitudCredito.getObservacionConstanciaSocial(),1));
			lstParametrosEntrada.add(UFuncionesGenerales.obtieneDescripcionPorSaltoLinea(eOperacionSolicitudCredito.getObservacionConstanciaSocial(),2));
			lstParametrosEntrada.add(UFuncionesGenerales.obtieneDescripcionPorSaltoLinea(eOperacionSolicitudCredito.getObservacionConstanciaSocial(),3));
			lstParametrosEntrada.add(UFuncionesGenerales.obtieneDescripcionPorSaltoLinea(eOperacionSolicitudCredito.getObservacionConstanciaSocial(),4));
			
			lstParametrosEntrada.add(eEInformeLegalAdicional.getCodigoEstadoCivil());
			lstParametrosEntrada.add(eEInformeLegalAdicional.getNumeroPartida());
			lstParametrosEntrada.add(eEInformeLegalAdicional.getOficinaRegistral());
			lstParametrosEntrada.add(eEInformeLegalAdicional.getCodigoTipoDuracionPartida());
			lstParametrosEntrada.add(eEInformeLegalAdicional.getRegistroPartida());
			lstParametrosEntrada.add(eEInformeLegalAdicional.getDescripcionConstitucion());
			lstParametrosEntrada.add(eEInformeLegalAdicional.getFechaConstitucion());
			lstParametrosEntrada.add(eEInformeLegalAdicional.getCodigoNotario());
			lstParametrosEntrada.add(eEInformeLegalAdicional.getDescripcionNotario());
			lstParametrosEntrada.add(eEInformeLegalAdicional.getNumeroAcciones());
			lstParametrosEntrada.add(eEInformeLegalAdicional.getCodigoTipoValorSuscripcion());
			lstParametrosEntrada.add(eEInformeLegalAdicional.getMontoValorNominal());
			lstParametrosEntrada.add(eEInformeLegalAdicional.getEstatuto());
			lstParametrosEntrada.add(eEInformeLegalAdicional.getDescripcionPatrimonio());
			lstParametrosEntrada.add(eEInformeLegalAdicional.getCodigoTipoNumeracionEstatuto());
			lstParametrosEntrada.add(eEInformeLegalAdicional.getNumeracionEstatuto());
			lstParametrosEntrada.add(eEInformeLegalAdicional.getAsiento());
			lstParametrosEntrada.add(eEInformeLegalAdicional.getFechaPeriodoInicio());
			lstParametrosEntrada.add(eEInformeLegalAdicional.getFechaPeriodoVencimiento());
			lstParametrosEntrada.add(eEInformeLegalAdicional.getUsuarioRegistro().getNombreUsuario());
			lstParametrosEntrada.add(eEInformeLegalAdicional.getFechaRegistro());
			lstParametrosEntrada.add(formato.format(eEInformeLegalAdicional.getFechaRegistro()));
			
			mensaje = objConexion.ejecutaTransaccion(SP_ABACOINLEGAL_INS_UPD_INFORMELEGALADICIONAL, lstParametrosEntrada);
		} catch(Exception objEx) {
			mensaje.setIdMensaje(-1);
			mensaje.setDescMensaje(objEx.getMessage());
			UManejadorLog.error("Acceso: Problemas al modificar.", objEx);
		}
		return mensaje;
	}
	
	public EMensaje eliminarEvaluacionSolicitudCreditoDocumentoRevision(EOperacionSolicitudCredito eOperacionSolicitudCredito) {
		EMensaje mensaje = new EMensaje();
		List<Object> lstParametrosEntrada;
		
		try {
			lstParametrosEntrada = new ArrayList<Object>();
			lstParametrosEntrada.add(eOperacionSolicitudCredito.getNumeroSolicitud());
			lstParametrosEntrada.add(eOperacionSolicitudCredito.getCodigoTipoCliente());
			lstParametrosEntrada.add(eOperacionSolicitudCredito.getCodigoCliente());
				
			mensaje = objConexion.ejecutaTransaccion(SP_ABACOINLEGAL_DEL_OPERACION_SOLICITUDCREDITO_DOCUMENTOREVISION, lstParametrosEntrada);
		} catch(Exception objEx) {
			mensaje.setIdMensaje(-1);
			mensaje.setDescMensaje(objEx.getMessage());
			UManejadorLog.error("Acceso: Problemas al eliminar.", objEx);
		}
		return mensaje;
	}
	
	public EMensaje eliminarEvaluacionSolicitudCreditoDocumentoPorAsignar(long numeroSolicitud, int codigoTipoCliente, int codigoCliente) {
		EMensaje mensaje = new EMensaje();
		List<Object> lstParametrosEntrada;
		
		try {
			lstParametrosEntrada = new ArrayList<Object>();
			lstParametrosEntrada.add(numeroSolicitud);
			lstParametrosEntrada.add(codigoTipoCliente);
			lstParametrosEntrada.add(codigoCliente);
				
			mensaje = objConexion.ejecutaTransaccion(SP_ABACOINLEGAL_DEL_OPERACION_SOLICITUDCREDITO_DOCUMENTOPORASIGNAR, lstParametrosEntrada);
		} catch(Exception objEx) {
			mensaje.setIdMensaje(-1);
			mensaje.setDescMensaje(objEx.getMessage());
			UManejadorLog.error("Acceso: Problemas al eliminar.", objEx);
		}
		return mensaje;
	}
	
	public List<EOperacionSolicitudCredito> listarEvaluacionSolicitudCredito(EOperacionSolicitudCredito eOperacionSolicitudCredito, int indicadorConsulta) {
		List<Object> lstParametrosEntrada;
		ResultSet oResultSet = null;
		EOperacionSolicitudCredito oEOperacionSolicitudCredito= null;
		List<EOperacionSolicitudCredito> lstOperacionSolicitudCredito = null;
		
		try {
			lstParametrosEntrada = new ArrayList<Object>();			
			lstParametrosEntrada.add(eOperacionSolicitudCredito.getNumeroSolicitud());
			lstParametrosEntrada.add(eOperacionSolicitudCredito.getNombreLargo());
			lstParametrosEntrada.add(eOperacionSolicitudCredito.getCodigoEstadoActual());
			lstParametrosEntrada.add(eOperacionSolicitudCredito.getCodigoAutorizacion());
			lstParametrosEntrada.add(eOperacionSolicitudCredito.getUsuarioRegistro().getNombreUsuario());
			lstParametrosEntrada.add(eOperacionSolicitudCredito.getUsuarioRegistro().getCodigoArea());
			
			if(indicadorConsulta == 1) {
				oResultSet = objConexion.ejecutaConsulta(SP_ABACOINLEGAL_SEL_OPERACION_SOLICITUDCREDITO, lstParametrosEntrada, null);
			}else if(indicadorConsulta == 2) {
				oResultSet = objConexion.ejecutaConsulta(SP_ABACOINLEGAL_SEL_OPERACION_SOLICITUDCREDITO_OTROS, lstParametrosEntrada, null);
			}else if(indicadorConsulta == 3) {
				oResultSet = objConexion.ejecutaConsulta(SP_ABACOINLEGAL_SEL_OPERACION_SOLICITUDCREDITO_PORAUTORIZAR, lstParametrosEntrada, null);
			}
			
			if (oResultSet != null) {
				lstOperacionSolicitudCredito = new ArrayList<EOperacionSolicitudCredito>();
				while (oResultSet.next()) {
					oEOperacionSolicitudCredito = new EOperacionSolicitudCredito();
					oEOperacionSolicitudCredito.setNumeroSolicitud(oResultSet.getInt("NROSOL"));
					oEOperacionSolicitudCredito.setCodigoCliente(oResultSet.getInt("CODCLI"));
					oEOperacionSolicitudCredito.setCodigoTipoCliente(oResultSet.getInt("TIPCLI"));
					oEOperacionSolicitudCredito.setCodigoEstadoActual(UFuncionesGenerales.revisaCadena(oResultSet.getString("STALEG")));
					oEOperacionSolicitudCredito.setCodigoTipoPersona(UFuncionesGenerales.revisaCadena(oResultSet.getString("PERSON")));
					oEOperacionSolicitudCredito.setCodigoTipoDocumento(UFuncionesGenerales.revisaCadena(oResultSet.getString("TIPDOC")));
					oEOperacionSolicitudCredito.setNumeroDocumento(UFuncionesGenerales.revisaCadena(oResultSet.getString("DOCUME")));
					oEOperacionSolicitudCredito.setRuc(UFuncionesGenerales.revisaCadena(oResultSet.getString("NRORUC")));
					oEOperacionSolicitudCredito.setNombreLargo(UFuncionesGenerales.revisaCadena(oResultSet.getString("NOMBCL")));
					oEOperacionSolicitudCredito.setNombreCorto(UFuncionesGenerales.revisaCadena(oResultSet.getString("NOMABV")));					
					oEOperacionSolicitudCredito.setFechaPrimeraEvaluacion(oResultSet.getDate("FECIEV"));
					oEOperacionSolicitudCredito.setHoraPrimeraEvaluacion(UFuncionesGenerales.convertirEnteroATime(oResultSet.getInt("HOAIEV")));
					oEOperacionSolicitudCredito.setUsuarioPrimeraEvaluacion(UFuncionesGenerales.revisaCadena(oResultSet.getString("USUIEV")));
					//oEOperacionSolicitudCredito.setUsuarioEvaluacionLegal(UFuncionesGenerales.revisaCadena(oResultSet.getString("NOMUSUEVALG")));
					oEOperacionSolicitudCredito.setFechaRegistroLegal(oResultSet.getDate("FECREGL"));
					oEOperacionSolicitudCredito.setHoraRegistroLegal(UFuncionesGenerales.convertirEnteroATime(oResultSet.getInt("HOREGL")));
					oEOperacionSolicitudCredito.setUsuarioRegistroLegal(UFuncionesGenerales.revisaCadena(oResultSet.getString("USREGL")));
					oEOperacionSolicitudCredito.setFechaUltimaRevision(oResultSet.getDate("FECUREV"));
					oEOperacionSolicitudCredito.setHoraUltimaRevision(UFuncionesGenerales.convertirEnteroATime(oResultSet.getInt("HOUREV")));
					oEOperacionSolicitudCredito.setUsuarioUltimaRevision(UFuncionesGenerales.revisaCadena(oResultSet.getString("USUREV")));
					
					oEOperacionSolicitudCredito.setCodigoAutorizacion(oResultSet.getInt("CODAUT"));
					oEOperacionSolicitudCredito.setFechaAutorizacion(oResultSet.getDate("FECAUT"));
					oEOperacionSolicitudCredito.setCodigoUbicacionRevision(oResultSet.getInt("UBIREV2"));
					oEOperacionSolicitudCredito.setNombreUsuarioRevision(UFuncionesGenerales.revisaCadena(oResultSet.getString("USUREV2")));
					oEOperacionSolicitudCredito.setDescripcionEstadoActual(UFuncionesGenerales.revisaCadena(oResultSet.getString("DESCSTALEG")));
					oEOperacionSolicitudCredito.setAbreviacionAutorizacion(UFuncionesGenerales.revisaCadena(oResultSet.getString("ABRVAUT")));
					oEOperacionSolicitudCredito.setAbreviacionTipoProducto(UFuncionesGenerales.revisaCadena(oResultSet.getString("ABREVTPRODU")));
					oEOperacionSolicitudCredito.setAbreviacionTipoCliente(UFuncionesGenerales.revisaCadena(oResultSet.getString("ABRVTIPCLI")));
					
					oEOperacionSolicitudCredito.setDiaTranscurridoSolicitud(oResultSet.getInt("DIATRANSCURRIDO"));
					oEOperacionSolicitudCredito.setTiempoTranscurridoSolicitud(UFuncionesGenerales.convertirValoresASexagesimal(oResultSet.getInt("DIATRANSCURRIDO"), oResultSet.getInt("SEGUNDOSTRANSCURRIDO")));
					oEOperacionSolicitudCredito.setDiaTranscurridoAutorizacion(oResultSet.getInt("DIATRANSCURRIDOAUT"));
					oEOperacionSolicitudCredito.setTiempoTranscurridoAutorizacion(UFuncionesGenerales.convertirValoresASexagesimal(oResultSet.getInt("DIATRANSCURRIDOAUT"), oResultSet.getInt("SEGUNDOSTRANSCURRIDOAUT")));
					
					/*
					oEOperacionSolicitudCredito.setNumeroSolicitud(oResultSet.getInt("NROSOL"));
					oEOperacionSolicitudCredito.setCodigoEstadoSolicitud(oResultSet.getInt("CODESTSOL"));
					oEOperacionSolicitudCredito.setDescripcionEstadoSolicitud(UFuncionesGenerales.revisaCadena(oResultSet.getString("DESCESTSOL")));
					
					oEOperacionSolicitudCredito.setCodigoCliente(oResultSet.getInt("CODCLI"));
					oEOperacionSolicitudCredito.setCodigoTipoCliente(oResultSet.getInt("TIPCLI"));
					oEOperacionSolicitudCredito.setCodigoTipoPersona(UFuncionesGenerales.revisaCadena(oResultSet.getString("PERSON")));
					oEOperacionSolicitudCredito.setCodigoTipoDocumento(UFuncionesGenerales.revisaCadena(oResultSet.getString("TIPDOC")));
					oEOperacionSolicitudCredito.setNumeroDocumento(UFuncionesGenerales.revisaCadena(oResultSet.getString("DOCUME")));
					oEOperacionSolicitudCredito.setNombreLargo(UFuncionesGenerales.revisaCadena(oResultSet.getString("NOMBCL")));
					oEOperacionSolicitudCredito.setNombreCorto(UFuncionesGenerales.revisaCadena(oResultSet.getString("NOMABV")));
					oEOperacionSolicitudCredito.setFechaPrimeraEvaluacion(oResultSet.getDate("FECIEV"));
					oEOperacionSolicitudCredito.setHoraPrimeraEvaluacion(UFuncionesGenerales.convertirEnteroATime(oResultSet.getInt("HOAIEV")));
					oEOperacionSolicitudCredito.setUsuarioPrimeraEvaluacion(UFuncionesGenerales.revisaCadena(oResultSet.getString("USUIEV")));
					oEOperacionSolicitudCredito.setFechaRegistroLegal(oResultSet.getDate("FECREGL"));
					oEOperacionSolicitudCredito.setHoraRegistroLegal(UFuncionesGenerales.convertirEnteroATime(oResultSet.getInt("HOREGL")));
					oEOperacionSolicitudCredito.setUsuarioRegistroLegal(UFuncionesGenerales.revisaCadena(oResultSet.getString("USREGL")));
					oEOperacionSolicitudCredito.setFechaUltimaRevision(oResultSet.getDate("FECUREV"));
					oEOperacionSolicitudCredito.setHoraUltimaRevision(UFuncionesGenerales.convertirEnteroATime(oResultSet.getInt("HOUREV")));
					oEOperacionSolicitudCredito.setUsuarioUltimaRevision(UFuncionesGenerales.revisaCadena(oResultSet.getString("USUREV")));
					
					oEOperacionSolicitudCredito.setDescripcionTipoCliente(UFuncionesGenerales.revisaCadena(oResultSet.getString("DESCTIPCLI")));
					oEOperacionSolicitudCredito.setAbreviacionTipoPersona(UFuncionesGenerales.revisaCadena(oResultSet.getString("ABRVPERSON")));
					*/
					
					lstOperacionSolicitudCredito.add(oEOperacionSolicitudCredito);
				}								
			}						
			
		} catch(Exception objEx) {
			UManejadorLog.error("Acceso: Problemas al obtener.", objEx);
		}
		return lstOperacionSolicitudCredito;
	}
	
	public EOperacionSolicitudCredito buscarEvaluacionSolicitudCredito(long numeroSolicitud, int codigoTipoCliente, int codigoCliente) {
		List<Object> lstParametrosEntrada;
		ResultSet oResultSet = null;
		EOperacionSolicitudCredito oEOperacionSolicitudCredito= null;
		SimpleDateFormat formato = new SimpleDateFormat("HH:mm:ss");
		
		try {
			lstParametrosEntrada = new ArrayList<Object>();
			lstParametrosEntrada.add(numeroSolicitud);
			lstParametrosEntrada.add(codigoTipoCliente);
			lstParametrosEntrada.add(codigoCliente);
			
			oResultSet = objConexion.ejecutaConsulta(SP_ABACOINLEGAL_BUS_OPERACION_SOLICITUDCREDITO, lstParametrosEntrada, null);
			if (oResultSet != null) {
				while (oResultSet.next()) {
					oEOperacionSolicitudCredito=new EOperacionSolicitudCredito();
					oEOperacionSolicitudCredito.setNumeroSolicitud(oResultSet.getInt("NROSOL"));
					oEOperacionSolicitudCredito.setCodigoEstadoActual(UFuncionesGenerales.revisaCadena(oResultSet.getString("STALEG")));
					oEOperacionSolicitudCredito.setDescripcionEstadoActual(UFuncionesGenerales.revisaCadena(oResultSet.getString("DESCSTALEG")));
					
					oEOperacionSolicitudCredito.setCodigoCliente(oResultSet.getInt("CODCLI"));
					oEOperacionSolicitudCredito.setCodigoTipoCliente(oResultSet.getInt("TIPCLI"));
					oEOperacionSolicitudCredito.setDescripcionTipoCliente(UFuncionesGenerales.revisaCadena(oResultSet.getString("DESCTIPCLI")));
					oEOperacionSolicitudCredito.setCodigoTipoPersona(UFuncionesGenerales.revisaCadena(oResultSet.getString("PERSON")));
					oEOperacionSolicitudCredito.setAbreviacionTipoPersona(UFuncionesGenerales.revisaCadena(oResultSet.getString("ABRVPERSON")));
					oEOperacionSolicitudCredito.setCodigoTipoDocumento(UFuncionesGenerales.revisaCadena(oResultSet.getString("TIPDOC")));
					oEOperacionSolicitudCredito.setNumeroDocumento(UFuncionesGenerales.revisaCadena(oResultSet.getString("DOCUME")));
					oEOperacionSolicitudCredito.setRuc(UFuncionesGenerales.revisaCadena(oResultSet.getString("NRORUC")));
					
					oEOperacionSolicitudCredito.setNombreLargo(UFuncionesGenerales.revisaCadena(oResultSet.getString("NOMBCL")));
					oEOperacionSolicitudCredito.setNombreCorto(UFuncionesGenerales.revisaCadena(oResultSet.getString("NOMABV")));
					
					oEOperacionSolicitudCredito.setCodigoTipoDocumentoConyugue(UFuncionesGenerales.revisaCadena(oResultSet.getString("TDOCON")));
					oEOperacionSolicitudCredito.setDocumentoConyugue(UFuncionesGenerales.revisaCadena(oResultSet.getString("DOCCON")));
					oEOperacionSolicitudCredito.setNombreLargoConyugue(UFuncionesGenerales.revisaCadena(oResultSet.getString("NOMCON")));
					
					oEOperacionSolicitudCredito.setCodigoUbigeoReal(oResultSet.getInt("CCIUDA"));
					oEOperacionSolicitudCredito.setDireccionReal(UFuncionesGenerales.revisaCadena(oResultSet.getString("DIRECC")));
					oEOperacionSolicitudCredito.setCodigoUbigeoContractual(oResultSet.getInt("CCIUDP"));
					oEOperacionSolicitudCredito.setDireccionContractual(UFuncionesGenerales.revisaCadena(oResultSet.getString("DIRECP")));
					
					oEOperacionSolicitudCredito.setCodigoTipoPersonaJuridica(UFuncionesGenerales.revisaCadena(oResultSet.getString("TIPOPJ")));
					oEOperacionSolicitudCredito.setMontoCapitalSocialRegistroPublicos(oResultSet.getDouble("CAPSRP"));
					oEOperacionSolicitudCredito.setMontoCapitalSocialActual(oResultSet.getDouble("CAPSAC"));
					oEOperacionSolicitudCredito.setCodigoFacultadOperar(UFuncionesGenerales.revisaCadena(oResultSet.getString("FACUOP")));
					oEOperacionSolicitudCredito.setCodigoTipoSuscripcionPago(UFuncionesGenerales.revisaCadena(oResultSet.getString("TIPSPG")));
					oEOperacionSolicitudCredito.setNumeroSuscripcionPago(oResultSet.getInt("SUSPAG"));
					oEOperacionSolicitudCredito.setNumeroAcciones(oResultSet.getInt("NROACC"));
					oEOperacionSolicitudCredito.setIndicadorAvalarTercero(UFuncionesGenerales.revisaCadena(oResultSet.getString("AVATER")));
					oEOperacionSolicitudCredito.setIndicadorGrabarBien(UFuncionesGenerales.revisaCadena(oResultSet.getString("GRABIE")));
					oEOperacionSolicitudCredito.setDescripcionAvalarTercero(UFuncionesGenerales.revisaCadena(oResultSet.getString("AVATFI")));
					oEOperacionSolicitudCredito.setObservacionAvalarTercero(UFuncionesGenerales.revisaCadenaSaltoLinea(UFuncionesGenerales.revisaCadena(oResultSet.getString("OB1AVT")))+
																			UFuncionesGenerales.revisaCadenaSaltoLinea(UFuncionesGenerales.revisaCadena(oResultSet.getString("OB2AVT")))+
																			UFuncionesGenerales.revisaCadena(oResultSet.getString("OB3AVT")));
					oEOperacionSolicitudCredito.setObservacionGrabarBien(UFuncionesGenerales.revisaCadenaSaltoLinea(UFuncionesGenerales.revisaCadena(oResultSet.getString("OB1GRA")))+
																		 UFuncionesGenerales.revisaCadenaSaltoLinea(UFuncionesGenerales.revisaCadena(oResultSet.getString("OB2GRA")))+
																		 UFuncionesGenerales.revisaCadena(oResultSet.getString("OB3GRA")));
					oEOperacionSolicitudCredito.setObservacionConstanciaSocial(UFuncionesGenerales.revisaCadenaSaltoLinea(UFuncionesGenerales.revisaCadena(oResultSet.getString("DAT1PJ")))+
																				UFuncionesGenerales.revisaCadenaSaltoLinea(UFuncionesGenerales.revisaCadena(oResultSet.getString("DAT2PJ")))+
																				UFuncionesGenerales.revisaCadenaSaltoLinea(UFuncionesGenerales.revisaCadena(oResultSet.getString("DAT3PJ")))+
																				UFuncionesGenerales.revisaCadenaSaltoLinea(UFuncionesGenerales.revisaCadena(oResultSet.getString("DAT4PJ")))+
																				UFuncionesGenerales.revisaCadena(oResultSet.getString("DAT5PJ")));
					oEOperacionSolicitudCredito.setObservacionSolicitud(UFuncionesGenerales.revisaCadenaSaltoLinea(UFuncionesGenerales.revisaCadena(oResultSet.getString("OBEL01")))+
																			 UFuncionesGenerales.revisaCadenaSaltoLinea(UFuncionesGenerales.revisaCadena(oResultSet.getString("OBEL02")))+
																			 UFuncionesGenerales.revisaCadenaSaltoLinea(UFuncionesGenerales.revisaCadena(oResultSet.getString("OBEL03")))+
																			 UFuncionesGenerales.revisaCadenaSaltoLinea(UFuncionesGenerales.revisaCadena(oResultSet.getString("OBEL04")))+
																			 UFuncionesGenerales.revisaCadenaSaltoLinea(UFuncionesGenerales.revisaCadena(oResultSet.getString("OBEL05")))+
																			 UFuncionesGenerales.revisaCadenaSaltoLinea(UFuncionesGenerales.revisaCadena(oResultSet.getString("OBEL06")))+
																			 UFuncionesGenerales.revisaCadenaSaltoLinea(UFuncionesGenerales.revisaCadena(oResultSet.getString("OBEL07")))+
																			 UFuncionesGenerales.revisaCadenaSaltoLinea(UFuncionesGenerales.revisaCadena(oResultSet.getString("OBEL08")))+
																			 UFuncionesGenerales.revisaCadenaSaltoLinea(UFuncionesGenerales.revisaCadena(oResultSet.getString("OBEL09")))+
																			 UFuncionesGenerales.revisaCadena(oResultSet.getString("OBEL10")));
					
					//oEOperacionSolicitudCredito.setUsuarioEvaluacionLegal(UFuncionesGenerales.revisaCadena(oResultSet.getString("NOMUSUEVALG")));
					oEOperacionSolicitudCredito.setFechaRegistroLegal(oResultSet.getDate("FECREGL"));
					oEOperacionSolicitudCredito.setHoraRegistroLegal(UFuncionesGenerales.convertirEnteroATime(oResultSet.getInt("HOREGL")));
					oEOperacionSolicitudCredito.setUsuarioRegistroLegal(UFuncionesGenerales.revisaCadena(oResultSet.getString("USREGL")));
					oEOperacionSolicitudCredito.setFechaUltimaRevision(oResultSet.getDate("FECUREV"));
					oEOperacionSolicitudCredito.setHoraUltimaRevision(UFuncionesGenerales.convertirEnteroATime(oResultSet.getInt("HOUREV")));
					oEOperacionSolicitudCredito.setUsuarioUltimaRevision(UFuncionesGenerales.revisaCadena(oResultSet.getString("USUREV")));
					
					oEOperacionSolicitudCredito.setCodigoAutorizacion(oResultSet.getInt("CODAUT"));
					oEOperacionSolicitudCredito.setDescripcionAutorizacion(UFuncionesGenerales.revisaCadena(oResultSet.getString("DESCAUT")));
					oEOperacionSolicitudCredito.setNombreUsuarioAutorizacion(UFuncionesGenerales.revisaCadena(oResultSet.getString("USUAUT")));
					oEOperacionSolicitudCredito.setObservacionAutorizacion(UFuncionesGenerales.revisaCadena(oResultSet.getString("OBSAUT")));
					oEOperacionSolicitudCredito.setFechaAutorizacion(oResultSet.getDate("FECAUT"));
					oEOperacionSolicitudCredito.setHoraAutorizacion(oResultSet.getString("HORAUT"));
				}						
			}						
			
		} catch(Exception objEx) {
			UManejadorLog.error("Acceso: Problemas al obtener.", objEx);
		}
		return oEOperacionSolicitudCredito;
	}
	
	public List<EOperacionSolicitudCreditoMensaje> listarEvaluacionSolicitudCreditoMensaje(long numeroSolicitud, int codigoTipoCliente, int codigoCliente) {
		List<Object> lstParametrosEntrada;
		ResultSet oResultSet = null;
		EOperacionSolicitudCreditoMensaje oEOperacionSolicitudCreditoMensaje= null;
		List<EOperacionSolicitudCreditoMensaje> lstResultado = null;
		
		try {
			lstParametrosEntrada = new ArrayList<Object>();
			lstParametrosEntrada.add(numeroSolicitud);
			lstParametrosEntrada.add(codigoTipoCliente);
			lstParametrosEntrada.add(codigoCliente);
			
			oResultSet = objConexion.ejecutaConsulta(SP_ABACOINLEGAL_SEL_OPERACION_SOLICITUDCREDITO_MENSAJE, lstParametrosEntrada, null);
				
			if (oResultSet != null) {
				lstResultado = new ArrayList<EOperacionSolicitudCreditoMensaje>();
				while (oResultSet.next()) {
					oEOperacionSolicitudCreditoMensaje = new EOperacionSolicitudCreditoMensaje();
					oEOperacionSolicitudCreditoMensaje.setNumeroSolicitud(oResultSet.getInt("NROSOL"));
					oEOperacionSolicitudCreditoMensaje.setCodigoTipoCliente(oResultSet.getInt("TIPCLI"));
					oEOperacionSolicitudCreditoMensaje.setCodigoCliente(oResultSet.getInt("CODCLI"));
					oEOperacionSolicitudCreditoMensaje.setCodigoMensaje(oResultSet.getInt("CODMSJ"));
					oEOperacionSolicitudCreditoMensaje.setDescripcionMensaje(oResultSet.getString("DESCMSJ"));
					oEOperacionSolicitudCreditoMensaje.setNombreUsuarioEnvio(oResultSet.getString("USUENV"));
					oEOperacionSolicitudCreditoMensaje.setFechaEnvio(oResultSet.getDate("FECENV"));
					oEOperacionSolicitudCreditoMensaje.setHoraEnvio(oResultSet.getString("HORENV"));
					
					lstResultado.add(oEOperacionSolicitudCreditoMensaje);
				}								
			}						
			
		} catch(Exception objEx) {
			UManejadorLog.error("Acceso: Problemas al obtener.", objEx);
		}
		return lstResultado;
	}
	
	public List<EOperacionSolicitudCreditoDocumento> listarEvaluacionSolicitudCreditoDocumento(long numeroSolicitud, int codigoTipoCliente, int codigoCliente) {
		List<Object> lstParametrosEntrada;
		ResultSet oResultSet = null;
		EOperacionSolicitudCreditoDocumento oEOperacionSolicitudCreditoDocumento= null;
		List<EOperacionSolicitudCreditoDocumento> lstOperacionSolicitudCreditoDocumento = null;
		
		try {	
			lstParametrosEntrada = new ArrayList<Object>();
			lstParametrosEntrada.add(numeroSolicitud);
			lstParametrosEntrada.add(codigoTipoCliente);
			lstParametrosEntrada.add(codigoCliente);
			 
			oResultSet = objConexion.ejecutaConsulta(SP_ABACOINLEGAL_SEL_OPERACION_SOLICITUDCREDITO_DOCUMENTO, lstParametrosEntrada, null);
			if (oResultSet != null) {
				lstOperacionSolicitudCreditoDocumento = new ArrayList<EOperacionSolicitudCreditoDocumento>();
				while (oResultSet.next()) {
					oEOperacionSolicitudCreditoDocumento=new EOperacionSolicitudCreditoDocumento();
					oEOperacionSolicitudCreditoDocumento.setNumeroSolicitud(oResultSet.getInt("NROSOL"));
					oEOperacionSolicitudCreditoDocumento.setCodigoTipoCliente(oResultSet.getInt("TIPCLI"));
					oEOperacionSolicitudCreditoDocumento.setCodigoCliente(oResultSet.getInt("CODCLI"));
					oEOperacionSolicitudCreditoDocumento.setCodigoDocumento(oResultSet.getInt("CODDOC"));
					oEOperacionSolicitudCreditoDocumento.setCodigoDocumentoLaserFiche(oResultSet.getString("CODDOCLSF"));
					oEOperacionSolicitudCreditoDocumento.setNombreDocumento(oResultSet.getString("NOMDOC"));
					oEOperacionSolicitudCreditoDocumento.setNombreDocumentoLaserFiche(oResultSet.getString("NOMDOCLSF"));
					oEOperacionSolicitudCreditoDocumento.setNombreDocumentoOriginal(oResultSet.getString("NOMDOCORI"));
					
					lstOperacionSolicitudCreditoDocumento.add(oEOperacionSolicitudCreditoDocumento);
				}								
			}						
			
		} catch(Exception objEx) {
			UManejadorLog.error("Acceso: Problemas al obtener.", objEx);
		}
		return lstOperacionSolicitudCreditoDocumento;
	}
	
	public List<EOperacionSolicitudCreditoDocumentoRevision> listarEvaluacionSolicitudCreditoDocumentoRevision(long numeroSolicitud, int codigoTipoCliente, int codigoCliente) {
		List<Object> lstParametrosEntrada;
		ResultSet oResultSet = null;
		EOperacionSolicitudCreditoDocumentoRevision oEOperacionSolicitudCreditoDocumentoRevision= null;
		List<EOperacionSolicitudCreditoDocumentoRevision> lstOperacionSolicitudCreditoDocumentoRevision = null;
		
		try {	
			lstParametrosEntrada = new ArrayList<Object>();
			lstParametrosEntrada.add(numeroSolicitud);
			lstParametrosEntrada.add(codigoTipoCliente);
			lstParametrosEntrada.add(codigoCliente);
			 
			oResultSet = objConexion.ejecutaConsulta(SP_ABACOINLEGAL_SEL_OPERACION_SOLICITUDCREDITO_DOCUMENTOREVISION, lstParametrosEntrada, null);
			if (oResultSet != null) {
				lstOperacionSolicitudCreditoDocumentoRevision = new ArrayList<EOperacionSolicitudCreditoDocumentoRevision>();
				while (oResultSet.next()) {			
					oEOperacionSolicitudCreditoDocumentoRevision=new EOperacionSolicitudCreditoDocumentoRevision();
					oEOperacionSolicitudCreditoDocumentoRevision.setNumeroSolicitud(oResultSet.getInt("NROSOL"));
					oEOperacionSolicitudCreditoDocumentoRevision.setCodigoTipoCliente(oResultSet.getInt("TIPCLI"));
					oEOperacionSolicitudCreditoDocumentoRevision.setCodigoCliente(oResultSet.getInt("CODCLI"));
					oEOperacionSolicitudCreditoDocumentoRevision.setCodigoDocumento(oResultSet.getInt("CODDOC"));
					oEOperacionSolicitudCreditoDocumentoRevision.setCodigoDocumentoRequerido(oResultSet.getInt("CODDOCREQ"));
					oEOperacionSolicitudCreditoDocumentoRevision.setCodigoDocumentoLaserFiche(oResultSet.getString("CODDOCLSF"));
					oEOperacionSolicitudCreditoDocumentoRevision.setNombreDocumento(oResultSet.getString("NOMDOC"));
					oEOperacionSolicitudCreditoDocumentoRevision.setNombreDocumentoLaserFiche(oResultSet.getString("NOMDOCLSF"));
					oEOperacionSolicitudCreditoDocumentoRevision.setNombreDocumentoOriginal(oResultSet.getString("NOMDOCORI"));
					
					lstOperacionSolicitudCreditoDocumentoRevision.add(oEOperacionSolicitudCreditoDocumentoRevision);
				}								
			}						
			
		} catch(Exception objEx) {
			UManejadorLog.error("Acceso: Problemas al obtener.", objEx);
		}
		return lstOperacionSolicitudCreditoDocumentoRevision;
	}
	
	public List<ERevisionDocumento> listarEvaluacionSolicitudCreditoDocumentoPorAsignar(long numeroSolicitud, int codigoTipoCliente, int codigoCliente) {
		List<Object> lstParametrosEntrada;
		ResultSet oResultSet = null;
		ERevisionDocumento oERevisionDocumento= null;
		List<ERevisionDocumento> lstRevisionDocumento = null;
		
		try {	
			lstParametrosEntrada = new ArrayList<Object>();
			lstParametrosEntrada.add(numeroSolicitud);
			lstParametrosEntrada.add(codigoTipoCliente);
			lstParametrosEntrada.add(codigoCliente);
			 
			oResultSet = objConexion.ejecutaConsulta(SP_ABACOINLEGAL_SEL_OPERACION_SOLICITUDCREDITO_DOCUMENTOPORASIGNAR, lstParametrosEntrada, null);
			if (oResultSet != null) {
				lstRevisionDocumento = new ArrayList<ERevisionDocumento>();
				while (oResultSet.next()) {			
					oERevisionDocumento=new ERevisionDocumento();
					oERevisionDocumento.setCodigoSolicitud(oResultSet.getInt("CODSOL"));
					oERevisionDocumento.setCodigoMensaje(oResultSet.getInt("CODMSJ"));
					oERevisionDocumento.setCodigoDocumento(oResultSet.getInt("CODDOC"));
					oERevisionDocumento.setCodigoDocumentoLaserFiche(oResultSet.getString("CODDOCLSF"));
					oERevisionDocumento.setNombreDocumento(oResultSet.getString("NOMDOC"));
					oERevisionDocumento.setNombreDocumentoOriginal(oResultSet.getString("NOMDOCORI"));
					
					lstRevisionDocumento.add(oERevisionDocumento);
				}								
			}						
			
		} catch(Exception objEx) {
			UManejadorLog.error("Acceso: Problemas al obtener.", objEx);
		}
		return lstRevisionDocumento;
	}
	
	public List<ERevisionDocumento> listarEvaluacionSolicitudCreditoDocumentoAsignado(long numeroSolicitud, int codigoTipoCliente, int codigoCliente) {
		List<Object> lstParametrosEntrada;
		ResultSet oResultSet = null;
		ERevisionDocumento oERevisionDocumento= null;
		List<ERevisionDocumento> lstRevisionDocumento = null;
		
		try {	
			lstParametrosEntrada = new ArrayList<Object>();
			lstParametrosEntrada.add(numeroSolicitud);
			lstParametrosEntrada.add(codigoTipoCliente);
			lstParametrosEntrada.add(codigoCliente);
			 
			oResultSet = objConexion.ejecutaConsulta(SP_ABACOINLEGAL_SEL_OPERACION_SOLICITUDCREDITO_DOCUMENTOASIGNADO, lstParametrosEntrada, null);
			if (oResultSet != null) {
				lstRevisionDocumento = new ArrayList<ERevisionDocumento>();
				while (oResultSet.next()) {			
					oERevisionDocumento=new ERevisionDocumento();
					oERevisionDocumento.setCodigoSolicitud(oResultSet.getInt("CODSOL"));
					oERevisionDocumento.setCodigoMensaje(oResultSet.getInt("CODMSJ"));
					oERevisionDocumento.setCodigoDocumento(oResultSet.getInt("CODDOC"));
					oERevisionDocumento.setCodigoDocumentoLaserFiche(oResultSet.getString("CODDOCLSF"));
					oERevisionDocumento.setNombreDocumento(oResultSet.getString("NOMDOC"));
					oERevisionDocumento.setNombreUsuarioTraslado(oResultSet.getString("USUREG"));
					oERevisionDocumento.setFechaTraslado(oResultSet.getDate("FECREG"));
					oERevisionDocumento.setHoraTraslado(oResultSet.getString("HORREG"));
					
					lstRevisionDocumento.add(oERevisionDocumento);
				}								
			}						
			
		} catch(Exception objEx) {
			UManejadorLog.error("Acceso: Problemas al obtener.", objEx);
		}
		return lstRevisionDocumento;
	}
	
	public List<EOperacionSolicitudCreditoDocumentoRequerido> listarEvaluacionSolicitudCreditoDocumentoRequerido() {
		List<Object> lstParametrosEntrada;
		ResultSet oResultSet = null;
		EOperacionSolicitudCreditoDocumentoRequerido oEOperacionSolicitudCreditoDocumentoRequerido= null;
		List<EOperacionSolicitudCreditoDocumentoRequerido> lstOperacionSolicitudCreditoDocumentoRequerido = null;
		
		try {	
			lstParametrosEntrada = new ArrayList<Object>();
			 
			oResultSet = objConexion.ejecutaConsulta(SP_ABACOINLEGAL_SEL_OPERACION_SOLICITUDCREDITO_DOCUMENTOREQUERIDO, lstParametrosEntrada, null);
			if (oResultSet != null) {
				lstOperacionSolicitudCreditoDocumentoRequerido = new ArrayList<EOperacionSolicitudCreditoDocumentoRequerido>();
				while (oResultSet.next()) {			
					oEOperacionSolicitudCreditoDocumentoRequerido=new EOperacionSolicitudCreditoDocumentoRequerido();
					oEOperacionSolicitudCreditoDocumentoRequerido.setCodigoDocumentoRequerido(oResultSet.getInt("CODDOCREQ"));
					oEOperacionSolicitudCreditoDocumentoRequerido.setDescripcionDocumentoRequerido(oResultSet.getString("DESCDOCREQ"));
					
					lstOperacionSolicitudCreditoDocumentoRequerido.add(oEOperacionSolicitudCreditoDocumentoRequerido);
				}								
			}						
			
		} catch(Exception objEx) {
			UManejadorLog.error("Acceso: Problemas al obtener.", objEx);
		}
		return lstOperacionSolicitudCreditoDocumentoRequerido;
	}
}
