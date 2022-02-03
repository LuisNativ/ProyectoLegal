package com.abaco.ageneral;

import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;






import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;

import lombok.Getter;
import lombok.Setter;

import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.DataFormat;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.VerticalAlignment;
import org.apache.poi.xssf.streaming.SXSSFSheet;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.primefaces.context.RequestContext;
import org.primefaces.event.TabChangeEvent;
import org.primefaces.json.JSONObject;

import com.abaco.bo.BOGeneral;
import com.abaco.bo.BOUsuario;
import com.abaco.entidad.EGeneral;
import com.abaco.entidad.EPersona;
import com.abaco.entidad.EPersonaParametro;
import com.abaco.entidad.EUsuario;
import com.abaco.entidad.EMensaje;
import com.abaco.negocio.util.UConstante;
import com.abaco.negocio.util.UConstante.UAccionExterna;
import com.abaco.negocio.util.UConstante.UArea;
import com.abaco.negocio.util.UConstante.UDatePattern;
import com.abaco.negocio.util.UConstante.UEstado;
import com.abaco.negocio.util.UConstante.UEstadoLegal;
import com.abaco.negocio.util.UConstante.UIndicadorSesion;
import com.abaco.negocio.util.UConstante.UIndicadorTipoReasignar;
import com.abaco.negocio.util.UConstante.UData;
import com.abaco.negocio.util.UConstante.URutaCarpetaCompartida;
import com.abaco.negocio.util.UConstante.USistemaOperativo;
import com.abaco.negocio.util.UConstante.UTipoArchivo;
import com.abaco.negocio.util.UConstante.UTipoBusquedaTercero;
import com.abaco.negocio.util.UConstante.UTipoCliente;
import com.abaco.negocio.util.UConstante.UTipoCorrelativo;
import com.abaco.negocio.util.UConstante.UTipoEnvio;
import com.abaco.negocio.util.UConstante.UTipoEstadoUsuario;
import com.abaco.negocio.util.UConstante.UTipoEvaluacion;
import com.abaco.negocio.util.UConstante.UUbicacion;
import com.abaco.negocio.util.UConstante.UVariablesQueryString;
import com.abaco.negocio.util.UConstante.UVariablesSesion;
import com.abaco.negocio.util.UFuncionesGenerales;
import com.abaco.negocio.util.UGeneradorQueryString;
import com.abaco.negocio.util.UManejadorListaDesplegable;
import com.abaco.negocio.util.UManejadorLog;
import com.abaco.negocio.util.UManejadorSesionWeb;
import com.abaco.negocio.util.UtilPoi;
import com.abaco.negocio.util.UtilWeb;

@ManagedBean(name = "mblistaoperacionsolicitudcreditootros")
@ViewScoped
public class MBListaOperacionSolicitudCreditoOtros implements Serializable {
	private static final long serialVersionUID = 1L;
	private EMensaje oEMensaje;
	private EUsuario oEUsuario;
	
	private BOOperacion oBOOperacion;
	private UManejadorListaDesplegable oUManejadorListaDesplegable;
	
	@Getter @Setter private List<EOperacionSolicitudCredito> lstOperacionSolicitudCredito;
	@Getter @Setter private List<EOperacionSolicitudCredito> lstOperacionSolicitudCreditoPendiente;
	@Getter @Setter private List<EOperacionSolicitudCredito> lstOperacionSolicitudCreditoHistorico;
	@Getter @Setter private List<EGeneral> lstEstado;
	@Getter @Setter private List<EGeneral> lstAutorizacion;
	@Getter @Setter private List<EPersona> lstPersona;
	
	/* Variables Interfaz */
	@Getter @Setter private int numeroSolicitud;
	@Getter @Setter private String codigoEstado;
	@Getter @Setter private int codigoAutorizacion;
	@Getter @Setter private String nombrePersona;
	
	/* Variables Internas */
	
	@PostConstruct
	public void inicio() {
		this.oEMensaje = new EMensaje();
		
		oBOOperacion = new BOOperacion();
		oUManejadorListaDesplegable = new UManejadorListaDesplegable();
		
		lstOperacionSolicitudCredito = new ArrayList<EOperacionSolicitudCredito>();
		lstOperacionSolicitudCreditoPendiente = new ArrayList<EOperacionSolicitudCredito>();
		lstOperacionSolicitudCreditoHistorico = new ArrayList<EOperacionSolicitudCredito>();
		lstEstado = new ArrayList<EGeneral>();
		lstAutorizacion = new ArrayList<EGeneral>();

		oEUsuario = (EUsuario) UManejadorSesionWeb.obtieneVariableSesion(UVariablesSesion.USUARIO);
		
		/*
		if (UManejadorSesionWeb.obtieneVariableSesion(UVariablesSesion.OPERACION_SESION) != null) {
			EOperacionSolicitud oEOperacionSolicitud = (EOperacionSolicitud) UManejadorSesionWeb.obtieneVariableSesion(UVariablesSesion.OPERACION_SESION);
			oBOOperacion.liberarSolicitudSesion(oEOperacionSolicitud.getCodigoSolicitud());
			UManejadorSesionWeb.eliminaVariableSesion(UVariablesSesion.OPERACION_SESION);
		}
		*/
		
		inicializar();
		listarSolicitud();
		listarDesplegable();
	}
	
	public void modificar(EOperacionSolicitudCredito oEOperacionSolicitudCreditoItem) {
		String ruta = "";
		if (oEOperacionSolicitudCreditoItem != null) {
			oEOperacionSolicitudCreditoItem.setUsuarioRegistro(oEUsuario);
			
			UManejadorSesionWeb.registraVariableSesion(UVariablesSesion.OPERACION_SESION, oEOperacionSolicitudCreditoItem);
			UManejadorSesionWeb.registraVariableSesion(UVariablesSesion.ACCION_EXTERNA, UAccionExterna.EDITAR);
			UManejadorSesionWeb.registraVariableSesion(UVariablesSesion.FICHA_PARAMETRO, oEOperacionSolicitudCreditoItem);
			
			ruta = "RegistroOperacinSolicitudCredito.xhtml";
			
			UGeneradorQueryString objUGeneradorQueryString = new UGeneradorQueryString(ruta);
			UManejadorSesionWeb.redirigePagina(objUGeneradorQueryString.obtieneUrlConParametros());
		}
	}
	
	public void visualizar(EOperacionSolicitudCredito oEOperacionSolicitudCreditoItem) {
		String ruta = "";
		if (oEOperacionSolicitudCreditoItem != null) {
			UManejadorSesionWeb.registraVariableSesion(UVariablesSesion.ACCION_EXTERNA, UAccionExterna.VER);
			UManejadorSesionWeb.registraVariableSesion(UVariablesSesion.FICHA_PARAMETRO, oEOperacionSolicitudCreditoItem);
			
			ruta = "RegistroOperacinSolicitudCredito.xhtml";
			
			UGeneradorQueryString objUGeneradorQueryString = new UGeneradorQueryString(ruta);
			UManejadorSesionWeb.redirigePagina(objUGeneradorQueryString.obtieneUrlConParametros());
		}
	}
	
	public void listarSolicitud() {
		EOperacionSolicitudCredito oEOperacionSolicitudCredito = new EOperacionSolicitudCredito();
		oEOperacionSolicitudCredito.setUsuarioRegistro(oEUsuario);
		oEOperacionSolicitudCredito.setNombreLargo(nombrePersona);
		oEOperacionSolicitudCredito.setNumeroSolicitud(numeroSolicitud);
		oEOperacionSolicitudCredito.setCodigoEstadoActual(codigoEstado);
		oEOperacionSolicitudCredito.setCodigoAutorizacion(codigoAutorizacion);
		oEOperacionSolicitudCredito.setUsuarioRegistro(oEUsuario);
		
		lstOperacionSolicitudCredito = oBOOperacion.listarEvaluacionSolicitudCredito(oEOperacionSolicitudCredito, 2);
		
		if (lstOperacionSolicitudCredito != null){
			lstOperacionSolicitudCreditoPendiente = new ArrayList<EOperacionSolicitudCredito>();
	        for (EOperacionSolicitudCredito obj: lstOperacionSolicitudCredito) {
	            if (obj.getCodigoEstadoActual().equals(UEstadoLegal.OBSERVADO)) {
	            	lstOperacionSolicitudCreditoPendiente.add(obj);
	            }
	        }
	        
	        lstOperacionSolicitudCreditoHistorico = new ArrayList<EOperacionSolicitudCredito>();
	        for (EOperacionSolicitudCredito obj: lstOperacionSolicitudCredito) {
	            if (obj.getCodigoEstadoActual().equals("") ||
	            	obj.getCodigoEstadoActual().equals(UEstadoLegal.ENEVALUACION) ||
	            	obj.getCodigoEstadoActual().equals(UEstadoLegal.EVALUADO) ||
	            	obj.getCodigoEstadoActual().equals(UEstadoLegal.DESAPROBADO) ||
	            	obj.getCodigoEstadoActual().equals(UEstadoLegal.APROBADO)) {
	            	lstOperacionSolicitudCreditoHistorico.add(obj);
	            }
	        }
		}
	}
	
	public void listarDesplegable(){
		lstEstado = oUManejadorListaDesplegable.obtieneEstadoLegal();
		lstAutorizacion = oUManejadorListaDesplegable.obtieneAutorizaJefe();
	}
	
	/*
	public void obtenerOpcionEditar(EOperacionSolicitudCredito oEOperacionSolicitudCreditoItem){
		if(oEUsuario.getIndicadorJefeInmediato() == 0){
			if(oEUsuario.getCodigoUbicacion() == oEOperacionSolicitudCreditoItem.getCodigoUbicacionRevision()){
				
			}
		}else if(oEUsuario.getIndicadorJefeInmediato() > 0){
			if(oEUsuario.getCodigoUbicacion() == oEOperacionSolicitudCreditoItem.getCodigoUbicacionRevision()) {
				
			}
		}
	}
	*/
	
	public void inicializar() {
		nombrePersona= "";
		codigoEstado = "0";
		codigoAutorizacion = 0;
	}
	
	public EMensaje getoEMensaje() {
		return oEMensaje;
	}

	public void setoEMensaje(EMensaje oEMensaje) {
		this.oEMensaje = oEMensaje;
	}
}
