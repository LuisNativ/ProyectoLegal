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
import com.abaco.negocio.util.UConstante.UEstado;
import com.abaco.negocio.util.UConstante.UEstadoAutorizacionJefe;
import com.abaco.negocio.util.UConstante.UIndicadorSesion;
import com.abaco.negocio.util.UConstante.UProcesoMantePostulante;
import com.abaco.negocio.util.UConstante.UTipoCorrelativo;
import com.abaco.negocio.util.UConstante.UTipoDocumentoGarantia;
import com.abaco.negocio.util.UConstante.UTipoEvaluacion;
import com.abaco.negocio.util.UConstante.UTipoPersona;
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

@ManagedBean(name = "mblistatramiteoperativosolicitud")
@ViewScoped
public class MBListaTramiteOperativoSolicitud implements Serializable {
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
	
	/* Variables Interfaz */
	@Getter @Setter private int codigoBuscar,codigoBuscarDocumento;
	@Getter @Setter private String descripcionBuscar,descripcionBuscarDocumento;
	@Getter @Setter private boolean indicadorNuevoDocumentoSolicitud;
	@Getter @Setter private boolean visualizarCodigoGarantiaDocumento;
	
	/* Variables Internas */
	
	/* Variables de carga File */
	private EOperacionDocumento oEOperacionDocumento,oEOperacionDocumentoDetalle;

  	
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
	@Getter @Setter private boolean renderizarBotonEvaluacionSolicitud;
	@Getter @Setter private boolean renderizarBotonObservacion;
	@Getter @Setter private boolean deshabilitarObservacionConformidad;

	@Getter @Setter private List<EGeneral> lstValorSiNo;

	@Getter @Setter private List<EOperacionSolicitud> lstTramiteOperativoSolicitudLegal;
	@Getter @Setter private List<EOperacionSolicitud> lstTramiteOperativoSolicitudLegalFiltro;
	@Getter @Setter private List<EOperacionSolicitud> lstObservacionTramiteOperativoSolicitud;
	@Getter @Setter private String observacionSolicitud;

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
		lstOperacionDocumento = new ArrayList<EOperacionDocumento>();
		lstOperacionDocumentoFiltro = new ArrayList<EOperacionDocumento>();
		lstDocumentoGarantia = new ArrayList<EOperacionDocumento>();
		lstEvaluacionSolicitudCreditoLegal = new ArrayList<EEvaluacionSolicitudCreditoLegal>();
		lstSolicitudDocumento = new ArrayList<EOperacionDocumento>();
		lstTramiteOperativoSolicitudLegal = new ArrayList<EOperacionSolicitud>();
		lstTramiteOperativoSolicitudLegalFiltro = new ArrayList<EOperacionSolicitud>();
		lstObservacionTramiteOperativoSolicitud = new ArrayList<EOperacionSolicitud>();
		oEUsuario = (EUsuario) UManejadorSesionWeb.obtieneVariableSesion(UVariablesSesion.USUARIO);
		inicializar();
		
		
		listarDesplegable();
		listarSolicitudTramiteOperativoLegal();
		
		if(oEUsuario.getCodigoArea() == UArea.LEGAL){
			renderizarBotonEvaluacionSolicitud = true;
			renderizarBotonObservacion = false;
		}else{
			renderizarBotonEvaluacionSolicitud = false;
			renderizarBotonObservacion = true;
		}
		
		
		
		
	}
	
	public void inicializar(){
		indicadorNuevoDocumentoSolicitud = true;
		visualizarCodigoGarantiaDocumento = true;
		deshabilitarBotonFirma = true;
	    visualizarGrabarDocumento = false;
	    deshabilitarObservacionDocumento = false;
	    deshabilitarAdjuntaDocumento = false;
	    visualizarEliminarDocumentoGarantia = true;
	    deshabilitarObservacionConformidad = true;
	}
	
	public void listarDesplegable(){
		lstValorSiNo = oUManejadorListaDesplegable.obtieneValorSiNo();

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
	
	public void evaluarSolicitudTramiteOperativoLegal(EOperacionSolicitud oEOperacionSolicitudItem){
		String ruta = "";
		if(oEOperacionSolicitudItem != null){
			UManejadorSesionWeb.registraVariableSesion(UVariablesSesion.ACCION_EXTERNA, UAccionExterna.EDITAR);
			UManejadorSesionWeb.registraVariableSesion(UVariablesSesion.FICHA_PARAMETRO, oEOperacionSolicitudItem);
			
			ruta = "RegistroTramiteOperativoSolicitud.xhtml";
			UGeneradorQueryString objUGeneradorQueryString = new UGeneradorQueryString(ruta);
			UManejadorSesionWeb.redirigePagina(objUGeneradorQueryString.obtieneUrlConParametros());
		}
		
	}

	
	public void listarSolicitudTramiteOperativoLegal(){
		lstTramiteOperativoSolicitudLegalFiltro = oBOGarantia.listarSolicitudTramiteOperativoLegal(0, "",oEUsuario);
	}

	public void buscarSolicitudTramiteOperativo(){
		lstTramiteOperativoSolicitudLegalFiltro =oBOGarantia.listarSolicitudTramiteOperativoLegal(codigoBuscar,descripcionBuscar,oEUsuario);
	}
	
	public void listarObservacionSolicitudTramiteOperativo(EOperacionSolicitud oEOperacionSolicitud){
		lstObservacionTramiteOperativoSolicitud = oBOGarantia.listarObservacionSolicitudTramiteOperativoLegal(oEOperacionSolicitud);
	}
	
	public void visualizarObservacionesSolicitud(EOperacionSolicitud oEOperacionSolicitudItem){
		if(oEOperacionSolicitudItem != null){
			listarObservacionSolicitudTramiteOperativo(oEOperacionSolicitudItem);
			
			RequestContext.getCurrentInstance().execute("PF('dlgObservacionSolicitud').show();");
		}
	}
	
	public void consultarObservacionSolicitud(EOperacionSolicitud oEOperacionSolicitudItem){
		if(oEOperacionSolicitudItem != null){
			observacionSolicitud = oEOperacionSolicitudItem.getObservacionConformidad();
			deshabilitarObservacionConformidad = true;
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
