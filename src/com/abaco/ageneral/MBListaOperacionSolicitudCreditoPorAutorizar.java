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
import java.util.stream.Collectors;

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
import com.abaco.negocio.util.UConstante.UEstadoAutorizacion;
import com.abaco.negocio.util.UConstante.UEstadoAutorizacionJefe;
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
import com.abaco.negocio.util.UConstante.UTipoPersona;
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

@ManagedBean(name = "mblistaoperacionsolicitudcreditoporautorizar")
@ViewScoped
public class MBListaOperacionSolicitudCreditoPorAutorizar implements Serializable {
	private static final long serialVersionUID = 1L;
	private EMensaje oEMensaje;
	private EUsuario oEUsuario;
	
	private BOOperacion oBOOperacion;
	private UManejadorListaDesplegable oUManejadorListaDesplegable;
	
	@Getter @Setter private List<EOperacionSolicitudCredito> lstOperacionSolicitudCredito;
	//@Getter @Setter private List<EOperacionSolicitudCredito> lstOperacionSolicitudCreditoSolicitado;
	//@Getter @Setter private List<EOperacionSolicitudCredito> lstOperacionSolicitudCreditoAutorizadoPendiente;
	@Getter @Setter private List<EGeneral> lstAutorizacion;
	@Getter @Setter private List<EGeneral> lstAutorizacionFiltro;
	
	/* Variables Interfaz */
	@Getter @Setter private int numeroSolicitud;
	@Getter @Setter private int codigoAutorizacion;
	@Getter @Setter private String nombrePersona;
	
	/* Variables Internas */
	
	@PostConstruct
	public void inicio() {
		this.oEMensaje = new EMensaje();
		
		oBOOperacion = new BOOperacion();
		oUManejadorListaDesplegable = new UManejadorListaDesplegable();
		
		lstOperacionSolicitudCredito = new ArrayList<EOperacionSolicitudCredito>();
		//lstOperacionSolicitudCreditoSolicitado = new ArrayList<EOperacionSolicitudCredito>();
		//lstOperacionSolicitudCreditoAutorizadoPendiente = new ArrayList<EOperacionSolicitudCredito>();
		lstAutorizacion = new ArrayList<EGeneral>();
		lstAutorizacionFiltro = new ArrayList<EGeneral>();

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
			oEOperacionSolicitudCreditoItem.setIndicadorMdlAutorizacion(1);
			
			UManejadorSesionWeb.registraVariableSesion(UVariablesSesion.OPERACION_SESION, oEOperacionSolicitudCreditoItem);
			UManejadorSesionWeb.registraVariableSesion(UVariablesSesion.ACCION_EXTERNA, UAccionExterna.EDITAR);
			UManejadorSesionWeb.registraVariableSesion(UVariablesSesion.FICHA_PARAMETRO, oEOperacionSolicitudCreditoItem);
			
			ruta = "RegistroOperacionSolicitudCredito.xhtml";
			
			UGeneradorQueryString objUGeneradorQueryString = new UGeneradorQueryString(ruta);
			UManejadorSesionWeb.redirigePagina(objUGeneradorQueryString.obtieneUrlConParametros());
		}
	}
	
	public void visualizar(EOperacionSolicitudCredito oEOperacionSolicitudCreditoItem) {
		String ruta = "";
		if (oEOperacionSolicitudCreditoItem != null) {
			UManejadorSesionWeb.registraVariableSesion(UVariablesSesion.ACCION_EXTERNA, UAccionExterna.VER);
			UManejadorSesionWeb.registraVariableSesion(UVariablesSesion.FICHA_PARAMETRO, oEOperacionSolicitudCreditoItem);
			
			ruta = "RegistroOperacionSolicitudCredito.xhtml";
			
			UGeneradorQueryString objUGeneradorQueryString = new UGeneradorQueryString(ruta);
			UManejadorSesionWeb.redirigePagina(objUGeneradorQueryString.obtieneUrlConParametros());
		}
	}
	
	public void listarSolicitud() {
		EOperacionSolicitudCredito oEOperacionSolicitudCredito = new EOperacionSolicitudCredito();
		oEOperacionSolicitudCredito.setUsuarioRegistro(oEUsuario);
		oEOperacionSolicitudCredito.setNombreLargo(nombrePersona);
		oEOperacionSolicitudCredito.setNumeroSolicitud(numeroSolicitud);
		oEOperacionSolicitudCredito.setCodigoEstadoActual("");
		oEOperacionSolicitudCredito.setCodigoAutorizacion(codigoAutorizacion);
		oEOperacionSolicitudCredito.setUsuarioRegistro(oEUsuario);
		
		lstOperacionSolicitudCredito = oBOOperacion.listarEvaluacionSolicitudCredito(oEOperacionSolicitudCredito, 3);
		
		/*
		if (lstOperacionSolicitudCredito != null){
			lstOperacionSolicitudCreditoSolicitado = new ArrayList<EOperacionSolicitudCredito>();
	        for (EOperacionSolicitudCredito obj: lstOperacionSolicitudCredito) {
	            if (obj.getCodigoAutorizacion() == UEstadoAutorizacionJefe.SOLICITADO) {
	            	lstOperacionSolicitudCreditoSolicitado.add(obj);
	            }
	        }
	        
	        lstOperacionSolicitudCreditoAutorizadoPendiente = new ArrayList<EOperacionSolicitudCredito>();
	        for (EOperacionSolicitudCredito obj: lstOperacionSolicitudCredito) {
	        	if (obj.getCodigoAutorizacion() == UEstadoAutorizacionJefe.AUTORIZADO) {
	            	lstOperacionSolicitudCreditoAutorizadoPendiente.add(obj);
	            }
	        }
		}
		*/
	}
	
	public void listarDesplegable(){
		lstAutorizacion = oUManejadorListaDesplegable.obtieneAutorizaJefe();
		lstAutorizacionFiltro = lstAutorizacion.stream()
				.filter(x -> x.getCodigo2() != UEstadoAutorizacionJefe.RECHAZADO)
				.filter(x -> x.getCodigo2() != UEstadoAutorizacionJefe.AUTORIZADOCOMPLETADO)
				.collect(Collectors.toList());
	}
	
	public void inicializar() {
		nombrePersona= "";
		codigoAutorizacion = 0;
	}
	
	public EMensaje getoEMensaje() {
		return oEMensaje;
	}

	public void setoEMensaje(EMensaje oEMensaje) {
		this.oEMensaje = oEMensaje;
	}
}
