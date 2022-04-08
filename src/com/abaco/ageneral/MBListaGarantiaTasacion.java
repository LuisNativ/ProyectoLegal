package com.abaco.ageneral;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

import lombok.Getter;
import lombok.Setter;

import org.primefaces.context.RequestContext;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;
import org.primefaces.model.UploadedFile;

import com.abaco.ageneral.BOSolicitudCredito;
import com.abaco.ageneral.ESolicitudCredito;
import com.abaco.bo.BOGeneral;
import com.abaco.ageneral.ESolicitudCredito;
import com.abaco.entidad.EGeneral;
import com.abaco.entidad.EPersona;
import com.abaco.entidad.EUsuario;
import com.abaco.entidad.EMensaje;
import com.abaco.negocio.util.UConstante.UAccionExterna;
import com.abaco.negocio.util.UConstante.UArea;
import com.abaco.negocio.util.UConstante.UCantidadCaracteres;
import com.abaco.negocio.util.UConstante.UEstado;
import com.abaco.negocio.util.UConstante.UEstadoAutorizacionJefe;
import com.abaco.negocio.util.UConstante.UFiltroGarantia;
import com.abaco.negocio.util.UConstante.UIndicadorSesion;
import com.abaco.negocio.util.UConstante.UProcesoMantePostulante;
import com.abaco.negocio.util.UConstante.UTipoCorrelativo;
import com.abaco.negocio.util.UConstante.UTipoDocumentoGarantia;
import com.abaco.negocio.util.UConstante.UTipoEvaluacion;
import com.abaco.negocio.util.UConstante.UVariablesQueryString;
import com.abaco.negocio.util.UConstante.UVariablesSesion;
import com.abaco.negocio.util.UFuncionesGenerales;
import com.abaco.negocio.util.UGeneradorQueryString;
import com.abaco.negocio.util.UManejadorArchivo;
import com.abaco.negocio.util.UManejadorListaDesplegable;
import com.abaco.negocio.util.UManejadorLog;
import com.abaco.negocio.util.UManejadorSesionWeb;
import com.abaco.negocio.util.UtilWeb;
import com.abaco.servicio.laserfiche.Documento;

@ManagedBean(name = "mblistagarantiatasacion")
@ViewScoped
public class MBListaGarantiaTasacion implements Serializable {
	private static final long serialVersionUID = 1L;
	private EMensaje oEMensaje;
	private EGarantia oEGarantiaData;
	private EUsuario oEUsuario;
	private BOGarantia oBOGarantia;
	
	private UManejadorListaDesplegable oUManejadorListaDesplegable;
	
	/* Variables Interfaz */
	@Getter @Setter private int codigoBuscar;
	@Getter @Setter private String descripcionBuscar;

	
	@Getter @Setter private List<EGarantia> lstGarantia;
	
	@Getter @Setter private int cantidadCaracteres;

	@PostConstruct
	public void inicio() {
		this.oEMensaje = new EMensaje();
		
		oBOGarantia = new BOGarantia();
		oEGarantiaData = new EGarantia();
		oUManejadorListaDesplegable = new UManejadorListaDesplegable();
		
		lstGarantia = new ArrayList<EGarantia>();
	
		oEUsuario = (EUsuario) UManejadorSesionWeb.obtieneVariableSesion(UVariablesSesion.USUARIO);
		listarDesplegable();
		listarGarantias();
		validarLongitudCaracteres();
		inicializar();
	}
	
	public void inicializar(){

	}
	
	public void listarDesplegable(){
		

	}
	
	public void listarGarantias(){
		lstGarantia = oBOGarantia.listarGarantia(0, "",UFiltroGarantia.VIGENTE);
	}
	
	public void buscarGarantia(){
		lstGarantia = oBOGarantia.listarGarantia(codigoBuscar, descripcionBuscar.trim(),UFiltroGarantia.VIGENTE);
	}
	
	/*Metodo para Obtener la cantidad maxima de caracteres por cada opcion
	 * de busqueda*/
	public void validarLongitudCaracteres(){
		descripcionBuscar = "";
		switch(codigoBuscar){
		  case 1: cantidadCaracteres = UCantidadCaracteres.CODIGO_SOCIO; 
		          break;
		  case 2: cantidadCaracteres = UCantidadCaracteres.NOMBRE_SOCIO; 
		          break;
		  case 3: cantidadCaracteres = UCantidadCaracteres.CODIGO_GARANTIA; 
		          break;
		  case 4: cantidadCaracteres = UCantidadCaracteres.PARTIDA_REGISTRAL; 
				  break;
		  default: cantidadCaracteres= UCantidadCaracteres.POR_DEFECTO;
		}
	}
	
	public void modificarGarantia(EGarantia oEGarantiaItem) {
		String ruta = "";
		if (oEGarantiaItem != null) {
			UManejadorSesionWeb.registraVariableSesion(UVariablesSesion.ACCION_EXTERNA, UAccionExterna.EDITARPARCIAL);
			UManejadorSesionWeb.registraVariableSesion(UVariablesSesion.FICHA_PARAMETRO, oEGarantiaItem);

			ruta = "MantenimientoOperacionGarantia.xhtml";
			UGeneradorQueryString objUGeneradorQueryString = new UGeneradorQueryString(ruta);
			UManejadorSesionWeb.redirigePagina(objUGeneradorQueryString.obtieneUrlConParametros());
		}
	}
	

	
	public void evaluarSolicitudDocumento(EOperacionDocumento oEOperacionDocumentoItem){
		
		if (oEOperacionDocumentoItem != null) {
			
			String ruta = "";
			//if (oEGarantiaSelected != null) {
				UManejadorSesionWeb.registraVariableSesion(UVariablesSesion.ACCION_EXTERNA, UAccionExterna.EDITAR);
				//UManejadorSesionWeb.registraVariableSesion(UVariablesSesion.FICHA_PARAMETRO, oEGarantiaSelected);
				
				ruta = "MantenimientoOperacionGarantia.xhtml";
				UGeneradorQueryString objUGeneradorQueryString = new UGeneradorQueryString(ruta);
				UManejadorSesionWeb.redirigePagina(objUGeneradorQueryString.obtieneUrlConParametros());
			//}
		}
	}
	
	
	
	public void actualizarDatosAjax(){
	}

	public EGarantia getoEGarantiaData() {
		return oEGarantiaData;
	}

	public void setoEGarantiaData(EGarantia oEGarantiaData) {
		this.oEGarantiaData = oEGarantiaData;
	}

	public EMensaje getoEMensaje() {
		return oEMensaje;
	}

	public void setoEMensaje(EMensaje oEMensaje) {
		this.oEMensaje = oEMensaje;
	}

		
		
	

}
