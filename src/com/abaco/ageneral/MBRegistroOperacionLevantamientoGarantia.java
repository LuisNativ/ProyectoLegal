package com.abaco.ageneral;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.nio.charset.CodingErrorAction;
import java.sql.Time;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Timer;
import java.util.TimerTask;
import java.util.stream.Collectors;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.component.UIComponent;
import javax.faces.component.UIInput;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.faces.event.PhaseEvent;
import javax.faces.event.PhaseId;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

import lombok.Getter;
import lombok.Setter;

import org.apache.commons.io.FilenameUtils;
import org.apache.commons.io.IOUtils;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.hibernate.validator.Email;
import org.primefaces.context.RequestContext;


import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;
import org.primefaces.model.UploadedFile;

import com.abaco.ageneral.BOOperacion;
import com.abaco.ageneral.EOperacionSolicitud;
import com.abaco.bo.BOGeneral;
import com.abaco.entidad.EGeneral;
import com.abaco.entidad.EPersona;
import com.abaco.entidad.EPersonaParametro;
import com.abaco.entidad.EPlantillaEmail;
import com.abaco.entidad.EUsuario;
import com.abaco.entidad.EMensaje;
import com.abaco.negocio.util.UConstante;
import com.abaco.negocio.util.UConstante.UAccionExterna;
import com.abaco.negocio.util.UConstante.UAccionInterna;
import com.abaco.negocio.util.UConstante.UAccionTabla;
import com.abaco.negocio.util.UConstante.UArea;
import com.abaco.negocio.util.UConstante.UCalificacion;
import com.abaco.negocio.util.UConstante.UCorreoEnvio;
import com.abaco.negocio.util.UConstante.UData;
import com.abaco.negocio.util.UConstante.UEstado;
import com.abaco.negocio.util.UConstante.UEstadoLegal;
import com.abaco.negocio.util.UConstante.UEstadoOperacionCliente;
import com.abaco.negocio.util.UConstante.UEstadoOperacionLevantamiento;
import com.abaco.negocio.util.UConstante.UFormatoAbaco;
import com.abaco.negocio.util.UConstante.UIndicadorDigitalizacion;
import com.abaco.negocio.util.UConstante.UIndicadorSesion;
import com.abaco.negocio.util.UConstante.UIndicadorSolicitud;
import com.abaco.negocio.util.UConstante.UIndicadorTemporal;
import com.abaco.negocio.util.UConstante.UMensajeValidacion;
import com.abaco.negocio.util.UConstante.UMoneda;
import com.abaco.negocio.util.UConstante.UNivel;
import com.abaco.negocio.util.UConstante.URutaCarpetaCompartida;
import com.abaco.negocio.util.UConstante.USistemaOperativo;
import com.abaco.negocio.util.UConstante.USituacionGarantia;
import com.abaco.negocio.util.UConstante.UTipoEnvio;
import com.abaco.negocio.util.UConstante.UMensajeConfirmacion;
import com.abaco.negocio.util.UConstante.UMotivo;
import com.abaco.negocio.util.UConstante.UPersonaRelacion;
import com.abaco.negocio.util.UConstante.UPlantillaEmail;
import com.abaco.negocio.util.UConstante.UTipoCorrelativo;
import com.abaco.negocio.util.UConstante.UTipoDocumento;
import com.abaco.negocio.util.UConstante.UTipoEstadoUsuario;
import com.abaco.negocio.util.UConstante.UTipoFirma;
import com.abaco.negocio.util.UConstante.UTipoGarantia;
import com.abaco.negocio.util.UConstante.UTipoCliente;
import com.abaco.negocio.util.UConstante.URegla;
import com.abaco.negocio.util.UConstante.UTipoPersona;
import com.abaco.negocio.util.UConstante.UVariablesQueryString;
import com.abaco.negocio.util.UConstante.UVariablesSesion;
import com.abaco.negocio.util.UFuncionesGenerales;
import com.abaco.negocio.util.UGeneradorQueryString;
import com.abaco.negocio.util.UGeneradorSelectItem;
import com.abaco.negocio.util.ULectorDeParametros;
import com.abaco.negocio.util.UManejadorArchivo;
import com.abaco.negocio.util.UManejadorCorreo;
import com.abaco.negocio.util.UManejadorListaDesplegable;
import com.abaco.negocio.util.UManejadorLog;
import com.abaco.negocio.util.UManejadorSesionWeb;
import com.abaco.negocio.util.UNumeroLetra;
import com.abaco.negocio.util.UtilPoi;
import com.abaco.servicio.laserfiche.Documento;
import com.laserfiche.repositoryaccess.Document;

@ManagedBean(name = "mbregistrooperacionlevantamientogarantia")
@ViewScoped
public class MBRegistroOperacionLevantamientoGarantia implements Serializable {
	private static final long serialVersionUID = 1L;
	private EMensaje oEMensaje;
	private EUsuario oEUsuario;
	private EOperacionLevantamientoGarantia oEOperacionLevantamientoGarantiaLoad;
	private EGarantiaSolicitud oEGarantiaSolicitudLoad;
	
	private ECliente oEClienteData;
	private EGarantia oEGarantiaData;
	private EGarantiaTramite oEGarantiaTramiteData;
	
	private BOOperacion oBOOperacion;
	private BOGeneral oBOGeneral;
	private BOGarantia oBOGarantia;
	private BOCliente oBOCliente;
	private BOSolicitudCredito oBOSolicitudCredito;
	private UManejadorListaDesplegable oUManejadorListaDesplegable;
	
	@Getter @Setter private List<EOperacionLevantamientoGarantiaMensaje> lstOperacionLevantamientoGarantiaMensaje;
	@Getter @Setter private List<EOperacionDocumento> lstOperacionDocumento;
	@Getter @Setter private List<EOperacionDocumento> lstOperacionDocumentoFiltro;
	@Getter @Setter private List<EDocumentoCarga> lstDocumentoCarga;
	@Getter @Setter private List<EAsignacionContratoGarantia> lstClienteGarantia;
	@Getter @Setter private List<EAsignacionContratoGarantia> lstCreditoGarantia;
	@Getter @Setter private List<EAsignacionContratoGarantia> lstCreditoVigenteRelacionadoFiltro;
	@Getter @Setter private List<EGeneral> lstTipoGarantia;
	@Getter @Setter private List<EGeneral> lstTipoPrendaGarantia;
	@Getter @Setter private List<EGeneral> lstTipoMoneda;
	@Getter @Setter private List<EGeneral> lstTipoCiaSeguro;
	
	@Getter @Setter private List<EGeneral> lstDepartamentoGarantia;
	@Getter @Setter private List<EGeneral> lstProvinciaGarantia;
	@Getter @Setter private List<EGeneral> lstDistritoGarantia;
	
	/* Variables Interfaz */
	
	//Datos de formulario General
	@Getter @Setter private String mensajeValidacion;
	@Getter @Setter private String mensajeConfirmacion;
	@Getter @Setter private String descripcionMensaje;
	@Getter @Setter private String descripcionMensajeSeleccion;
	@Getter @Setter private String descripcionDocumentoCarga;
	
	//Datos de formulario Garantia
	@Getter @Setter private int codigoDepartamentoGarantia;
	@Getter @Setter private int codigoProvinciaGarantia;
	@Getter @Setter private int codigoDistritoGarantia;
	
	//Panel Garantia
	@Getter @Setter private boolean visualizarFrmGarantiaVehicular;
	@Getter @Setter private boolean visualizarFrmGarantiaPredio;
	@Getter @Setter private boolean visualizarFrmGarantiaMaquinaria;
	@Getter @Setter private boolean visualizarComentario;
	@Getter @Setter private boolean deshabilitarFrmGarantia;
	
	//Indicadores
	@Getter @Setter private boolean indicadorValidarBtnLiberar;
	
	//Control de Acciones
	@Getter @Setter private boolean deshabilitar;
	@Getter @Setter private boolean deshabilitarBtnGrabar;
	
	@Getter @Setter private boolean visualizar;
	@Getter @Setter private boolean visualizarTblMensaje;
	@Getter @Setter private boolean visualizarPnlInforme;
	@Getter @Setter private boolean visualizarPnlDocumentoLevantamiento;
	@Getter @Setter private boolean visualizarPnlClienteRelacionado;
	@Getter @Setter private boolean visualizarPnlCreditoRelacionado;
	
	@Getter @Setter private boolean visualizarBtnSalir;
	@Getter @Setter private boolean visualizarBtnAdjuntar;
	@Getter @Setter private boolean visualizarBtnEnviar;
	@Getter @Setter private boolean visualizarBtnRechazar;
	@Getter @Setter private boolean visualizarBtnConfirmar;
	@Getter @Setter private boolean visualizarBtnLiberar;
	@Getter @Setter private boolean visualizarBtnEntregar;
	@Getter @Setter private boolean visualizarBtnArchivar;
	@Getter @Setter private boolean visualizarBtnGrabar;
	@Getter @Setter private boolean visualizarBtnEnProcesoLevantamiento;
	@Getter @Setter private boolean visualizarBtnGenerarDocumento1;
	@Getter @Setter private boolean visualizarBtnGenerarDocumento2;
	@Getter @Setter private boolean visualizarBtnGenerarDocumento3;
	@Getter @Setter private boolean visualizarBtnGenerarDocumento4;

	/* Variables Internas */
	private int codigoEstado;
	private int codigoEstadoDocumento;
	private boolean indicadorLiberarGarantia;
	private int accionExterna;

	/* Variables de carga File */
	@Getter @Setter private StreamedContent fileDownload;
	@Getter @Setter private List<UploadedFile> files;
	
	private String rutaBaseFormato;
	private String rutaBasePlantilla;
	
	//Adicional 
	@Getter @Setter private boolean deshabilitarGrabarDocumento;
	@Getter @Setter private boolean deshabilitarSolFirma;
	private EOperacionDocumento oEOperacionDocumento;
	@Getter @Setter private int codigoFirmaSiNo;
	@Getter @Setter private List<EGeneral> lstValorSiNo;
	@Getter @Setter private Double montoAcumuladoAsignadoCredito;    
	@Getter @Setter private Double montoAcumuladoSaldoCredito;  
	@Getter @Setter private Double montoAcumuladoCoberturado;
	
	@PostConstruct
	public void inicio() {
		this.oEMensaje = new EMensaje();
		this.oEOperacionLevantamientoGarantiaLoad = new EOperacionLevantamientoGarantia();
		this.oEGarantiaSolicitudLoad = new EGarantiaSolicitud();
		
		this.oEClienteData = new ECliente();
		this.oEGarantiaData = new EGarantia();
		
		oBOOperacion = new BOOperacion();
		oBOGeneral = new BOGeneral();
		oBOGarantia = new BOGarantia();
		oBOCliente = new BOCliente();
		oBOSolicitudCredito = new BOSolicitudCredito();
		oUManejadorListaDesplegable = new UManejadorListaDesplegable();
		
		lstOperacionLevantamientoGarantiaMensaje = new ArrayList<EOperacionLevantamientoGarantiaMensaje>();
		lstOperacionDocumento = new ArrayList<EOperacionDocumento>();
		lstOperacionDocumentoFiltro = new ArrayList<EOperacionDocumento>();
		lstDocumentoCarga = new ArrayList<EDocumentoCarga>();
		lstClienteGarantia = new ArrayList<EAsignacionContratoGarantia>();
		lstCreditoGarantia = new ArrayList<EAsignacionContratoGarantia>();
		lstCreditoVigenteRelacionadoFiltro = new ArrayList<EAsignacionContratoGarantia>();
		lstTipoGarantia = new ArrayList<EGeneral>();
		lstTipoPrendaGarantia = new ArrayList<EGeneral>();
		lstTipoMoneda = new ArrayList<EGeneral>();
		lstTipoCiaSeguro = new ArrayList<EGeneral>();
		
		files = new ArrayList<UploadedFile>();
		
		//Adicional
		oEOperacionDocumento = new EOperacionDocumento();
		
		oEUsuario = (EUsuario) UManejadorSesionWeb.obtieneVariableSesion(UVariablesSesion.USUARIO);
		inicializar();
		

		if (UManejadorSesionWeb.obtieneVariableSesion(UVariablesSesion.ACCION_EXTERNA) != null) {
			accionExterna = (Integer) UManejadorSesionWeb.obtieneVariableSesion(UVariablesSesion.ACCION_EXTERNA);
			
			switch (accionExterna) {
			case UAccionExterna.NUEVO:
				deshabilitar = false;
				visualizar = true;
				break;
			case UAccionExterna.EDITAR:
				deshabilitar = true;
				visualizar = true;
				break;
			case UAccionExterna.VER:
				deshabilitar = true;
				visualizar = false;
			}
			
			if(UAccionExterna.NUEVO == accionExterna){
				if (UManejadorSesionWeb.obtieneVariableSesion(UVariablesSesion.FICHA_PARAMETRO) != null) {
					oEGarantiaSolicitudLoad = (EGarantiaSolicitud) UManejadorSesionWeb.obtieneVariableSesion(UVariablesSesion.FICHA_PARAMETRO);
					oEClienteData = oBOCliente.buscarSocio(oEGarantiaSolicitudLoad.getCodigoCliente());
					oEGarantiaData = oBOGarantia.buscarGarantia(oEGarantiaSolicitudLoad.getCodigoGarantia());
					
					deshabilitarFrmGarantia = true;
					visualizarComentario = true;
					visualizarPnlClienteRelacionado = true;
					visualizarPnlCreditoRelacionado = true;
					visualizarBtnSalir = true;
					visualizarBtnEnviar = true;

				}
			}else if(UAccionExterna.EDITAR == accionExterna){
				if (UManejadorSesionWeb.obtieneVariableSesion(UVariablesSesion.FICHA_PARAMETRO) != null) {
					oEGarantiaSolicitudLoad = (EGarantiaSolicitud) UManejadorSesionWeb.obtieneVariableSesion(UVariablesSesion.FICHA_PARAMETRO);
					oEClienteData = oBOCliente.buscarSocio(oEGarantiaSolicitudLoad.getCodigoCliente());
					oEGarantiaData = oBOGarantia.buscarGarantia(oEGarantiaSolicitudLoad.getCodigoGarantia());
					oEGarantiaTramiteData = oBOGarantia.buscarGarantiaTramite(oEGarantiaSolicitudLoad.getCodigoGarantia());
					listarCreditosAsociadosGarantia();
					listarCreditoVigenteRelacionadoFiltro();
					
					if(oEGarantiaSolicitudLoad.getCodigoEstadoLevantamiento() == UEstadoOperacionLevantamiento.SOLICITADO){
						visualizarBtnRechazar = true;
						visualizarBtnConfirmar = true;
					}else if(oEGarantiaSolicitudLoad.getCodigoEstadoLevantamiento() == UEstadoOperacionLevantamiento.CONFIRMADO){
						visualizarPnlInforme = true;
						visualizarPnlDocumentoLevantamiento = true;
						visualizarBtnLiberar = true;
						visualizarBtnEnProcesoLevantamiento = true;
						
						if(oEGarantiaData.getCodigoTipoGarantia() == UTipoGarantia.VEHICULAR){
							visualizarBtnGenerarDocumento1 = true;
						}else if(oEGarantiaData.getCodigoTipoGarantia() == UTipoGarantia.MAQUINARIA){
							visualizarBtnGenerarDocumento2 = true;
						}else if(oEGarantiaData.getCodigoTipoGarantia() == UTipoGarantia.PREDIO){							
							if(lstCreditoVigenteRelacionadoFiltro.size() > 0){
								visualizarBtnGenerarDocumento4 = true;
							}else{
								visualizarBtnGenerarDocumento2 = true;
								visualizarBtnGenerarDocumento3 = true;
							}
						}
						indicadorValidarBtnLiberar = true;
					}else if(oEGarantiaSolicitudLoad.getCodigoEstadoLevantamiento() == UEstadoOperacionLevantamiento.OBSERVADO){
						visualizarPnlInforme = true;
						visualizarPnlDocumentoLevantamiento = true;
						visualizarBtnEntregar = true;
						visualizarBtnArchivar = true;
					}else if(oEGarantiaSolicitudLoad.getCodigoEstadoLevantamiento() == UEstadoOperacionLevantamiento.ENEVALUACION){
						visualizarPnlDocumentoLevantamiento = true;
						visualizarBtnEnProcesoLevantamiento = true;
						visualizarBtnLiberar = true;
						indicadorValidarBtnLiberar = true;
					}else if(oEGarantiaSolicitudLoad.getCodigoEstadoLevantamiento() == UEstadoOperacionLevantamiento.LIBERADO){
						visualizarPnlDocumentoLevantamiento = true;
						visualizarBtnGrabar = true;
					}
					
					deshabilitarFrmGarantia = true;
					visualizarComentario = true;
					visualizarTblMensaje = true;
					visualizarPnlClienteRelacionado = true;
					visualizarPnlCreditoRelacionado = true;
					visualizarBtnSalir = true;
					visualizarBtnAdjuntar = true;
				}
			}else if(UAccionExterna.VER == accionExterna){
				if (UManejadorSesionWeb.obtieneVariableSesion(UVariablesSesion.FICHA_PARAMETRO) != null) {
					oEGarantiaSolicitudLoad = (EGarantiaSolicitud) UManejadorSesionWeb.obtieneVariableSesion(UVariablesSesion.FICHA_PARAMETRO);
					oEClienteData = oBOCliente.buscarSocio(oEGarantiaSolicitudLoad.getCodigoCliente());
					oEGarantiaData = oBOGarantia.buscarGarantia(oEGarantiaSolicitudLoad.getCodigoGarantia());
					listarCreditosAsociadosGarantia();
					
					deshabilitarFrmGarantia = true;
					visualizarComentario = false;
					visualizarTblMensaje = true;
					visualizarPnlDocumentoLevantamiento = true;
					visualizarPnlClienteRelacionado = true;
					visualizarPnlCreditoRelacionado = true;
					visualizarBtnSalir = true;
					
					if(oEUsuario.getCodigoArea() == UArea.LEGAL){
						visualizarBtnAdjuntar = true;
					}
				}
			}
			
			if(oEGarantiaData.getCodigoTipoGarantia() == UTipoGarantia.VEHICULAR){
				visualizarFrmGarantiaVehicular = true;
			}else if(oEGarantiaData.getCodigoTipoGarantia() == UTipoGarantia.PREDIO){
				visualizarFrmGarantiaPredio = true;
			}else if(oEGarantiaData.getCodigoTipoGarantia() == UTipoGarantia.MAQUINARIA){
				visualizarFrmGarantiaMaquinaria = true;
			}
			
			listarDesplegable();
			listarMensaje();
			listarDocumento();
			listarUbigeoGarantia();
			listarClienteAsociadosGarantia();
			habilitarAccionLiberar();
		}
	}
	
	public void obtenerEstado(int ind){
		if(validar()){
			obtenerEstadoDocumentoLevantamiento();
			if (ind == 1) {
				codigoEstado = UEstadoOperacionLevantamiento.SOLICITADO;
				indicadorLiberarGarantia = false;
				mensajeConfirmacion = UMensajeConfirmacion.MSJ_1;
				RequestContext.getCurrentInstance().execute("PF('dlgMensajeConfirmacion').show();");
			}else if(ind == 2){
				codigoEstado = UEstadoOperacionLevantamiento.RECHAZADO;
				indicadorLiberarGarantia = false;
				mensajeConfirmacion = UMensajeConfirmacion.MSJ_2;
				RequestContext.getCurrentInstance().execute("PF('dlgMensajeConfirmacion').show();");
			}else if(ind == 3){
				codigoEstado = UEstadoOperacionLevantamiento.CONFIRMADO;
				indicadorLiberarGarantia = false;
				if(validarLiberarGarantiaCreditoCancelado()){
					mensajeConfirmacion = UMensajeConfirmacion.MSJ_3;
					RequestContext.getCurrentInstance().execute("PF('dlgMensajeConfirmacion').show();");
				}else{
					RequestContext.getCurrentInstance().execute("PF('dlgMensajeValidacion').show();");
				}
			}else if(ind == 4){
				codigoEstado = oEGarantiaSolicitudLoad.getCodigoEstadoLevantamiento();
				indicadorLiberarGarantia = true;
				if(validarLiberarGarantiaCreditoVigente()){
					mensajeConfirmacion = UMensajeConfirmacion.MSJ_4;
					RequestContext.getCurrentInstance().execute("PF('dlgMensajeConfirmacion').show();");
				}else{
					RequestContext.getCurrentInstance().execute("PF('dlgMensajeValidacion').show();");
				}
			}else if(ind == 5){
				codigoEstado = UEstadoOperacionLevantamiento.ENEVALUACION;
				indicadorLiberarGarantia = false;
				guardar();
			}else if(ind == 6){
				codigoEstado = UEstadoOperacionLevantamiento.OBSERVADO;
				indicadorLiberarGarantia = false;
				mensajeConfirmacion = UMensajeConfirmacion.MSJ_6;
				RequestContext.getCurrentInstance().execute("PF('dlgMensajeConfirmacion').show();");
			}else if(ind == 7){
				codigoEstado = UEstadoOperacionLevantamiento.ARCHIVADO;
				indicadorLiberarGarantia = false;
				mensajeConfirmacion = UMensajeConfirmacion.MSJ_7;
				RequestContext.getCurrentInstance().execute("PF('dlgMensajeConfirmacion').show();");
			}else if(ind == 8){
				codigoEstado = oEGarantiaSolicitudLoad.getCodigoEstadoLevantamiento();
				indicadorLiberarGarantia = false;
				guardar();
			}
		}else{
			RequestContext.getCurrentInstance().execute("PF('dlgMensajeValidacion').show();");
		}
	}
	
	public void guardar() {
		obtenerEstadoDocumentoLevantamiento();
		EOperacionLevantamientoGarantia oEOperacionLevantamientoGarantia = new EOperacionLevantamientoGarantia();
		EGarantia oEGarantia = new EGarantia();
		
		oEGarantia = oEGarantiaData;
			
		oEOperacionLevantamientoGarantia.setCodigoCliente(oEGarantiaData.getCodigoCliente());
		oEOperacionLevantamientoGarantia.setCodigoServicio(oEGarantiaData.getCodigoServicio());
		oEOperacionLevantamientoGarantia.setCodigoGarantia(oEGarantiaData.getCodigoGarantia());
		oEOperacionLevantamientoGarantia.setCodigoMoneda(oEGarantiaData.getCodigoMoneda());
		oEOperacionLevantamientoGarantia.setCodigoEstadoSolicitud(codigoEstado);
		oEOperacionLevantamientoGarantia.setCodigoEstadoDocumento(codigoEstadoDocumento);
		oEOperacionLevantamientoGarantia.setDescripcionMensaje(descripcionMensaje);
		oEOperacionLevantamientoGarantia.setFechaRegistro(new Date());
		oEOperacionLevantamientoGarantia.setUsuarioRegistro(oEUsuario);
		
		oEGarantia.setFechaRegistro(oEOperacionLevantamientoGarantia.getFechaRegistro());
		oEGarantia.setUsuarioRegistro(oEUsuario);
		
		if(UAccionExterna.NUEVO == accionExterna){
			oEMensaje = oBOOperacion.agregarEvaluacionLevantamientoGarantia(oEOperacionLevantamientoGarantia);
		}else if(UAccionExterna.EDITAR == accionExterna){
			oEMensaje = oBOOperacion.modificarEvaluacionLevantamientoGarantia(oEOperacionLevantamientoGarantia, indicadorLiberarGarantia);
		}
		
		UManejadorLog.log(" Guardar: " + oEMensaje.getDescMensaje());
		RequestContext.getCurrentInstance().execute("PF('dlgMensajeOperacion').show();");
	}
	
	public void obtenerEstadoDocumentoLevantamiento() {
		if(lstOperacionDocumento != null){
			for(int i=0;i<lstOperacionDocumento.size();i++){
				if(lstOperacionDocumento.get(i).getEstadoDocumento() == UEstado.ENTRAMITE){
					codigoEstadoDocumento = lstOperacionDocumento.get(i).getEstadoDocumento();
					break;
				}
			}
			for(int i=0;i<lstOperacionDocumento.size();i++){
				if(lstOperacionDocumento.get(i).getEstadoDocumento() == UEstado.DOCUMENTOFIRMADO){
					codigoEstadoDocumento = lstOperacionDocumento.get(i).getEstadoDocumento();
					break;
				}
			}
			for(int i=0;i<lstOperacionDocumento.size();i++){
				if(lstOperacionDocumento.get(i).getEstadoDocumento() == UEstado.ENTREGADOSOCIO){
					codigoEstadoDocumento = lstOperacionDocumento.get(i).getEstadoDocumento();
					break;
				}
			}
		}
	}

	public boolean validar() {
		boolean ind=true;
		mensajeValidacion = "";
        return ind;
	}
	
	public boolean validarLiberarGarantiaCreditoCancelado() {
		boolean ind=true;
		mensajeValidacion = "";
		List<EAsignacionContratoGarantia> lstCreditoRelacionadoFiltro = new ArrayList<EAsignacionContratoGarantia>();
		
		if(lstCreditoGarantia !=null){
	        for (EAsignacionContratoGarantia obj: lstCreditoGarantia) {
	            if (obj.getDescripcionRelacion().equals("Enlazado") &&
	            	(obj.getDescripcionSituacionCredito().equals("CANCELADO") ||
	            	obj.getDescripcionSituacionCredito().equals("CANCELADA"))){
	            	lstCreditoRelacionadoFiltro.add(obj);
	            }
	        }
		}
		
		if(lstCreditoRelacionadoFiltro !=null){
			if(lstCreditoRelacionadoFiltro.size() > 0){
				mensajeValidacion = UMensajeValidacion.MSJ_11;
				
				for(int i=0;i<lstCreditoRelacionadoFiltro.size();i++){
					mensajeValidacion = mensajeValidacion + "\n" +lstCreditoRelacionadoFiltro.get(i).getCodigoCliente()+"-"+lstCreditoRelacionadoFiltro.get(i).getDescripcionOperacion();
				}
				ind = false;
			}
		}
		return ind;
	}
	
	public boolean validarLiberarGarantiaCreditoVigente() {
		boolean ind=true;
		mensajeValidacion = "";
		listarCreditoVigenteRelacionadoFiltro();
		
		if(lstCreditoVigenteRelacionadoFiltro !=null){
			if(lstCreditoVigenteRelacionadoFiltro.size() > 0){
				mensajeValidacion = UMensajeValidacion.MSJ_12;
				
				for(int i=0;i<lstCreditoVigenteRelacionadoFiltro.size();i++){
					mensajeValidacion = mensajeValidacion + "\n" +lstCreditoVigenteRelacionadoFiltro.get(i).getCodigoCliente()+"-"+lstCreditoVigenteRelacionadoFiltro.get(i).getDescripcionOperacion();
				}
				ind = false;
			}
		}
		return ind;
	}
	
	public void listarCreditoVigenteRelacionadoFiltro() {
		lstCreditoVigenteRelacionadoFiltro = new ArrayList<EAsignacionContratoGarantia>();
		
		if(lstCreditoGarantia !=null){
	        for (EAsignacionContratoGarantia obj: lstCreditoGarantia) {
	            if (obj.getDescripcionRelacion().equals("Enlazado") &&
	            	(obj.getDescripcionSituacionCredito().equals("ACTIVO") ||
	            	obj.getDescripcionSituacionCredito().equals("ACTIVA") ||
	            	obj.getDescripcionSituacionCredito().equals("VIGENTE"))){
	            	lstCreditoVigenteRelacionadoFiltro.add(obj);
	            }
	        }
		}
	}
	
	public void salir() {
		String ruta = "";
		
		if(oEUsuario.getCodigoArea() == UArea.LEGAL){;
			ruta = "ListaOperacionLevantamiento.xhtml";
		}else{
			ruta = "ListaOperacionLevantamientoOtros.xhtml";
		}
		
		inicializar();
		/*Cerrar Sesion*/
		UManejadorSesionWeb.eliminaVariableSesion(UVariablesSesion.OPERACION_SESION);
		UManejadorSesionWeb.eliminaVariableSesion(UVariablesSesion.ACCION_EXTERNA);
		UManejadorSesionWeb.eliminaVariableSesion(UVariablesSesion.FICHA_PARAMETRO);
		
		UGeneradorQueryString objUGeneradorQueryString = new UGeneradorQueryString(ruta);
		UManejadorSesionWeb.redirigePagina(objUGeneradorQueryString.obtieneUrlConParametros());
	}
	
	public void listarDesplegable(){
		lstTipoGarantia = oUManejadorListaDesplegable.obtieneTipoGarantia();
		lstTipoPrendaGarantia = oUManejadorListaDesplegable.obtieneTipoPrendaGarantia();
		lstTipoMoneda = oUManejadorListaDesplegable.obtieneTipoMoneda();
		lstTipoCiaSeguro = oUManejadorListaDesplegable.obtieneTipoCiaSeguro();
		//Adicional
		lstValorSiNo = oUManejadorListaDesplegable.obtieneValorSiNo();
	}
	
	public void listarMensaje() {
		lstOperacionLevantamientoGarantiaMensaje = oBOOperacion.listarMensajeLevantamientoGarantia(oEGarantiaData.getCodigoServicio(), oEGarantiaData.getCodigoGarantia(), oEGarantiaData.getCodigoMoneda());
	}
	
	//*************************************//
	//Metodos para Documento
	//*************************************//
	public void eliminarDocumentoCarga(EDocumentoCarga oEDocumentoCargaItem){
		lstDocumentoCarga.remove(oEDocumentoCargaItem);
		//Adicional
		if(lstDocumentoCarga.size()==0 ||lstDocumentoCarga == null) deshabilitarGrabarDocumento = true;
	}
	
	public void agregarDocumentoCarga(FileUploadEvent objUploadEvent) {
		UploadedFile oUploadedFile = objUploadEvent.getFile();
		files.add(oUploadedFile);
		//Adicional
		for(int i=0;i<files.size();i++){
			EDocumentoCarga oEDocumentoCarga = new EDocumentoCarga();
			if(lstDocumentoCarga.size() > 0){
				boolean isValida = false;
				for(int x=0;x<lstDocumentoCarga.size();x++){
					if(lstDocumentoCarga.get(x).getNombre().equals(descripcionDocumentoCarga+UFuncionesGenerales.obtieneTipoArchivo(files.get(i).getFileName()))){
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
		if(lstDocumentoCarga.size()>0) deshabilitarGrabarDocumento = false;
		descripcionDocumentoCarga = "";
		files = new ArrayList<UploadedFile>();
	}
	
	public void listarDocumentoCargaSimple() {
		if(!descripcionDocumentoCarga.equals("") || descripcionDocumentoCarga.length()> 0){
		for(int i=0;i<files.size();i++){
			EDocumentoCarga oEDocumentoCarga = new EDocumentoCarga();
			if(lstDocumentoCarga.size() > 0){
				boolean isValida = false;
				for(int x=0;x<lstDocumentoCarga.size();x++){
					if(lstDocumentoCarga.get(x).getNombre().equals(descripcionDocumentoCarga+UFuncionesGenerales.obtieneTipoArchivo(files.get(i).getFileName()))){
						isValida = true;
					}
				}
				if(!isValida){
					oEDocumentoCarga.setNombre(descripcionDocumentoCarga + UFuncionesGenerales.obtieneTipoArchivo(files.get(i).getFileName()));
					oEDocumentoCarga.setNombreOriginal(files.get(i).getFileName());
					oEDocumentoCarga.setData(files.get(i).getContents());
					oEDocumentoCarga.setSize(files.get(i).getSize());
					oEDocumentoCarga.setDescripcionSize(UFuncionesGenerales.getSize(files.get(i).getSize()));
					lstDocumentoCarga.add(oEDocumentoCarga);
				}
			}else{
				oEDocumentoCarga.setNombre(descripcionDocumentoCarga + UFuncionesGenerales.obtieneTipoArchivo(files.get(i).getFileName()));
				oEDocumentoCarga.setNombreOriginal(files.get(i).getFileName());
				oEDocumentoCarga.setData(files.get(i).getContents());
				oEDocumentoCarga.setSize(files.get(i).getSize());
				oEDocumentoCarga.setDescripcionSize(UFuncionesGenerales.getSize(files.get(i).getSize()));
				lstDocumentoCarga.add(oEDocumentoCarga);
			}
		}
		descripcionDocumentoCarga = "";
		files = new ArrayList<UploadedFile>();
		RequestContext.getCurrentInstance().execute("PF('dlgDescripcionDocumentoCarga').hide();");
		}
	}
	
	public void listarDocumentoCargaMultiple() {
		for(int i=0;i<files.size();i++){
			EDocumentoCarga oEDocumentoCarga = new EDocumentoCarga();
			if(lstDocumentoCarga.size() > 0){
				boolean isValida = false;
				for(int x=0;x<lstDocumentoCarga.size();x++){
					if(lstDocumentoCarga.get(x).getNombre().equals(files.get(i).getFileName())){
						isValida = true;
					}
				}
				if(!isValida){
					oEDocumentoCarga.setNombre(files.get(i).getFileName());
					oEDocumentoCarga.setNombreOriginal(files.get(i).getFileName());
					oEDocumentoCarga.setData(files.get(i).getContents());
					oEDocumentoCarga.setSize(files.get(i).getSize());
					oEDocumentoCarga.setDescripcionSize(UFuncionesGenerales.getSize(files.get(i).getSize()));
					lstDocumentoCarga.add(oEDocumentoCarga);
				}
			}else{
				oEDocumentoCarga.setNombre(files.get(i).getFileName());
				oEDocumentoCarga.setNombreOriginal(files.get(i).getFileName());
				oEDocumentoCarga.setData(files.get(i).getContents());
				oEDocumentoCarga.setSize(files.get(i).getSize());
				oEDocumentoCarga.setDescripcionSize(UFuncionesGenerales.getSize(files.get(i).getSize()));
				lstDocumentoCarga.add(oEDocumentoCarga);
			}
		}
		files = new ArrayList<UploadedFile>();
	}
	
	public void descargarDocumento(EOperacionDocumento oEOperacionDocumentoItem) {
		//Adicional
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
	
	public void visualizarMensaje(String mensaje) {
		descripcionMensajeSeleccion = mensaje;
		RequestContext.getCurrentInstance().execute("PF('dlgDetalleMensaje').show();");
	}
	
	//*************************************//
	//Metodos para validar accion
	//*************************************//
	public void habilitarAccionLiberar(){
		deshabilitarBtnGrabar = true;
		if(indicadorValidarBtnLiberar){
			if(lstOperacionDocumentoFiltro.size() >= URegla.LIBERAR_NUMERO_MIN_ADJUNTA){
				for(int i=0;i<lstOperacionDocumentoFiltro.size();i++){
					if(lstOperacionDocumentoFiltro.get(i).getEstadoDocumento() == UEstado.ENTRAMITE){
						deshabilitarBtnGrabar = false;
						break;
					}
				}
			}
		}
	}
	
	//*************************************//
	//Metodos para Garantia
	//*************************************//
	public void listarUbigeoGarantia() {
		String codigoUbigeoGarantia = oEGarantiaData.getCodigoUbigeo()+"";
		
		if (UFuncionesGenerales.revisaCadena(codigoUbigeoGarantia).length() == 5) {
			codigoDepartamentoGarantia = UFuncionesGenerales.convierteCadenaAEntero(codigoUbigeoGarantia.substring(0, 1));
			codigoProvinciaGarantia = UFuncionesGenerales.convierteCadenaAEntero(codigoUbigeoGarantia.substring(1, 3));
			codigoDistritoGarantia = UFuncionesGenerales.convierteCadenaAEntero(codigoUbigeoGarantia.substring(3, 5));
		}else if (UFuncionesGenerales.revisaCadena(codigoUbigeoGarantia).length() == 6) {
			codigoDepartamentoGarantia = UFuncionesGenerales.convierteCadenaAEntero(codigoUbigeoGarantia.substring(0, 2));
			codigoProvinciaGarantia = UFuncionesGenerales.convierteCadenaAEntero(codigoUbigeoGarantia.substring(2, 4));
			codigoDistritoGarantia = UFuncionesGenerales.convierteCadenaAEntero(codigoUbigeoGarantia.substring(4, 6));
		}
		
		lstDepartamentoGarantia = oUManejadorListaDesplegable.obtieneDepartamento();
		lstProvinciaGarantia = oUManejadorListaDesplegable.obtieneProvincia(codigoDepartamentoGarantia);
		lstDistritoGarantia = oUManejadorListaDesplegable.obtieneDistrito(codigoDepartamentoGarantia, codigoProvinciaGarantia);
	}
	
    public void listarClienteAsociadosGarantia(){
    	lstClienteGarantia = oBOGarantia.listarClienteAsociadosGarantia(oEGarantiaData.getCodigoGarantia());
    }
    
	public void listarCreditosAsociadosGarantia(){
		montoAcumuladoCoberturado = 0.0;
		montoAcumuladoAsignadoCredito = 0.0;
		double montoConversionCoberturado = 0;
		double montoConversionAsignado = 0;
		double montoTotalConversionCoberturado = 0;
		double montoTotalConversionAsignado = 0;
		
		//Data de Tipo de Cambio
		ETipoCambio oETipoCambioData = new ETipoCambio();
		oETipoCambioData = oBOGarantia.buscarTipoCambioDiario();
		if(oETipoCambioData == null ){
			oETipoCambioData = new ETipoCambio();
		}
		
		lstCreditoGarantia = oBOGarantia.listarCreditosAsociadosGarantia2(oEGarantiaData.getCodigoGarantia());
		for(int i=0;i<lstCreditoGarantia.size();i++){
			montoConversionCoberturado = 0;
			montoConversionAsignado = 0;
			double montoCoberturado = 0;
			
			ECredito oECredito = new ECredito();
			
			if(lstCreditoGarantia.get(i).getServicioBase() == 800 || lstCreditoGarantia.get(i).getServicioBase() == 801){
				oECredito = oBOSolicitudCredito.buscarCreditoLineaCredito(lstCreditoGarantia.get(i).getServicio(), lstCreditoGarantia.get(i).getNumeroPlanilla());
			}else {
				if(lstCreditoGarantia.get(i).getCodigoProducto() == 200){
					if(lstCreditoGarantia.get(i).getCodigoSubProducto() == 8){
						oECredito = oBOSolicitudCredito.buscarCreditoPrestamo(lstCreditoGarantia.get(i).getServicio(), lstCreditoGarantia.get(i).getNumeroOperacion());
					}else{
						oECredito = oBOSolicitudCredito.buscarCreditoPrestamo(lstCreditoGarantia.get(i).getServicio(), lstCreditoGarantia.get(i).getNumeroOperacion());
					}	
				}else if(lstCreditoGarantia.get(i).getCodigoProducto() == 301){
					oECredito = oBOSolicitudCredito.buscarCreditoAbamoshi(lstCreditoGarantia.get(i).getServicio(), lstCreditoGarantia.get(i).getNumeroOperacionTanomoshi(), lstCreditoGarantia.get(i).getNumeroLista());
				}else if(lstCreditoGarantia.get(i).getCodigoProducto() == 302){
					oECredito = oBOSolicitudCredito.buscarCreditoAbamoshi(lstCreditoGarantia.get(i).getServicio(), lstCreditoGarantia.get(i).getNumeroOperacionTanomoshi(), lstCreditoGarantia.get(i).getNumeroLista());
				}else if(lstCreditoGarantia.get(i).getCodigoProducto() == 81){
					oECredito = oBOSolicitudCredito.buscarCreditoCartaFianza(lstCreditoGarantia.get(i).getServicio(), lstCreditoGarantia.get(i).getCodigoMoneda(), lstCreditoGarantia.get(i).getNumeroOperacion());
				}
			}
			
			if(oECredito != null){
				lstCreditoGarantia.get(i).setMontoSaldoCredito(oECredito.getSaldoCredito());
				lstCreditoGarantia.get(i).setDescripcionEstadoCredito(oECredito.getDescripcionEstado());
				lstCreditoGarantia.get(i).setDescripcionSituacionCredito(oECredito.getDescripcionSituacion());
			}
			
			if(lstCreditoGarantia.get(i).getServicioBase() == 800 || lstCreditoGarantia.get(i).getServicioBase() == 801){
				montoCoberturado = lstCreditoGarantia.get(i).getMontoImporteCubierto();
			}else{
				montoCoberturado = lstCreditoGarantia.get(i).getMontoSaldoCredito();
			}
			
			if(oEGarantiaData.getCodigoMoneda() == lstCreditoGarantia.get(i).getCodigoMoneda()){
				montoConversionCoberturado += montoCoberturado;
				montoConversionAsignado += lstCreditoGarantia.get(i).getMontoImporteCubierto();
			}else{
				if(oEGarantiaData.getCodigoMoneda() == UMoneda.COD_SOLES && lstCreditoGarantia.get(i).getCodigoMoneda() == UMoneda.COD_DOLARES){
					montoConversionCoberturado += montoCoberturado * oETipoCambioData.getTipoCambioSBS();
					montoConversionAsignado += lstCreditoGarantia.get(i).getMontoImporteCubierto() * oETipoCambioData.getTipoCambioSBS();
				}else if(oEGarantiaData.getCodigoMoneda() == UMoneda.COD_DOLARES && lstCreditoGarantia.get(i).getCodigoMoneda() == UMoneda.COD_SOLES){
					montoConversionCoberturado += montoCoberturado / oETipoCambioData.getTipoCambioSBS();
					montoConversionAsignado += lstCreditoGarantia.get(i).getMontoImporteCubierto() / oETipoCambioData.getTipoCambioSBS();
				}
			}
			lstCreditoGarantia.get(i).setMontoCoberturado(montoCoberturado);
			lstCreditoGarantia.get(i).setMontoConversionCoberturado(montoConversionCoberturado);
			montoTotalConversionCoberturado = montoTotalConversionCoberturado + montoConversionCoberturado;
			montoTotalConversionAsignado = montoTotalConversionAsignado + montoConversionAsignado;
		}
		
		montoAcumuladoCoberturado = montoTotalConversionCoberturado;
		montoAcumuladoAsignadoCredito = montoTotalConversionAsignado;
		
		for(int j=0;j<lstCreditoGarantia.size();j++){
			double porcentajeCoberturado = 0;
			if(lstCreditoGarantia.get(j).getMontoConversionCoberturado() != 0){
				porcentajeCoberturado = ((lstCreditoGarantia.get(j).getMontoConversionCoberturado() * 100) / montoTotalConversionCoberturado);
			}
			lstCreditoGarantia.get(j).setPorcentajeCoberturado(porcentajeCoberturado);
		}
	}
	
	//*************************************//
	//Metodos para Generar Documento
	//*************************************//
	public void imprimirDocumento1() {
		if (USistemaOperativo.ES_WINDOWS) {
			rutaBaseFormato = URutaCarpetaCompartida.rutaBaseWindows;
		}else if (USistemaOperativo.ES_LINUX) {
			rutaBaseFormato = URutaCarpetaCompartida.rutaBaseLinux;
		}
        
		rutaBasePlantilla = rutaBaseFormato + "Legal";
		
		String plantilla = "";
		String nombreArchivo = "";
		UNumeroLetra NumLetra = new UNumeroLetra();

		plantilla = "FormatoCancelacionyLevantamiento_001.docx";
		nombreArchivo = "CancelacionyLevantamiento_";
		
		String rutaPlantillaFichaSocio = rutaBasePlantilla + File.separator + plantilla;
		String codigoSocio = oEClienteData.getCodigoCliente()+"";
		XWPFDocument documeWord = UtilPoi.obtenerArchivoWord(rutaPlantillaFichaSocio);
		
		String placa = (oEGarantiaData.getPlaca() != null ? oEGarantiaData.getPlaca():"");
		String marca = (oEGarantiaData.getMarca() != null ? oEGarantiaData.getMarca():"");
		String modelo = (oEGarantiaData.getModelo() != null ? oEGarantiaData.getModelo():"");
		String motor = (oEGarantiaData.getUbicacion2() != null ? oEGarantiaData.getUbicacion2():"");
		String serie = (oEGarantiaData.getUbicacion1() != null ? oEGarantiaData.getUbicacion1():"");
		String montoLetra = NumLetra.convertir(oEGarantiaData.getMontoGarantia()+"", false, oEGarantiaData.getCodigoMoneda()+"");
		String abreviacionMoneda = (oEGarantiaData.getAbreviacionMoneda() != null ? oEGarantiaData.getAbreviacionMoneda():"");
		String monto = abreviacionMoneda +" "+ UFuncionesGenerales.convertirEnteroACadenaDecimal(oEGarantiaData.getMontoGarantia());
		//String monto = "("+ oEGarantiaData.getAbreviacionMoneda() +" "+ String.format("%,.2f", oEGarantiaData.getMontoGarantizado()) +")";
		String nombreSocio = (oEClienteData.getNombreLargo() != null ? oEClienteData.getNombreLargo():"");
		String documentoSocio = (oEClienteData.getDocumento() != null ? oEClienteData.getDocumento():"");
		String fechaInscripcion = (UFuncionesGenerales.convertirFechaACadena(oEGarantiaTramiteData.getFechaInscripcion(), "dd 'de' MMMM 'de' yyyy"));
		String fichaInscripcion = (oEGarantiaTramiteData.getFichaInscripcion() != null ? oEGarantiaTramiteData.getFichaInscripcion():"");
		
		String sociedadSocio = "";
		String tipoDocumentoSocio = "";
		if(oEClienteData.getCodigoTipoDocumento().equals(UTipoDocumento.RUC)){
			sociedadSocio = "a favor ";
			tipoDocumentoSocio = "RUC ";
		}else{
			tipoDocumentoSocio = "DNI ";
			sociedadSocio = "a favor ";
			if(oEClienteData.getCodigoTipoDocumentoConyugue() != null){
			if(!oEClienteData.getCodigoTipoDocumentoConyugue().equals("")){
				sociedadSocio = "a favor de la sociedad conyugal conformada por el señor(a) ";
			}
			}
		}
		
		String nombreConyugue = "";
		String documentoConyugue = "";
		if(oEClienteData.getCodigoTipoDocumentoConyugue() != null){
		if(!oEClienteData.getCodigoTipoDocumentoConyugue().equals("")){
			nombreConyugue = "y por la señora "+ oEClienteData.getNombreConyugue() +",";
			documentoConyugue = "identificada con DNI "+ oEClienteData.getDocumentoConyugue() +",";
		}
		}
		
		UtilPoi.reemplazarPalabraenTabla(documeWord, "@001", placa);
		UtilPoi.reemplazarPalabraenTabla(documeWord, "@002", marca);
		UtilPoi.reemplazarPalabraenTabla(documeWord, "@003", modelo);
		UtilPoi.reemplazarPalabraenTabla(documeWord, "@004", motor);
		UtilPoi.reemplazarPalabraenTabla(documeWord, "@005", serie);
		UtilPoi.reemplazarPalabraenParrafo(documeWord, "@006", montoLetra);
		UtilPoi.reemplazarPalabraenParrafo(documeWord, "@007", monto);
		UtilPoi.reemplazarPalabraenParrafo(documeWord, "@008", UFuncionesGenerales.convertirFechaACadena(new Date(), "dd 'de' MMMM 'de' yyyy"));
		UtilPoi.reemplazarPalabraenParrafo(documeWord, "@009", sociedadSocio);
		UtilPoi.reemplazarPalabraenParrafo(documeWord, "@010", tipoDocumentoSocio);
		UtilPoi.reemplazarPalabraenParrafo(documeWord, "@011", nombreSocio);
		UtilPoi.reemplazarPalabraenParrafo(documeWord, "@012", documentoSocio);
		UtilPoi.reemplazarPalabraenParrafo(documeWord, "@013", nombreConyugue);
		UtilPoi.reemplazarPalabraenParrafo(documeWord, "@014", documentoConyugue);
		UtilPoi.reemplazarPalabraenParrafo(documeWord, "@015", fechaInscripcion);
		UtilPoi.reemplazarPalabraenParrafo(documeWord, "@016", fichaInscripcion);
		
		String sufijo = UFuncionesGenerales.convertirFechaACadena(new Date(), "ddMMyyyyHHmmss");
		String nombreArchivoWord = nombreArchivo + UFuncionesGenerales.revisaCadena(codigoSocio).concat("_").concat(sufijo).concat(".docx");
		String nombreArchivoPdf = nombreArchivo + UFuncionesGenerales.revisaCadena(codigoSocio).concat("_").concat(sufijo).concat(".pdf");

		String rutaWordGenerado = UtilPoi.generarArchivoWord(documeWord, rutaBaseFormato + nombreArchivoWord);
		String rutaPdfGenerado = rutaBaseFormato + nombreArchivoPdf;

		//UManejadorArchivo.conviertirArchivoAPDF(rutaWordGenerado);
		UFuncionesGenerales.descargaArchivo(rutaWordGenerado);
		UFuncionesGenerales.borrarArchivo(rutaWordGenerado);
		//UFuncionesGenerales.borrarArchivo(rutaPdfGenerado);
	}
	
	public void imprimirDocumento2(int indicadorDocumento) {
		if (USistemaOperativo.ES_WINDOWS) {
			rutaBaseFormato = URutaCarpetaCompartida.rutaBaseWindows;
		}else if (USistemaOperativo.ES_LINUX) {
			rutaBaseFormato = URutaCarpetaCompartida.rutaBaseLinux;
		}
        
		rutaBasePlantilla = rutaBaseFormato + "Legal";
		
		String plantilla = "";
		String nombreArchivo = "";
		UNumeroLetra NumLetra = new UNumeroLetra();

		if(indicadorDocumento == 1){
			plantilla = "FormatoCancelacionyLevantamiento_002.docx";
		}else if(indicadorDocumento == 2){
			plantilla = "FormatoCancelacionyLevantamiento_003.docx";
		}else if(indicadorDocumento == 3){
			plantilla = "FormatoCancelacionyLevantamiento_004.docx";
		}
		
		nombreArchivo = "CancelacionyLevantamiento_";
		
		String rutaPlantillaFichaSocio = rutaBasePlantilla + File.separator + plantilla;
		String codigoSocio = oEClienteData.getCodigoCliente()+"";
		XWPFDocument documeWord = UtilPoi.obtenerArchivoWord(rutaPlantillaFichaSocio);
		
		String montoLetra = NumLetra.convertir(oEGarantiaData.getMontoGarantia()+"", false, oEGarantiaData.getCodigoMoneda()+"");
		String abreviacionMoneda = (oEGarantiaData.getAbreviacionMoneda() != null ? oEGarantiaData.getAbreviacionMoneda():"");
		String monto = abreviacionMoneda +" "+ UFuncionesGenerales.convertirEnteroACadenaDecimal(oEGarantiaData.getMontoGarantia());
		String nombreSocio = (oEClienteData.getNombreLargo() != null ? oEClienteData.getNombreLargo():"");
		String documentoSocio = (oEClienteData.getDocumento() != null ? oEClienteData.getDocumento():"");
		String fechaInscripcion = (UFuncionesGenerales.convertirFechaACadena(oEGarantiaTramiteData.getFechaInscripcion(), "dd 'de' MMMM 'de' yyyy"));
		String fichaInscripcion = (oEGarantiaTramiteData.getFichaInscripcion() != null ? oEGarantiaTramiteData.getFichaInscripcion():"");
		
		String sociedadSocio = "";
		String tipoDocumentoSocio = "";
		if(oEClienteData.getCodigoTipoDocumento().equals(UTipoDocumento.RUC)){
			sociedadSocio = "a favor ";
			tipoDocumentoSocio = "RUC ";
		}else{
			tipoDocumentoSocio = "DNI ";
			sociedadSocio = "a favor ";
			if(oEClienteData.getCodigoTipoDocumentoConyugue() != null){
				if(!oEClienteData.getCodigoTipoDocumentoConyugue().equals("")){
					sociedadSocio = "a favor de la sociedad conyugal conformada por el señor(a) ";
				}
			}
		}
		
		String nombreConyugue = "";
		String documentoConyugue = "";
		if(oEClienteData.getCodigoTipoDocumentoConyugue() != null){
			if(!oEClienteData.getCodigoTipoDocumentoConyugue().equals("")){
				nombreConyugue = "y por la señora "+ oEClienteData.getNombreConyugue() +",";
				documentoConyugue = "identificada con DNI "+ oEClienteData.getDocumentoConyugue() +",";
			}
		}
		
		UtilPoi.reemplazarPalabraenParrafo(documeWord, "@001", "");
		UtilPoi.reemplazarPalabraenParrafo(documeWord, "@002", "");
		UtilPoi.reemplazarPalabraenParrafo(documeWord, "@003", "");
		UtilPoi.reemplazarPalabraenParrafo(documeWord, "@004", "");
		UtilPoi.reemplazarPalabraenParrafo(documeWord, "@005", "");
		UtilPoi.reemplazarPalabraenParrafo(documeWord, "@006", montoLetra);
		UtilPoi.reemplazarPalabraenParrafo(documeWord, "@007", monto);
		UtilPoi.reemplazarPalabraenParrafo(documeWord, "@008", UFuncionesGenerales.convertirFechaACadena(new Date(), "dd 'de' MMMM 'de' yyyy"));
		UtilPoi.reemplazarPalabraenParrafo(documeWord, "@009", sociedadSocio);
		UtilPoi.reemplazarPalabraenParrafo(documeWord, "@010", tipoDocumentoSocio);
		UtilPoi.reemplazarPalabraenParrafo(documeWord, "@011", nombreSocio);
		UtilPoi.reemplazarPalabraenParrafo(documeWord, "@012", documentoSocio);
		UtilPoi.reemplazarPalabraenParrafo(documeWord, "@013", nombreConyugue);
		UtilPoi.reemplazarPalabraenParrafo(documeWord, "@014", documentoConyugue);
		UtilPoi.reemplazarPalabraenParrafo(documeWord, "@015", fechaInscripcion);
		UtilPoi.reemplazarPalabraenParrafo(documeWord, "@016", fichaInscripcion);
		
		String sufijo = UFuncionesGenerales.convertirFechaACadena(new Date(), "ddMMyyyyHHmmss");
		String nombreArchivoWord = nombreArchivo + UFuncionesGenerales.revisaCadena(codigoSocio).concat("_").concat(sufijo).concat(".docx");
		String nombreArchivoPdf = nombreArchivo + UFuncionesGenerales.revisaCadena(codigoSocio).concat("_").concat(sufijo).concat(".pdf");
		
		String rutaArchivoWord = rutaBaseFormato + nombreArchivoWord;
		String rutaWordGenerado = UtilPoi.generarArchivoWord(documeWord, rutaArchivoWord);
		String rutaPdfGenerado = rutaBaseFormato + nombreArchivoPdf;

		//UManejadorArchivo.conviertirArchivoAPDF(rutaWordGenerado);
		UFuncionesGenerales.descargaArchivo(rutaWordGenerado);
		UFuncionesGenerales.borrarArchivo(rutaWordGenerado);
		//UFuncionesGenerales.borrarArchivo(rutaPdfGenerado);
	}
	
	public void inicializar() {
		codigoEstadoDocumento = 0;
		
		indicadorValidarBtnLiberar = false;
		
		deshabilitar = true;
		deshabilitarBtnGrabar = true;
		
		visualizar = false;
		visualizarTblMensaje = false;
		visualizarPnlInforme = false;
		visualizarPnlDocumentoLevantamiento = false;
		visualizarPnlClienteRelacionado = false;
		visualizarPnlCreditoRelacionado = false;

		visualizarBtnSalir = false;
		visualizarBtnAdjuntar = false;
		visualizarBtnEnviar = false;
		visualizarBtnRechazar = false;
		visualizarBtnConfirmar = false;
		visualizarBtnLiberar = false;
		visualizarBtnEntregar = false;
		visualizarBtnArchivar = false;
		visualizarBtnGrabar = false;
		visualizarBtnGenerarDocumento1 = false;
		visualizarBtnGenerarDocumento2 = false;
		visualizarBtnGenerarDocumento3 = false;
		visualizarBtnGenerarDocumento4 = false;
		//Adicional
		deshabilitarGrabarDocumento = true;
		deshabilitarSolFirma = false;
	}
		
	//*************************************//
	//Metodos para documento de carga
    //*************************************//
	public void generarCorrelativoDocumentoCarga() {
		int correlativoDocumento = 0;
		for(int i=0;i<lstDocumentoCarga.size();i++){
			correlativoDocumento = correlativoDocumento +1;
			String nombreDocumento = lstDocumentoCarga.get(i).getNombre();
			lstDocumentoCarga.get(i).setNombreLaserFiche(correlativoDocumento+"-"+nombreDocumento);
		}
	}
	
	public void grabarDocumento(){
		generarCorrelativoDocumentoCarga();
		List<EDocumentoCarga> elstDocumentoCarga = new ArrayList<EDocumentoCarga>();
		elstDocumentoCarga = lstDocumentoCarga;
		EGarantia oEGarantia = new EGarantia();
		oEGarantia = oEGarantiaData;
		
		oEGarantia.setCodigoUbigeo((UFuncionesGenerales.convierteCadenaAEntero(
					UFuncionesGenerales.convertirEnteroACadenaUbigeo(codigoDepartamentoGarantia) + 
					UFuncionesGenerales.convertirEnteroACadenaUbigeo(codigoProvinciaGarantia) + 
					UFuncionesGenerales.convertirEnteroACadenaUbigeo(codigoDistritoGarantia))));	
		oEGarantia.setUsuarioRegistro(oEUsuario);	
		oEGarantia.setAreaEmisora(UArea.LEGAL_DESC);
		if(lstOperacionDocumento != null){
			if(lstOperacionDocumento.size()>0){
				oEGarantia.setLstOperacionDocumento(lstOperacionDocumento);
			}	
		}
		
		oEMensaje = oBOGarantia.agregarDocumentoGarantia(oEGarantia, elstDocumentoCarga);	
		deshabilitarGrabarDocumento = true;
		lstDocumentoCarga = new ArrayList<EDocumentoCarga>();
		
		UManejadorLog.log(" Guardar: " + oEMensaje.getDescMensaje());
		RequestContext.getCurrentInstance().execute("PF('dlgMensajeOperacionAjax').show();");
	}
	
	public void listarDocumento() {
		EGarantia eGarantia = new EGarantia();
		eGarantia = oEGarantiaData;
		eGarantia.setUsuarioRegistro(oEUsuario);
		lstOperacionDocumento = oBOOperacion.listarDocumentoGarantia(eGarantia,1);
		lstOperacionDocumentoFiltro = lstOperacionDocumento.stream()
				.filter(x -> !x.getAreaReceptora().equals("NOTARIA"))
				.collect(Collectors.toList());
	}
	
	public void visualizarObservacionFirma(EOperacionDocumento oEOperacionDocumentoItem){
		if(oEOperacionDocumentoItem != null){
			if(oEOperacionDocumentoItem.getEstadoDocumento() == 56){
				deshabilitarSolFirma = true;
			}
			oEOperacionDocumento = oEOperacionDocumentoItem;
			RequestContext.getCurrentInstance().execute("PF('dlgSolicitudFirma').show();");
		}
		
	}
	
	public void verificarFirma(EOperacionDocumento oEOperacionDocumentoItem){
		if(oEOperacionDocumentoItem != null){
			if(oEOperacionDocumentoItem.getFirmaDocumento().equals("SI")) codigoFirmaSiNo = 1;		
			else codigoFirmaSiNo = 2;
			oEOperacionDocumento = oEOperacionDocumentoItem;
			RequestContext.getCurrentInstance().execute("PF('dlgFirma').show();");
		}
	}
	
    public void guardarSolicitudFirma(){
		RequestContext.getCurrentInstance().execute("PF('dlgSolicitudFirma').hide();");
		
		EGarantia oEGarantia = new EGarantia();
		oEGarantia = oEGarantiaData;
	
		oEGarantia.setUsuarioRegistro(oEUsuario);	
		oEGarantia.setAreaEmisora(UArea.LEGAL_DESC);
		oEGarantia.setAreaReceptora(UArea.NEGOCIOS_DESC);
		oEGarantia.setEstadoDocumento(UEstado.ENTRAMITE);
		oEGarantia.setFirmaDocumento("NO");
		oEGarantia.setOperacionDocumento(oEOperacionDocumento);
		oEGarantia.setObservacionDocumento(oEOperacionDocumento.getObervacionDocumento());
		/*if(lstOperacionDocumento != null){
			if(lstOperacionDocumento.size()>0){
				oEGarantia.setLstOperacionDocumento(lstOperacionDocumento);
			}	
		}*/
		
		oEMensaje = oBOOperacion.modificarDocumentoGarantia(oEGarantia);
		
		UManejadorLog.log(" Guardar: " + oEMensaje.getDescMensaje());
		RequestContext.getCurrentInstance().execute("PF('dlgMensajeOperacionAjax').show();");
	
	}
	
	public boolean visualizarBtnEntrega(EOperacionDocumento oEOperacionDocumentoItem){
		boolean ind = false;
		if (oEOperacionDocumentoItem != null) {
			if(oEOperacionDocumentoItem.getEstadoDocumento() == UEstado.DOCUMENTOFIRMADO){
				ind = true;
			}else{
				ind = false;
			}
		}
		
		return ind;
	}
	
    public void verificarEntrega(EOperacionDocumento oEOperacionDocumentoItem){
    	oEOperacionDocumento = oEOperacionDocumentoItem;
    	RequestContext.getCurrentInstance().execute("PF('dlgEntrega').show();");
    }
    
	public void guardarEntrega(){
		if(oEOperacionDocumento != null){
			oEOperacionDocumento.setEstadoDocumento(UEstado.ENTREGADOSOCIO);
			oEOperacionDocumento.setUsuarioRegistro(oEUsuario);
			oEMensaje = oBOOperacion.modificarFirmaDocumentoGarantia(oEOperacionDocumento);
			
			UManejadorLog.log(" Guardar: " + oEMensaje.getDescMensaje());
			RequestContext.getCurrentInstance().execute("PF('dlgEntrega').hide();");
			RequestContext.getCurrentInstance().execute("PF('dlgMensajeOperacionAjax').show();");
		}
	}
    
    public void procesarFirma(){
		RequestContext.getCurrentInstance().execute("PF('dlgFirma').hide();");
		EOperacionDocumento eoperacionDocumento = new EOperacionDocumento();
		
		eoperacionDocumento = oEOperacionDocumento;
		if(codigoFirmaSiNo == 1){
			eoperacionDocumento.setFirmaDocumento("SI");
			eoperacionDocumento.setEstadoDocumento(UEstado.DOCUMENTOFIRMADO);
		}else{
			eoperacionDocumento.setFirmaDocumento("NO");
			eoperacionDocumento.setEstadoDocumento(UEstado.PENDIENTEFIRMA);
		}
		
		eoperacionDocumento.setUsuarioRegistro(oEUsuario);
			oEMensaje = oBOOperacion.modificarFirmaDocumentoGarantia(eoperacionDocumento);
		
		UManejadorLog.log(" Guardar: " + oEMensaje.getDescMensaje());
		RequestContext.getCurrentInstance().execute("PF('dlgMensajeOperacionAjax').show();");
	}
    
    public void actualizarDatoasAjax(){
		listarDocumento();
		habilitarAccionLiberar();
	}
	
	public EMensaje getoEMensaje() {
		return oEMensaje;
	}

	public void setoEMensaje(EMensaje oEMensaje) {
		this.oEMensaje = oEMensaje;
	}
	
	public EGarantiaSolicitud getoEGarantiaSolicitudLoad() {
		return oEGarantiaSolicitudLoad;
	}

	public void setoEGarantiaSolicitudLoad(
			EGarantiaSolicitud oEGarantiaSolicitudLoad) {
		this.oEGarantiaSolicitudLoad = oEGarantiaSolicitudLoad;
	}
	
	public ECliente getoEClienteData() {
		return oEClienteData;
	}

	public void setoEClienteData(ECliente oEClienteData) {
		this.oEClienteData = oEClienteData;
	}
	
	public EGarantia getoEGarantiaData() {
		return oEGarantiaData;
	}

	public void setoEGarantiaData(EGarantia oEGarantiaData) {
		this.oEGarantiaData = oEGarantiaData;
	}
	
	/*
	public EOperacionMensaje getoEOperacionMensajeSelected() {
		return oEOperacionMensajeSelected;
	}

	public void setoEOperacionMensajeSelected(
			EOperacionMensaje oEOperacionMensajeSelected) {
		this.oEOperacionMensajeSelected = oEOperacionMensajeSelected;
	}
	
	public EPersona getoEPersonaSelected() {
		return oEPersonaSelected;
	}

	public void setoEPersonaSelected(EPersona oEPersonaSelected) {
		this.oEPersonaSelected = oEPersonaSelected;
	}

	public EGeneral getoEReceptorSelected() {
		return oEReceptorSelected;
	}

	public void setoEReceptorSelected(EGeneral oEReceptorSelected) {
		this.oEReceptorSelected = oEReceptorSelected;
	}
	*/
	
	//Adicional
	public EOperacionDocumento getoEOperacionDocumento() {
		return oEOperacionDocumento;
	}

	public void setoEOperacionDocumento(EOperacionDocumento oEOperacionDocumento) {
		this.oEOperacionDocumento = oEOperacionDocumento;
	}
	
	
	
	
	
}
