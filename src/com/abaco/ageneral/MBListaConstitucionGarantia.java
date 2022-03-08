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

import org.primefaces.component.tabview.TabView;
import org.primefaces.context.RequestContext;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.event.TabChangeEvent;
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

@ManagedBean(name = "mblistaconstituciongarantia")
@ViewScoped
public class MBListaConstitucionGarantia implements Serializable {
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
	@Getter @Setter private StreamedContent fileDownload;
	@Getter @Setter private List<UploadedFile> files;
	@Getter @Setter private List<EDocumentoCarga> lstDocumentoCarga;
	@Getter @Setter private int codigoFirmaSiNo;
	@Getter @Setter private int codigoTabview2Index;
    private int codigoGarantiaDocumento;
  	
	@Getter @Setter private List<EOperacionDocumento> lstOperacionDocumento,lstDocumentoGarantia;
	@Getter @Setter private List<EOperacionDocumento> lstDocumentoGarantiaPendiente;
	@Getter @Setter private List<EOperacionDocumento> lstDocumentoGarantiaHistorico;
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
	@Getter @Setter private boolean visualizarActualizacionFirmaDocGarantia;

	@Getter @Setter private List<EGeneral> lstValorSiNo;
	@Getter @Setter private List<EOperacionDocumento> lstOperacionDocumentoLegalFiltro;
	@Getter @Setter private List<EOperacionDocumento> lstOperacionDocumentoNegociosFiltro;

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
		lstDocumentoCarga = new ArrayList<EDocumentoCarga>();
		files = new ArrayList<UploadedFile>(); 
		lstOperacionDocumento = new ArrayList<EOperacionDocumento>();
		lstOperacionDocumentoFiltro = new ArrayList<EOperacionDocumento>();
		lstDocumentoGarantia = new ArrayList<EOperacionDocumento>();
		lstDocumentoGarantiaPendiente = new ArrayList<EOperacionDocumento>();
		lstDocumentoGarantiaHistorico = new ArrayList<EOperacionDocumento>();
		lstEvaluacionSolicitudCreditoLegal = new ArrayList<EEvaluacionSolicitudCreditoLegal>();
		lstSolicitudDocumento = new ArrayList<EOperacionDocumento>();
		lstOperacionDocumentoLegalFiltro = new ArrayList<EOperacionDocumento>();
		lstOperacionDocumentoNegociosFiltro = new ArrayList<EOperacionDocumento>();
		
		oEUsuario = (EUsuario) UManejadorSesionWeb.obtieneVariableSesion(UVariablesSesion.USUARIO);
		listarSolicitudDocumento();
		listarDesplegable();
		inicializar();
	}
	
	public void inicializar(){
		indicadorNuevoDocumentoSolicitud = true;
		visualizarCodigoGarantiaDocumento = true;
		deshabilitarBotonFirma = true;
	    visualizarGrabarDocumento = false;
	    deshabilitarObservacionDocumento = false;
	    deshabilitarAdjuntaDocumento = false;
	    visualizarEliminarDocumentoGarantia = true;
	    visualizarActualizacionFirmaDocGarantia = true;
	    codigoTabview2Index = 0;
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
	

	
	public void evaluarSolicitudDocumento(EOperacionDocumento oEOperacionDocumentoItem){
		
		if (oEOperacionDocumentoItem != null) {
			
			oEGarantiaSelected = oBOGarantia.buscarGarantia(oEOperacionDocumentoItem.getCodigoGarantia());

			String ruta = "";
			if (oEGarantiaSelected != null) {
				UManejadorSesionWeb.registraVariableSesion(UVariablesSesion.ACCION_EXTERNA, UAccionExterna.EDITAR);
				UManejadorSesionWeb.registraVariableSesion(UVariablesSesion.FICHA_PARAMETRO, oEGarantiaSelected);
				
				ruta = "MantenimientoOperacionGarantia.xhtml";
				UGeneradorQueryString objUGeneradorQueryString = new UGeneradorQueryString(ruta);
				UManejadorSesionWeb.redirigePagina(objUGeneradorQueryString.obtieneUrlConParametros());
			}
		}
	}
	
	
	
	//DOCUMENTACIONNNNN
	//*************************************//
	//Metodos para documento de carga
    //*************************************//
	
	/**Metodos de Cabecera
	 * */


	public void grabarDocumento(){
		if(oEOperacionDocumento != null){
			generarCorrelativoDocumentoCarga();
			List<EDocumentoCarga> elstDocumentoCarga = new ArrayList<EDocumentoCarga>();
			elstDocumentoCarga = lstDocumentoCarga;
			EGarantia oEGarantia = new EGarantia();
			EOperacionDocumento eOperacionDocumento = new EOperacionDocumento();
			eOperacionDocumento = oEOperacionDocumento;
			if(lstOperacionDocumento != null){
				if(lstOperacionDocumento.size()>0){
					oEGarantia.setLstOperacionDocumento(lstOperacionDocumento);
				}	
			}
			
			visualizarGrabarDocumento = false;
			lstDocumentoCarga = new ArrayList<EDocumentoCarga>();
			
			oEGarantia.setUsuarioRegistro(oEUsuario);	
			oEGarantia.setTipoDocumento(UTipoDocumentoGarantia.CONSTITUCION);
			oEGarantia.setNumeroSolicitud(eOperacionDocumento.getCodigoSolicitud());
			oEGarantia.setNumeroSolicitudCredito(eOperacionDocumento.getCodigoSolicitudCredito());
			oEGarantia.setCodigoAreaEmisora(oEUsuario.getCodigoArea());
			oEGarantia.setSecuenciaDocumento(eOperacionDocumento.getCodigoDocumento());
			oEGarantia.setCodigoGarantia(eOperacionDocumento.getCodigoGarantia());
			oEGarantia.setFirmaDocumento("NO");
			oEGarantia.setObservacionDocumento("");
			oEMensaje = oBOGarantia.agregarDetalleSolicitudDocumentoGarantia(oEGarantia, elstDocumentoCarga);
			UManejadorLog.log(" Guardar: " + oEMensaje.getDescMensaje());
			RequestContext.getCurrentInstance().execute("PF('dlgMensajeOperacionAjax').show();");
		}
		
	
		
	}
	
	

	

	
	//Documento Carga
	public void agregarDocumentoCarga(FileUploadEvent objUploadEvent) {
			UploadedFile oUploadedFile = objUploadEvent.getFile();
			files.add(oUploadedFile);
			for(int i=0;i<files.size();i++){
				EDocumentoCarga oEDocumentoCarga = new EDocumentoCarga();
				if(lstDocumentoCarga.size() > 0){
					boolean isValida = false;
					for(int x=0;x<lstDocumentoCarga.size();x++){
						if(lstDocumentoCarga.get(x).getNombre().equals(UFuncionesGenerales.obtieneTipoArchivo(files.get(i).getFileName()))){
							isValida = true;
						}
					}
					if(!isValida){
						oEDocumentoCarga.setNombre(files.get(i).getFileName() + UFuncionesGenerales.obtieneTipoArchivo(files.get(i).getFileName()));
						oEDocumentoCarga.setNombreOriginal(files.get(i).getFileName());
						oEDocumentoCarga.setData(files.get(i).getContents());
						oEDocumentoCarga.setSize(files.get(i).getSize());
						oEDocumentoCarga.setDescripcionSize(UFuncionesGenerales.getSize(files.get(i).getSize()));
						lstDocumentoCarga.add(oEDocumentoCarga);
					}
				}else{
					oEDocumentoCarga.setNombre(files.get(i).getFileName() + UFuncionesGenerales.obtieneTipoArchivo(files.get(i).getFileName()));
					oEDocumentoCarga.setNombreOriginal(files.get(i).getFileName());
					oEDocumentoCarga.setData(files.get(i).getContents());
					oEDocumentoCarga.setSize(files.get(i).getSize());
					oEDocumentoCarga.setDescripcionSize(UFuncionesGenerales.getSize(files.get(i).getSize()));
					lstDocumentoCarga.add(oEDocumentoCarga);
				}
			}
			if(lstDocumentoCarga.size()>0) visualizarGrabarDocumento = true;
			files = new ArrayList<UploadedFile>();
		}
	
	public void onTabChangeDocumentoGarantia(TabChangeEvent event){
		TabView tv = (TabView) event.getTab().getParent();		
		if(tv.getActiveIndex() == 0) codigoTabview2Index = 0;
		else codigoTabview2Index = 1;
	}
		
	public void listarSolicitudDocumento() {
		EGarantia eGarantia = new EGarantia();
		eGarantia.setUsuarioRegistro(oEUsuario);
		eGarantia.setTipoDocumento(UTipoDocumentoGarantia.CONSTITUCION);
		lstDocumentoGarantia = oBOGarantia.listarSolicitudDocumentoGarantia(0,"",eGarantia);
		
		if(lstDocumentoGarantia != null){
			lstDocumentoGarantiaPendiente = new ArrayList<EOperacionDocumento>();
			for(EOperacionDocumento obj : lstDocumentoGarantia){				
				if(obj.getEstadoDocumento() != UEstado.DOCUMENTOFIRMADO){
					lstDocumentoGarantiaPendiente.add(obj);
				}				
			}			
			lstDocumentoGarantiaHistorico = new ArrayList<EOperacionDocumento>();
			for(EOperacionDocumento obj : lstDocumentoGarantia){
				if(obj.getEstadoDocumento() == UEstado.DOCUMENTOFIRMADO ){
					lstDocumentoGarantiaHistorico.add(obj);;
				}
				
			}
		}
	}
	public void buscarSolicitudDocumento(){
		EGarantia eGarantia = new EGarantia();
		eGarantia.setUsuarioRegistro(oEUsuario);
		eGarantia.setTipoDocumento(UTipoDocumentoGarantia.CONSTITUCION);
		lstDocumentoGarantia = oBOGarantia.listarSolicitudDocumentoGarantia(codigoBuscar, descripcionBuscar, eGarantia);

		if(codigoTabview2Index == 0){
			lstDocumentoGarantiaPendiente = new ArrayList<EOperacionDocumento>();
			if(lstDocumentoGarantia != null){
				for(EOperacionDocumento obj: lstDocumentoGarantia){
					if(obj.getEstadoDocumento() != UEstado.DOCUMENTOFIRMADO ){
						lstDocumentoGarantiaPendiente.add(obj);
					}
				}
			}
		}else if (codigoTabview2Index == 1){
			lstDocumentoGarantiaHistorico = new ArrayList<EOperacionDocumento>();
			if(lstDocumentoGarantia != null){
				for(EOperacionDocumento obj: lstDocumentoGarantia){
					if(obj.getEstadoDocumento() == UEstado.DOCUMENTOFIRMADO ){
						lstDocumentoGarantiaHistorico.add(obj);
					}
				}
			}
		}else{
			lstDocumentoGarantiaPendiente = new ArrayList<EOperacionDocumento>();
			lstDocumentoGarantiaHistorico = new ArrayList<EOperacionDocumento>();
		}

	}
		
	public void listarSolicitudDetalleDocumento(EOperacionDocumento eOperacionDocumento){
		if(eOperacionDocumento != null){
			List<EOperacionDocumento> lstOpDoc = oBOGarantia.listarSolicitudDocumentoGarantiaDetalle(eOperacionDocumento);
			
			lstOperacionDocumentoLegalFiltro = lstOpDoc.stream()
							                    .filter(x -> x.getCodigoAreaEmisora() == 100)
							                    .filter(x -> x.getTipoDocumento() != UTipoDocumentoGarantia.NOTARIA)
							                    .collect(Collectors.toList());
			lstOperacionDocumentoNegociosFiltro = lstOpDoc.stream()
								                .filter(x -> x.getCodigoAreaEmisora() != 100)
								                .filter(x -> x.getTipoDocumento() != UTipoDocumentoGarantia.NOTARIA)
								                .collect(Collectors.toList());
		}
	
	}
	
	public void eliminarDocumentoCarga(EDocumentoCarga oEDocumentoCargaItem){
		lstDocumentoCarga.remove(oEDocumentoCargaItem);
		if(lstDocumentoCarga.size()==0 ||lstDocumentoCarga == null) visualizarGrabarDocumento = false;
	}
	
	public void descargarDocumento(EOperacionDocumento oEOperacionDocumentoItem) {
		if (oEOperacionDocumentoItem != null) {
			UManejadorArchivo manejoArchivo = new UManejadorArchivo();
			Documento archivo = manejoArchivo.obtenerDocumento(oEOperacionDocumentoItem.getCodigoDocumentoLaserFiche());
			if (archivo != null && archivo.getArchivoBinario() != null && archivo.getArchivoBinario().length > 0) {
				InputStream stream = new ByteArrayInputStream(archivo.getArchivoBinario());
				fileDownload = new DefaultStreamedContent(stream, "image/png", oEOperacionDocumentoItem.getNombreDocumento());
			} else {
				if (oEOperacionDocumentoItem.getDataDocumento() != null && oEOperacionDocumentoItem.getDataDocumento().length > 0) {
					UFuncionesGenerales.descargaArchivo(oEOperacionDocumentoItem.getNombreDocumento(), oEOperacionDocumentoItem.getDataDocumento());
				}
			}
		}
	}
	
	public void generarCorrelativoDocumentoCarga() {
		int correlativoDocumento = 0;
		for(int i=0;i<lstDocumentoCarga.size();i++){
			correlativoDocumento = correlativoDocumento +1;
			String nombreDocumento = lstDocumentoCarga.get(i).getNombre();
			lstDocumentoCarga.get(i).setNombreLaserFiche(correlativoDocumento+"-"+nombreDocumento);
		}
	}
	
	public void visualizarObservacionDocumento(EOperacionDocumento oEOperacionDocumentoItem){
		if(oEOperacionDocumentoItem != null){
			deshabilitarObservacionDocumento = oEOperacionDocumentoItem.isValidarObservacion();
			oEOperacionDocumentoDetalle = oEOperacionDocumentoItem;
			RequestContext.getCurrentInstance().execute("PF('dlgObservacionDocumento').show();");
		}
		
	}
	
	public void procesarObservacionDocumento(){	
        EGarantia eGarantia = new EGarantia();
        eGarantia.setCodigoGarantia(oEOperacionDocumentoDetalle.getCodigoGarantia());
        eGarantia.setNumeroSolicitud(oEOperacionDocumentoDetalle.getCodigoSolicitud());
        eGarantia.setSecuenciaDocumento(oEOperacionDocumentoDetalle.getCodigoDocumento());
		eGarantia.setTipoDocumento(oEOperacionDocumento.getTipoDocumento());
		eGarantia.setFirmaDocumento(oEOperacionDocumentoDetalle.getFirmaDocumento());
		eGarantia.setObservacionDocumento(oEOperacionDocumentoDetalle.getObervacionDocumento());
		eGarantia.setUsuarioRegistro(oEUsuario);
		
		oEMensaje = oBOGarantia.modificarDetalleSolicitudDocumentoGarantia(eGarantia, 1);
		
		
		UManejadorLog.log(" Guardar: " + oEMensaje.getDescMensaje());
		RequestContext.getCurrentInstance().execute("PF('dlgMensajeOperacionAjax').show();");
	}
	
	public void visualizarFirmaDocumento(EOperacionDocumento oEOperacionDocumentoItem){
		if(oEOperacionDocumentoItem != null){
			if(oEOperacionDocumentoItem.getFirmaDocumento().equals("SI")) codigoFirmaSiNo = 1;		
			else codigoFirmaSiNo = 2;
			oEOperacionDocumentoDetalle = oEOperacionDocumentoItem;
			RequestContext.getCurrentInstance().execute("PF('dlgFirma').show();");
		}
	}
	
	public void procesarFirmaDocumento(){
		EGarantia eGarantia = new EGarantia();
    	if(codigoFirmaSiNo == 1){
    		oEOperacionDocumentoDetalle.setFirmaDocumento("SI");
		}else{
			oEOperacionDocumentoDetalle.setFirmaDocumento("NO");
		}
    	eGarantia.setCodigoGarantia(oEOperacionDocumentoDetalle.getCodigoGarantia());
        eGarantia.setNumeroSolicitud(oEOperacionDocumentoDetalle.getCodigoSolicitud());
        eGarantia.setSecuenciaDocumento(oEOperacionDocumentoDetalle.getCodigoDocumento());
		eGarantia.setTipoDocumento(oEOperacionDocumentoDetalle.getTipoDocumento());
		eGarantia.setFirmaDocumento(oEOperacionDocumentoDetalle.getFirmaDocumento());
		eGarantia.setObservacionDocumento(oEOperacionDocumentoDetalle.getObervacionDocumento());
		eGarantia.setUsuarioRegistro(oEUsuario);
		
		oEMensaje = oBOGarantia.modificarDetalleSolicitudDocumentoGarantia(eGarantia, 2);

		UManejadorLog.log(" Guardar: " + oEMensaje.getDescMensaje());
		RequestContext.getCurrentInstance().execute("PF('dlgMensajeOperacionAjax').show();");
	}
	
	public void visualizarEliminarDocumento(EOperacionDocumento oEOperacionDocumentoItem){
		if(oEOperacionDocumentoItem != null){
			oEOperacionDocumentoDetalle = oEOperacionDocumentoItem;
			RequestContext.getCurrentInstance().execute("PF('dlgConfirmacionEliminarDocumento').show();");
		}
	}
	
	public void procesarEliminarDocumento(){
		if(oEOperacionDocumentoDetalle != null){
			EGarantia eGarantia = new EGarantia();
			eGarantia.setCodigoGarantia(oEOperacionDocumentoDetalle.getCodigoGarantia());
			eGarantia.setNumeroSolicitud(oEOperacionDocumentoDetalle.getCodigoSolicitud());
			eGarantia.setSecuenciaDocumento(oEOperacionDocumentoDetalle.getCodigoDocumento());
			oEMensaje = oBOGarantia.eliminarDetalleSolicitudDocumentoGarantia(eGarantia);
			
			UManejadorLog.log(" Guardar: " + oEMensaje.getDescMensaje());
			RequestContext.getCurrentInstance().execute("PF('dlgMensajeOperacionAjax').show();");
		}
	}
	
	
	public void visualizarConfirmarFirmaDocumento(EOperacionDocumento oEOperacionDocumentoItem){
		if(oEOperacionDocumentoItem != null){
			oEOperacionDocumento = oEOperacionDocumentoItem;
			RequestContext.getCurrentInstance().execute("PF('dlgConfirmacionDocumentoFirma').show();");
		}
	}

	public void procesarConfirmarFirmaDocumento(){
        EGarantia eGarantia = new EGarantia();
        
        eGarantia.setNumeroSolicitud(oEOperacionDocumento.getCodigoSolicitud());
		eGarantia.setCodigoGarantia(oEOperacionDocumento.getCodigoGarantia());
		eGarantia.setTipoDocumento(oEOperacionDocumento.getTipoDocumento());
		eGarantia.setEstadoDocumentoLegal(oEOperacionDocumento.getEstadoDocumentoLegal());
		eGarantia.setEstadoDocumentoNegocio(UEstado.FIRMACONFIRMADA);
		eGarantia.setEstadoDocumento(oEOperacionDocumento.getEstadoDocumento());
        eGarantia.setUsuarioRegistro(oEUsuario);
        
		oEMensaje = oBOGarantia.modificarSolicitudDocumentoGarantia(eGarantia);
		
		
		UManejadorLog.log(" Guardar: " + oEMensaje.getDescMensaje());
		RequestContext.getCurrentInstance().execute("PF('dlgMensajeOperacionAjax').show();");
	}
	
	
	public void evaluarDocumentoSolicitud(EOperacionDocumento oEOperacionDocumentoItem){
		if(oEOperacionDocumentoItem != null){
			listarSolicitudDetalleDocumento(oEOperacionDocumentoItem);
			visualizarGrabarDocumento = false;
			indicadorTituloDocumento = false;
			deshabilitarAdjuntaDocumento = oEOperacionDocumentoItem.getEstadoDocumento() == 60 ? true : false;
			visualizarEliminarDocumentoGarantia = oEOperacionDocumentoItem.getEstadoDocumento() == 56? true : false;
			visualizarActualizacionFirmaDocGarantia = oEOperacionDocumentoItem.getEstadoDocumento() == 56? true : false;
			
			oEOperacionDocumento = oEOperacionDocumentoItem;
			lstDocumentoCarga = new ArrayList<EDocumentoCarga>();
			RequestContext.getCurrentInstance().execute("PF('dlgDocumentacion').show();");
		}
	}
	
	

	
	public void actualizarDatosAjax(){
		listarSolicitudDocumento();
		if(oEOperacionDocumento != null) listarSolicitudDetalleDocumento(oEOperacionDocumento);
		
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
