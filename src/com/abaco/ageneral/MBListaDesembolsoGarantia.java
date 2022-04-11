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
import com.abaco.negocio.util.UConstante.UIndicadorSesion;
import com.abaco.negocio.util.UConstante.UProcesoMantePostulante;
import com.abaco.negocio.util.UConstante.UTipoCorrelativo;
import com.abaco.negocio.util.UConstante.UTipoDocumentoGarantia;
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

@ManagedBean(name = "mblistadesembolsogarantia")
@ViewScoped
public class MBListaDesembolsoGarantia implements Serializable {
	private static final long serialVersionUID = 1L;
	private EMensaje oEMensaje;
	private EGarantia oEGarantiaData;
	private EUsuario oEUsuario;
	private BOOperacion oBOOperacion;
	private BOGarantia oBOGarantia;
	
	private UManejadorListaDesplegable oUManejadorListaDesplegable;
	
	private BOGeneral oBOGeneral;
	private BOSolicitudCredito oBOSolicitudCredito;
	private EGarantia oEGarantiaSelected;
	
	@Getter @Setter private List<EEvaluacionSolicitudCreditoLegal> lstEvaluacionSolicitudCreditoLegal;
	@Getter @Setter private List<EOperacionDocumento> lstSolicitudDocumento;
	@Getter @Setter private List<EFlagReqLegal> lstDetalleFlagsReqLegal;
	
	/* Variables Interfaz */
	@Getter @Setter private int codigoBuscar,codigoBuscarDocumento;
	@Getter @Setter private String descripcionBuscar,descripcionBuscarDocumento;
	@Getter @Setter private boolean indicadorNuevoDocumentoSolicitud;
	@Getter @Setter private boolean visualizarCodigoGarantiaDocumento;
	
	/* Variables Internas */
	
	/* Variables de carga File */
	private EOperacionDocumento oEOperacionDocumento,oEOperacionDocumentoDetalle;
	@Getter @Setter private StreamedContent fileDownload;
	@Getter @Setter private List<UploadedFile> files;
	@Getter @Setter private List<EDocumentoCarga> lstDocumentoCarga;
	@Getter @Setter private int codigoFirmaSiNo;
    private int codigoGarantiaDocumento;
  	
	@Getter @Setter private List<EOperacionDocumento> lstOperacionDocumento,lstDocumentoGarantia;
	@Getter @Setter private List<EOperacionDocumento> lstOperacionDocumentoFiltro; 
	@Getter @Setter private boolean deshabilitarGrabarDocumento,deshabilitarBotonFirma;
	@Getter @Setter private boolean deshabilitarSolFirma;
	@Getter @Setter private boolean estadoDocumento; 
	@Getter @Setter private boolean deshabilitarObservacion;
	@Getter @Setter private boolean visualizarGrabarDocumento;
	@Getter @Setter private boolean  deshabilitarObservacionDocumento;
	@Getter @Setter private boolean indicadorTituloDocumento;
	@Getter @Setter private boolean deshabilitarAdjuntaDocumento;
	@Getter @Setter private boolean visualizarEliminarDocumentoGarantia;
	@Getter @Setter private boolean visualizarCampo1,visualizarCampo2;
	@Getter @Setter private int codigoEstadoDesembolsoGarantia;
	@Getter @Setter private int cantidadCaracteres;

	@Getter @Setter private List<EGeneral> lstValorSiNo;
	@Getter @Setter private List<EGeneral> lstEstadoDesembolsoGarantia;
	@Getter @Setter private List<EOperacionDocumento> lstOperacionDocumentoLegalFiltro;
	@Getter @Setter private List<EOperacionDocumento> lstOperacionDocumentoNegociosFiltro;
	@Getter @Setter private List<EOperacionDocumento> lstOperacionDocumentoDesembolso;


	@PostConstruct
	public void inicio() {
		this.oEMensaje = new EMensaje();
		
		oBOGeneral = new BOGeneral();
		oBOGarantia = new BOGarantia();
		oBOOperacion = new BOOperacion();
		oBOSolicitudCredito = new BOSolicitudCredito();
		oEGarantiaSelected = new EGarantia();
		oEGarantiaData = new EGarantia();
		oUManejadorListaDesplegable = new UManejadorListaDesplegable();
		
		oEOperacionDocumento = new EOperacionDocumento();
		oEOperacionDocumentoDetalle = new EOperacionDocumento();
		
		lstValorSiNo = new ArrayList<EGeneral>();
		lstEstadoDesembolsoGarantia = new ArrayList<EGeneral>();
		lstDocumentoCarga = new ArrayList<EDocumentoCarga>();
		files = new ArrayList<UploadedFile>(); 
		lstOperacionDocumento = new ArrayList<EOperacionDocumento>();
		lstOperacionDocumentoFiltro = new ArrayList<EOperacionDocumento>();
		lstDocumentoGarantia = new ArrayList<EOperacionDocumento>();
		lstEvaluacionSolicitudCreditoLegal = new ArrayList<EEvaluacionSolicitudCreditoLegal>();
		lstSolicitudDocumento = new ArrayList<EOperacionDocumento>();
		lstOperacionDocumentoLegalFiltro = new ArrayList<EOperacionDocumento>();
		lstOperacionDocumentoNegociosFiltro = new ArrayList<EOperacionDocumento>();
		lstOperacionDocumentoDesembolso = new ArrayList<EOperacionDocumento>();
		lstDetalleFlagsReqLegal = new ArrayList<EFlagReqLegal>();
		
		oEUsuario = (EUsuario) UManejadorSesionWeb.obtieneVariableSesion(UVariablesSesion.USUARIO);
		inicializar();
		listarDesplegable();
		listarSolicitudDesembolsoGarantia();
		
	}
	
	public void inicializar(){
		indicadorNuevoDocumentoSolicitud = true;
		visualizarCodigoGarantiaDocumento = true;
		deshabilitarBotonFirma = true;
	    visualizarGrabarDocumento = false;
	    deshabilitarObservacionDocumento = false;
	    deshabilitarAdjuntaDocumento = false;
	    visualizarEliminarDocumentoGarantia = true;
	    visualizarCampo1 = true;
	    visualizarCampo2 = false;
	}
	
	public void listarDesplegable(){
		lstValorSiNo = oUManejadorListaDesplegable.obtieneValorSiNo();
		lstEstadoDesembolsoGarantia = oUManejadorListaDesplegable.obtieneEstadoDesembolsoGarantia();

	}
	
	public void modificar(EEvaluacionSolicitudCreditoLegal oEEvaluacionSolicitudCreditoLegalItem) {
		String ruta = "";
		if (oEEvaluacionSolicitudCreditoLegalItem != null) {
			//UManejadorSesionWeb.registraVariableSesion(UVariablesSesion.OPERACION_SESION, oEOperacionSolicitud);
			UManejadorSesionWeb.registraVariableSesion(UVariablesSesion.ACCION_EXTERNA, UAccionExterna.EDITAR);
			UManejadorSesionWeb.registraVariableSesion(UVariablesSesion.FICHA_PARAMETRO, oEEvaluacionSolicitudCreditoLegalItem);
			
			ruta = "RegistroOperacionCliente.xhtml";
			UGeneradorQueryString objUGeneradorQueryString = new UGeneradorQueryString(ruta);
			UManejadorSesionWeb.redirigePagina(objUGeneradorQueryString.obtieneUrlConParametros());
		}
	}
	
	public void listarSolicitudDesembolsoGarantia() {
		EGarantia eGarantia = new EGarantia();
		eGarantia.setUsuarioRegistro(oEUsuario);
		eGarantia.setTipoDocumento(UTipoDocumentoGarantia.CONSTITUCION);
		lstOperacionDocumentoDesembolso = oBOGarantia.listarSolicitudDesembolsoGarantia(0, "", eGarantia);
	}
	
	public void buscarSolicitudDesembolsoGarantia(){
		EGarantia eGarantia = new EGarantia();
		eGarantia.setUsuarioRegistro(oEUsuario);
		eGarantia.setTipoDocumento(UTipoDocumentoGarantia.CONSTITUCION);
		if(codigoBuscar == 3 ) {
			descripcionBuscar = codigoEstadoDesembolsoGarantia+"";
		}
		lstOperacionDocumentoDesembolso = oBOGarantia.listarSolicitudDesembolsoGarantia(codigoBuscar,descripcionBuscar.trim(), eGarantia);
	}

	public void visualizarDetalleCumplimientoReqLegal(EOperacionDocumento eOperacionDocumentoItem){
		if(eOperacionDocumentoItem != null){
			lstDetalleFlagsReqLegal = oBOGarantia.listarDetalleFlagRequisitoLegal(eOperacionDocumentoItem.getCodigoSolicitudCredito(),eOperacionDocumentoItem.getSecuenciaGarantia());
			RequestContext.getCurrentInstance().execute("PF('dlgCondicionDesembolso').show();");
		}
	}
	
	public void validarCriterioBusquedaSolicitud(){
		descripcionBuscar = "";
		switch(codigoBuscar){
		 case 1: 
			 cantidadCaracteres = UCantidadCaracteres.CODIGO_SOLICITUD;
			 visualizarCampo1 = true; 
			 visualizarCampo2 = false;
			 break;
		 case 2:
			 cantidadCaracteres = UCantidadCaracteres.CODIGO_GARANTIA;
			 visualizarCampo1 = true; 
			 visualizarCampo2 = false; 
			 break;
		 case 3:
			 cantidadCaracteres= UCantidadCaracteres.POR_DEFECTO;
			 visualizarCampo1 = false; 
			 visualizarCampo2 = true; 
			 break;
		 default:
			 cantidadCaracteres= UCantidadCaracteres.POR_DEFECTO;
			 visualizarCampo1 = true; 
			 visualizarCampo2 = false; 
		}
	}

	public EGarantia getoEGarantiaData() {
		return oEGarantiaData;
	}

	public void setoEGarantiaData(EGarantia oEGarantiaData) {
		this.oEGarantiaData = oEGarantiaData;
	}

	public EOperacionDocumento getoEOperacionDocumento() {
		return oEOperacionDocumento;
	}

	public void setoEOperacionDocumento(EOperacionDocumento oEOperacionDocumento) {
		this.oEOperacionDocumento = oEOperacionDocumento;
	}

	public int getCodigoGarantiaDocumento() {
		return codigoGarantiaDocumento;
	}

	public void setCodigoGarantiaDocumento(int codigoGarantiaDocumento) {
		this.codigoGarantiaDocumento = codigoGarantiaDocumento;
	}

	public EMensaje getoEMensaje() {
		return oEMensaje;
	}

	public void setoEMensaje(EMensaje oEMensaje) {
		this.oEMensaje = oEMensaje;
	}

	public EOperacionDocumento getoEOperacionDocumentoDetalle() {
		return oEOperacionDocumentoDetalle;
	}

	public void setoEOperacionDocumentoDetalle(
			EOperacionDocumento oEOperacionDocumentoDetalle) {
		this.oEOperacionDocumentoDetalle = oEOperacionDocumentoDetalle;
	}


		
		
	

}
