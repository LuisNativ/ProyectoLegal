package com.abaco.ageneral;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.nio.charset.CodingErrorAction;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Properties;
import java.util.stream.Collector;
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
import javax.persistence.metamodel.CollectionAttribute;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

import lombok.Data;
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

import com.abaco.ageneral.BORevision;
import com.abaco.ageneral.EOperacionSolicitud;
import com.abaco.bo.BOGeneral;
import com.abaco.entidad.EGeneral;
import com.abaco.entidad.EPersona;
import com.abaco.entidad.EPersonaParametro;
import com.abaco.entidad.EPlantillaEmail;
import com.abaco.ageneral.ESolicitudCredito;
import com.abaco.entidad.EUsuario;
import com.abaco.entidad.EMensaje;
import com.abaco.negocio.util.UConstante;
import com.abaco.negocio.util.UConstante.UAccionExterna;
import com.abaco.negocio.util.UConstante.UAccionInterna;
import com.abaco.negocio.util.UConstante.UAccionTabla;
import com.abaco.negocio.util.UConstante.UActualizacionFlag;
import com.abaco.negocio.util.UConstante.UArea;
import com.abaco.negocio.util.UConstante.UAsignacionInmueble;
import com.abaco.negocio.util.UConstante.UEstado;
import com.abaco.negocio.util.UConstante.UEstadoGarantia;
import com.abaco.negocio.util.UConstante.UMaximoTamanio;
import com.abaco.negocio.util.UConstante.UMensajeTabla;
import com.abaco.negocio.util.UConstante.UModoIngreso;
import com.abaco.negocio.util.UConstante.UMoneda;
import com.abaco.negocio.util.UConstante.UPersonaRelacion;
import com.abaco.negocio.util.UConstante.URutaCarpetaCompartida;
import com.abaco.negocio.util.UConstante.UTipoCliente;
import com.abaco.negocio.util.UConstante.UTipoDocumento;
import com.abaco.negocio.util.UConstante.UTipoDocumentoGarantia;
import com.abaco.negocio.util.UConstante.UTipoEstadoUsuario;
import com.abaco.negocio.util.UConstante.UTipoGarantia;
import com.abaco.negocio.util.UConstante.UTipoInmueble;
import com.abaco.negocio.util.UConstante.UTipoPersona;
import com.abaco.negocio.util.UConstante.UTipoTerceroPersona;
import com.abaco.negocio.util.UConstante.UVariablesSesion;
import com.abaco.negocio.util.UFuncionesGenerales;
import com.abaco.negocio.util.UGeneradorQueryString;
import com.abaco.negocio.util.UManejadorArchivo;
import com.abaco.negocio.util.UManejadorListaDesplegable;
import com.abaco.negocio.util.UManejadorLog;
import com.abaco.negocio.util.UManejadorSesionWeb;
import com.abaco.negocio.util.UNumeroLetra;
import com.abaco.negocio.util.UtilPoi;
import com.abaco.servicio.laserfiche.Documento;


@ManagedBean(name = "mbmantenimientooperaciongarantia")
@ViewScoped
public class MBMantenimientoOperacionGarantia implements Serializable {
	private static final long serialVersionUID = 1L;
	//Entidades
	private EUsuario oEUsuario;
	private EMensaje oEMensaje;
	private EGarantia oEGarantiaLoad;
	private EGeneral oETipoGarantiaLoad;
	private EGarantia oEGarantiaData;
	private ETercero oETerceroData;
	private EGarantia oEGarantiaAnexoData;
	private EGarantia oEGarantiaInmuebleData;
	private EPersona oEPersonaSelected;
	private EPoliza oEPolizaSelected;
	private EPoliza oEPolizaGarantiaData;
	private EOperacionDocumento oEOperacionDocumentoData;
	private EOperacionDocumento oEOperacionDocumentoNotariaData;
	private EFlagReqLegal oEFlagRequisitoLegalData;
	//BO
	private BOGarantia oBOGarantia;
	private BOCliente oBOCliente;
	private BOOperacion oBOOperacion;
	private BORepresentanteLegal oBORepresentanteLegal;
	private BOSolicitudCredito oBOSolicitudCredito;
	
	//Indicadores para Habilitar/Deshabilitar 
	@Getter @Setter private boolean deshabilitarCampo,deshabilitarCampoInmuebleAdicional,deshabilitarCampoAsignacion;
	@Getter @Setter private boolean deshabilitarCampoTasacion;
	@Getter @Setter private boolean deshabilitarBusqueda;
	@Getter @Setter private boolean deshabilitarBotonEnviar;
	@Getter @Setter private boolean visualizarCamposOtrasGarantias;
	@Getter @Setter private boolean visualizarTextoRegistro;
	@Getter @Setter private boolean visualizarBtnAgregarPropietario,visualizarBtnEliminarPropietario;
	
	//Indicadores para Renderizar y Visualizar Paneles de Acuerdo al Tipo de Garantía
	@Getter @Setter private boolean indicadorPnlDetalleGarantiaPredio;
	@Getter @Setter private boolean indicadorPnlDetalleGarantiaVehicular;
	@Getter @Setter private boolean indicadorPnlDetalleGarantiaAcciones;
	@Getter @Setter private boolean indicadorPnlDetalleGarantiaFianzas;
	@Getter @Setter private boolean indicadorPnlDetalleGarantiaMaquinaria;
	@Getter @Setter private boolean indicadorPnlDetalleGarantiaMercaderia;
	@Getter @Setter private boolean indicadorPnlDetalleGarantiaWarrant;
	@Getter @Setter private boolean indicadorPnlDetalleGarantiaFideicomiso;
	@Getter @Setter private boolean indicadorPnlDetalleGarantiaDocPorCobrar;
	@Getter @Setter private boolean indicadorPnlDetalleGarantiaOtros;
	
	//Ubigeo para Garantia (Predios)
	@Getter @Setter private int codigoDepartamentoGarantia,codigoDepartamentoInmueble;
	@Getter @Setter private int codigoProvinciaGarantia,codigoProvinciaInmueble;
	@Getter @Setter private int codigoDistritoGarantia,codigoDistritoInmueble;
	@Getter @Setter private List<EGeneral> lstDepartamentoGarantia,lstDepartamentoInmueble;
	@Getter @Setter private List<EGeneral> lstProvinciaGarantia,lstProvinciaInmueble;
	@Getter @Setter private List<EGeneral> lstDistritoGarantia,lstDistritoInmueble;
	
	//Ubigeo para Tercero
	@Getter @Setter private int codigoDepartamentoGarantiaTercero,codigoDepartamentoGarantiaPostalTercero;
	@Getter @Setter private int codigoProvinciaGarantiaTercero,codigoProvinciaGarantiaPostalTercero;
	@Getter @Setter private int codigoDistritoGarantiaTercero,codigoDistritoGarantiaPostalTercero;
	@Getter @Setter private List<EGeneral> lstDepartamentoGarantiaTercero,lstDepartamentoGarantiaPostalTercero;
	@Getter @Setter private List<EGeneral> lstProvinciaGarantiaTercero,lstProvinciaGarantiaPostalTercero;
	@Getter @Setter private List<EGeneral> lstDistritoGarantiaTercero,lstDistritoGarantiaPostalTercero;
	
	//Listas Desplegables 
	private UManejadorListaDesplegable oUManejadorListaDesplegable;
	@Getter @Setter private List<EGeneral> lstTipoGarantia;
	@Getter @Setter private List<EGeneral> lstTipoPrendaGarantia;
	@Getter @Setter private List<EGeneral> lstTipoMoneda;
	@Getter @Setter private List<EGeneral> lstTipoCiaSeguro;
	@Getter @Setter private List<EGeneral> lstTipoFiador;
	@Getter @Setter private List<EGeneral> lstTipoFianza;
	@Getter @Setter private List<EGeneral> lstTipoPlazo;
	@Getter @Setter private List<EGeneral> lstTipoBanco;
	@Getter @Setter private List<EGeneral> lstEstadoGarantia;
	@Getter @Setter private List<EGeneral> lstSituacionGarantia;
	@Getter @Setter private List<EGeneral> lstTipoAlmacen;
	@Getter @Setter private List<EGeneral> lstValorSiNo;
	@Getter @Setter private List<EGeneral> lstTipoMercaderiaWarrant;
	@Getter @Setter private List<EGeneral> lstOficinaRegistral;
	@Getter @Setter private List<EGeneral> lstTipoRegistral;
	@Getter @Setter private List<EGeneral> lstTipoDocumento;
	@Getter @Setter private List<EGeneral> lstClasePersona;
	@Getter @Setter private List<EGeneral> lstProveedor;
	@Getter @Setter private List<EGeneral> lstAceptante;
	@Getter @Setter private List<EGeneral> lstComunidad;
	@Getter @Setter private List<EGeneral> lstBancaUnidad;
	@Getter @Setter private List<EGeneral> lstZonaGeografica;
	@Getter @Setter private List<EGeneral> lstEstadoCivil;
	@Getter @Setter private List<EGeneral> lstSexo;
	
	private int accionExterna;
	private int indexPropietario;
	@Getter @Setter private Double montoAcumuladoAsignadoCredito;    
	@Getter @Setter private Double montoAcumuladoSaldoCredito;  
	@Getter @Setter private Double montoAcumuladoCoberturado;
	//Listas de Entidades
	@Getter @Setter private List<EAsignacionContratoGarantia> lstCreditoGarantia;
	@Getter @Setter private List<EPersona> lstPropietario;
	@Getter @Setter private List<EGarantia> lstInmueblesAdicionales;
	@Getter @Setter private List<EGarantia> lstInmueblesAdicionalesFiltro;
	@Getter @Setter private List<EFlagReqLegal> lstDetalleFlagsReqLegal;
	@Getter @Setter private List<ETasacion> lstDetalleTasacion;
	@Getter @Setter private List<EOperacionDocumento> lstOperacionDocumentoGeneralGarantia;
	
	
	//Atributos para Búsqueda de Socio y Poliza
	@Getter @Setter private List<EPersona> lstPersona;
	@Getter @Setter private int codigoBuscar;
	@Getter @Setter private String descripcionBuscar;
	@Getter @Setter private int indicadorPersona; //1:Tasador, 2:Propietario, 3: Depositario, 4: Socio/Tercero
	@Getter @Setter private int indicadorCiaSeguro; //Para Buscar Cia Seguro con Poliza
	@Getter @Setter private List<EPoliza> lstPolizaSeguro ;
	
	
	//Atributos para Documentacion
	@Getter @Setter private boolean visualizarTabDocumento,visualizarTabPrestamos;
	@Getter @Setter private boolean visualizarGenerarDocumento,visualizarGenerarDocumentoHipotecario;
	@Getter @Setter private boolean  visualizarBotonIrTramite;
	@Getter @Setter private boolean  deshabilitarObservacionDocumento;
	@Getter @Setter private boolean visualizarGrabarDocumento;
	@Getter @Setter private boolean deshabilitarGrabarDocumentoNotaria;
	@Getter @Setter private boolean deshabilitarSolFirma;
	@Getter @Setter private boolean deshabilitarPanel;
	private String rutaBaseFormato;
	private String rutaBasePlantilla;
	
	/* Variables de carga File */
	private EOperacionDocumento oEOperacionDocumento,oEOperacionDocumentoDetalle,oEOperacionDocumentoDesembolso,oEOpDocDesemb;
	@Getter @Setter private StreamedContent fileDownload;
	@Getter @Setter private List<UploadedFile> files;
	@Getter @Setter private List<EDocumentoCarga> lstDocumentoCarga;
	@Getter @Setter private List<EDocumentoCarga> lstDocumentoCargaNotaria;
	@Getter @Setter private List<EDocumentoCarga> lstDocumentoCargaGeneral;
	@Getter @Setter private String descripcionDocumentoCarga;
	@Getter @Setter private String descripcionDocumento,observacionDocumento;
	@Getter @Setter private int codigoFirmaSiNo,codigoFirmaDocumento;
	@Getter @Setter private String observacionDocumentoFirma;
	@Getter @Setter private List<EOperacionDocumento> lstOperacionDocumento,lstDocumentoGarantia;
	@Getter @Setter private List<EOperacionDocumento> lstOperacionDocumentoFiltro;
	@Getter @Setter private List<EOperacionDocumento> lstOperacionDocumentoLegalFiltro;
	@Getter @Setter private List<EOperacionDocumento> lstOperacionDocumentoNegociosFiltro;
	@Getter @Setter private List<EOperacionDocumento> lstOperacionDocumentoDesembolso;
	@Getter @Setter private List<EOperacionDocumento> lstDocumentoNotaria;
	
	@Getter @Setter private boolean indicadorTituloDocumento,indicadorNuevoDocumento;
	@Getter @Setter private boolean deshabilitarAdjuntaDocumento;
	@Getter @Setter private boolean visualizarEliminarDocumentoGarantia;
	@Getter @Setter private boolean visualizarConfirmacionDocumentoGarantia;
	@Getter @Setter private boolean visualizarConfirmacionNotaria;
	@Getter @Setter private boolean habilitarIndicadorContratoPrivado;
	@Getter @Setter private boolean habilitarIndicadorBloqueoRegistral;
	@Getter @Setter private boolean habilitarFirmaDocNotarial;
	@Getter @Setter private boolean visualizarConfirmarDesembolso;
	@Getter @Setter private boolean visualizarNroSolicitud;
	@Getter @Setter private boolean colapsarPanelListaDocumento,colapsarPanelNotaria;
	@Getter @Setter private boolean tooglePanelListaDocumento,tooglePanelNotaria;
	@Getter @Setter private boolean visualizarBotonNuevoDocNotaria;
	@Getter @Setter private boolean indicadorNuevoDocumentoNotaria;
	@Getter @Setter private boolean visualizarCondicionDesembolsoMinuta;
	@Getter @Setter private boolean visualizarCondicionDesembolsoCHBF;
	@Getter @Setter private boolean visualizarCondicionDesembolsoCHBEX;
	@Getter @Setter private boolean visualizarCondicionDesembolsoGeneral;
	@Getter @Setter private boolean visualizarInmuebleAdicional;
	@Getter @Setter private boolean renderizarBotonAñadirInmueble;
	@Getter @Setter private boolean renderizarBotonCancelarInmueble;
	@Getter @Setter private boolean renderizarBotonNuevoInmueble;
	@Getter @Setter private boolean renderizarAdjuntarDocumentos;
	@Getter @Setter private boolean visualizarGenerarContratoPrivado;
	@Getter @Setter private boolean visualizarBotonAnadirCondicionLegal;
	@Getter @Setter private boolean deshabilitarCampoFlagReqLeg;
	@Getter @Setter private boolean visualizarBotonEditarEliminarReqLegal;
	@Getter @Setter private boolean visualizarTabCumplimiento;
	@Getter @Setter private boolean visualizarEditarInmueble;
	@Getter @Setter private boolean visualizarEliminarInmueble;
	@Getter @Setter private String mensajeTablaPolizaPrestamo;
	@Getter @Setter private int accionInternaInmueble;
	@Getter @Setter private int maxLgnNumeroDocumentoTercero;
	
	//Para Tercero
	@Getter @Setter private boolean visualizarDatosPersonaNatural;
	@Getter @Setter private boolean visualizarDatosPN,visualizarDatosPJ;
	@Getter @Setter private boolean renderizarBotonTercero;
	
	@Getter @Setter private boolean renderizarPolizaGarantia;
	@Getter @Setter private boolean renderizarDetalleTasacion;
	
	@PostConstruct
	public void inicio() {
		this.oEMensaje = new EMensaje();		
		oEUsuario = (EUsuario) UManejadorSesionWeb.obtieneVariableSesion(UVariablesSesion.USUARIO);
		oEOperacionDocumento = new EOperacionDocumento();
		oEOperacionDocumentoDetalle = new EOperacionDocumento();
		oEOperacionDocumentoDesembolso = new EOperacionDocumento();
		oEGarantiaInmuebleData = new EGarantia();
		oETerceroData = new ETercero();
		oEPersonaSelected = new EPersona();
		oEPolizaSelected = new EPoliza();
		oEPolizaGarantiaData = new EPoliza();
		oEOperacionDocumentoNotariaData = new EOperacionDocumento();
		oEFlagRequisitoLegalData = new EFlagReqLegal();
		oEOperacionDocumentoData = new EOperacionDocumento();
		oEOpDocDesemb = null;
		oUManejadorListaDesplegable = new UManejadorListaDesplegable();
		oBOGarantia = new BOGarantia();
		oBOCliente = new BOCliente();
		oBOOperacion = new BOOperacion();
		oBORepresentanteLegal = new BORepresentanteLegal();
		oBOSolicitudCredito = new BOSolicitudCredito();
		
		lstDepartamentoGarantia = new ArrayList<EGeneral>();
		lstProvinciaGarantia = new ArrayList<EGeneral>();
		lstDistritoGarantia = new ArrayList<EGeneral>();		
		lstDepartamentoGarantiaTercero = new ArrayList<EGeneral>();
		lstDepartamentoGarantiaPostalTercero = new ArrayList<EGeneral>();
		lstProvinciaGarantiaTercero = new ArrayList<EGeneral>();
		lstProvinciaGarantiaPostalTercero = new ArrayList<EGeneral>();
		lstDistritoGarantiaTercero = new ArrayList<EGeneral>();
		lstDistritoGarantiaPostalTercero= new ArrayList<EGeneral>();
		lstTipoGarantia = new ArrayList<EGeneral>();
		lstTipoPrendaGarantia = new ArrayList<EGeneral>();
		lstTipoMoneda = new ArrayList<EGeneral>();
		lstTipoCiaSeguro = new ArrayList<EGeneral>();
		lstTipoFiador = new ArrayList<EGeneral>();
		lstTipoFianza = new ArrayList<EGeneral>();
		lstTipoPlazo = new ArrayList<EGeneral>();
		lstTipoBanco = new ArrayList<EGeneral>();
		lstEstadoGarantia = new ArrayList<EGeneral>();
		lstSituacionGarantia = new ArrayList<EGeneral>();
		lstTipoAlmacen = new ArrayList<EGeneral>();
		lstValorSiNo = new ArrayList<EGeneral>();
		lstOficinaRegistral = new ArrayList<EGeneral>();
		lstTipoRegistral = new ArrayList<EGeneral>();
		lstTipoMercaderiaWarrant = new ArrayList<EGeneral>();
		lstTipoDocumento = new ArrayList<EGeneral>();
		lstClasePersona = new ArrayList<EGeneral>();
		lstProveedor = new ArrayList<EGeneral>();
		lstAceptante = new ArrayList<EGeneral>();
		lstComunidad = new ArrayList<EGeneral>();
	    lstBancaUnidad = new ArrayList<EGeneral>();
	    lstZonaGeografica = new ArrayList<EGeneral>();
	    lstEstadoCivil = new ArrayList<EGeneral>();
	    lstSexo = new ArrayList<EGeneral>();
		lstDocumentoCarga = new ArrayList<EDocumentoCarga>();
		lstDocumentoCargaNotaria = new ArrayList<EDocumentoCarga>();
		lstDocumentoCargaGeneral = new ArrayList<EDocumentoCarga>();
		lstDocumentoNotaria = new ArrayList<EOperacionDocumento>();
		files = new ArrayList<UploadedFile>(); 
		lstOperacionDocumento = new ArrayList<EOperacionDocumento>();
		lstOperacionDocumentoFiltro = new ArrayList<EOperacionDocumento>();
		lstOperacionDocumentoDesembolso = new ArrayList<EOperacionDocumento>();
		lstDocumentoGarantia = new ArrayList<EOperacionDocumento>();
		lstCreditoGarantia = new ArrayList<EAsignacionContratoGarantia>();
		lstPropietario = new ArrayList<EPersona>();
		lstInmueblesAdicionales = new ArrayList<EGarantia>();
		lstInmueblesAdicionalesFiltro = new ArrayList<EGarantia>();
		lstDetalleFlagsReqLegal = new ArrayList<EFlagReqLegal>();
		lstDetalleTasacion = new ArrayList<ETasacion>();
		lstOperacionDocumentoGeneralGarantia = new ArrayList<EOperacionDocumento>();

		inicializar();

		if (UManejadorSesionWeb.obtieneVariableSesion(UVariablesSesion.ACCION_EXTERNA) != null) {
			accionExterna = (Integer) UManejadorSesionWeb.obtieneVariableSesion(UVariablesSesion.ACCION_EXTERNA);
			if(UAccionExterna.NUEVO == accionExterna){
				oETipoGarantiaLoad = (EGeneral) UManejadorSesionWeb.obtieneVariableSesion(UVariablesSesion.FICHA_PARAMETRO);
				visualizarTextoRegistro = true;
				deshabilitarBusqueda = false;
				oEGarantiaData = new EGarantia();
				oEGarantiaAnexoData = new EGarantia();
				oEGarantiaData.setLstPropietario(new ArrayList<EPersona>());
				oEGarantiaData.setFechaGravamen(new Date());
				visualizarTabDocumento = false;
				visualizarTabPrestamos= false;
				visualizarTabCumplimiento= false;
				visualizarBotonIrTramite = false;
				visualizarBtnAgregarPropietario = true;
				visualizarBtnEliminarPropietario = true;
				renderizarDetalleTasacion = false;
				renderizarAdjuntarDocumentos = false;
				//Data de F5101 o F5151
				EPersona ePersona = oBOCliente.listarSocioyTercero(1, oETipoGarantiaLoad.getCodigo3()+"").get(0);
				//Habilitar Paneles de Acuerdo al Tipo de Garantía
				switch(oETipoGarantiaLoad.getCodigo2()){
				case  UTipoGarantia.PREDIO:
					 indicadorPnlDetalleGarantiaPredio= true;
					 renderizarPolizaGarantia = true;
					 listarUbigeoGarantia();			 
					 break;
				case  UTipoGarantia.VEHICULAR: indicadorPnlDetalleGarantiaVehicular= true; break;
				case  UTipoGarantia.ACCIONES:  indicadorPnlDetalleGarantiaAcciones=true; break;
				case  UTipoGarantia.FIANZAS:   indicadorPnlDetalleGarantiaFianzas=true; break;
				case  UTipoGarantia.MAQUINARIA:indicadorPnlDetalleGarantiaMaquinaria=true; break;
				case  UTipoGarantia.MERCADERIAS:indicadorPnlDetalleGarantiaMercaderia=true; break;
				case  UTipoGarantia.WARRANT:indicadorPnlDetalleGarantiaWarrant = true; break;
				case  UTipoGarantia.DOCUMENTOS_POR_COBRAR: indicadorPnlDetalleGarantiaDocPorCobrar = true; break;
				case  UTipoGarantia.FIDEICOMISO_BIENES: indicadorPnlDetalleGarantiaFideicomiso = true; break;
				case  UTipoGarantia.FLUJOS: 
				case  UTipoGarantia.SALDOCUENTA:
				case  UTipoGarantia.INVENTARIO:
					indicadorPnlDetalleGarantiaOtros = true;
					break;
				default:	
					 indicadorPnlDetalleGarantiaPredio= false;
					 indicadorPnlDetalleGarantiaVehicular= false;
					 indicadorPnlDetalleGarantiaAcciones=false;
					 indicadorPnlDetalleGarantiaFianzas=false;
					 indicadorPnlDetalleGarantiaMaquinaria=false;
					 indicadorPnlDetalleGarantiaMercaderia=false;
					 indicadorPnlDetalleGarantiaWarrant = false;
					 indicadorPnlDetalleGarantiaOtros = false;
					 indicadorPnlDetalleGarantiaFideicomiso = false;
					 indicadorPnlDetalleGarantiaDocPorCobrar = false;
					 
				}
				//Para el caso de Otras Garantías
				if(oETipoGarantiaLoad.getCodigo2()>21 && oETipoGarantiaLoad.getCodigo2()!=88){
					indicadorPnlDetalleGarantiaOtros = true;
					visualizarCamposOtrasGarantias = true; 
				}
				
				listarDesplegable();
				
				oEGarantiaData.setCodigoTipoGarantia(oETipoGarantiaLoad.getCodigo2());
				oEGarantiaData.setDescripcionTipoGarantia(oETipoGarantiaLoad.getNombreCorto());
				oEGarantiaData.setCodigoCliente(Integer.parseInt(String.valueOf(ePersona.getCodigo())));
				oEGarantiaData.setNombreCorto(ePersona.getNombreCorte());
				listarCreditosAsociadosGarantia();
	
			}else if(UAccionExterna.EDITAR == accionExterna){
				oEGarantiaLoad = (EGarantia) UManejadorSesionWeb.obtieneVariableSesion(UVariablesSesion.FICHA_PARAMETRO);
				visualizarTextoRegistro = false; 
				visualizarBotonIrTramite = true;
				deshabilitarBusqueda = true;
				renderizarAdjuntarDocumentos = true;
				visualizarBtnEliminarPropietario = true;	
				//Data de F9201
				oEGarantiaData = oBOGarantia.buscarGarantia(oEGarantiaLoad.getCodigoGarantia());
				oEPolizaGarantiaData = oBOGarantia.buscarPolizaAsociadoGarantiaMaxCorrelativo(oEGarantiaLoad.getCodigoGarantia());
				oEGarantiaData.setLstPropietario(new ArrayList<EPersona>());
				if(oEPolizaGarantiaData == null){
					oEPolizaGarantiaData = new EPoliza();
				}
				//Data de F92011
				oEGarantiaAnexoData = oBOGarantia.buscarAnexoGarantia(oEGarantiaLoad.getCodigoGarantia());
				if(oEGarantiaAnexoData != null){
					oEGarantiaData.setUbicacion1(oEGarantiaAnexoData.getUbicacion1Largo() != null ? 
							oEGarantiaAnexoData.getUbicacion1Largo():"");
					oEGarantiaData.setPartidaRegistral(oEGarantiaAnexoData.getPartidaRegistral());
					oEGarantiaData.setOficinaRegistral(oEGarantiaAnexoData.getOficinaRegistral());
					oEGarantiaData.setTipoRegistral(oEGarantiaAnexoData.getTipoRegistral());
					oEGarantiaData.setFechaComercial(oEGarantiaAnexoData.getFechaComercial());
					oEGarantiaData.setMontoComercial(oEGarantiaAnexoData.getMontoComercial());
					oEGarantiaData.setLstPropietario(oEGarantiaAnexoData.getLstPropietario());
					oEGarantiaData.setCodigoAsignacionInmueble(oEGarantiaAnexoData.getCodigoAsignacionInmueble());
					oEGarantiaData.setMontoValorizacion(oEGarantiaAnexoData.getMontoValorizacion());
				}
				
				EPersona ePersona = new EPersona();
				ePersona.setCodigo(oEGarantiaData.getCodigoPropietario());
				ePersona.setNombreCorte(oEGarantiaData.getDescripcionPropietario());
				if(oEGarantiaData.getCodigoPropietario() != 0){
					oEGarantiaData.getLstPropietario().add(0, ePersona);
				}
				visualizarBtnAgregarPropietario = oEGarantiaData.getLstPropietario().size() >= 6 ? false: true;
				//Habilitar Paneles de Acuerdo al Tipo de Garantía
				switch(oEGarantiaLoad.getCodigoTipoGarantia()){
				case  UTipoGarantia.PREDIO:
					 indicadorPnlDetalleGarantiaPredio= true;
					 renderizarPolizaGarantia=true;
					 renderizarDetalleTasacion = true;
					 listarUbigeoGarantia();
					 visualizarGenerarContratoPrivado = true;
					 EGarantiaSolicitud obj = oBOGarantia.buscarSolicitudxGarantia(oEGarantiaData.getCodigoGarantia());
					 if(obj == null){
						 visualizarGenerarDocumento= true;
						 visualizarGenerarDocumentoHipotecario = false; 
					 }else{
						 visualizarGenerarDocumento= false;
						 visualizarGenerarDocumentoHipotecario = true;
					 }
					 visualizarInmuebleAdicional = oEGarantiaData.getCodigoAsignacionInmueble() == 1 ? true : false; 
					break;
				case  UTipoGarantia.VEHICULAR: indicadorPnlDetalleGarantiaVehicular= true; renderizarPolizaGarantia=true; break;
				case  UTipoGarantia.ACCIONES: indicadorPnlDetalleGarantiaAcciones=true; break;
				case  UTipoGarantia.FIANZAS: indicadorPnlDetalleGarantiaFianzas=true; break;
				case  UTipoGarantia.MAQUINARIA: indicadorPnlDetalleGarantiaMaquinaria=true;renderizarPolizaGarantia=true;break;
				case  UTipoGarantia.MERCADERIAS: indicadorPnlDetalleGarantiaMercaderia=true;renderizarPolizaGarantia=true; break;
				case  UTipoGarantia.WARRANT: indicadorPnlDetalleGarantiaWarrant = true; break;
				case  UTipoGarantia.DOCUMENTOS_POR_COBRAR: indicadorPnlDetalleGarantiaDocPorCobrar = true;renderizarPolizaGarantia=true; break;
				case  UTipoGarantia.FIDEICOMISO_BIENES: indicadorPnlDetalleGarantiaFideicomiso = true;renderizarPolizaGarantia=true; break;
				case  UTipoGarantia.FLUJOS: 
				case  UTipoGarantia.SALDOCUENTA:
				case  UTipoGarantia.INVENTARIO:
					indicadorPnlDetalleGarantiaOtros = true; 
					renderizarPolizaGarantia=true;
					break;
				default:	
					 indicadorPnlDetalleGarantiaPredio= false;
					 indicadorPnlDetalleGarantiaVehicular= false;
					 indicadorPnlDetalleGarantiaAcciones=false;
					 indicadorPnlDetalleGarantiaFianzas=false;
					 indicadorPnlDetalleGarantiaMaquinaria=false;
					 indicadorPnlDetalleGarantiaMercaderia=false;
					 indicadorPnlDetalleGarantiaWarrant=false;
					 indicadorPnlDetalleGarantiaFideicomiso = false;
					 indicadorPnlDetalleGarantiaOtros = false;
					 indicadorPnlDetalleGarantiaDocPorCobrar = false;
				}
				//Para el caso de Otras Garantías
				if(oEGarantiaData.getCodigoTipoGarantia()>21 && oEGarantiaData.getCodigoTipoGarantia()!=88){
					indicadorPnlDetalleGarantiaOtros = true;
					visualizarCamposOtrasGarantias = true; 
				}
				visualizarTabDocumento = true;
				visualizarTabPrestamos = true;
				visualizarTabCumplimiento = true;
				listarDesplegable();
				listarDetalleTasacionGarantia();
				listarSolicitudDocumento();
				listarInmueblesAdicionales();
				listarDocumentoNotario();
				listarDocumentoGeneralGarantia();
				listarSolicitudDesembolsoGarantia();
				listarCreditosAsociadosGarantia();
				validarConfirmacionDesembolso();
				
				
				//Acciones para Negocios y sus Areas
				if(oEUsuario.getCodigoArea() == UArea.NEGOCIOS || 
				   oEUsuario.getCodigoArea()==103   ||
				   oEUsuario.getCodigoArea()==104   ||
				   oEUsuario.getCodigoArea()==105 ||
				   oEUsuario.getCodigoArea()== UArea.CREDITOS){
					if(oEGarantiaLoad.getIndicadorAccion() == 1){
						deshabilitarCampoTasacion = false;
						deshabilitarBotonEnviar = false;
						renderizarAdjuntarDocumentos = true;
					}else{
						deshabilitarCampoTasacion = true;
						deshabilitarBotonEnviar = true;
						renderizarAdjuntarDocumentos = false;
					}
					deshabilitarCampo = true;
					deshabilitarCampoAsignacion = true;
					visualizarBtnAgregarPropietario = false;
					visualizarBtnEliminarPropietario = false;
					visualizarGenerarDocumento = false;
					visualizarGenerarDocumentoHipotecario = false;
					visualizarBotonIrTramite = false;
					deshabilitarPanel = true;
					visualizarTabDocumento = false;
					visualizarTabPrestamos = true;
					visualizarTabCumplimiento = false;
					visualizarGenerarContratoPrivado = false;
					renderizarBotonNuevoInmueble = false;
					visualizarEditarInmueble = false;
					visualizarEliminarInmueble = false;
				}
				
			}
			
			
		}
	}
	
	private void inicializar(){
		 indicadorPnlDetalleGarantiaPredio= false;
		 indicadorPnlDetalleGarantiaVehicular= false;
		 indicadorPnlDetalleGarantiaAcciones=false;
		 indicadorPnlDetalleGarantiaFianzas=false;
		 indicadorPnlDetalleGarantiaMaquinaria=false;
		 indicadorPnlDetalleGarantiaMercaderia=false;
		 indicadorPnlDetalleGarantiaWarrant = false;
		 indicadorPnlDetalleGarantiaFideicomiso = false;
		 indicadorPnlDetalleGarantiaOtros = false;
		 deshabilitarCampo = false;
		 deshabilitarCampoTasacion = false;
		 deshabilitarCampoAsignacion = false;
		 visualizarCamposOtrasGarantias = false;
		 visualizarTextoRegistro = false;
		 visualizarGrabarDocumento = false;
		 deshabilitarGrabarDocumentoNotaria = true;
		 deshabilitarSolFirma = false;
		 deshabilitarBotonEnviar = false;
		 deshabilitarPanel = false;
		 descripcionDocumento="";
	     codigoFirmaDocumento=0;
	     observacionDocumento= "";
	     deshabilitarObservacionDocumento = false;
	     indicadorTituloDocumento = false;
	     deshabilitarAdjuntaDocumento = false;
	     indicadorNuevoDocumento = false;
	     visualizarEliminarDocumentoGarantia = true;
	     visualizarConfirmacionDocumentoGarantia = false;
	 	 habilitarIndicadorContratoPrivado = false;
		 habilitarIndicadorBloqueoRegistral = false;
		 habilitarFirmaDocNotarial = false;
		 visualizarNroSolicitud = false;
		 colapsarPanelListaDocumento = true;
		 tooglePanelListaDocumento = false;
		 colapsarPanelNotaria = true;
		 tooglePanelNotaria = false;
		 visualizarBotonNuevoDocNotaria = false;
		 visualizarConfirmacionNotaria = false;
		 indicadorNuevoDocumentoNotaria = false;
		 visualizarCondicionDesembolsoCHBF = false;
		 visualizarCondicionDesembolsoMinuta = false;
		 visualizarCondicionDesembolsoCHBEX = false;
		 visualizarCondicionDesembolsoGeneral = false;
		 renderizarBotonAñadirInmueble = false;
		 renderizarBotonCancelarInmueble = false;
		 renderizarBotonNuevoInmueble = true;
		 deshabilitarCampoInmuebleAdicional = true;
		 visualizarInmuebleAdicional = false;
		 visualizarGenerarContratoPrivado = false;
		 renderizarBotonTercero = false;
		 deshabilitarCampoFlagReqLeg = false;
		 visualizarBotonAnadirCondicionLegal = false;
		 visualizarBotonEditarEliminarReqLegal = false;
		 visualizarEditarInmueble = true;
		 visualizarEliminarInmueble = true;
		 renderizarPolizaGarantia = false;
		 renderizarDetalleTasacion = false;
		 renderizarAdjuntarDocumentos = false;
		 
		 mensajeTablaPolizaPrestamo = UMensajeTabla.MSJ_1;
	}
	
	public void listarDesplegable(){
		lstTipoGarantia = oUManejadorListaDesplegable.obtieneTipoGarantia();
		lstTipoPrendaGarantia = oUManejadorListaDesplegable.obtieneTipoPrendaGarantia();
		lstTipoMoneda = oUManejadorListaDesplegable.obtieneTipoMoneda();
		lstTipoCiaSeguro = oUManejadorListaDesplegable.obtieneTipoCiaSeguro();
		lstTipoFiador = oUManejadorListaDesplegable.obtieneTipoFiador();
		lstTipoFianza = oUManejadorListaDesplegable.obtieneTipoFianza();
		lstTipoPlazo = oUManejadorListaDesplegable.obtieneTipoPlazo();
		lstTipoBanco = oUManejadorListaDesplegable.obtieneTipoBanco();
		lstEstadoGarantia = oUManejadorListaDesplegable.obtieneEstadoGarantia();
		lstSituacionGarantia = oUManejadorListaDesplegable.obtieneSituacionGarantia();
		lstTipoAlmacen = oUManejadorListaDesplegable.obtieneTipoAlmacen();
		lstTipoMercaderiaWarrant = oUManejadorListaDesplegable.obtieneTipoMercaderiaWarrant();
		lstOficinaRegistral = oUManejadorListaDesplegable.obtieneOficinaRegistral();
		lstTipoRegistral = oUManejadorListaDesplegable.obtieneTipoRegistral();
		lstValorSiNo = oUManejadorListaDesplegable.obtieneValorSiNo();
		
		lstTipoDocumento = oUManejadorListaDesplegable.obtieneTipoDocumento();
		lstClasePersona = oUManejadorListaDesplegable.obtieneTipoPersona();
		lstProveedor =oUManejadorListaDesplegable.obtieneProveedor();
		lstAceptante =oUManejadorListaDesplegable.obtieneAceptante();
		lstComunidad = oUManejadorListaDesplegable.obtieneComunidadOrigen();
	    lstBancaUnidad = oUManejadorListaDesplegable.obtieneUnidad();
	    lstZonaGeografica = oUManejadorListaDesplegable.obtieneZonaGeograficaF0713();
	    lstEstadoCivil = oUManejadorListaDesplegable.obtieneEstadoCivil();
	    lstSexo = oUManejadorListaDesplegable.obtieneTipoSexo();
	}
	
	public void guardar(){

		generarCorrelativoDocumentoCargaGeneral();
		
		//Instancia de Objetos
		EGarantia oEGarantia = new EGarantia();
		List<EGarantia> lstInmueblePredios = new ArrayList<EGarantia>();
		//Pasar Datos Globales a Locales
		oEGarantia = oEGarantiaData;
		List<EDocumentoCarga> elstDocumentoCarga = new ArrayList<EDocumentoCarga>();
		elstDocumentoCarga = lstDocumentoCargaGeneral;
		

		//Setear atributos de los Objetos Instanciados
		oEGarantia.setCodigoUbigeo((UFuncionesGenerales.convierteCadenaAEntero(
					UFuncionesGenerales.convertirEnteroACadenaUbigeo(codigoDepartamentoGarantia) + 
					UFuncionesGenerales.convertirEnteroACadenaUbigeo(codigoProvinciaGarantia) + 
					UFuncionesGenerales.convertirEnteroACadenaUbigeo(codigoDistritoGarantia))));	
		oEGarantia.setUsuarioRegistro(oEUsuario);	
		
	
		oEGarantia.setUbicacion2(distribuirObservacionGarantia().getUbicacion2());
		oEGarantia.setDescripcionA(distribuirObservacionGarantia().getDescripcionA());
		oEGarantia.setDescripcionB(distribuirObservacionGarantia().getDescripcionB());
		oEGarantia.setDescripcionC(distribuirObservacionGarantia().getDescripcionC());	
		oEGarantia.setComentario(distribuirObservacionGarantia().getComentario());
		oEGarantia.setDescripcionD(distribuirObservacionGarantia().getDescripcionD());
		if(oEGarantiaData.getLstPropietario() != null){
			oEGarantia.setCodigoPropietario(oEGarantiaData.getLstPropietario().size() >= 1 ? oEGarantiaData.getLstPropietario().get(0).getCodigo(): 0);
			oEGarantia.setCodigoPropietario2(oEGarantiaData.getLstPropietario().size() >= 2 ? oEGarantiaData.getLstPropietario().get(1).getCodigo(): 0);
			oEGarantia.setCodigoPropietario3(oEGarantiaData.getLstPropietario().size() >= 3 ? oEGarantiaData.getLstPropietario().get(2).getCodigo(): 0);
			oEGarantia.setCodigoPropietario4(oEGarantiaData.getLstPropietario().size() >= 4 ? oEGarantiaData.getLstPropietario().get(3).getCodigo(): 0);
			oEGarantia.setCodigoPropietario5(oEGarantiaData.getLstPropietario().size() >= 5 ? oEGarantiaData.getLstPropietario().get(4).getCodigo(): 0);
			oEGarantia.setCodigoPropietario6(oEGarantiaData.getLstPropietario().size() == 6 ? oEGarantiaData.getLstPropietario().get(5).getCodigo(): 0);
		}

	
		
		if(UAccionExterna.NUEVO == accionExterna){
			if(oEGarantia.getCodigoTipoGarantia() == UTipoGarantia.PREDIO){
				lstInmueblePredios = lstInmueblesAdicionales;
				if(oEGarantia.getCodigoAsignacionInmueble() == UAsignacionInmueble.CONJUNTO ){
					if(lstInmueblesAdicionalesFiltro.size()>=1){
						oEMensaje = oBOGarantia.agregarGarantiaMantenimientoyInmueblePredios(oEGarantia,lstInmueblePredios);
						UManejadorLog.log(" Guardar: " + oEMensaje.getDescMensaje());
						RequestContext.getCurrentInstance().execute("PF('dlgMensajeOperacion').show();");
					}else{
						oEMensaje.setDescMensaje("No se permite Grabar en CONJUNTO si no existen Inmuebles Adicionales");
						RequestContext.getCurrentInstance().execute("PF('dlgMensaje').show();");
					}
					
				}else if (oEGarantia.getCodigoAsignacionInmueble() == UAsignacionInmueble.INDIVIDUAL){
					if(lstInmueblesAdicionalesFiltro.size() == 0){
						oEMensaje = oBOGarantia.agregarGarantiaMantenimientoyInmueblePredios(oEGarantia,lstInmueblePredios);
						UManejadorLog.log(" Guardar: " + oEMensaje.getDescMensaje());
						RequestContext.getCurrentInstance().execute("PF('dlgMensajeOperacion').show();");
					}else{
						oEMensaje.setDescMensaje("No se permite Grabar en INDIVIDUAL si aún existen inmuebles adicionales añadidos."+"\n"+
					    "Elimine los Adicionales e Intente Nuevamente");
						RequestContext.getCurrentInstance().execute("PF('dlgMensaje').show();");
					}
				}
				
			}else{
				oEMensaje = oBOGarantia.agregarGarantiaMantenimiento(oEGarantia);
				UManejadorLog.log(" Guardar: " + oEMensaje.getDescMensaje());
				RequestContext.getCurrentInstance().execute("PF('dlgMensajeOperacion').show();");
			}
			
		}else if(UAccionExterna.EDITAR == accionExterna){
			oEGarantia.setTipoDocumento(UTipoDocumentoGarantia.TASACION);
			if(oEGarantia.getCodigoTipoGarantia() == UTipoGarantia.PREDIO){
				lstInmueblePredios = lstInmueblesAdicionales;
				if(oEGarantia.getCodigoAsignacionInmueble() == UAsignacionInmueble.CONJUNTO ){
					if(lstInmueblesAdicionalesFiltro.size()>=1){
						oEMensaje = oBOGarantia.modificarGarantiaMantenimientoyInmueblePrediosyDocumentoGeneral(oEGarantia, lstInmueblePredios,elstDocumentoCarga);
						UManejadorLog.log(" Guardar: " + oEMensaje.getDescMensaje());
						RequestContext.getCurrentInstance().execute("PF('dlgMensajeOperacion').show();");
					}else{
						oEMensaje.setDescMensaje("No se permite Grabar en CONJUNTO si no existen Inmuebles Adicionales");
						RequestContext.getCurrentInstance().execute("PF('dlgMensaje').show();");
					}
					
				}else if (oEGarantia.getCodigoAsignacionInmueble() == UAsignacionInmueble.INDIVIDUAL){
					if(lstInmueblesAdicionalesFiltro.size() == 0){
						oEMensaje = oBOGarantia.modificarGarantiaMantenimientoyInmueblePrediosyDocumentoGeneral(oEGarantia, lstInmueblePredios,elstDocumentoCarga);;
						UManejadorLog.log(" Guardar: " + oEMensaje.getDescMensaje());
						RequestContext.getCurrentInstance().execute("PF('dlgMensajeOperacion').show();");
					}else{
						oEMensaje.setDescMensaje("No se permite Grabar en INDIVIDUAL si aún existen inmuebles adicionales añadidos."+"\n"+
							    "Elimine los Adicionales e Intente Nuevamente");
						RequestContext.getCurrentInstance().execute("PF('dlgMensaje').show();");
					}
				}
			}else{
				oEMensaje = oBOGarantia.modificarGarantiaMantenimientoyDocumentoGeneral(oEGarantia,elstDocumentoCarga);
				UManejadorLog.log(" Guardar: " + oEMensaje.getDescMensaje());
				RequestContext.getCurrentInstance().execute("PF('dlgMensajeOperacion').show();");
			}
			
			
		}
		
		
	}
	
	public void salir() {
		String ruta = "";
		
		if(oEUsuario.getCodigoArea() == UArea.LEGAL){
			ruta = "ListaGarantiaPorConstituir.xhtml";
			
			UManejadorSesionWeb.registraVariableSesion(UVariablesSesion.TABVIEWINDEX, 1); //Par que se muestre el mantenimiento
		}else if(oEUsuario.getCodigoArea()  == UArea.NEGOCIOS ||
				 oEUsuario.getCodigoArea()  == 103     ||
				 oEUsuario.getCodigoArea()  == 104     ||
				 oEUsuario.getCodigoArea()  == 105){
			ruta = "ListaConsultaGarantia.xhtml";
		}else if (oEUsuario.getCodigoArea()  == UArea.CREDITOS){
			if(oEGarantiaLoad.getIndicadorAccion() == 1){
				ruta = "ListaGarantiaTasacion.xhtml";		
			}else{
				ruta = "ListaConsultaGarantia.xhtml";
			}
			
		}
		else {
			ruta = "BandejaOperacionOtros.xhtml";
		}
		
		
		UManejadorSesionWeb.eliminaVariableSesion(UVariablesSesion.ACCION_EXTERNA);
		UManejadorSesionWeb.eliminaVariableSesion(UVariablesSesion.FICHA_PARAMETRO);
		
		
		UGeneradorQueryString objUGeneradorQueryString = new UGeneradorQueryString(ruta);
		UManejadorSesionWeb.redirigePagina(objUGeneradorQueryString.obtieneUrlConParametros());
	}
	
	public void actualizarDatosAjax(){
		listarSolicitudDocumento();
		listarSolicitudDesembolsoGarantia();
		listarDocumentoNotario();
		listarDocumentoGeneralGarantia();
		if(oEOperacionDocumento != null) listarSolicitudDetalleDocumento(oEOperacionDocumento);
		
	}
	
	//*************************************//
	//Begin: Metodos para Garantia (TAB=1)
	//*************************************//
	
	/*
	 * Metodo para distribuir el texto de la observacion de una garantía 
	 * hacia varios campos del Archivo F9201
	*/
	private EGarantia distribuirObservacionGarantia(){
		EGarantia eGarantia = oEGarantiaData;
		String observacion = eGarantia.getObervacionGarantia() == null ? "" : eGarantia.getObervacionGarantia() ;
		int cantCaracteresObservacion = observacion.length();
		
		switch(eGarantia.getCodigoTipoGarantia()){
		case UTipoGarantia.PREDIO:		
			if(cantCaracteresObservacion <= 0){
				eGarantia.setUbicacion2("");
				eGarantia.setDescripcionB("");
				eGarantia.setDescripcionC("");
				eGarantia.setComentario("");
				eGarantia.setDescripcionD("");
			}else if(cantCaracteresObservacion < 50){
				eGarantia.setUbicacion2(observacion.substring(0,cantCaracteresObservacion));
				eGarantia.setDescripcionB("");
				eGarantia.setDescripcionC("");
				eGarantia.setComentario("");
				eGarantia.setDescripcionD("");
			}else if(cantCaracteresObservacion < 110){
				eGarantia.setUbicacion2(observacion.substring(0,50));
				eGarantia.setDescripcionB(observacion.substring(50,cantCaracteresObservacion));
				eGarantia.setDescripcionC("");
				eGarantia.setComentario("");
				eGarantia.setDescripcionD("");
			}else if(cantCaracteresObservacion < 170 ){
				eGarantia.setUbicacion2(observacion.substring(0,50));
				eGarantia.setDescripcionB(observacion.substring(50,110));
				eGarantia.setDescripcionC(observacion.substring(110,cantCaracteresObservacion));
				eGarantia.setComentario("");
				eGarantia.setDescripcionD("");
			}else if(cantCaracteresObservacion < 230){
				eGarantia.setUbicacion2(observacion.substring(0,50));
				eGarantia.setDescripcionB(observacion.substring(50,110));
				eGarantia.setDescripcionC(observacion.substring(110,170));
				eGarantia.setComentario(observacion.substring(170,cantCaracteresObservacion));
				eGarantia.setDescripcionD("");
			}else{
				eGarantia.setUbicacion2(observacion.substring(0,50));
				eGarantia.setDescripcionB(observacion.substring(50,110));
				eGarantia.setDescripcionC(observacion.substring(110,170));
				eGarantia.setComentario(observacion.substring(170,230));
				eGarantia.setDescripcionD(observacion.substring(230));
			}


			break;
		case UTipoGarantia.VEHICULAR:	
			if(cantCaracteresObservacion <= 0){
				eGarantia.setDescripcionA("");
				eGarantia.setDescripcionB("");
				eGarantia.setDescripcionC("");
				eGarantia.setDescripcionD("");
			}else if(cantCaracteresObservacion < 60){
				eGarantia.setDescripcionA(observacion.substring(0,cantCaracteresObservacion));
				eGarantia.setDescripcionB("");
				eGarantia.setDescripcionC("");
				eGarantia.setDescripcionD("");
			}else if(cantCaracteresObservacion < 120 ){
				eGarantia.setDescripcionA(observacion.substring(0,60));
				eGarantia.setDescripcionB(observacion.substring(60,cantCaracteresObservacion));
				eGarantia.setDescripcionC("");
				eGarantia.setDescripcionD("");
			}else if(cantCaracteresObservacion < 180){
				eGarantia.setDescripcionA(observacion.substring(0,60));
				eGarantia.setDescripcionB(observacion.substring(60,120));
				eGarantia.setDescripcionC(observacion.substring(120,cantCaracteresObservacion));
				eGarantia.setDescripcionD("");
			}else{
				eGarantia.setDescripcionA(observacion.substring(0,60));
				eGarantia.setDescripcionB(observacion.substring(60,120));
				eGarantia.setDescripcionC(observacion.substring(120,180));
				eGarantia.setDescripcionD(observacion.substring(180));
			}
			
		
			break;
		default:
	    }
		
		
		if((eGarantia.getCodigoTipoGarantia()>21 && eGarantia.getCodigoTipoGarantia()!=88) ||
				eGarantia.getCodigoTipoGarantia() == UTipoGarantia.FIDEICOMISO_BIENES ||
				eGarantia.getCodigoTipoGarantia() == UTipoGarantia.SALDOCUENTA ||
				eGarantia.getCodigoTipoGarantia() == UTipoGarantia.FLUJOS ||
				eGarantia.getCodigoTipoGarantia() == UTipoGarantia.INVENTARIO){
			if(cantCaracteresObservacion <= 0){
				eGarantia.setUbicacion2("");
				eGarantia.setDescripcionB("");
				eGarantia.setDescripcionC("");
				eGarantia.setComentario("");
				eGarantia.setDescripcionD("");
			}else if(cantCaracteresObservacion < 50){
				eGarantia.setUbicacion2(observacion.substring(0,cantCaracteresObservacion));
				eGarantia.setDescripcionB("");eGarantia.setDescripcionC("");eGarantia.setComentario("");eGarantia.setDescripcionD("");
			}else if(cantCaracteresObservacion < 110){
				eGarantia.setUbicacion2(observacion.substring(0,50));
				eGarantia.setDescripcionB(observacion.substring(50,cantCaracteresObservacion));
				eGarantia.setDescripcionC("");eGarantia.setComentario("");eGarantia.setDescripcionD("");
			}else if(cantCaracteresObservacion < 170 ){
				eGarantia.setUbicacion2(observacion.substring(0,50));
				eGarantia.setDescripcionB(observacion.substring(50,110));
				eGarantia.setDescripcionC(observacion.substring(110,cantCaracteresObservacion));
				eGarantia.setComentario("");eGarantia.setDescripcionD("");
			}else if(cantCaracteresObservacion < 230){
				eGarantia.setUbicacion2(observacion.substring(0,50));
				eGarantia.setDescripcionB(observacion.substring(50,110));
				eGarantia.setDescripcionC(observacion.substring(110,170));
				eGarantia.setComentario(observacion.substring(170,cantCaracteresObservacion));
				eGarantia.setDescripcionD("");
			}else{
				eGarantia.setUbicacion2(observacion.substring(0,50));
				eGarantia.setDescripcionB(observacion.substring(50,110));
				eGarantia.setDescripcionC(observacion.substring(110,170));
				eGarantia.setComentario(observacion.substring(170,230));
				eGarantia.setDescripcionD(observacion.substring(230));
			}

		}
		
		return eGarantia;
	}
	
	
	
	//Begin: Métodos de Ubigeo
	public void obtenerDepartamentoGarantia() {
		codigoProvinciaGarantia = 0;
		codigoDistritoGarantia = 0;
		lstProvinciaGarantia = oUManejadorListaDesplegable.obtieneProvincia(codigoDepartamentoGarantia);
		lstDistritoGarantia = oUManejadorListaDesplegable.obtieneDistrito(codigoDepartamentoGarantia, codigoProvinciaGarantia);
	}
	
	public void obtenerProvinciaGarantia() {
		codigoDistritoGarantia = 0;
		lstDistritoGarantia = oUManejadorListaDesplegable.obtieneDistrito(codigoDepartamentoGarantia, codigoProvinciaGarantia);
	}

	
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
	//End : Métodos de Ubigeo
	
	//Begin: Métodos Tasacion
	public void listarDetalleTasacionGarantia(){
		lstDetalleTasacion = oBOGarantia.listarDetalleTasacionGarantia(oEGarantiaData);
	}
	//End: Métodos Tasacion
	
	//*************************************//
  	//End: Metodos para Garantia (TAB=1)
  	//*************************************//
	
	//*************************************//
  	//Begin: Metodos para Préstamos Asociadas a Garantías (TAB=2)
  	//*************************************//
	
	public void listarCreditosAsociadosGarantia(){
		double montoConversionCoberturado = 0;
		double montoTotalConversionCoberturado = 0;
		
		//Data de Tipo de Cambio
		ETipoCambio oETipoCambioData = new ETipoCambio();
		oETipoCambioData = oBOGarantia.buscarTipoCambioDiario();
		if(oETipoCambioData == null ){
			oETipoCambioData = new ETipoCambio();
		}
		
		lstCreditoGarantia = oBOGarantia.listarCreditosAsociadosGarantia(oEGarantiaData.getCodigoGarantia());
		for(int i=0;i<lstCreditoGarantia.size();i++){
			montoConversionCoberturado = 0;
			double montoCoberturado = 0;
			
			ECredito oECredito = new ECredito();
			if(lstCreditoGarantia.get(i).getCodigoProducto() == 200 && lstCreditoGarantia.get(i).getCodigoSubProducto() == 8){
				montoCoberturado = 0;
			}else {
				if(lstCreditoGarantia.get(i).getCodigoProducto() == 301 || lstCreditoGarantia.get(i).getCodigoProducto() == 302){
					oECredito = oBOSolicitudCredito.buscarCreditoAbamoshi(lstCreditoGarantia.get(i).getServicio(), lstCreditoGarantia.get(i).getNumeroOperacionTanomoshi(), lstCreditoGarantia.get(i).getNumeroLista());
				}else if(lstCreditoGarantia.get(i).getCodigoProducto() == 81){
					oECredito = oBOSolicitudCredito.buscarCreditoCartaFianza(lstCreditoGarantia.get(i).getServicio(), lstCreditoGarantia.get(i).getCodigoMoneda(), lstCreditoGarantia.get(i).getNumeroOperacion());
				}else {
					oECredito = oBOSolicitudCredito.buscarCreditoPrestamo(lstCreditoGarantia.get(i).getServicio(), lstCreditoGarantia.get(i).getNumeroOperacion());
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
			}else{
				if(oEGarantiaData.getCodigoMoneda() == UMoneda.COD_SOLES && lstCreditoGarantia.get(i).getCodigoMoneda() == UMoneda.COD_DOLARES){
					montoConversionCoberturado += montoCoberturado * oETipoCambioData.getTipoCambioSBS();
				}else if(oEGarantiaData.getCodigoMoneda() == UMoneda.COD_DOLARES && lstCreditoGarantia.get(i).getCodigoMoneda() == UMoneda.COD_SOLES){
					montoConversionCoberturado += montoCoberturado / oETipoCambioData.getTipoCambioSBS();
				}
			}
			lstCreditoGarantia.get(i).setMontoCoberturado(montoCoberturado);
			lstCreditoGarantia.get(i).setMontoConversionCoberturado(montoConversionCoberturado);
			montoTotalConversionCoberturado = montoTotalConversionCoberturado + montoConversionCoberturado;
		}
		
		for(int j=0;j<lstCreditoGarantia.size();j++){
			double porcentajeCoberturado = 0;
			if(lstCreditoGarantia.get(j).getMontoConversionCoberturado() != 0){
				porcentajeCoberturado = ((lstCreditoGarantia.get(j).getMontoConversionCoberturado() * 100) / montoTotalConversionCoberturado);
			}
			lstCreditoGarantia.get(j).setPorcentajeCoberturado(porcentajeCoberturado);
		}
	}
	
	/*
	public void consultarPolizaPrestamo(EAsignacionContratoGarantia eAsignacionContratoGarantiaItem){
		if(eAsignacionContratoGarantiaItem != null){
			if(eAsignacionContratoGarantiaItem.getNumeroOperacion()>0){
				oEPolizaGarantiaData = oBOGarantia.buscarPolizaAsociadoPrestamoMaxCorrelativo(eAsignacionContratoGarantiaItem.getNumeroOperacion());			
				if(oEPolizaGarantiaData == null){
					oEPolizaGarantiaData = new EPoliza();	
				}
			}
		}
	}
	*/
	
	//*************************************//
  	//End: Metodos para Préstamos Asociadas a Garantías (TAB=2)
  	//*************************************//
	
	 //*************************************//
   	//Begin: Metodos para Dialogs 
  	//*************************************//
	
	//->Begin: Para Dialog: dlgBuscarSocio
	public void buscarIndicadorPersona(int codigo){
		indicadorPersona = codigo;
		lstPersona = new ArrayList<EPersona>();
		codigoBuscar = 0;
		descripcionBuscar= "";
		renderizarBotonTercero = (codigo == 1) ? true : false;
		RequestContext.getCurrentInstance().execute("PF('dlgBuscarSocio').show();");
	}
	
	/*BÚSQUEDA SOCIO(F5101)/TERCERO(F5151)*/
	public void buscarSocio(){
		lstPersona = oBOCliente.listarSocioyTercero(codigoBuscar,descripcionBuscar);
	}
	
	
	public void asignarPersona(){
		if(oEPersonaSelected != null){						
			switch (indicadorPersona) {
			case 1:
				oEGarantiaData.setCodigoTasador(oEPersonaSelected.getCodigo());
				oEGarantiaData.setDescripcionTasador(oEPersonaSelected.getNombreCorte());
				break;
			case 2:
				oEGarantiaData.getLstPropietario().add(oEPersonaSelected);			
				visualizarBtnAgregarPropietario = oEGarantiaData.getLstPropietario().size() >= 6 ? false: true;
				break;
			case 3:
				oEGarantiaData.setDepositario(oEPersonaSelected.getCodigo());
				oEGarantiaData.setDescripcionDepositario(oEPersonaSelected.getNombreCorte());
				break;
			case 4:
				oEGarantiaData.setCodigoPropietario(oEPersonaSelected.getCodigo());
				oEGarantiaData.setDescripcionPropietario(oEPersonaSelected.getNombreCorte());
				oEGarantiaData.getLstPropietario().add(oEPersonaSelected);	
				break;
			default:
				oEGarantiaData.setCodigoCliente(oEPersonaSelected.getCodigo());
				oEGarantiaData.setNombreCorto(oEPersonaSelected.getNombreCorte());
				break;
			}
			
			
		}
	}
	public void asignarPersona(EPersona ePersonaItem){
		if(ePersonaItem != null){						
			switch (indicadorPersona) {
			case 1:
				oEGarantiaData.setCodigoTasador(ePersonaItem.getCodigo());
				oEGarantiaData.setDescripcionTasador(ePersonaItem.getNombreCorte());
				break;
			case 2:
				oEGarantiaData.getLstPropietario().add(ePersonaItem);			
				visualizarBtnAgregarPropietario = oEGarantiaData.getLstPropietario().size() >= 6 ? false: true;
				break;
			case 3:
				oEGarantiaData.setDepositario(ePersonaItem.getCodigo());
				oEGarantiaData.setDescripcionDepositario(ePersonaItem.getNombreCorte());
				break;
			case 4:
				oEGarantiaData.setCodigoPropietario(ePersonaItem.getCodigo());
				oEGarantiaData.setDescripcionPropietario(ePersonaItem.getNombreCorte());
				oEGarantiaData.getLstPropietario().add(ePersonaItem);	
				break;
			default:
				oEGarantiaData.setCodigoCliente(ePersonaItem.getCodigo());
				oEGarantiaData.setNombreCorto(ePersonaItem.getNombreCorte());
				break;
			}
			
			
		}
	}
	public void obtenerEliminarPropietario(EPersona oEPersonaItem){
		if(oEPersonaItem != null){
			indexPropietario = oEGarantiaData.getLstPropietario().indexOf(oEPersonaItem);			
			RequestContext.getCurrentInstance().execute("PF('dlgConfirmacion').show();");
		}
	}
	
	public void eliminarPropietario(){
		    oEGarantiaData.getLstPropietario().remove(indexPropietario);
			visualizarBtnAgregarPropietario = oEGarantiaData.getLstPropietario().size() < 6 ? true : false;
	}
	//->End: Para Dialog: dlgBuscarSocio
	
	//->Begin: Para Dialog: dlgBuscarCiaSeguro
	public void buscarIndicadorCiaSeguro(int codigo){
		indicadorCiaSeguro = codigo;
		lstPolizaSeguro = new ArrayList<EPoliza>();
		codigoBuscar = 0;
		descripcionBuscar= "";
		RequestContext.getCurrentInstance().execute("PF('dlgBuscarCiaSeguro').show();");
	}
	/*BÚSQUEDA POLIZA / CIA SEGURO*/
	public void buscarPoliza(){	
		lstPolizaSeguro = oBOGarantia.listarPolizaSeguro(codigoBuscar, descripcionBuscar,"");
	}

	public void asignarPoliza(){
		if(oEPolizaSelected != null){
			switch (indicadorCiaSeguro) {
			case 1:
				oEGarantiaData.setCodigoCiaSeguro(oEPolizaSelected.getCodigoCiaSeguro());
				oEGarantiaData.setDescripcionCiaSeguro(oEPolizaSelected.getDescripcionCiaSeguro());
				oEGarantiaData.setPoliza(oEPolizaSelected.getNumeroPoliza());
				break;
			case 2:
				break;
			case 3:
				break;
			default:
			}
			
		}
	}
	public void asignarPoliza(EPoliza ePolizaItem){
		if(ePolizaItem != null){
			switch (indicadorCiaSeguro) {
			case 1:
				oEGarantiaData.setCodigoCiaSeguro(ePolizaItem.getCodigoCiaSeguro());
				oEGarantiaData.setDescripcionCiaSeguro(ePolizaItem.getDescripcionCiaSeguro());
				oEGarantiaData.setPoliza(ePolizaItem.getNumeroPoliza());
				oEGarantiaData.setCodigoInspector(ePolizaItem.getCorrelativoPoliza());
				break;
			case 2:
				break;
			case 3:
				break;
			default:
			}
			
		}
	}
	//->End: Para Dialog: dlgBuscarCiaSeguro
	
	//*************************************//
   	//Begin: Metodos para Documentación
  	//*************************************//
	
	public void generarContratoPrivado(EOperacionDocumento oEOperacionDocumentoItem) {
		EGarantia eGarantia = oBOGarantia.buscarCaracteristicaInmueblePredio(oEOperacionDocumentoItem.getCodigoSolicitudCredito());
		int codigoTipoInmueble = eGarantia != null ? eGarantia.getTipoInmueble() : 0;
		switch(codigoTipoInmueble){
		case UTipoInmueble.TERMINADO:
			generarDocumentoCreditoHipotecarioBP(oEOperacionDocumentoItem);
			break;
		case UTipoInmueble.ENCONSTRUCCION: 
			generarDocumentoCreditoHipotecarioBF(oEOperacionDocumentoItem);
			break;
		default:					
			generarDocumentoMinutaConstitucion(oEOperacionDocumentoItem);
		}
	}
	
	//Generar Documento de Constitución (Minuta de Hipoteca)
	public void generarDocumentoMinutaConstitucion(EOperacionDocumento oEOperacionDocumentoItem) {
		
		//Instancia de Atributos Locales
		String nroDocumento = "";
		String direccion ="";
		String departamentoCliente="", provinciaCliente="",distritoCliente="";
		String plantilla = "";
		String nombreArchivo = "";
		List<ERepresentanteLegal> eRepresentante = null;
		UNumeroLetra numLetra = new UNumeroLetra();
		
		String codigoCliente = oEOperacionDocumentoItem.getCodigoCliente()+"";
		String nombreSocio = oEOperacionDocumentoItem.getNombreCliente() != null ? oEOperacionDocumentoItem.getNombreCliente():"";
		
		//Obtener Datos del Socio(F5101) o Tercero(F5151)	 
		ECliente oECliente = oBOCliente.buscarSocio(Integer.parseInt(codigoCliente)) != null ? oBOCliente.buscarSocio(Integer.parseInt(codigoCliente)):null;
		ETercero oETercero = oBOCliente.buscarTercero(Integer.parseInt(codigoCliente)) != null ? oBOCliente.buscarTercero(Integer.parseInt(codigoCliente)):null;
		
		//Obtener Datos del Usuario de Sistema
		ECliente oUsuario = oBOCliente.buscarUsuarioDetalle(oEUsuario) != null ? oBOCliente.buscarUsuarioDetalle(oEUsuario):new ECliente();
		
		switch(oUsuario.getCodigoEstadoCivil()){
		case "C":
		case "P":
			oUsuario.setDescripcionEstadoCivil("CASADO");
			break;
		default:
		}
		
		if(oECliente != null){
			nombreSocio = oECliente.getNombreLargo();
			nroDocumento = oECliente.getDocumento() != null ? oECliente.getDocumento() : "";
			direccion = oECliente.getDireccion() != null ? oECliente.getDireccion() : "";
			List<Object> ubigeo = ubigeoCliente(oECliente.getCodigoUbigeo()+"");
			departamentoCliente = ubigeo.get(0).toString();
			provinciaCliente =  ubigeo.get(1).toString();
			distritoCliente = ubigeo.get(2).toString();
			if(oECliente.getCodigoTipoPersona().equals(UTipoPersona.NATURAL)){
				plantilla = "FormatoConstitucionHipoteca_PN_001.docx";
				nombreArchivo = "ConstitucionHipoteca_PN_";
			}else if(oECliente.getCodigoTipoPersona().equals(UTipoPersona.JURIDICA_F_LUCRO) || 
					oECliente.getCodigoTipoPersona().equals(UTipoPersona.JURIDICA_S_LUCRO)){
				plantilla = "FormatoConstitucionHipoteca_PJ_001.docx";
				nombreArchivo = "ConstitucionHipoteca_PJ_";
				//Obtener Representantes de Cliente
				eRepresentante = oBORepresentanteLegal.listarRepresentanteLegal(1, oECliente.getCodigoCliente()).stream()
						         .filter(x -> x.getDocumentoRelacion().equals(oECliente.getDocumento()))
						         .filter(x -> x.getIndicadorFirma() == 1)
						         .collect(Collectors.toList());
			}
		}else if (oETercero != null){
			nombreSocio = oETercero.getNombreLargo();
			nroDocumento = oETercero.getDocumento() != null ? oETercero.getDocumento() : "";
			direccion = oETercero.getDireccion() != null ? oETercero.getDireccion() : "";
			List<Object> ubigeo = ubigeoCliente(oETercero.getCodigoUbigeo()+"");
			departamentoCliente = ubigeo.get(0).toString();
			provinciaCliente =  ubigeo.get(1).toString();
			distritoCliente = ubigeo.get(2).toString();
			if(oETercero.getCodigoTipoPersona().equals(UTipoPersona.NATURAL)){
				plantilla = "FormatoConstitucionHipoteca_PN_001.docx";
				nombreArchivo = "ConstitucionHipoteca_PN_";
			}else if(oETercero.getCodigoTipoPersona().equals(UTipoPersona.JURIDICA_F_LUCRO) ||
					oETercero.getCodigoTipoPersona().equals(UTipoPersona.JURIDICA_S_LUCRO)){
				plantilla = "FormatoConstitucionHipoteca_PJ_001.docx";
				nombreArchivo = "ConstitucionHipoteca_PJ_";
				//Obtener Representantes de Tercero
				eRepresentante = oBORepresentanteLegal.listarRepresentanteLegal(1, oETercero.getCodigoCliente()).stream()
				         .filter(x -> x.getDocumentoRelacion().equals(oETercero.getDocumento()))
				         .filter(x -> x.getIndicadorFirma() == 1)
				         .collect(Collectors.toList());
			}
		}
		
		rutaBaseFormato = URutaCarpetaCompartida.rutaBaseLinux;
		rutaBasePlantilla = rutaBaseFormato + "Legal";
		String rutaPlantilla = rutaBasePlantilla + File.separator + plantilla;
	
		//Obtener la plantila del Documento Word 
		XWPFDocument documeWord = UtilPoi.obtenerArchivoWord(rutaPlantilla);
		
		// Lenado de Datos
		String texto1 = nombreSocio;
		String texto2 = nroDocumento;
		String texto3 = direccion;
		String texto4 = departamentoCliente;
		String texto5 = provinciaCliente;
		String texto6 = distritoCliente;
		String texto7 = getFechaActual();
		String texto8 = nombreSocio;
		String texto9 = nroDocumento;
		String texto10 = oEGarantiaData.getUbicacion1();
		String texto11 =  oUManejadorListaDesplegable.obtieneDepartamento().stream()
				.filter(x -> x.getCodigo2() == codigoDepartamentoGarantia ).findAny().orElse(null).getDescripcion();
		String texto12 = oUManejadorListaDesplegable.obtieneProvincia(codigoDepartamentoGarantia).stream()
				.filter(x -> x.getCodigo2() == codigoProvinciaGarantia ).findAny().orElse(null).getDescripcion();
		String texto13 = oUManejadorListaDesplegable.obtieneDistrito(codigoDepartamentoGarantia, codigoProvinciaGarantia).stream()
				.filter(x -> x.getCodigo2() == codigoDistritoGarantia ).findAny().orElse(null).getDescripcion();
		String texto14 = numLetra.convertir(oEGarantiaData.getMontoGravamen()+"", false, oEGarantiaData.getCodigoMoneda()+"");
		String texto15 = "("+oUManejadorListaDesplegable.obtieneTipoMoneda().stream()
				.filter(x -> x.getCodigo2() == oEGarantiaData.getCodigoMoneda()).findAny().orElse(null).getNombreCorto() +" "+ oEGarantiaData.getMontoGravamen()+")";
		String texto16 = numLetra.convertir(oEGarantiaData.getMontoValoracion()+"", false, oEGarantiaData.getCodigoMoneda()+"");
		String texto17 = "("+oUManejadorListaDesplegable.obtieneTipoMoneda().stream()
				.filter(x -> x.getCodigo2() == oEGarantiaData.getCodigoMoneda()).findAny().orElse(null).getNombreCorto() +" "+ oEGarantiaData.getMontoValoracion()+")";
		String texto18 = oUsuario.getNombreLargo().trim();
		String texto19 = oUsuario.getDescripcionEstadoCivil().trim();
		String texto20 = oUsuario.getDescripcionProfesion().trim();
		String texto21 = oUsuario.getDocumento().trim();
		String texto22 = "debidamente representado por el señor ___________,"+ 
		                 " identificado con DNI __________,"+
				         " según poderes inscritos en la partida electrónica"+
		                 " ________ del Registro de Personas Jurídicas de ________";
		
		if(eRepresentante != null && eRepresentante.size()>0){
			texto22 = "";
			for(ERepresentanteLegal eRep : eRepresentante){
				if(eRep.getCodigoGenero().equals("M") && eRep.getNominativo().isEmpty()){
					eRep.setNominativo("el Sr.");
				}else if(eRep.getCodigoGenero().equals("F") && eRep.getNominativo().isEmpty()){
					eRep.setNominativo("la Srta.");
				}
				
				texto22 += "debidamente representado por " + eRep.getNominativo() + " " + eRep.getNombreLargo()+ 
						  ", identificado con DNI "+ eRep.getDocumento() + ", según poderes inscritos en la "
						  + (eRep.getInscripcionPoder1().isEmpty() ? "_______":eRep.getInscripcionPoder1())+
						  (eRepresentante.size()>1 && eRep != (eRepresentante.get(eRepresentante.size() - 1)) ? "y también, ":".");
				
			}
			
		}
		
		//Reemplazar Texto en el Documento Word
		UtilPoi.reemplazarPalabraenParrafoBold(documeWord, "@001", texto1,true);
		UtilPoi.reemplazarPalabraenParrafoBold(documeWord, "@002", texto2,false);
		UtilPoi.reemplazarPalabraenParrafoBold(documeWord, "@003", texto3,false);
		UtilPoi.reemplazarPalabraenParrafoBold(documeWord, "@004", texto4,false);
		UtilPoi.reemplazarPalabraenParrafoBold(documeWord, "@005", texto5,false);
		UtilPoi.reemplazarPalabraenParrafoBold(documeWord, "@006", texto6,false);
		UtilPoi.reemplazarPalabraenParrafo(documeWord, "@007", texto7);
		UtilPoi.reemplazarPalabraenTabla2(documeWord, "@008", texto8,true);
		UtilPoi.reemplazarPalabraenTabla2(documeWord, "@009", texto9,true);
		UtilPoi.reemplazarPalabraenTabla2(documeWord, "@010", texto10,false);
		UtilPoi.reemplazarPalabraenTabla2(documeWord, "@011", texto11,false);
		UtilPoi.reemplazarPalabraenTabla2(documeWord, "@012", texto12,false);
		UtilPoi.reemplazarPalabraenTabla2(documeWord, "@013", texto13,false);
		UtilPoi.reemplazarPalabraenTabla2(documeWord, "@014", texto14,false);
		UtilPoi.reemplazarPalabraenTabla2(documeWord, "@015", texto15,true);
		UtilPoi.reemplazarPalabraenTabla2(documeWord, "@016", texto16,false);
		UtilPoi.reemplazarPalabraenTabla2(documeWord, "@017", texto17,true);
		UtilPoi.reemplazarPalabraenParrafo(documeWord, "@018", texto18);
		UtilPoi.reemplazarPalabraenParrafo(documeWord, "@019", texto19);
		UtilPoi.reemplazarPalabraenParrafo(documeWord, "@020", texto20);
		UtilPoi.reemplazarPalabraenParrafo(documeWord, "@021", texto21);
		UtilPoi.reemplazarPalabraenParrafoBold(documeWord, "@022", texto22,false);
		
		String sufijo = UFuncionesGenerales.convertirFechaACadena(new Date(), "ddMMyyyyHHmmss");
		String nombreArchivoWord = nombreArchivo + UFuncionesGenerales.revisaCadena(codigoCliente).concat("_").concat(sufijo).concat(".docx");
		String nombreArchivoPdf = nombreArchivo + UFuncionesGenerales.revisaCadena(codigoCliente).concat("_").concat(sufijo).concat(".pdf");

		String rutaLinuxArchivoWord = rutaBaseFormato + nombreArchivoWord;
		String rutaLinuxWordGenerado = UtilPoi.generarArchivoWord(documeWord, rutaLinuxArchivoWord);
		String rutaWindowsWordGenerado = URutaCarpetaCompartida.rutaBaseWindows + nombreArchivoWord;
		String rutaLinuxWordPdfGenerado = rutaBaseFormato + nombreArchivoPdf;

		UManejadorArchivo.conviertirArchivoAPDF(rutaLinuxWordGenerado);
		UFuncionesGenerales.descargaArchivo(rutaLinuxWordGenerado);
		UFuncionesGenerales.borrarArchivo(rutaLinuxWordGenerado);
		UFuncionesGenerales.borrarArchivo(rutaLinuxWordPdfGenerado);
	}
	
	
	public void generarDocumentoCreditoHipotecarioBF(EOperacionDocumento oEOperacionDocumentoItem) {
		//Instancia de Atributos Locales
		String nroDocumento = "";
		String direccion ="";
		String departamentoCliente="", provinciaCliente="",distritoCliente="";
		String plantilla = "";
		String nombreArchivo = "";	
		UNumeroLetra numLetra = new UNumeroLetra();
		
		String codigoCliente = oEOperacionDocumentoItem.getCodigoCliente()+"";
		
		//Obtener Datos del Socio(F5101) o Tercero(F5151)	 
		ECliente oECliente = oBOCliente.buscarSocio(Integer.parseInt(codigoCliente)) != null ? oBOCliente.buscarSocio(Integer.parseInt(codigoCliente)):null;
		ETercero oETercero = oBOCliente.buscarTercero(Integer.parseInt(codigoCliente)) != null ? oBOCliente.buscarTercero(Integer.parseInt(codigoCliente)):null;
		
		//Obtener Datos del Usuario de Sistema
		ECliente oUsuario = oBOCliente.buscarUsuarioDetalle(oEUsuario) != null ? oBOCliente.buscarUsuarioDetalle(oEUsuario):new ECliente();
		
		
		if(oECliente != null){
			nroDocumento = oECliente.getDocumento();
			direccion = oECliente.getDireccion();
			List<Object> ubigeo = ubigeoCliente(oECliente.getCodigoUbigeo()+"");
			departamentoCliente = ubigeo.get(0).toString();
			provinciaCliente =  ubigeo.get(1).toString();
			distritoCliente = ubigeo.get(2).toString();
			if(oECliente.getCodigoTipoPersona().equals(UTipoPersona.NATURAL)){
				plantilla = "FormatoCreditoHipotecarioBF_001.docx";
				nombreArchivo = "CreditoHipotecarioBF_PN_";
			}else if(oECliente.getCodigoTipoPersona().equals(UTipoPersona.JURIDICA_F_LUCRO) || 
					oECliente.getCodigoTipoPersona().equals(UTipoPersona.JURIDICA_S_LUCRO)){
				plantilla = "FormatoCreditoHipotecarioBF_001.docx";
				nombreArchivo = "CreditoHipotecarioBF_PJ_";
			}
		}else if (oETercero != null){
			nroDocumento = oETercero.getDocumento();
			direccion = oETercero.getDireccion();
			List<Object> ubigeo = ubigeoCliente(oETercero.getCodigoUbigeo()+"");
			departamentoCliente = ubigeo.get(0).toString();
			provinciaCliente =  ubigeo.get(1).toString();
			distritoCliente = ubigeo.get(2).toString();
			if(oETercero.getCodigoTipoPersona().equals(UTipoPersona.NATURAL)){
				plantilla = "FormatoCreditoHipotecarioBF_001.docx";
				nombreArchivo = "ConstitucionHipoteca_PN_";
			}else if(oETercero.getCodigoTipoPersona().equals(UTipoPersona.JURIDICA_F_LUCRO) ||
					oETercero.getCodigoTipoPersona().equals(UTipoPersona.JURIDICA_S_LUCRO)){
				plantilla = "FormatoCreditoHipotecarioBF_001.docx";
				nombreArchivo = "ConstitucionHipoteca_PJ_";
			}
		}
		
		rutaBaseFormato = URutaCarpetaCompartida.rutaBaseLinux;
		rutaBasePlantilla = rutaBaseFormato + "Legal";
			
		
		String rutaPlantilla = rutaBasePlantilla + File.separator + plantilla;
	
		XWPFDocument documeWord = UtilPoi.obtenerArchivoWord(rutaPlantilla);
		
		
		String nombreSocio = oEOperacionDocumentoItem.getNombreCliente() != null ? oEOperacionDocumentoItem.getNombreCliente():"";
		String texto1 = nombreSocio;
		String texto2 = nroDocumento;
		String texto3 = direccion;
		String texto4 = departamentoCliente;
		String texto5 = provinciaCliente;
		String texto6 = distritoCliente;
		String texto7 = getFechaActual();
		String texto8 = nombreSocio;
		String texto9 = nroDocumento;
		String texto10 = oEGarantiaData.getUbicacion1();
		String texto11 =  oUManejadorListaDesplegable.obtieneDepartamento().stream()
				.filter(x -> x.getCodigo2() == codigoDepartamentoGarantia ).findAny().orElse(null).getDescripcion();
		String texto12 = oUManejadorListaDesplegable.obtieneProvincia(codigoDepartamentoGarantia).stream()
				.filter(x -> x.getCodigo2() == codigoProvinciaGarantia ).findAny().orElse(null).getDescripcion();
		String texto13 = oUManejadorListaDesplegable.obtieneDistrito(codigoDepartamentoGarantia, codigoProvinciaGarantia).stream()
				.filter(x -> x.getCodigo2() == codigoDistritoGarantia ).findAny().orElse(null).getDescripcion();
		String texto14 = numLetra.convertir(oEGarantiaData.getMontoGravamen()+"", false, oEGarantiaData.getCodigoMoneda()+"");
		String texto15 = "("+oUManejadorListaDesplegable.obtieneTipoMoneda().stream()
				.filter(x -> x.getCodigo2() == oEGarantiaData.getCodigoMoneda()).findAny().orElse(null).getNombreCorto() +" "+ oEGarantiaData.getMontoGravamen()+")";
		String texto16 = numLetra.convertir(oEGarantiaData.getMontoValoracion()+"", false, oEGarantiaData.getCodigoMoneda()+"");
		String texto17 = "("+oUManejadorListaDesplegable.obtieneTipoMoneda().stream()
				.filter(x -> x.getCodigo2() == oEGarantiaData.getCodigoMoneda()).findAny().orElse(null).getNombreCorto() +" "+ oEGarantiaData.getMontoValoracion()+")";
		String texto18=oUManejadorListaDesplegable.obtieneTipoMoneda().stream().
				filter(x -> x.getCodigo2() == oEGarantiaData.getCodigoMoneda()).findAny().orElse(null).getDescripcion();

		
		UtilPoi.reemplazarPalabraenParrafo(documeWord, "@001", texto1);
		UtilPoi.reemplazarPalabraenParrafo(documeWord, "@002", texto2);
		UtilPoi.reemplazarPalabraenParrafo(documeWord, "@003", texto3);
		UtilPoi.reemplazarPalabraenParrafo(documeWord, "@004", texto4);
		UtilPoi.reemplazarPalabraenParrafo(documeWord, "@005", texto5);
		UtilPoi.reemplazarPalabraenParrafo(documeWord, "@006", texto6);
		UtilPoi.reemplazarPalabraenParrafo(documeWord, "@007", texto7);
		UtilPoi.reemplazarPalabraenTabla2(documeWord, "@008", texto8,true);
		UtilPoi.reemplazarPalabraenTabla2(documeWord, "@009", texto9,true);
		UtilPoi.reemplazarPalabraenTabla2(documeWord, "@010", texto10,false);
		UtilPoi.reemplazarPalabraenTabla2(documeWord, "@011", texto11,false);
		UtilPoi.reemplazarPalabraenTabla2(documeWord, "@012", texto12,false);
		UtilPoi.reemplazarPalabraenTabla2(documeWord, "@013", texto13,false);
		UtilPoi.reemplazarPalabraenTabla2(documeWord, "@014", texto14,false);
		UtilPoi.reemplazarPalabraenTabla2(documeWord, "@015", texto15,true);
		UtilPoi.reemplazarPalabraenTabla2(documeWord, "@016", texto16,false);
		UtilPoi.reemplazarPalabraenTabla2(documeWord, "@017", texto17,true);
		UtilPoi.reemplazarPalabraenTabla2(documeWord, "@018", texto18,true);
		
		String sufijo = UFuncionesGenerales.convertirFechaACadena(new Date(), "ddMMyyyyHHmmss");
		String nombreArchivoWord = nombreArchivo + UFuncionesGenerales.revisaCadena(codigoCliente).concat("_").concat(sufijo).concat(".docx");
		String nombreArchivoPdf = nombreArchivo + UFuncionesGenerales.revisaCadena(codigoCliente).concat("_").concat(sufijo).concat(".pdf");

		String rutaLinuxArchivoWord = rutaBaseFormato + nombreArchivoWord;
		String rutaLinuxWordGenerado = UtilPoi.generarArchivoWord(documeWord, rutaLinuxArchivoWord);
		String rutaWindowsWordGenerado = URutaCarpetaCompartida.rutaBaseWindows + nombreArchivoWord;
		String rutaLinuxWordPdfGenerado = rutaBaseFormato + nombreArchivoPdf;

		UManejadorArchivo.conviertirArchivoAPDF(rutaLinuxWordGenerado);
		UFuncionesGenerales.descargaArchivo(rutaWindowsWordGenerado);
		UFuncionesGenerales.borrarArchivo(rutaLinuxWordGenerado);
		UFuncionesGenerales.borrarArchivo(rutaLinuxWordPdfGenerado);
	}
	
	
	
	public void generarDocumentoCreditoHipotecarioBP(EOperacionDocumento oEOperacionDocumentoItem) {
		//Instancia de Atributos Locales
		String nroDocumento = "";
		String direccion ="";
		String departamentoCliente="", provinciaCliente="",distritoCliente="";
		String plantilla = "";
		String nombreArchivo = "";	
		UNumeroLetra numLetra = new UNumeroLetra();
		
		String codigoCliente = oEOperacionDocumentoItem.getCodigoCliente()+"";
		
		//Obtener Datos del Socio(F5101) o Tercero(F5151)	 
		ECliente oECliente = oBOCliente.buscarSocio(Integer.parseInt(codigoCliente)) != null ? oBOCliente.buscarSocio(Integer.parseInt(codigoCliente)):null;
		ETercero oETercero = oBOCliente.buscarTercero(Integer.parseInt(codigoCliente)) != null ? oBOCliente.buscarTercero(Integer.parseInt(codigoCliente)):null;
		
		//Obtener Datos del Usuario de Sistema
		ECliente oUsuario = oBOCliente.buscarUsuarioDetalle(oEUsuario) != null ? oBOCliente.buscarUsuarioDetalle(oEUsuario):new ECliente();

		

		if(oECliente != null){
			nroDocumento = oECliente.getDocumento();
			direccion = oECliente.getDireccion();
			List<Object> ubigeo = ubigeoCliente(oECliente.getCodigoUbigeo()+"");
			departamentoCliente = ubigeo.get(0).toString();
			provinciaCliente =  ubigeo.get(1).toString();
			distritoCliente = ubigeo.get(2).toString();
			if(oECliente.getCodigoTipoPersona().equals(UTipoPersona.NATURAL)){
				plantilla = "FormatoCreditoHipotecarioBP_001.docx";
				nombreArchivo = "CreditoHipotecarioBP_PN_";
				
			}else if(oECliente.getCodigoTipoPersona().equals(UTipoPersona.JURIDICA_F_LUCRO) || 
					oECliente.getCodigoTipoPersona().equals(UTipoPersona.JURIDICA_S_LUCRO)){
				plantilla = "FormatoCreditoHipotecarioBP_001.docx";
				nombreArchivo = "CreditoHipotecarioBP_PJ_";
				
			}else{
				plantilla = "FormatoCreditoHipotecarioBP_001.docx";
				nombreArchivo = "CreditoHipotecarioBP_";
			}
		}else if (oETercero != null){
			nroDocumento = oETercero.getDocumento();
			direccion = oETercero.getDireccion();
			List<Object> ubigeo = ubigeoCliente(oETercero.getCodigoUbigeo()+"");
			departamentoCliente = ubigeo.get(0).toString();
			provinciaCliente =  ubigeo.get(1).toString();
			distritoCliente = ubigeo.get(2).toString();
			if(oETercero.getCodigoTipoPersona().equals(UTipoPersona.NATURAL)){
				plantilla = "FormatoCreditoHipotecarioBP_001.docx";
				nombreArchivo = "CreditoHipotecarioBP_PN_";
			
			}else if(oETercero.getCodigoTipoPersona().equals(UTipoPersona.JURIDICA_F_LUCRO) ||
					oETercero.getCodigoTipoPersona().equals(UTipoPersona.JURIDICA_S_LUCRO)){
				plantilla = "FormatoCreditoHipotecarioBP_001.docx";
				nombreArchivo = "CreditoHipotecarioBP_PJ_";
				
			}else{
				plantilla = "FormatoCreditoHipotecarioBP_001.docx";
				nombreArchivo = "CreditoHipotecarioBP_";
			}
		}
		
		rutaBaseFormato = URutaCarpetaCompartida.rutaBaseLinux;
		rutaBasePlantilla = rutaBaseFormato + "Legal";
			
		
		String rutaPlantilla = rutaBasePlantilla + File.separator + plantilla;
	
		XWPFDocument documeWord = UtilPoi.obtenerArchivoWord(rutaPlantilla);
		
		
		String nombreSocio = oEOperacionDocumentoItem.getNombreCliente() != null ? oEOperacionDocumentoItem.getNombreCliente():"";
		String texto1 = nombreSocio;
		String texto2 = nroDocumento;
		String texto3 = direccion;
		String texto4 = departamentoCliente;
		String texto5 = provinciaCliente;
		String texto6 = distritoCliente;
		String texto7 = getFechaActual();
		String texto8 = nombreSocio;
		String texto9 = nroDocumento;
		String texto10 = oEGarantiaData.getUbicacion1();
		String texto11 =  oUManejadorListaDesplegable.obtieneDepartamento().stream()
				.filter(x -> x.getCodigo2() == codigoDepartamentoGarantia ).findAny().orElse(null).getDescripcion();
		String texto12 = oUManejadorListaDesplegable.obtieneProvincia(codigoDepartamentoGarantia).stream()
				.filter(x -> x.getCodigo2() == codigoProvinciaGarantia ).findAny().orElse(null).getDescripcion();
		String texto13 = oUManejadorListaDesplegable.obtieneDistrito(codigoDepartamentoGarantia, codigoProvinciaGarantia).stream()
				.filter(x -> x.getCodigo2() == codigoDistritoGarantia ).findAny().orElse(null).getDescripcion();
		String texto14 = numLetra.convertir(oEGarantiaData.getMontoGravamen()+"", false, oEGarantiaData.getCodigoMoneda()+"");
		String texto15 = "("+oUManejadorListaDesplegable.obtieneTipoMoneda().stream()
				.filter(x -> x.getCodigo2() == oEGarantiaData.getCodigoMoneda()).findAny().orElse(null).getNombreCorto() +" "+ oEGarantiaData.getMontoGravamen()+")";
		String texto16 = numLetra.convertir(oEGarantiaData.getMontoValoracion()+"", false, oEGarantiaData.getCodigoMoneda()+"");
		String texto17 = "("+oUManejadorListaDesplegable.obtieneTipoMoneda().stream()
				.filter(x -> x.getCodigo2() == oEGarantiaData.getCodigoMoneda()).findAny().orElse(null).getNombreCorto() +" "+ oEGarantiaData.getMontoValoracion()+")";
		String texto18=oUManejadorListaDesplegable.obtieneTipoMoneda().stream().
				filter(x -> x.getCodigo2() == oEGarantiaData.getCodigoMoneda()).findAny().orElse(null).getDescripcion();

		
		UtilPoi.reemplazarPalabraenParrafo(documeWord, "@001", texto1);
		UtilPoi.reemplazarPalabraenParrafo(documeWord, "@002", texto2);
		UtilPoi.reemplazarPalabraenParrafo(documeWord, "@003", texto3);
		UtilPoi.reemplazarPalabraenParrafo(documeWord, "@004", texto4);
		UtilPoi.reemplazarPalabraenParrafo(documeWord, "@005", texto5);
		UtilPoi.reemplazarPalabraenParrafo(documeWord, "@006", texto6);
		UtilPoi.reemplazarPalabraenParrafo(documeWord, "@007", texto7);
		UtilPoi.reemplazarPalabraenTabla2(documeWord, "@008", texto8,true);
		UtilPoi.reemplazarPalabraenTabla2(documeWord, "@009", texto9,true);
		UtilPoi.reemplazarPalabraenTabla2(documeWord, "@010", texto10,false);
		UtilPoi.reemplazarPalabraenTabla2(documeWord, "@011", texto11,false);
		UtilPoi.reemplazarPalabraenTabla2(documeWord, "@012", texto12,false);
		UtilPoi.reemplazarPalabraenTabla2(documeWord, "@013", texto13,false);
		UtilPoi.reemplazarPalabraenTabla2(documeWord, "@014", texto14,false);
		UtilPoi.reemplazarPalabraenTabla2(documeWord, "@015", texto15,true);
		UtilPoi.reemplazarPalabraenTabla2(documeWord, "@016", texto16,false);
		UtilPoi.reemplazarPalabraenTabla2(documeWord, "@017", texto17,true);
		UtilPoi.reemplazarPalabraenTabla2(documeWord, "@018", texto18,true);
		
		String sufijo = UFuncionesGenerales.convertirFechaACadena(new Date(), "ddMMyyyyHHmmss");
		String nombreArchivoWord = nombreArchivo + UFuncionesGenerales.revisaCadena(codigoCliente).concat("_").concat(sufijo).concat(".docx");
		String nombreArchivoPdf = nombreArchivo + UFuncionesGenerales.revisaCadena(codigoCliente).concat("_").concat(sufijo).concat(".pdf");

		String rutaLinuxArchivoWord = rutaBaseFormato + nombreArchivoWord;
		String rutaLinuxWordGenerado = UtilPoi.generarArchivoWord(documeWord, rutaLinuxArchivoWord);
		String rutaWindowsWordGenerado = URutaCarpetaCompartida.rutaBaseWindows + nombreArchivoWord;
		String rutaLinuxWordPdfGenerado = rutaBaseFormato + nombreArchivoPdf;

		UManejadorArchivo.conviertirArchivoAPDF(rutaLinuxWordGenerado);
		UFuncionesGenerales.descargaArchivo(rutaWindowsWordGenerado);
		UFuncionesGenerales.borrarArchivo(rutaLinuxWordGenerado);
		UFuncionesGenerales.borrarArchivo(rutaLinuxWordPdfGenerado);
	}
	
	//Obtener Departamento, Provincia y Distrito para el Documento
	private List<Object> ubigeoCliente(String codigoUbigeo){
		List<Object> ubigeoCliente = new ArrayList<Object>();
		int codigoDepartamentoCliente=0,codigoProvinciaCliente=0,codigoDistritoCliente=0;
		String descDepartamentoCliente="",descProvinciaCliente="",descDistritoCliente="";
		
		if (UFuncionesGenerales.revisaCadena(codigoUbigeo).length() == 5) {
			codigoDepartamentoCliente = UFuncionesGenerales.convierteCadenaAEntero(codigoUbigeo.substring(0, 1));
			codigoProvinciaCliente = UFuncionesGenerales.convierteCadenaAEntero(codigoUbigeo.substring(1, 3));
			codigoDistritoCliente = UFuncionesGenerales.convierteCadenaAEntero(codigoUbigeo.substring(3, 5));
		}else if (UFuncionesGenerales.revisaCadena(codigoUbigeo).length() == 6) {
			codigoDepartamentoCliente = UFuncionesGenerales.convierteCadenaAEntero(codigoUbigeo.substring(0, 2));
			codigoProvinciaCliente = UFuncionesGenerales.convierteCadenaAEntero(codigoUbigeo.substring(2, 4));
			codigoDistritoCliente = UFuncionesGenerales.convierteCadenaAEntero(codigoUbigeo.substring(4, 6));
		}
		final int codigoDepartamento = codigoDepartamentoCliente,codigoProvincia = codigoProvinciaCliente,codigoDistrito=codigoDistritoCliente ;
		
		descDepartamentoCliente= oUManejadorListaDesplegable.obtieneDepartamento().stream()
				.filter(x -> x.getCodigo2() == codigoDepartamento ).findAny().orElse(null).getDescripcion();
		descProvinciaCliente = oUManejadorListaDesplegable.obtieneProvincia(codigoDepartamento).stream()
				.filter(x -> x.getCodigo2() == codigoProvincia ).findAny().orElse(null).getDescripcion();
		descDistritoCliente = oUManejadorListaDesplegable.obtieneDistrito(codigoDepartamento, codigoProvincia).stream()
				.filter(x -> x.getCodigo2() == codigoDistrito ).findAny().orElse(null).getDescripcion();
		
		ubigeoCliente.add(descDepartamentoCliente != null ? descDepartamentoCliente : "");
		ubigeoCliente.add(descProvinciaCliente != null ? descProvinciaCliente: "");
		ubigeoCliente.add(descDistritoCliente != null ? descDistritoCliente: "");
		 
		return ubigeoCliente;
	}
	
	//Obtener Fecha Actual de Sistema con Formato Especificado
	private  String getFechaActual() {
		SimpleDateFormat objSimpleDateFormat = new SimpleDateFormat("EEEE, d 'de' ", new Locale("es"));
		Date objFecha = UFuncionesGenerales.getFechaActual();
		String strDia, strMes, strFecha;
		strDia = objFecha.getDate()+ " de ";
		//strDia = objSimpleDateFormat.format(objFecha);
		//strDia = strDia.substring(0,1).toUpperCase() + strDia.substring(1);
		objSimpleDateFormat.applyPattern("MMMM");
		strMes = objSimpleDateFormat.format(objFecha);
		strMes = strMes.substring(0,1).toUpperCase() + strMes.substring(1);
		objSimpleDateFormat.applyPattern(" 'de' yyyy");
		strFecha =  strDia + strMes + objSimpleDateFormat.format(objFecha);
		return strFecha;
	}
	
	//*************************************//
	//Metodos para documento de carga
    //*************************************//

	
	
	//Documento Carga Documento Garantia
	public void agregarDocumentoCarga(FileUploadEvent objUploadEvent) {
		UploadedFile oUploadedFile = objUploadEvent.getFile();
		files.add(oUploadedFile);
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
		if(lstDocumentoCarga.size()>0) visualizarGrabarDocumento = true;
		descripcionDocumentoCarga = "";
		files = new ArrayList<UploadedFile>();
	}
	
	//Documento Carga Documento Notaria
	public void agregarDocumentoCargaNotaria(FileUploadEvent objUploadEvent) {
		UploadedFile oUploadedFile = objUploadEvent.getFile();
		files.add(oUploadedFile);
		for(int i=0;i<files.size();i++){
			EDocumentoCarga oEDocumentoCarga = new EDocumentoCarga();
			if(lstDocumentoCargaNotaria.size() > 0){
				boolean isValida = false;
				for(int x=0;x<lstDocumentoCargaNotaria.size();x++){
					if(lstDocumentoCargaNotaria.get(x).getNombre().equals(descripcionDocumentoCarga+UFuncionesGenerales.obtieneTipoArchivo(files.get(i).getFileName()))){
						isValida = true;
					}
				}
				if(!isValida){
					oEDocumentoCarga.setNombre(files.get(i).getFileName() + UFuncionesGenerales.obtieneTipoArchivo(files.get(i).getFileName()));
					oEDocumentoCarga.setNombreOriginal(files.get(i).getFileName());
					oEDocumentoCarga.setData(files.get(i).getContents());
					oEDocumentoCarga.setSize(files.get(i).getSize());
					oEDocumentoCarga.setDescripcionSize(UFuncionesGenerales.getSize(files.get(i).getSize()));
					lstDocumentoCargaNotaria.add(oEDocumentoCarga);
				}
			}else{
				oEDocumentoCarga.setNombre(files.get(i).getFileName() + UFuncionesGenerales.obtieneTipoArchivo(files.get(i).getFileName()));
				oEDocumentoCarga.setNombreOriginal(files.get(i).getFileName());
				oEDocumentoCarga.setData(files.get(i).getContents());
				oEDocumentoCarga.setSize(files.get(i).getSize());
				oEDocumentoCarga.setDescripcionSize(UFuncionesGenerales.getSize(files.get(i).getSize()));
				lstDocumentoCargaNotaria.add(oEDocumentoCarga);
			}
		}
		if(lstDocumentoCargaNotaria.size()>0) deshabilitarGrabarDocumentoNotaria = false;
		descripcionDocumentoCarga = "";
		files = new ArrayList<UploadedFile>();
	}
	
	//Documento Carga Documento General
	public void agregarDocumentoCargaGeneral(FileUploadEvent objUploadEvent) {
		UploadedFile oUploadedFile = objUploadEvent.getFile();
		files.add(oUploadedFile);
		for(int i=0;i<files.size();i++){
			EDocumentoCarga oEDocumentoCarga = new EDocumentoCarga();
			if(lstDocumentoCargaGeneral.size() > 0){
				boolean isValida = false;
				for(int x=0;x<lstDocumentoCargaGeneral.size();x++){
					if(lstDocumentoCargaGeneral.get(x).getNombre().equals(UFuncionesGenerales.obtieneTipoArchivo(files.get(i).getFileName()))){
						isValida = true;
					}
				}
				if(!isValida){
					oEDocumentoCarga.setNombre(files.get(i).getFileName());
					oEDocumentoCarga.setNombreOriginal(files.get(i).getFileName());
					oEDocumentoCarga.setData(files.get(i).getContents());
					oEDocumentoCarga.setSize(files.get(i).getSize());
					oEDocumentoCarga.setDescripcionSize(UFuncionesGenerales.getSize(files.get(i).getSize()));
					lstDocumentoCargaGeneral.add(oEDocumentoCarga);
				}
			}else{
				oEDocumentoCarga.setNombre(files.get(i).getFileName());
				oEDocumentoCarga.setNombreOriginal(files.get(i).getFileName());
				oEDocumentoCarga.setData(files.get(i).getContents());
				oEDocumentoCarga.setSize(files.get(i).getSize());
				oEDocumentoCarga.setDescripcionSize(UFuncionesGenerales.getSize(files.get(i).getSize()));
				lstDocumentoCargaGeneral.add(oEDocumentoCarga);
			}
		}
		files = new ArrayList<UploadedFile>();
	}
	

	
	
	
	public void listarDocumentoNotario(){
		lstOperacionDocumento = oBOOperacion.listarDocumentoGarantia(oEGarantiaData, 1);
		lstDocumentoNotaria=lstOperacionDocumento.stream()
				.filter(x -> x.getAreaReceptora().equals("NOTARIA"))
				.collect(Collectors.toList());
		
		
	}
	
	public void listarDocumentoGeneralGarantia(){
		lstOperacionDocumentoGeneralGarantia = oBOGarantia.listarDocumentoGeneralGarantia(oEGarantiaLoad.getCodigoGarantia());
	}
	
	public void eliminarDocumentoCarga(EDocumentoCarga oEDocumentoCargaItem){
		lstDocumentoCarga.remove(oEDocumentoCargaItem);
		if(lstDocumentoCarga.size()==0 ||lstDocumentoCarga == null) visualizarGrabarDocumento = false;
	}
	
	public void eliminarDocumentoCargaNotaria(EDocumentoCarga oEDocumentoCargaItem){
		lstDocumentoCargaNotaria.remove(oEDocumentoCargaItem);
		if(lstDocumentoCargaNotaria.size()==0 ||lstDocumentoCargaNotaria == null) deshabilitarGrabarDocumentoNotaria = true;
	}
	
	public void eliminarDocumentoCargaGeneral(EDocumentoCarga oEDocumentoCargaItem){
		lstDocumentoCargaGeneral.remove(oEDocumentoCargaItem);
	}
	
	public void visualizarEliminarDocumentoGeneral(EOperacionDocumento oEOperacionDocumentoItem){
		if (oEOperacionDocumentoItem != null) {
			oEOperacionDocumentoData = oEOperacionDocumentoItem;
			RequestContext.getCurrentInstance().execute("PF('dlgConfirmacionDocumento').show();");
		}
	}
	
	public void eliminarDocumentoGeneral(){
		if(oEOperacionDocumentoData != null){
			EGarantia eGarantia = new EGarantia();
			eGarantia.setCodigoGarantia(oEOperacionDocumentoData.getCodigoGarantia());
			eGarantia.setSecuenciaDocumento(oEOperacionDocumentoData.getCodigoDocumento());
			oEMensaje = oBOGarantia.eliminarDocumentoGeneralGarantia(eGarantia);
			UManejadorLog.log(" Guardar: " + oEMensaje.getDescMensaje());
			RequestContext.getCurrentInstance().execute("PF('dlgMensajeOperacionAjax').show();");
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
	
	public void generarCorrelativoDocumentoCargaGeneral(){
		int correlativoDocumento = 0;
		for(int i=0;i<lstDocumentoCargaGeneral.size();i++){
			correlativoDocumento = correlativoDocumento +1;
			String nombreDocumento = lstDocumentoCargaGeneral.get(i).getNombre();
			lstDocumentoCargaGeneral.get(i).setNombreLaserFiche(correlativoDocumento+"-"+nombreDocumento);
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
		

		if(UAccionExterna.EDITAR == accionExterna){
			oEMensaje = oBOOperacion.modificarDocumentoGarantia(oEGarantia);
		}
		
		UManejadorLog.log(" Guardar: " + oEMensaje.getDescMensaje());
		RequestContext.getCurrentInstance().execute("PF('dlgMensajeOperacionAjax').show();");
	
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
		if(UAccionExterna.EDITAR == accionExterna){
			oEMensaje = oBOOperacion.modificarFirmaDocumentoGarantia(eoperacionDocumento);
		}
		
		UManejadorLog.log(" Guardar: " + oEMensaje.getDescMensaje());
		RequestContext.getCurrentInstance().execute("PF('dlgMensajeOperacionAjax').show();");
		
		
	}
	
	public void nuevoDocumentoNotaria(){
		oEOperacionDocumentoNotariaData = new EOperacionDocumento();
		indicadorNuevoDocumentoNotaria = true;
		RequestContext.getCurrentInstance().execute("PF('dlgNuevoDocumentoNotaria').show();");
		
	}
	
	public void procesarDocumentoNotaria(){
		RequestContext.getCurrentInstance().execute("PF('dlgNuevoDocumentoNotaria').hide();");
		EGarantia eGarantia = new EGarantia();
		EDocumentoCarga eDocumentoCargaNotaria	= new EDocumentoCarga();
		EOperacionDocumento eoperacionDocumentoNotaria = new EOperacionDocumento();

		eGarantia = oEGarantiaData;
		
		eoperacionDocumentoNotaria = oEOperacionDocumentoNotariaData;
		if(eoperacionDocumentoNotaria.getCodigofirmaDocumento() == 1){
			eoperacionDocumentoNotaria.setFirmaDocumento("SI");
		}else{
			eoperacionDocumentoNotaria.setFirmaDocumento("NO");
		}
		

		if(UAccionExterna.EDITAR == accionExterna){
			eGarantia.setUsuarioRegistro(oEUsuario);	
			eGarantia.setTipoDocumento(UTipoDocumentoGarantia.NOTARIA);
			
			eGarantia.setCodigoAreaEmisora(oEUsuario.getCodigoArea());
			eDocumentoCargaNotaria.setCodigoLaserFiche("");
			eDocumentoCargaNotaria.setNombre(eoperacionDocumentoNotaria.getNombreDocumentoOriginal());
			eDocumentoCargaNotaria.setNombreLaserFiche(eoperacionDocumentoNotaria.getNombreDocumentoOriginal());
			eDocumentoCargaNotaria.setNombreOriginal(eoperacionDocumentoNotaria.getNombreDocumentoOriginal());
			eGarantia.setFirmaDocumento(eoperacionDocumentoNotaria.getFirmaDocumento());
			eGarantia.setObservacionDocumento(eoperacionDocumentoNotaria.getObervacionDocumento());
			if(indicadorNuevoDocumentoNotaria){
				eGarantia.setNumeroSolicitud(oEOperacionDocumento.getCodigoSolicitud());
				eGarantia.setNumeroSolicitudCredito(oEOperacionDocumento.getCodigoSolicitudCredito());
				oEMensaje = oBOGarantia.registrarDetalleSolicitudDocumentoGarantia(eGarantia, eDocumentoCargaNotaria);
			}else{
				eGarantia.setNumeroSolicitud(eoperacionDocumentoNotaria.getCodigoSolicitud());
				eGarantia.setNumeroSolicitudCredito(eoperacionDocumentoNotaria.getCodigoSolicitudCredito());
				eGarantia.setSecuenciaDocumento(eoperacionDocumentoNotaria.getCodigoDocumento());
				eGarantia.setDescripcionDocumento(eoperacionDocumentoNotaria.getNombreDocumentoOriginal());
				oEMensaje = oBOGarantia.modificarDetalleSolicitudDocumentoGarantia(eGarantia, 3);
			}
			
			UManejadorLog.log(" Guardar: " + oEMensaje.getDescMensaje());
			RequestContext.getCurrentInstance().execute("PF('dlgMensajeOperacionAjax').show();");
			
			
		}
		
	}
	
	public void modificarDocumentoNotaria(EOperacionDocumento eOperacionDocumentoItem){
		if(eOperacionDocumentoItem != null){
			oEOperacionDocumentoNotariaData = eOperacionDocumentoItem;
			indicadorNuevoDocumentoNotaria = false;
			oEOperacionDocumentoNotariaData.setCodigofirmaDocumento(oEOperacionDocumentoNotariaData.getFirmaDocumento().equals("SI") ? 1: 2);
			RequestContext.getCurrentInstance().execute("PF('dlgNuevoDocumentoNotaria').show();");
		}
	}
	
	

	public void redireccionarTramiteGarantia(){
		String ruta = "";
		
		UManejadorSesionWeb.eliminaVariableSesion(UVariablesSesion.ACCION_EXTERNA);
		UManejadorSesionWeb.eliminaVariableSesion(UVariablesSesion.FICHA_PARAMETRO);
		
		if(oEUsuario.getCodigoArea() == UArea.LEGAL){
			Object oEGarantiaTramite = oBOGarantia.buscarGarantiaTramite(oEGarantiaData.getCodigoGarantia());
			
			if(oEGarantiaTramite == null){
				UManejadorSesionWeb.registraVariableSesion(UVariablesSesion.ACCION_EXTERNA, UAccionExterna.NUEVO);
			}else{
				UManejadorSesionWeb.registraVariableSesion(UVariablesSesion.ACCION_EXTERNA, UAccionExterna.EDITAR);
			}
			UManejadorSesionWeb.registraVariableSesion(UVariablesSesion.FICHA_PARAMETRO, oEGarantiaData);
			ruta = "TramiteOperacionGarantia.xhtml";
			
			
			UGeneradorQueryString objUGeneradorQueryString = new UGeneradorQueryString(ruta);
			UManejadorSesionWeb.redirigePagina(objUGeneradorQueryString.obtieneUrlConParametros());
		}
	
	}
	
	/**NUEVO FLUJO DE DOCUMENTOS ***/
	
	/**Metodos de Cabecera
	 * */
	public void nuevaSolicitudDocumento(){
		visualizarGrabarDocumento = false;
		indicadorTituloDocumento = true;
		indicadorNuevoDocumento = true;
		deshabilitarAdjuntaDocumento = false;
		lstOperacionDocumentoLegalFiltro = new ArrayList<EOperacionDocumento>();
		lstOperacionDocumentoNegociosFiltro = new ArrayList<EOperacionDocumento>();
		lstDocumentoCarga = new ArrayList<EDocumentoCarga>();
		RequestContext.getCurrentInstance().execute("PF('dlgDocumentacion').show();");

	}
	
	public void grabarDocumento(){
		generarCorrelativoDocumentoCarga();
		List<EDocumentoCarga> elstDocumentoCarga = new ArrayList<EDocumentoCarga>();
		elstDocumentoCarga = lstDocumentoCarga;
		EGarantia oEGarantia = new EGarantia();
		EOperacionDocumento eOperacionDocumento = new EOperacionDocumento();
		oEGarantia = oEGarantiaData;
		
	
		oEGarantia.setUsuarioRegistro(oEUsuario);	
		oEGarantia.setTipoDocumento(UTipoDocumentoGarantia.CONSTITUCION);
		oEGarantia.setEstadoDocumento(UEstado.PENDIENTEFIRMA);
		
		if(lstOperacionDocumento != null){
			if(lstOperacionDocumento.size()>0){
				oEGarantia.setLstOperacionDocumento(lstOperacionDocumento);
			}	
		}
		
		visualizarGrabarDocumento = false;
		lstDocumentoCarga = new ArrayList<EDocumentoCarga>();

		if(UAccionExterna.EDITAR == accionExterna){
		   //	oEMensaje = oBOGarantia.agregarDocumentoGarantia(oEGarantia, elstDocumentoCarga);
			if(indicadorNuevoDocumento){
				oEGarantia.setCodigoAreaEmisora(UArea.LEGAL);
				oEMensaje = oBOGarantia.agregarSolicitudDocumentoGarantia(oEGarantia, elstDocumentoCarga);
				UManejadorLog.log(" Guardar: " + oEMensaje.getDescMensaje());
				RequestContext.getCurrentInstance().execute("PF('dlgMensajeOperacionAjax').show();");
			}else{
				eOperacionDocumento = oEOperacionDocumento;
				oEGarantia.setNumeroSolicitud(eOperacionDocumento.getCodigoSolicitud());
				oEGarantia.setCodigoAreaEmisora(oEUsuario.getCodigoArea());
				oEGarantia.setSecuenciaDocumento(oEOperacionDocumento.getCodigoDocumento());
				oEGarantia.setNumeroSolicitudCredito(eOperacionDocumento.getCodigoSolicitudCredito());
				oEGarantia.setFirmaDocumento("NO");
				oEGarantia.setObservacionDocumento("");
				oEMensaje = oBOGarantia.agregarDetalleSolicitudDocumentoGarantia(oEGarantia, elstDocumentoCarga);
				UManejadorLog.log(" Guardar: " + oEMensaje.getDescMensaje());
				RequestContext.getCurrentInstance().execute("PF('dlgMensajeOperacionAjax').show();");
			}
			
		}
		
		
	}
	
	public void visualizarSolicitudFirma(EOperacionDocumento oEOperacionDocumentoItem){
		if(oEOperacionDocumentoItem != null){
			oEOperacionDocumento = oEOperacionDocumentoItem;
			RequestContext.getCurrentInstance().execute("PF('dlgConfirmacionSolicitudFirma').show();");
		}
	}
	
	public void procesarSolicitudFirmaDocumento(){
        EGarantia eGarantia = new EGarantia();
        
        eGarantia.setNumeroSolicitud(oEOperacionDocumento.getCodigoSolicitud());
		eGarantia.setCodigoGarantia(oEOperacionDocumento.getCodigoGarantia());
		eGarantia.setTipoDocumento(oEOperacionDocumento.getTipoDocumento());
		eGarantia.setEstadoDocumento(UEstado.ENTRAMITE);

        eGarantia.setUsuarioRegistro(oEUsuario);
		if(UAccionExterna.EDITAR == accionExterna){
			oEMensaje = oBOGarantia.modificarSolicitudDocumentoGarantia(eGarantia);
		}
		
		UManejadorLog.log(" Guardar: " + oEMensaje.getDescMensaje());
		RequestContext.getCurrentInstance().execute("PF('dlgMensajeOperacionAjax').show();");
	}
	
	/**Metodos de Detalle
	 * */
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
	
	public void visualizarObservacionDocumento(EOperacionDocumento oEOperacionDocumentoItem){
		if(oEOperacionDocumentoItem != null){
			deshabilitarObservacionDocumento = oEOperacionDocumentoItem.isValidarObservacion();
			oEOperacionDocumentoDetalle = oEOperacionDocumentoItem;
			RequestContext.getCurrentInstance().execute("PF('dlgObservacionDocumento').show();");
		}
		
	}
	
	public void procesarObservacionDocumento(){	
		RequestContext.getCurrentInstance().execute("PF('dlgObservacionDocumento').hide();");
        EGarantia eGarantia = new EGarantia();
        eGarantia = oEGarantiaData;
        eGarantia.setNumeroSolicitud(oEOperacionDocumentoDetalle.getCodigoSolicitud());
        eGarantia.setSecuenciaDocumento(oEOperacionDocumentoDetalle.getCodigoDocumento());
		eGarantia.setTipoDocumento(oEOperacionDocumento.getTipoDocumento());
		eGarantia.setFirmaDocumento(oEOperacionDocumentoDetalle.getFirmaDocumento());
		eGarantia.setObservacionDocumento(oEOperacionDocumentoDetalle.getObervacionDocumento());
		eGarantia.setUsuarioRegistro(oEUsuario);
		
		if(UAccionExterna.EDITAR == accionExterna){
			oEMensaje = oBOGarantia.modificarDetalleSolicitudDocumentoGarantia(eGarantia, 1);
		}
		
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
        eGarantia = oEGarantiaData;
    	if(codigoFirmaSiNo == 1){
    		oEOperacionDocumentoDetalle.setFirmaDocumento("SI");
		}else{
			oEOperacionDocumentoDetalle.setFirmaDocumento("NO");
		}
        eGarantia.setNumeroSolicitud(oEOperacionDocumentoDetalle.getCodigoSolicitud());
        eGarantia.setSecuenciaDocumento(oEOperacionDocumentoDetalle.getCodigoDocumento());
		eGarantia.setTipoDocumento(oEOperacionDocumentoDetalle.getTipoDocumento());
		eGarantia.setFirmaDocumento(oEOperacionDocumentoDetalle.getFirmaDocumento());
		eGarantia.setObservacionDocumento(oEOperacionDocumentoDetalle.getObervacionDocumento());
		eGarantia.setUsuarioRegistro(oEUsuario);
		
		if(UAccionExterna.EDITAR == accionExterna){
			oEMensaje = oBOGarantia.modificarDetalleSolicitudDocumentoGarantia(eGarantia, 2);
		}
		
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
        eGarantia.setNumeroSolicitudCredito(oEOperacionDocumento.getCodigoSolicitudCredito());
		eGarantia.setCodigoGarantia(oEOperacionDocumento.getCodigoGarantia());
		eGarantia.setTipoDocumento(oEOperacionDocumento.getTipoDocumento());
		eGarantia.setEstadoDocumento(UEstado.DOCUMENTOFIRMADO);
		eGarantia.setEstadoDocumentoLegal(UEstado.FIRMACONFIRMADA);
		eGarantia.setEstadoDocumentoNegocio(oEOperacionDocumento.getEstadoDocumentoNegocio());
		eGarantia.setEstadoDocumentoNotaria(oEOperacionDocumento.getEstadoDocumentoNotaria());
        eGarantia.setUsuarioRegistro(oEUsuario);
        
		if(UAccionExterna.EDITAR == accionExterna){
			oEMensaje = oBOGarantia.modificarSolicitudDocumentoyDesembolsoGarantia(eGarantia);
		}
		
		UManejadorLog.log(" Guardar: " + oEMensaje.getDescMensaje());
		RequestContext.getCurrentInstance().execute("PF('dlgMensajeOperacionAjax').show();");
	}
	
	
	public void visualizarConfirmarFirmaNotaria(EOperacionDocumento oEOperacionDocumentoItem){
		if(oEOperacionDocumentoItem != null){
			oEOperacionDocumento = oEOperacionDocumentoItem;
			RequestContext.getCurrentInstance().execute("PF('dlgConfirmacionNotaria').show();");
		}
	}
	
	public void procesarConfirmarFirmaNotaria(){
        EGarantia eGarantia = new EGarantia();
        
        eGarantia.setNumeroSolicitud(oEOperacionDocumento.getCodigoSolicitud());
        eGarantia.setNumeroSolicitudCredito(oEOperacionDocumento.getCodigoSolicitudCredito());
		eGarantia.setCodigoGarantia(oEOperacionDocumento.getCodigoGarantia());
		eGarantia.setTipoDocumento(oEOperacionDocumento.getTipoDocumento());
		eGarantia.setEstadoDocumento(oEOperacionDocumento.getEstadoDocumento());
		eGarantia.setEstadoDocumentoLegal(oEOperacionDocumento.getEstadoDocumentoLegal());
		eGarantia.setEstadoDocumentoNegocio(oEOperacionDocumento.getEstadoDocumentoNegocio());
		eGarantia.setEstadoDocumentoNotaria(UEstado.FIRMACONFIRMADA);
        eGarantia.setUsuarioRegistro(oEUsuario);
        
		if(UAccionExterna.EDITAR == accionExterna){
			oEMensaje = oBOGarantia.modificarDocumentoNotariayDesembolsoGarantia(eGarantia);
		}
		
		UManejadorLog.log(" Guardar: " + oEMensaje.getDescMensaje());
		RequestContext.getCurrentInstance().execute("PF('dlgMensajeOperacionAjax').show();");
	}
	/*
	public void visualizarConfirmarBloqueoRegistral(EOperacionDocumento oEOperacionDocumentoItem){
		if(oEOperacionDocumentoItem != null){
			oEOperacionDocumentoDesembolso = oEOperacionDocumentoItem;
			RequestContext.getCurrentInstance().execute("PF('dlgConfirmacionBloqueoReg').show();");
		}
	}*/
	public void visualizarConfirmarBloqueoRegistral(EFlagReqLegal oEFlagReqLegalItem){
		if(oEFlagReqLegalItem != null){
			oEFlagRequisitoLegalData = oEFlagReqLegalItem;
			RequestContext.getCurrentInstance().execute("PF('dlgConfirmacionBloqueoReg').show();");
		}
	}
	
	public void procesarConfirmarBloqueoRegistral(){
       /* EGarantia eGarantia = new EGarantia();
        
        eGarantia.setNumeroSolicitud(oEOperacionDocumentoDesembolso.getCodigoSolicitud());
		eGarantia.setCodigoGarantia(oEOperacionDocumentoDesembolso.getCodigoGarantia());
		eGarantia.setTipoDocumento(oEOperacionDocumentoDesembolso.getTipoDocumento());
		eGarantia.setEstadoDesembolso(oEOperacionDocumentoDesembolso.getEstadoDesembolso());
		eGarantia.setCondicionDesembolso1(oEOperacionDocumentoDesembolso.getCondicionDesembolso1());
		eGarantia.setCondicionDesembolso2(UEstado.FIRMACONFIRMADA);
		eGarantia.setCondicionDesembolso3(oEOperacionDocumentoDesembolso.getCondicionDesembolso3());
		eGarantia.setCondicionDesembolso4(oEOperacionDocumentoDesembolso.getCondicionDesembolso4());
        eGarantia.setUsuarioRegistro(oEUsuario);*/
		
		EFlagReqLegal eFlagRequisitoLegal = new EFlagReqLegal();
		eFlagRequisitoLegal.setNumeroSolicitud(oEOperacionDocumentoDesembolso.getCodigoSolicitudCredito());
		eFlagRequisitoLegal.setNumeroFlag(2);
		eFlagRequisitoLegal.setModoIngresoFlag(UModoIngreso.AUTOMATICO);
		eFlagRequisitoLegal.setActualizacionFlag(UActualizacionFlag.CUMPLIDOTOTAL);
		eFlagRequisitoLegal.setUsuarioRegistro(oEUsuario);
        
		if(UAccionExterna.EDITAR == accionExterna){
			//oEMensaje = oBOGarantia.modificarSolicitudDesembolsoGarantia(eGarantia);
			oEMensaje = oBOGarantia.modificarDetalleFlagRequisitoLegal(eFlagRequisitoLegal);
		}
		listarDetalleFlagRequisitoLegal(oEOperacionDocumentoDesembolso);
		oEOpDocDesemb = oEOperacionDocumentoDesembolso;
		UManejadorLog.log(" Guardar: " + oEMensaje.getDescMensaje());
		RequestContext.getCurrentInstance().execute("PF('dlgMensajeOperacionAjax').show();");
	}
	/*
	public void visualizarConfirmarCartaFianza(EOperacionDocumento oEOperacionDocumentoItem){
		if(oEOperacionDocumentoItem != null){
			oEOperacionDocumentoDesembolso = oEOperacionDocumentoItem;
			RequestContext.getCurrentInstance().execute("PF('dlgConfirmacionCartaFianza').show();");
		}
	}*/

	public void visualizarConfirmarCartaFianza(EFlagReqLegal oEFlagReqLegalItem){
		if(oEFlagReqLegalItem != null){
			oEFlagRequisitoLegalData = oEFlagReqLegalItem;
			RequestContext.getCurrentInstance().execute("PF('dlgConfirmacionCartaFianza').show();");
		}
	}
	
	public void procesarConfirmarCartaFianza(){
		/*EGarantia eGarantia = new EGarantia();	    
        eGarantia.setNumeroSolicitud(oEOperacionDocumentoDesembolso.getCodigoSolicitud());
		eGarantia.setCodigoGarantia(oEOperacionDocumentoDesembolso.getCodigoGarantia());
		eGarantia.setTipoDocumento(oEOperacionDocumentoDesembolso.getTipoDocumento());
		eGarantia.setEstadoDesembolso(oEOperacionDocumentoDesembolso.getEstadoDesembolso());
		eGarantia.setCondicionDesembolso1(oEOperacionDocumentoDesembolso.getCondicionDesembolso1());
		eGarantia.setCondicionDesembolso2(oEOperacionDocumentoDesembolso.getCondicionDesembolso2());
		eGarantia.setCondicionDesembolso3(oEOperacionDocumentoDesembolso.getCondicionDesembolso3());
		eGarantia.setCondicionDesembolso4(UEstado.FIRMACONFIRMADA);
        eGarantia.setUsuarioRegistro(oEUsuario);*/
		
		EFlagReqLegal eFlagRequisitoLegal = new EFlagReqLegal();
		eFlagRequisitoLegal.setNumeroSolicitud(oEOperacionDocumentoDesembolso.getCodigoSolicitudCredito());
		eFlagRequisitoLegal.setNumeroFlag(4);
		eFlagRequisitoLegal.setModoIngresoFlag(UModoIngreso.AUTOMATICO);
		eFlagRequisitoLegal.setActualizacionFlag(UActualizacionFlag.CUMPLIDOTOTAL);
		eFlagRequisitoLegal.setUsuarioRegistro(oEUsuario);
        
		if(UAccionExterna.EDITAR == accionExterna){
			//oEMensaje = oBOGarantia.modificarSolicitudDesembolsoGarantia(eGarantia);
			oEMensaje = oBOGarantia.modificarDetalleFlagRequisitoLegal(eFlagRequisitoLegal);
		}
		listarDetalleFlagRequisitoLegal(oEOperacionDocumentoDesembolso);
		oEOpDocDesemb = oEOperacionDocumentoDesembolso;
		UManejadorLog.log(" Guardar: " + oEMensaje.getDescMensaje());
		RequestContext.getCurrentInstance().execute("PF('dlgMensajeOperacionAjax').show();");
	}
	
	public void listarSolicitudDesembolsoGarantia() {
		EGarantia eGarantia = new EGarantia();
		eGarantia = oEGarantiaLoad;
		eGarantia.setUsuarioRegistro(oEUsuario);
		eGarantia.setTipoDocumento(UTipoDocumentoGarantia.CONSTITUCION);
		lstOperacionDocumentoDesembolso = oBOGarantia.listarSolicitudDesembolsoXNroGarantia(0,"",eGarantia);
		
		for(int i = 0; i<lstOperacionDocumentoDesembolso.size();i++){
			if(oEOpDocDesemb!=null){
				if(lstOperacionDocumentoDesembolso.get(i).getCodigoSolicitudCredito()  ==oEOpDocDesemb.getCodigoSolicitudCredito() ){
					oEOperacionDocumentoDesembolso.setIndicadorConfirmacionDesembolso(lstOperacionDocumentoDesembolso.get(i).getIndicadorConfirmacionDesembolso());
					break;
				}
			}
			
		}
	}
	
	
	public void listarSolicitudDocumento() {
		EGarantia eGarantia = new EGarantia();
		eGarantia = oEGarantiaLoad;
		eGarantia.setUsuarioRegistro(oEUsuario);
		eGarantia.setTipoDocumento(UTipoDocumentoGarantia.CONSTITUCION);
		lstDocumentoGarantia = oBOGarantia.listarSolicitudDocumentoXNroGarantia(0,"",eGarantia);
		/*lstOperacionDocumentoFiltro = lstOperacionDocumento.stream()
				.filter(x -> !x.getAreaReceptora().equals("NOTARIA"))
				.collect(Collectors.toList());*/
		
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
			lstDocumentoNotaria = lstOpDoc.stream()
				                .filter(x -> x.getCodigoAreaEmisora() == 100)
				                .filter(x -> x.getTipoDocumento() == UTipoDocumentoGarantia.NOTARIA)
				                .collect(Collectors.toList());
		}
	
		/*
		for(EOperacionDocumento obj : lstOpDoc){
			if(obj.getCodigoAreaEmisora() == 100){
				lstOperacionDocumentoLegalFiltro.add(obj);
			}else{
				lstOperacionDocumentoNegociosFiltro.add(obj);
			}
		}*/
	}
	
	public void evaluarDocumentoSolicitud(EOperacionDocumento oEOperacionDocumentoItem){
		if(oEOperacionDocumentoItem != null){
			listarSolicitudDetalleDocumento(oEOperacionDocumentoItem);
			visualizarGrabarDocumento = false;
			indicadorTituloDocumento = false;
			indicadorNuevoDocumento = false;
			visualizarNroSolicitud = true;
			colapsarPanelListaDocumento = false;
			tooglePanelListaDocumento = true;
			colapsarPanelNotaria = false;
			visualizarBotonNuevoDocNotaria = oEOperacionDocumentoItem.getEstadoDocumentoNotaria() != 57 ? true : false;
			tooglePanelNotaria = true;
			deshabilitarAdjuntaDocumento = oEOperacionDocumentoItem.getEstadoDocumento() == 60 ? true : false;
			visualizarEliminarDocumentoGarantia = oEOperacionDocumentoItem.getEstadoDocumento() == 55? true : false;
			oEOperacionDocumento = oEOperacionDocumentoItem;
			lstDocumentoCarga = new ArrayList<EDocumentoCarga>();
			//RequestContext.getCurrentInstance().execute("PF('dlgDocumentacion').show();");
			
		}
	}
	
	public void validarConfirmacionDesembolso(){
		for(EOperacionDocumento obj :lstDocumentoGarantia){
			if(obj.getEstadoDocumento() == 60) habilitarIndicadorContratoPrivado = true;
		}
		
		for(EOperacionDocumento obj : lstDocumentoNotaria){
			if(obj.getEstadoDocumento() == 60) habilitarFirmaDocNotarial = true;
		}
		
		habilitarIndicadorBloqueoRegistral = oEGarantiaData.getCodigoEstado() == 5 ? true: false;
		
		if(visualizarGenerarDocumento){
			 visualizarConfirmarDesembolso = habilitarIndicadorContratoPrivado && habilitarFirmaDocNotarial && habilitarIndicadorBloqueoRegistral ? true : false;
		 }else if(visualizarGenerarDocumentoHipotecario){
			 visualizarConfirmarDesembolso = habilitarIndicadorContratoPrivado && habilitarFirmaDocNotarial ? true : false;
		 }
	}
		
	public void listarDetalleFlagRequisitoLegal(EOperacionDocumento oEOperacionDocumentoDesembolso){
		lstDetalleFlagsReqLegal = oBOGarantia.listarDetalleFlagRequisitoLegal(oEOperacionDocumentoDesembolso.getCodigoSolicitudCredito());
		visualizarBotonAnadirCondicionLegal = oEOperacionDocumentoDesembolso.getEstadoDesembolso() == UEstado.FIRMACONFIRMADA ? false : true;
		if(oEOperacionDocumentoDesembolso.getEstadoDesembolso() != UEstado.FIRMACONFIRMADA){
			for(int i = 0 ; i<lstDetalleFlagsReqLegal.size();i++ ){
				if(oEGarantiaData.getCodigoEstado() == UEstadoGarantia.BLOQUEADA &&
				   lstDetalleFlagsReqLegal.get(i).getNumeroFlag() == 2 &&
				   lstDetalleFlagsReqLegal.get(i).getActualizacionFlag() != UActualizacionFlag.CUMPLIDOTOTAL &&
				   lstDetalleFlagsReqLegal.get(i).getModoIngresoFlag() == UModoIngreso.AUTOMATICO){
					lstDetalleFlagsReqLegal.get(i).setIndicadorBloqueoRegistral(1);
				}
				
				if(lstDetalleFlagsReqLegal.get(i).getNumeroFlag() == 4 && 
				   lstDetalleFlagsReqLegal.get(i).getActualizacionFlag() != UActualizacionFlag.CUMPLIDOTOTAL &&
					lstDetalleFlagsReqLegal.get(i).getModoIngresoFlag() == UModoIngreso.AUTOMATICO){
					lstDetalleFlagsReqLegal.get(i).setIndicadorCartaFianza(1);
				}
				
				if(lstDetalleFlagsReqLegal.get(i).getModoIngresoFlag() == UModoIngreso.MANUAL){
					lstDetalleFlagsReqLegal.get(i).setIndicadorVisualizarAccion(1);
				}
			}
		}
	}
	
	public void evaluarConfirmacionDesembolso(EOperacionDocumento oEOperacionDocumentoItem){
		if(oEOperacionDocumentoItem != null){
			oEOperacionDocumentoDesembolso = oEOperacionDocumentoItem;
			listarDetalleFlagRequisitoLegal(oEOperacionDocumentoItem);		
		}
	}
	
	public void nuevoFlagRequisitoLegal(){
		if(oEOperacionDocumentoDesembolso != null){
			oEFlagRequisitoLegalData = new EFlagReqLegal();
			deshabilitarCampoFlagReqLeg = false;
			RequestContext.getCurrentInstance().execute("PF('dlgNuevoFlagReqLegal').show();");
		}
	}
	
	public void visualizarDlgEditarFlag(EFlagReqLegal eFlagReqLegalItem){
		if(eFlagReqLegalItem != null){
			oEFlagRequisitoLegalData = eFlagReqLegalItem;
			RequestContext.getCurrentInstance().execute("PF('dlgNuevoFlagReqLegal').show();");
		}
		
	}
	public void procesarFlagRequisitoLegal(){
		EFlagReqLegal eFlagReqLegal = new EFlagReqLegal();
		eFlagReqLegal = oEFlagRequisitoLegalData;
		eFlagReqLegal.setUsuarioRegistro(oEUsuario);
		
		if(UAccionExterna.EDITAR == accionExterna){
			if(eFlagReqLegal.getNumeroFlag() == 0){
				eFlagReqLegal.setNumeroSolicitud(oEOperacionDocumentoDesembolso.getCodigoSolicitudCredito());
				eFlagReqLegal.setModoIngresoFlag(UModoIngreso.MANUAL);
				oEMensaje = oBOGarantia.agregarDetalleFlagRequisitoLegal(eFlagReqLegal);
			}else{
				oEMensaje = oBOGarantia.modificarDetalleFlagRequisitoLegal(eFlagReqLegal);
			}
			
		}
		
		listarDetalleFlagRequisitoLegal(oEOperacionDocumentoDesembolso);
		UManejadorLog.log(" Guardar: " + oEMensaje.getDescMensaje());
		RequestContext.getCurrentInstance().execute("PF('dlgNuevoFlagReqLegal').hide();");
		RequestContext.getCurrentInstance().execute("PF('dlgMensajeOperacionAjax').show();");
		
	}
	
	public void visualizarEliminarFlagRequisitoLegal(EFlagReqLegal eFlagRequisitoLegalItem){
		if(eFlagRequisitoLegalItem != null){
			oEFlagRequisitoLegalData = eFlagRequisitoLegalItem;
			RequestContext.getCurrentInstance().execute("PF('dlgConfirmacionEliminarReqLegal').show();");
		}
	}
	
	public void eliminarFlagRequisitoLegal(){
		if(oEFlagRequisitoLegalData != null){
			if(UAccionExterna.EDITAR == accionExterna){
				oEMensaje = oBOGarantia.eliminarDetalleFlagRequisitoLegal(oEFlagRequisitoLegalData);
				listarDetalleFlagRequisitoLegal(oEOperacionDocumentoDesembolso);
				UManejadorLog.log(" Guardar: " + oEMensaje.getDescMensaje());
				RequestContext.getCurrentInstance().execute("PF('dlgMensajeOperacionAjax').show();");
			}
		}
	}
	
	public void visualizarConfirmarDesembolsoGarantia(){
		int contadorFlags = 0;
		if(oEOperacionDocumentoDesembolso != null){
			for(int i = 0 ; i<lstDetalleFlagsReqLegal.size();i++ ){
				if(lstDetalleFlagsReqLegal.get(i).getActualizacionFlag() == UActualizacionFlag.CUMPLIDOTOTAL ){
					contadorFlags = contadorFlags + 1;
				}

			}
			
			if(contadorFlags == lstDetalleFlagsReqLegal.size()){
				RequestContext.getCurrentInstance().execute("PF('dlgConfirmacionDesembolso').show();");
			}else{
				oEMensaje.setDescMensaje("Todas las condiciones deben estar cumplidas para realizar la Confirmación");
				RequestContext.getCurrentInstance().execute("PF('dlgMensaje').show();");
			}
			
		}
	}
	
	public void procesarConfirmacionDesembolso(){
		EGarantia eGarantia = new EGarantia();
        
        eGarantia.setNumeroSolicitud(oEOperacionDocumentoDesembolso.getCodigoSolicitud());
		eGarantia.setCodigoGarantia(oEOperacionDocumentoDesembolso.getCodigoGarantia());
		eGarantia.setTipoDocumento(oEOperacionDocumentoDesembolso.getTipoDocumento());
		eGarantia.setEstadoDesembolso(UEstado.FIRMACONFIRMADA);
        eGarantia.setUsuarioRegistro(oEUsuario);
        
		if(UAccionExterna.EDITAR == accionExterna){
			oEMensaje = oBOGarantia.modificarSolicitudDesembolsoGarantia(eGarantia);
		}
		oEOperacionDocumentoDesembolso.setIndicadorConfirmacionDesembolso(0);
		lstDetalleFlagsReqLegal = new ArrayList<EFlagReqLegal>();
		visualizarBotonAnadirCondicionLegal = false;
		UManejadorLog.log(" Guardar: " + oEMensaje.getDescMensaje());
		RequestContext.getCurrentInstance().execute("PF('dlgMensajeOperacionAjax').show();");
	}
	
	
	
	// INMUEBLE ADICIONAL
	
	public void listarInmueblesAdicionales(){
		lstInmueblesAdicionales = oBOGarantia.listarInmueblesGarantiaPredios(oEGarantiaData.getCodigoGarantia());
		int correlativo = 0; 
		for(int i=0;i<lstInmueblesAdicionales.size();i++){
			correlativo = correlativo + 1;
			lstInmueblesAdicionales.get(i).setCodigoOrden(correlativo);
		}
		
		listarInmueblesAdicionalesFiltro();
	}
	
	public void listarInmueblesAdicionalesFiltro(){
		lstInmueblesAdicionalesFiltro = lstInmueblesAdicionales.stream()
				   .filter(x -> x.getCodigoAccion() != UAccionTabla.ELIMINAR)
				   .collect(Collectors.toList());
		deshabilitarCampoAsignacion = lstInmueblesAdicionalesFiltro.size() > 0 ? true : false;
	}
	
	public void evaluarAsignacion(){
		if(oEGarantiaData != null){
			switch(oEGarantiaData.getCodigoAsignacionInmueble()){
			case 0:
				visualizarInmuebleAdicional = false;
				deshabilitarCampoAsignacion = lstInmueblesAdicionalesFiltro.size() == 2 ? true : false;
				break;
			case 1:
				visualizarInmuebleAdicional = true;
				deshabilitarCampoAsignacion = lstInmueblesAdicionalesFiltro.size() > 0 ? true : false;
				break;
			default:
			}
		}
		
	}
	

	
	public void nuevoInmueble(){
		oEGarantiaInmuebleData = new EGarantia();
		deshabilitarCampoInmuebleAdicional = false;
		codigoDepartamentoInmueble = 0;
		codigoProvinciaInmueble = 0;
		codigoDistritoInmueble = 0;
		listarUbigeoInmueble();
		accionInternaInmueble = UAccionInterna.NUEVO;
		RequestContext.getCurrentInstance().execute("PF('dlgInmueblesAdicionales').show();");
	}
	
	
	
	public void editarInmueble(EGarantia eGarantiaInmuebleItem){
		if(eGarantiaInmuebleItem != null){
			deshabilitarCampoInmuebleAdicional = false;
			oEGarantiaInmuebleData = eGarantiaInmuebleItem;
			accionInternaInmueble = UAccionInterna.EDITAR;

			listarUbigeoInmueble();
			RequestContext.getCurrentInstance().execute("PF('dlgInmueblesAdicionales').show();");
		}
	}
	
	public void guardarInmuebleGarantiaPredios(){
		if(oEGarantiaInmuebleData != null){
			if(accionInternaInmueble == UAccionInterna.NUEVO){
				int correlativo = 0;
				EGarantia oEGarantiaInmueble = new EGarantia();
				for(int i=0;i<lstInmueblesAdicionales.size();i++){
					correlativo = lstInmueblesAdicionales.get(i).getCodigoOrden();
				}
				oEGarantiaInmueble = oEGarantiaInmuebleData;
				oEGarantiaInmueble.setCodigoOrden(correlativo+1);
				oEGarantiaInmueble.setCodigoAccion(UAccionTabla.INSERTAR);
				
				oEGarantiaInmueble.setCodigoUbigeo((UFuncionesGenerales.convierteCadenaAEntero(
						UFuncionesGenerales.convertirEnteroACadenaUbigeo(codigoDepartamentoInmueble) + 
						UFuncionesGenerales.convertirEnteroACadenaUbigeo(codigoProvinciaInmueble) + 
						UFuncionesGenerales.convertirEnteroACadenaUbigeo(codigoDistritoInmueble))));
				
				lstInmueblesAdicionales.add(oEGarantiaInmueble);
				listarInmueblesAdicionalesFiltro();
				RequestContext.getCurrentInstance().execute("PF('dlgInmueblesAdicionales').hide();");
			}else if(accionInternaInmueble == UAccionInterna.EDITAR){
				EGarantia oEGarantiaInmueble = new EGarantia();
				oEGarantiaInmueble = oEGarantiaInmuebleData;
				if(oEGarantiaInmueble.getNumeroSecuenciaInmueble() != 0){
					oEGarantiaInmueble.setCodigoAccion(UAccionTabla.EDITAR);
				}
				
				oEGarantiaInmueble.setCodigoUbigeo((UFuncionesGenerales.convierteCadenaAEntero(
						UFuncionesGenerales.convertirEnteroACadenaUbigeo(codigoDepartamentoInmueble) + 
						UFuncionesGenerales.convertirEnteroACadenaUbigeo(codigoProvinciaInmueble) + 
						UFuncionesGenerales.convertirEnteroACadenaUbigeo(codigoDistritoInmueble))));
				
				for(EGarantia obj : lstInmueblesAdicionales ){
					if(obj.getCodigoOrden() == oEGarantiaInmueble.getCodigoOrden()){
						lstInmueblesAdicionales.set(obj.getCodigoOrden()-1, oEGarantiaInmueble);
					}
				}
				listarInmueblesAdicionalesFiltro();
				RequestContext.getCurrentInstance().execute("PF('dlgInmueblesAdicionales').hide();");
			}
			

			
		}

		
	}
	
	public void visualizarConfirmarEliminarInmueble(EGarantia eGarantiaInmuebleItem){
		if(eGarantiaInmuebleItem != null){
			oEGarantiaInmuebleData = eGarantiaInmuebleItem;
			RequestContext.getCurrentInstance().execute("PF('dlgConfirmacionEliminarInmueble').show();");
		}
	}
	
	public void eliminarInmueblePredios(){
		if(oEGarantiaInmuebleData != null){
			EGarantia oEGarantiaInmueble = new EGarantia();
			oEGarantiaInmueble = oEGarantiaInmuebleData;
			oEGarantiaInmueble.setCodigoAccion(UAccionTabla.ELIMINAR);
			listarInmueblesAdicionalesFiltro();
			
		}
	}
	
	//Begin: Métodos de Ubigeo Inmueble
	public void obtenerDepartamentoInmueble() {
		codigoProvinciaInmueble = 0;
		codigoDistritoInmueble = 0;
		lstProvinciaInmueble = oUManejadorListaDesplegable.obtieneProvincia(codigoDepartamentoInmueble);
		lstDistritoInmueble = oUManejadorListaDesplegable.obtieneDistrito(codigoDepartamentoInmueble, codigoProvinciaInmueble);
	}
	
	public void obtenerProvinciaInmueble() {
		codigoDistritoInmueble = 0;
		lstDistritoInmueble = oUManejadorListaDesplegable.obtieneDistrito(codigoDepartamentoInmueble, codigoProvinciaInmueble);
	}

	
	public void listarUbigeoInmueble() {
		String codigoUbigeoInmueble = oEGarantiaInmuebleData.getCodigoUbigeo()+"";
		
		if (UFuncionesGenerales.revisaCadena(codigoUbigeoInmueble).length() == 5) {
			codigoDepartamentoInmueble = UFuncionesGenerales.convierteCadenaAEntero(codigoUbigeoInmueble.substring(0, 1));
			codigoProvinciaInmueble = UFuncionesGenerales.convierteCadenaAEntero(codigoUbigeoInmueble.substring(1, 3));
			codigoDistritoInmueble = UFuncionesGenerales.convierteCadenaAEntero(codigoUbigeoInmueble.substring(3, 5));
		}else if (UFuncionesGenerales.revisaCadena(codigoUbigeoInmueble).length() == 6) {
			codigoDepartamentoInmueble = UFuncionesGenerales.convierteCadenaAEntero(codigoUbigeoInmueble.substring(0, 2));
			codigoProvinciaInmueble = UFuncionesGenerales.convierteCadenaAEntero(codigoUbigeoInmueble.substring(2, 4));
			codigoDistritoInmueble = UFuncionesGenerales.convierteCadenaAEntero(codigoUbigeoInmueble.substring(4, 6));
		}
		
		lstDepartamentoInmueble = oUManejadorListaDesplegable.obtieneDepartamento();
		lstProvinciaInmueble = oUManejadorListaDesplegable.obtieneProvincia(codigoDepartamentoInmueble);
		lstDistritoInmueble = oUManejadorListaDesplegable.obtieneDistrito(codigoDepartamentoInmueble, codigoProvinciaInmueble);
	}
	//End : Métodos de Ubigeo
	
	//Begin Mantenimiento Tercero
	public void obtenerDepartamentoGarantiaTercero() {
		codigoProvinciaGarantiaTercero = 0;
		codigoDistritoGarantiaTercero = 0;
		lstProvinciaGarantiaTercero = oUManejadorListaDesplegable.obtieneProvincia(codigoDepartamentoGarantiaTercero);
		lstDistritoGarantiaTercero = oUManejadorListaDesplegable.obtieneDistrito(codigoDepartamentoGarantiaTercero, codigoProvinciaGarantiaTercero);
	}
	
	public void obtenerProvinciaGarantiaTercero() {
		codigoDistritoGarantiaTercero = 0;
		lstDistritoGarantiaTercero = oUManejadorListaDesplegable.obtieneDistrito(codigoDepartamentoGarantiaTercero, codigoProvinciaGarantiaTercero);
	}

	
	public void listarUbigeoGarantiaTercero() {
		String codigoUbigeoGarantia = oETerceroData.getCodigoUbigeo()+"";
		
		if (UFuncionesGenerales.revisaCadena(codigoUbigeoGarantia).length() == 5) {
			codigoDepartamentoGarantiaTercero = UFuncionesGenerales.convierteCadenaAEntero(codigoUbigeoGarantia.substring(0, 1));
			codigoProvinciaGarantiaTercero = UFuncionesGenerales.convierteCadenaAEntero(codigoUbigeoGarantia.substring(1, 3));
			codigoDistritoGarantiaTercero = UFuncionesGenerales.convierteCadenaAEntero(codigoUbigeoGarantia.substring(3, 5));
		}else if (UFuncionesGenerales.revisaCadena(codigoUbigeoGarantia).length() == 6) {
			codigoDepartamentoGarantiaTercero = UFuncionesGenerales.convierteCadenaAEntero(codigoUbigeoGarantia.substring(0, 2));
			codigoProvinciaGarantiaTercero = UFuncionesGenerales.convierteCadenaAEntero(codigoUbigeoGarantia.substring(2, 4));
			codigoDistritoGarantiaTercero = UFuncionesGenerales.convierteCadenaAEntero(codigoUbigeoGarantia.substring(4, 6));
		}
		
		lstDepartamentoGarantiaTercero = oUManejadorListaDesplegable.obtieneDepartamento();
		lstProvinciaGarantiaTercero = oUManejadorListaDesplegable.obtieneProvincia(codigoDepartamentoGarantiaTercero);
		lstDistritoGarantiaTercero = oUManejadorListaDesplegable.obtieneDistrito(codigoDepartamentoGarantiaTercero, codigoProvinciaGarantiaTercero);
	}
	
	public void obtenerDepartamentoGarantiaPostalTercero() {
		codigoProvinciaGarantiaPostalTercero = 0;
		codigoDistritoGarantiaPostalTercero = 0;
		lstProvinciaGarantiaPostalTercero = oUManejadorListaDesplegable.obtieneProvincia(codigoDepartamentoGarantiaPostalTercero);
		lstDistritoGarantiaPostalTercero = oUManejadorListaDesplegable.obtieneDistrito(codigoDepartamentoGarantiaPostalTercero, codigoProvinciaGarantiaPostalTercero);
	}
	
	public void obtenerProvinciaGarantiaPostalTercero() {
		codigoDistritoGarantiaPostalTercero = 0;
		lstDistritoGarantiaPostalTercero = oUManejadorListaDesplegable.obtieneDistrito(codigoDepartamentoGarantiaPostalTercero, codigoProvinciaGarantiaPostalTercero);
	}

	
	public void listarUbigeoGarantiaPostalTercero() {
		String codigoUbigeoGarantia = oETerceroData.getCodigoUbigeoPostal()+"";
		
		if (UFuncionesGenerales.revisaCadena(codigoUbigeoGarantia).length() == 5) {
			codigoDepartamentoGarantiaPostalTercero = UFuncionesGenerales.convierteCadenaAEntero(codigoUbigeoGarantia.substring(0, 1));
			codigoProvinciaGarantiaPostalTercero = UFuncionesGenerales.convierteCadenaAEntero(codigoUbigeoGarantia.substring(1, 3));
			codigoDistritoGarantiaPostalTercero = UFuncionesGenerales.convierteCadenaAEntero(codigoUbigeoGarantia.substring(3, 5));
		}else if (UFuncionesGenerales.revisaCadena(codigoUbigeoGarantia).length() == 6) {
			codigoDepartamentoGarantiaPostalTercero = UFuncionesGenerales.convierteCadenaAEntero(codigoUbigeoGarantia.substring(0, 2));
			codigoProvinciaGarantiaPostalTercero = UFuncionesGenerales.convierteCadenaAEntero(codigoUbigeoGarantia.substring(2, 4));
			codigoDistritoGarantiaPostalTercero = UFuncionesGenerales.convierteCadenaAEntero(codigoUbigeoGarantia.substring(4, 6));
		}
		
		lstDepartamentoGarantiaPostalTercero = oUManejadorListaDesplegable.obtieneDepartamento();
		lstProvinciaGarantiaPostalTercero = oUManejadorListaDesplegable.obtieneProvincia(codigoDepartamentoGarantiaPostalTercero);
		lstDistritoGarantiaPostalTercero = oUManejadorListaDesplegable.obtieneDistrito(codigoDepartamentoGarantiaPostalTercero, codigoProvinciaGarantiaPostalTercero);
	}
	
	public void validarClasePersona(){
		if(oETerceroData.getCodigoTipoPersona().equals("N")){
			visualizarDatosPersonaNatural = true;
		}else{
			visualizarDatosPersonaNatural = false;
		}
	}
	public void validarDocumentoIdentidad(){
		switch(oETerceroData.getCodigoTipoDocumento()){
		case "D": 
			visualizarDatosPN = true;
			visualizarDatosPJ = false;
			oETerceroData.setNombreSuperLargo("");
			break;
		case "4": 
			visualizarDatosPN = false;
			visualizarDatosPJ = true;
			oETerceroData.setApellidoPaterno("");
			oETerceroData.setApellidoMaterno("");
			oETerceroData.setNombres("");
			break;
		default:
			visualizarDatosPN = false;
			visualizarDatosPJ = true;
			oETerceroData.setApellidoPaterno("");
			oETerceroData.setApellidoMaterno("");
			oETerceroData.setNombres("");
		}
		validarTamanioDocumentoTercero();
		
	}
	
	public void validarTamanioDocumentoTercero(){
		if(oETerceroData.getCodigoTipoDocumento() != null){
			switch(oETerceroData.getCodigoTipoDocumento()){
			case UTipoDocumento.RUC: maxLgnNumeroDocumentoTercero = UMaximoTamanio.RUC_MAXLGN; break;
			case UTipoDocumento.DNI:
			case UTipoDocumento.LIBRETA_ELECTORAL:
				maxLgnNumeroDocumentoTercero = UMaximoTamanio.DNI_MAXLGN; break;
			default: maxLgnNumeroDocumentoTercero = UMaximoTamanio.OTROS_MAXLGN;
			}
		}else{
			maxLgnNumeroDocumentoTercero = UMaximoTamanio.OTROS_MAXLGN;
		}
	}
	
	public void nuevoTercero(){
		oETerceroData = new ETercero();
		oETerceroData.setCodigoTipoPersona("");
		oETerceroData.setTipoProveedor(-1);
		oETerceroData.setTipoAceptante(-1);
		oETerceroData.setCodigoTipoDocumento("");
		 codigoDepartamentoGarantiaTercero=0;
		 codigoDepartamentoGarantiaPostalTercero=0;
		 codigoProvinciaGarantiaTercero=0;
		 codigoProvinciaGarantiaPostalTercero=0;
		 codigoDistritoGarantiaTercero=0;
		 codigoDistritoGarantiaPostalTercero=0;
		 listarUbigeoGarantiaTercero();
		 listarUbigeoGarantiaPostalTercero();
		 validarClasePersona();
		 validarDocumentoIdentidad();
		RequestContext.getCurrentInstance().execute("PF('dlgMantenimientoTercero').show();");
	}
	
	public void modificarTercero(ETercero eTerceroItem){
		if(eTerceroItem != null){			
			oETerceroData = oBOCliente.buscarTercero(eTerceroItem.getCodigoCliente());
			EEmail oEmail = oBOSolicitudCredito.buscarEmailCliente(eTerceroItem.getCodigoCliente());
			ETercero oEAnexoTercero = oBOCliente.buscarTerceroAnexo(eTerceroItem.getCodigoCliente());
			ETercero oEClienteInforPerNatural = oBOCliente.buscarCliente_Info_PersonaNatural(eTerceroItem.getCodigoCliente());
			
			if(oEAnexoTercero != null){
				oETerceroData.setApellidoPaterno(oEAnexoTercero.getApellidoPaterno());
				oETerceroData.setApellidoMaterno(oEAnexoTercero.getApellidoMaterno());
				oETerceroData.setNombres(oEAnexoTercero.getNombres());
				oETerceroData.setNombreLargo(oEAnexoTercero.getNombreLargo());
				oETerceroData.setNombreSuperLargo(oEAnexoTercero.getNombreSuperLargo());
			}
			
			if(oEmail != null){
				oETerceroData.setEmail1(oEmail.getEmail1());
				oETerceroData.setEmail2(oEmail.getEmail2());
			}
			
			if(oEClienteInforPerNatural != null){
				oETerceroData.setCodigoEstadoCivil(oEClienteInforPerNatural.getCodigoEstadoCivil());
				oETerceroData.setCodigoSexo(oEClienteInforPerNatural.getCodigoSexo());
			}
			validarClasePersona();
			validarDocumentoIdentidad();
			listarUbigeoGarantiaTercero();
			listarUbigeoGarantiaPostalTercero();
			RequestContext.getCurrentInstance().execute("PF('dlgMantenimientoTercero').show();");
		}
	}
	
	public void grabarTercero(){
		ETercero eTercero = new ETercero();
		if(oETerceroData != null){
			eTercero = oETerceroData;
			eTercero.setUsuarioRegistro(oEUsuario);
			eTercero.setCodigoUbigeo((UFuncionesGenerales.convierteCadenaAEntero(
					UFuncionesGenerales.convertirEnteroACadenaUbigeo(codigoDepartamentoGarantiaTercero) + 
					UFuncionesGenerales.convertirEnteroACadenaUbigeo(codigoProvinciaGarantiaTercero) + 
					UFuncionesGenerales.convertirEnteroACadenaUbigeo(codigoDistritoGarantiaTercero))));
			eTercero.setCodigoUbigeoPostal((UFuncionesGenerales.convierteCadenaAEntero(
					UFuncionesGenerales.convertirEnteroACadenaUbigeo(codigoDepartamentoGarantiaPostalTercero) + 
					UFuncionesGenerales.convertirEnteroACadenaUbigeo(codigoProvinciaGarantiaPostalTercero) + 
					UFuncionesGenerales.convertirEnteroACadenaUbigeo(codigoDistritoGarantiaPostalTercero))));
			eTercero.setIndicadorTipoPersona(UTipoTerceroPersona.TASADOR);
			if(eTercero.getCodigoCliente()!=0){
				oEMensaje = oBOCliente.modificarTercero(eTercero);
				
			}else{
				oEMensaje = oBOCliente.registrarTercero(eTercero);
			}
			
			UManejadorLog.log(" Guardar: " + oEMensaje.getDescMensaje());
			RequestContext.getCurrentInstance().execute("PF('dlgMantenimientoTercero').hide();");
			RequestContext.getCurrentInstance().execute("PF('dlgMensajeOperacionAjax').show();");
			

		}
	}
	
	
	//End  Mantenimiento Tercero
	
	


	public EGarantia getoEGarantiaData() {
		return oEGarantiaData;
	}



	public void setoEGarantiaData(EGarantia oEGarantiaData) {
		this.oEGarantiaData = oEGarantiaData;
	}


	public EPersona getoEPersonaSelected() {
		return oEPersonaSelected;
	}


	public void setoEPersonaSelected(EPersona oEPersonaSelected) {
		this.oEPersonaSelected = oEPersonaSelected;
	}


	public EPoliza getoEPolizaSelected() {
		return oEPolizaSelected;
	}


	public void setoEPolizaSelected(EPoliza oEPolizaSelected) {
		this.oEPolizaSelected = oEPolizaSelected;
	}


	public EMensaje getoEMensaje() {
		return oEMensaje;
	}


	public void setoEMensaje(EMensaje oEMensaje) {
		this.oEMensaje = oEMensaje;
	}


	public EOperacionDocumento getoEOperacionDocumento() {
		return oEOperacionDocumento;
	}


	public void setoEOperacionDocumento(EOperacionDocumento oEOperacionDocumento) {
		this.oEOperacionDocumento = oEOperacionDocumento;
	}


	public EOperacionDocumento getoEOperacionDocumentoNotariaData() {
		return oEOperacionDocumentoNotariaData;
	}


	public void setoEOperacionDocumentoNotariaData(
			EOperacionDocumento oEOperacionDocumentoNotariaData) {
		this.oEOperacionDocumentoNotariaData = oEOperacionDocumentoNotariaData;
	}

	public EOperacionDocumento getoEOperacionDocumentoDetalle() {
		return oEOperacionDocumentoDetalle;
	}

	public void setoEOperacionDocumentoDetalle(
			EOperacionDocumento oEOperacionDocumentoDetalle) {
		this.oEOperacionDocumentoDetalle = oEOperacionDocumentoDetalle;
	}

	public EOperacionDocumento getoEOperacionDocumentoDesembolso() {
		return oEOperacionDocumentoDesembolso;
	}

	public void setoEOperacionDocumentoDesembolso(
			EOperacionDocumento oEOperacionDocumentoDesembolso) {
		this.oEOperacionDocumentoDesembolso = oEOperacionDocumentoDesembolso;
	}

	public EGarantia getoEGarantiaInmuebleData() {
		return oEGarantiaInmuebleData;
	}

	public void setoEGarantiaInmuebleData(EGarantia oEGarantiaInmuebleData) {
		this.oEGarantiaInmuebleData = oEGarantiaInmuebleData;
	}

	public ETercero getoETerceroData() {
		return oETerceroData;
	}

	public void setoETerceroData(ETercero oETerceroData) {
		this.oETerceroData = oETerceroData;
	}

	public EFlagReqLegal getoEFlagRequisitoLegalData() {
		return oEFlagRequisitoLegalData;
	}

	public void setoEFlagRequisitoLegalData(EFlagReqLegal oEFlagRequisitoLegalData) {
		this.oEFlagRequisitoLegalData = oEFlagRequisitoLegalData;
	}

	public EPoliza getoEPolizaGarantiaData() {
		return oEPolizaGarantiaData;
	}

	public void setoEPolizaGarantiaData(EPoliza oEPolizaGarantiaData) {
		this.oEPolizaGarantiaData = oEPolizaGarantiaData;
	}

	
	

	
	

	
	
	
	
	
	
	
	
	
	
}
