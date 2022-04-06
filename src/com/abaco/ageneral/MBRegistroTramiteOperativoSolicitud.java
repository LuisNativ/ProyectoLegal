package com.abaco.ageneral;

import java.io.File;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import lombok.Getter;
import lombok.Setter;

import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.primefaces.context.RequestContext;

import com.abaco.entidad.EGeneral;
import com.abaco.entidad.EMensaje;
import com.abaco.entidad.EUsuario;
import com.abaco.negocio.util.UConstante.UAccionExterna;
import com.abaco.negocio.util.UConstante.UAccionTabla;
import com.abaco.negocio.util.UConstante.UArea;
import com.abaco.negocio.util.UConstante.URutaCarpetaCompartida;
import com.abaco.negocio.util.UConstante.USistemaOperativo;
import com.abaco.negocio.util.UConstante.UTipoPersona;
import com.abaco.negocio.util.UConstante.UVariablesSesion;
import com.abaco.negocio.util.UFuncionesGenerales;
import com.abaco.negocio.util.UGeneradorQueryString;
import com.abaco.negocio.util.UManejadorArchivo;
import com.abaco.negocio.util.UManejadorListaDesplegable;
import com.abaco.negocio.util.UManejadorLog;
import com.abaco.negocio.util.UManejadorSesionWeb;
import com.abaco.negocio.util.UNumeroLetra;
import com.abaco.negocio.util.UtilPoi;

@ManagedBean(name = "mbregistrotramiteoperativosolicitud")
@ViewScoped
public class MBRegistroTramiteOperativoSolicitud implements Serializable {
	private static final long serialVersionUID = 1L;
	private EMensaje oEMensaje;
	private EUsuario oEUsuario;
	private EOperacionSolicitud oEOperacionSolicitudLoad;
	private EOperacionSolicitud oEOperacionSolicitudData;
	private EOperacionSolicitud oEOperacionSolicitudContratoData;
	private EFirmanteSolicitud oEFirmanteSolicitudData;
	private EGarantiaSolicitud oEGarantiaSolicitudData;
	private EComprobanteGarantia oEComprobanteGarantiaData;
	private ERepresentanteCIA oERepresentanteCIASelected;
	private EContrato oEContratoData;
	private EDocumentoGenerado oEDocumentoGeneradoData;
	
	private BOGarantia oBOGarantia;
	private BOCliente oBOCliente;
	private UManejadorListaDesplegable oUManejadorListaDesplegable;
	
	//Listas Específicas
	@Getter @Setter private List<EOperacionSolicitud> lstDetalleFlagsSolCredito ;
	@Getter @Setter private List<EFirmanteSolicitud> lstFirmanteSolicitud;
	@Getter @Setter private List<EGarantiaSolicitud> lstGarantiaSolicitud;
	@Getter @Setter private List<EComprobanteGarantia> lstComprobanteGarantia;
	@Getter @Setter private List<ERepresentanteCIA> lstRepresentanteCIA;
	@Getter @Setter private List<ERepresentanteCIAContrato> lstRepresentanteCIAContrato;
	@Getter @Setter private List<ERepresentanteCIAContrato> lstRepresentanteCIAContratoFiltro;
	@Getter @Setter private List<EOperacionSolicitud> lstObservacionTramiteOperativoSolicitud;

	
	//Datos de ubigeo
	@Getter @Setter private int codigoDepartamentoFirmante;
	@Getter @Setter private int codigoProvinciaFirmante;
	@Getter @Setter private int codigoDistritoFirmante;
	@Getter @Setter private int codigoDepartamentoConyugue;
	@Getter @Setter private int codigoProvinciaConyugue;
	@Getter @Setter private int codigoDistritoConyugue;
		

	//Listas Desplegables 
	@Getter @Setter private List<EGeneral> lstTipoMoneda;
	@Getter @Setter private List<EGeneral> lstValorSiNo;
	@Getter @Setter private List<EGeneral> lstDepartamentoFirmante;
	@Getter @Setter private List<EGeneral> lstProvinciaFirmante;
	@Getter @Setter private List<EGeneral> lstDistritoFirmante;
	@Getter @Setter private List<EGeneral> lstDepartamentoConyugue;
	@Getter @Setter private List<EGeneral> lstProvinciaConyugue;
	@Getter @Setter private List<EGeneral> lstDistritoConyugue;
	@Getter @Setter private List<EGeneral> lstTipoDocumento;
	@Getter @Setter private List<EGeneral> lstNivel;
	@Getter @Setter private List<EGeneral> lstClaseVehiculo;
	@Getter @Setter private List<EGeneral> lstMarcaVehiculo;
	@Getter @Setter private List<EGeneral> lstModeloVehiculo;
	@Getter @Setter private List<EGeneral> lstCombustibleVehiculo;
	@Getter @Setter private List<EGeneral> lstRiesgoVehiculo;
	@Getter @Setter private List<EGeneral> lstTipoContrato;
	@Getter @Setter private List<EGeneral> lstTipoAplicacion;
	@Getter @Setter private List<EGeneral> lstTipoPeriodo;
	

	//Indicadores
	@Getter @Setter private boolean deshabilitarCampoDetalleFirmante;
	@Getter @Setter private boolean deshabilitarCampoDetalleFirmaContrato;
	@Getter @Setter private boolean deshabilitarCampoDetGenGarantia;
	@Getter @Setter private boolean deshabilitarCampoDetalleComprobante;
	@Getter @Setter private boolean deshabilitarCampoContrato;
	@Getter @Setter private boolean deshabilitarCampoFechaHoraFirmante;
	@Getter @Setter private boolean deshabilitarCampoFechaHoraConyugue;
	@Getter @Setter private boolean renderizarBotonGrabarContrato;
	@Getter @Setter private boolean renderizarBotonGenerarContrato;
	@Getter @Setter private boolean renderizarBotonConfirmaDatoGarantia;
	@Getter @Setter private boolean visualizarBotonAñadir;
	@Getter @Setter private boolean visualizarBotonEliminar;
	@Getter @Setter private boolean deshabilitarObservacionConformidad;
	@Getter @Setter private int indicadorBoton;
	@Getter @Setter private int indicadorSalir;
	@Getter @Setter private int codigoBuscar;
	@Getter @Setter private boolean habilitarAccionGuardarContrato;
	@Getter @Setter private String descripcionBuscar;
	@Getter @Setter private String observacionSolicitud,observacionConformidad;
		
	/* Variables Internas */
	private int accionExterna;
	@Getter @Setter private int ultimaRevisionSolicitud;
	
	private String rutaBaseFormato;
	private String rutaBasePlantilla;
	
	
	
	
	@PostConstruct
	public void inicio() {
		this.oEMensaje = new EMensaje();
	

		
		oEOperacionSolicitudLoad = new EOperacionSolicitud();
		oEOperacionSolicitudData = new EOperacionSolicitud();
		oEOperacionSolicitudContratoData = new EOperacionSolicitud();
		oEFirmanteSolicitudData = new EFirmanteSolicitud();
		oEGarantiaSolicitudData = new EGarantiaSolicitud();
		oEContratoData = new EContrato();
		oEComprobanteGarantiaData = new EComprobanteGarantia();
		oERepresentanteCIASelected = new ERepresentanteCIA();
		lstDetalleFlagsSolCredito = new ArrayList<EOperacionSolicitud>();
		lstTipoDocumento = new ArrayList<EGeneral>();

		oBOGarantia = new BOGarantia();
		oBOCliente = new BOCliente();
		oUManejadorListaDesplegable = new UManejadorListaDesplegable();
		
		
		lstValorSiNo = new ArrayList<EGeneral>();
			
		lstDepartamentoFirmante = new ArrayList<EGeneral>();
		lstProvinciaFirmante = new ArrayList<EGeneral>();
		lstDistritoFirmante = new ArrayList<EGeneral>();
		lstDepartamentoConyugue = new ArrayList<EGeneral>();
		lstProvinciaConyugue = new ArrayList<EGeneral>();
		lstDistritoConyugue = new ArrayList<EGeneral>();
		lstNivel = new ArrayList<EGeneral>();
		lstClaseVehiculo = new ArrayList<EGeneral>();
		lstMarcaVehiculo = new ArrayList<EGeneral>();
		lstModeloVehiculo = new ArrayList<EGeneral>();
		lstCombustibleVehiculo = new ArrayList<EGeneral>();
		lstRiesgoVehiculo = new ArrayList<EGeneral>();
		lstFirmanteSolicitud = new ArrayList<EFirmanteSolicitud>();
		lstGarantiaSolicitud = new ArrayList<EGarantiaSolicitud>();
		lstComprobanteGarantia = new ArrayList<EComprobanteGarantia>();
		lstRepresentanteCIA = new ArrayList<ERepresentanteCIA>();
		lstRepresentanteCIAContrato = new ArrayList<ERepresentanteCIAContrato>();
		lstRepresentanteCIAContratoFiltro = new ArrayList<ERepresentanteCIAContrato>();
		lstObservacionTramiteOperativoSolicitud = new ArrayList<EOperacionSolicitud>();

		
		oEUsuario = (EUsuario) UManejadorSesionWeb.obtieneVariableSesion(UVariablesSesion.USUARIO);
		inicializar();

		if (UManejadorSesionWeb.obtieneVariableSesion(UVariablesSesion.ACCION_EXTERNA) != null) {
			accionExterna = (Integer) UManejadorSesionWeb.obtieneVariableSesion(UVariablesSesion.ACCION_EXTERNA);
			
			if(UAccionExterna.EDITAR == accionExterna){
				if (UManejadorSesionWeb.obtieneVariableSesion(UVariablesSesion.FICHA_PARAMETRO) != null) {
					oEOperacionSolicitudLoad = (EOperacionSolicitud) UManejadorSesionWeb.obtieneVariableSesion(UVariablesSesion.FICHA_PARAMETRO);
					ultimaRevisionSolicitud = oBOGarantia.buscarUltimaRevisionSolicitudCredito(oEOperacionSolicitudLoad.getCodigoSolicitud()).getNumeroRevision();
					oEOperacionSolicitudData = oBOGarantia.buscarInstruccionAprobacionOperacionesF7320(oEOperacionSolicitudLoad.getCodigoSolicitud(), 0);
					oEContratoData = oBOGarantia.buscarMaestroContratoF7401(oBOGarantia.buscarInstruccionAprobacionOperacionesF7320(oEOperacionSolicitudLoad.getCodigoSolicitud(),
							                                                ultimaRevisionSolicitud).getNumeroContratoVehicular());
					if(oEContratoData == null){
						oEContratoData = new EContrato();
						oEOperacionSolicitudContratoData =  oBOGarantia.buscarInstruccionAprobacionOperacionesF7320(oEOperacionSolicitudLoad.getCodigoSolicitud(), ultimaRevisionSolicitud);
						//Para Nuevo Contrato se asigna Valores por Defecto
						if(oEOperacionSolicitudContratoData != null){
							oEContratoData.setNumeroContrato(0);
							oEContratoData.setCodigoMonedaPagare(oEOperacionSolicitudContratoData.getCodigoMoneda());
							oEContratoData.setCodigoCliente(oEOperacionSolicitudContratoData.getCodigoClientePersona());
							oEContratoData.setCodigoAsociado(0);
							oEContratoData.setFirmaConyugue("N");
							oEContratoData.setNombreSocioCorto(oEOperacionSolicitudContratoData.getNombrePersona());
							oEContratoData.setCodigoTipoContrato("C");
							oEContratoData.setCodigoRepresentante(0);
							oEContratoData.setCodigoServicio(oEOperacionSolicitudContratoData.getCodigoMoneda() == 1 ? 78 : 79);
							oEContratoData.setNumeroOperacion(0);
							oEContratoData.setNumeroLista(0);
							oEContratoData.setImporteAprobado(oEOperacionSolicitudContratoData.getMontoSolicitado());
							oEContratoData.setTasaInteresMostrada(oEOperacionSolicitudContratoData.getTasaSolicitada());
							oEContratoData.setCodigoPeriodo(oEOperacionSolicitudContratoData.getTipoTasaSolicitada());
							oEContratoData.setPlazoDias(oEOperacionSolicitudContratoData.getPlazoTotalSolicitado());
						}
						
					}
					
					if(oEOperacionSolicitudData == null){
						oEOperacionSolicitudData = oBOGarantia.buscarRegistroSolicitudCreditoF7301(oEOperacionSolicitudLoad.getCodigoSolicitud());
					}
					
					listarDetalleFlagsSolicitudCredito();
					listarFirmantesSolicitud();
					listarUbigeoFirmante();
					listarUbigeoConyugue();
					listarGarantiaAsociadaSolicitud();
					listarRepresentantesCIAContrato();
					listarObservacionSolicitudTramiteOperativo();
						
					
				}
			}
			
			listarDesplegable();
		}
	}
	
	public void inicializar() {		
		deshabilitarCampoDetalleFirmante = false;		
		deshabilitarCampoDetGenGarantia = true;
		deshabilitarCampoDetalleFirmaContrato = false;
		deshabilitarCampoDetalleComprobante = false;
		deshabilitarCampoContrato = true;
		renderizarBotonGrabarContrato = true;
		renderizarBotonGenerarContrato = false;
		visualizarBotonAñadir = true;
		visualizarBotonEliminar = true;
		renderizarBotonConfirmaDatoGarantia = false;
		indicadorSalir = 0;
		deshabilitarObservacionConformidad = true;
		habilitarAccionGuardarContrato = false;
	
	}
	
	public void listarDesplegable(){
		

		lstTipoMoneda = oUManejadorListaDesplegable.obtieneTipoMoneda();
		lstValorSiNo = oUManejadorListaDesplegable.obtieneValorSiNo();
		lstTipoDocumento = oUManejadorListaDesplegable.obtieneTipoDocumento();
		lstNivel = oUManejadorListaDesplegable.obtieneNivel();
		lstClaseVehiculo = oUManejadorListaDesplegable.obtieneClaseVehiculo();
		lstMarcaVehiculo = oUManejadorListaDesplegable.obtieneMarcaVehiculo();
		lstModeloVehiculo = oUManejadorListaDesplegable.obtieneModeloVehiculo();
		lstCombustibleVehiculo = oUManejadorListaDesplegable.obtieneCombustibleVehiculo();
		lstRiesgoVehiculo = oUManejadorListaDesplegable.obtieneRiesgoVehiculo();
		lstTipoContrato = oUManejadorListaDesplegable.obtieneTipoContrato();
		lstTipoAplicacion = oUManejadorListaDesplegable.obtieneTipoAplicacion();
		lstTipoPeriodo = oUManejadorListaDesplegable.obtieneTipoPeriodo();
	}
	
	public void listarDetalleFlagsSolicitudCredito(){
		lstDetalleFlagsSolCredito = oBOGarantia.listarDetalleFlagsSolicitudCredito(oEOperacionSolicitudLoad.getCodigoSolicitud());
	}
	
	public void listarFirmantesSolicitud(){
		lstFirmanteSolicitud = oBOGarantia.listarFirmantesDocumentoSolicitud(oEOperacionSolicitudLoad.getCodigoSolicitud());
	}
	
	public void listarGarantiaAsociadaSolicitud(){
		lstGarantiaSolicitud = oBOGarantia.listarGarantiaAsociadaSolicitudFirmante(oEOperacionSolicitudLoad.getCodigoSolicitud());
	}
	
	public void listarComprobantesGarantia(int secuenciaGarantia){
		lstComprobanteGarantia = oBOGarantia.listarComprobanteGarantiaSolicitud(oEOperacionSolicitudLoad.getCodigoSolicitud(), secuenciaGarantia);
	}
	
	public void listarRepresentantesCIAContrato(){
		long numeroContrato = oEContratoData.getNumeroContrato();
		lstRepresentanteCIAContrato = oBOGarantia.listarRepresentantesCompaniaxContrato(18, numeroContrato);
		int correlativo = 0;
		for(int i = 0;i<lstRepresentanteCIAContrato.size();i++){
			correlativo  = correlativo + 1;
			lstRepresentanteCIAContrato.get(i).setCodigoOrden(correlativo);
			lstRepresentanteCIAContrato.get(i).setCodigoTipoContrato(18);
		}
		listarRepresentantesCIAContratoFiltro();
		visualizarBotonAñadir = lstRepresentanteCIAContratoFiltro.size() >= 2 ? false : true;
	}
	
	public void listarRepresentantesCIAContratoFiltro(){
		lstRepresentanteCIAContratoFiltro = lstRepresentanteCIAContrato.stream()
				   .filter(x -> x.getCodigoAccion() != UAccionTabla.ELIMINAR)
				   .collect(Collectors.toList());
	}
	
	public void listarRepresentantesCompania(){
		lstRepresentanteCIA = oBOGarantia.listarMaestroRepresentantesCompania();
		if(lstRepresentanteCIAContratoFiltro.size() > 0 && lstRepresentanteCIA.size()>0){
			for(int i=0;i<lstRepresentanteCIA.size();i++){
				for(int j=0;j<lstRepresentanteCIAContratoFiltro.size();j++){
					if(lstRepresentanteCIAContratoFiltro.get(j).getCodigoRepresentante() ==  lstRepresentanteCIA.get(i).getCodigoRepresentante()){
						lstRepresentanteCIA.remove(i);
					}
				}
			}
		}
	}
	
	public void listarObservacionSolicitudTramiteOperativo(){
		lstObservacionTramiteOperativoSolicitud = oBOGarantia.listarObservacionSolicitudTramiteOperativoLegal(oEOperacionSolicitudData);
	}
	
	public void consultarObservacionSolicitud(EOperacionSolicitud oEOperacionSolicitudItem){
		if(oEOperacionSolicitudItem != null){
			observacionSolicitud = oEOperacionSolicitudItem.getObservacionConformidad();
			deshabilitarObservacionConformidad = true;
		}
	}
	
	public void asignarRepresentanteContrato(){
		ERepresentanteCIAContrato eRepContrato = new ERepresentanteCIAContrato();
		if(oERepresentanteCIASelected != null){	
			eRepContrato.setNumeroContrato(oEContratoData.getNumeroContrato());
			eRepContrato.setCodigoTipoContrato(18);
			eRepContrato.setCodigoAccion(UAccionTabla.INSERTAR);
			eRepContrato.setCodigoRepresentante(oERepresentanteCIASelected.getCodigoRepresentante());
			eRepContrato.setNombreRepresentante(oERepresentanteCIASelected.getNombreCortoRepresentante());
			if(lstRepresentanteCIAContrato.size()>0){
				for(ERepresentanteCIAContrato obj : lstRepresentanteCIAContrato){
					if(obj.getCodigoRepresentante() != oERepresentanteCIASelected.getCodigoRepresentante()){
						lstRepresentanteCIAContrato.add(eRepContrato);
						break;
					}else{
						oEMensaje.setDescMensaje("El Representante Ya Existe");
						RequestContext.getCurrentInstance().execute("PF('dlgMensaje').show();");
						break;
					}
				}
			}else{
				lstRepresentanteCIAContrato.add(eRepContrato);
			}
			listarRepresentantesCIAContratoFiltro();
			visualizarBotonAñadir = lstRepresentanteCIAContratoFiltro.size() >= 2 ? false : true;
			
			
		}
	}
	
	public void asignarRepresentanteContrato(ERepresentanteCIA eRepresentanteCIAItem){
		ERepresentanteCIAContrato eRepContrato = new ERepresentanteCIAContrato();
		if(eRepresentanteCIAItem != null){	
			eRepContrato.setNumeroContrato(oEContratoData.getNumeroContrato());
			eRepContrato.setCodigoTipoContrato(18);
			eRepContrato.setCodigoAccion(UAccionTabla.INSERTAR);
			eRepContrato.setCodigoRepresentante(eRepresentanteCIAItem.getCodigoRepresentante());
			eRepContrato.setNombreRepresentante(eRepresentanteCIAItem.getNombreCortoRepresentante());
			if(lstRepresentanteCIAContrato.size()>0){
				for(ERepresentanteCIAContrato obj : lstRepresentanteCIAContrato){
					if(obj.getCodigoRepresentante() != eRepresentanteCIAItem.getCodigoRepresentante()){
						lstRepresentanteCIAContrato.add(eRepContrato);
						break;
					}else{
						oEMensaje.setDescMensaje("El Representante Ya Existe");
						RequestContext.getCurrentInstance().execute("PF('dlgMensaje').show();");
						break;
					}
				}
			}else{
				lstRepresentanteCIAContrato.add(eRepContrato);
			}
			listarRepresentantesCIAContratoFiltro();
			visualizarBotonAñadir = lstRepresentanteCIAContratoFiltro.size() >= 2 ? false : true;
			
			
		}
	}
	
	public void eliminarRepresentanteCIAContrato(ERepresentanteCIAContrato eRepCIAContratoItem){
		if(eRepCIAContratoItem != null){
			for(int i=0;i<lstRepresentanteCIAContrato.size();i++){
				if(eRepCIAContratoItem.getCodigoRepresentante() == lstRepresentanteCIAContrato.get(i).getCodigoRepresentante()){
					if(eRepCIAContratoItem.getNumeroContrato() > 0 ){
						if(lstRepresentanteCIAContrato.get(i).getCodigoAccion() == UAccionTabla.INSERTAR){
							lstRepresentanteCIAContrato.remove(i);
						}else{
							lstRepresentanteCIAContrato.get(i).setCodigoAccion(UAccionTabla.ELIMINAR);
						}
						
					}else{
						lstRepresentanteCIAContrato.remove(i);
					}
					
				}
			}
			listarRepresentantesCIAContratoFiltro();
			visualizarBotonAñadir = lstRepresentanteCIAContratoFiltro.size() >= 2 ? false : true;
		}
	}
	
	public void buscarRepresentantesCooperativa(){
		listarRepresentantesCompania();
		RequestContext.getCurrentInstance().execute("PF('dlgRepresentanteCIA').show();");
	}
	
	//Metodo para Registrar o Actualizar Solicitud y/o Garantia
	public void guardar() {
	
		
		UManejadorLog.log(" Guardar: " + oEMensaje.getDescMensaje());
		RequestContext.getCurrentInstance().execute("PF('dlgMensajeOperacion').show();");
	}
	
	//Método para Actualizar Información de la Solicitud 
	
	
	
	
	
	
	
	//*************************************//
	//Begin: Metodos para Garantia (TAB=2)
	//*************************************//
	
	//Begin: Métodos de Ubigeo
	public void obtenerDepartamentoFirmante() {
		codigoProvinciaFirmante = 0;
		codigoDistritoFirmante = 0;
		lstProvinciaFirmante = oUManejadorListaDesplegable.obtieneProvincia(codigoDepartamentoFirmante);
		lstDistritoFirmante = oUManejadorListaDesplegable.obtieneDistrito(codigoDepartamentoFirmante, codigoProvinciaFirmante);
	}
	
	public void obtenerProvinciaFirmante() {
		codigoDistritoFirmante = 0;
		lstDistritoFirmante = oUManejadorListaDesplegable.obtieneDistrito(codigoDepartamentoFirmante, codigoProvinciaFirmante);
	}
	
	public void listarUbigeoFirmante() {
		String codigoUbigeoFirmante = oEFirmanteSolicitudData.getCodigoUbigeoFirmante()+"";
		if (UFuncionesGenerales.revisaCadena(codigoUbigeoFirmante).length() == 5) {
			codigoDepartamentoFirmante = UFuncionesGenerales.convierteCadenaAEntero(codigoUbigeoFirmante.substring(0, 1));
			codigoProvinciaFirmante = UFuncionesGenerales.convierteCadenaAEntero(codigoUbigeoFirmante.substring(1, 3));
			codigoDistritoFirmante = UFuncionesGenerales.convierteCadenaAEntero(codigoUbigeoFirmante.substring(3, 5));
		}else if (UFuncionesGenerales.revisaCadena(codigoUbigeoFirmante).length() == 6) {
			codigoDepartamentoFirmante= UFuncionesGenerales.convierteCadenaAEntero(codigoUbigeoFirmante.substring(0, 2));
			codigoProvinciaFirmante = UFuncionesGenerales.convierteCadenaAEntero(codigoUbigeoFirmante.substring(2, 4));
			codigoDistritoFirmante = UFuncionesGenerales.convierteCadenaAEntero(codigoUbigeoFirmante.substring(4, 6));
		}
		
		lstDepartamentoFirmante = oUManejadorListaDesplegable.obtieneDepartamento();
		lstProvinciaFirmante = oUManejadorListaDesplegable.obtieneProvincia(codigoDepartamentoFirmante);
		lstDistritoFirmante = oUManejadorListaDesplegable.obtieneDistrito(codigoDepartamentoFirmante, codigoProvinciaFirmante);
	}
	public void obtenerDepartamentoConyugue() {
		codigoProvinciaConyugue = 0;
		codigoDistritoConyugue = 0;
		lstProvinciaConyugue = oUManejadorListaDesplegable.obtieneProvincia(codigoDepartamentoConyugue);
		lstDistritoConyugue = oUManejadorListaDesplegable.obtieneDistrito(codigoDepartamentoConyugue, codigoProvinciaConyugue);
	}
	
	public void obtenerProvinciaConyugue() {
		codigoDistritoConyugue = 0;
		lstDistritoConyugue = oUManejadorListaDesplegable.obtieneDistrito(codigoDepartamentoConyugue, codigoProvinciaConyugue);
	}
	
	public void listarUbigeoConyugue() {
		String codigoUbigeoConyugue = oEFirmanteSolicitudData.getCodigoUbigeoConyugue()+"";
		if (UFuncionesGenerales.revisaCadena(codigoUbigeoConyugue).length() == 5) {
			codigoDepartamentoConyugue = UFuncionesGenerales.convierteCadenaAEntero(codigoUbigeoConyugue.substring(0, 1));
			codigoProvinciaConyugue = UFuncionesGenerales.convierteCadenaAEntero(codigoUbigeoConyugue.substring(1, 3));
			codigoDistritoConyugue = UFuncionesGenerales.convierteCadenaAEntero(codigoUbigeoConyugue.substring(3, 5));
		}else if (UFuncionesGenerales.revisaCadena(codigoUbigeoConyugue).length() == 6) {
			codigoDepartamentoConyugue= UFuncionesGenerales.convierteCadenaAEntero(codigoUbigeoConyugue.substring(0, 2));
			codigoProvinciaConyugue = UFuncionesGenerales.convierteCadenaAEntero(codigoUbigeoConyugue.substring(2, 4));
			codigoDistritoConyugue = UFuncionesGenerales.convierteCadenaAEntero(codigoUbigeoConyugue.substring(4, 6));
		}
		
		lstDepartamentoConyugue = oUManejadorListaDesplegable.obtieneDepartamento();
		lstProvinciaConyugue = oUManejadorListaDesplegable.obtieneProvincia(codigoDepartamentoConyugue);
		lstDistritoConyugue = oUManejadorListaDesplegable.obtieneDistrito(codigoDepartamentoConyugue, codigoProvinciaConyugue);
	}
	//End : Métodos de Ubigeo
	

	public void visualizarDetalleFirmanteSolicitud(EFirmanteSolicitud oEFirmanteSolicitudItem){
		if(oEFirmanteSolicitudItem != null){
			oEFirmanteSolicitudData = oEFirmanteSolicitudItem;
			deshabilitarCampoDetalleFirmante = true;
			switch(oEFirmanteSolicitudData.getFirmaConyugue()){
			case "S": 
				oEFirmanteSolicitudData.setCodigoFirmaConyugue(1);
			case "N":
				oEFirmanteSolicitudData.setCodigoFirmaConyugue(2);
			default:
			}
			listarUbigeoFirmante();
			listarUbigeoConyugue();
			RequestContext.getCurrentInstance().execute("PF('dlgMantenimientoFirmantes').show();");
		}
	}
	
	public void procesarConformidadFirma(int indicador){
		EFirmanteSolicitud eFirmanteSolicitud = new EFirmanteSolicitud();
		if(oEFirmanteSolicitudData != null){
			eFirmanteSolicitud = oEFirmanteSolicitudData;
			eFirmanteSolicitud.setUsuarioRegistro(oEUsuario);
			switch(indicador){
			case 1:
				eFirmanteSolicitud.setFlagConfirmacion("S");
				break;
			case 2:
				eFirmanteSolicitud.setFlagConfirmacion("");
				break;
			default:
			}
			oEMensaje = oBOGarantia.modificarConfirmacionFirmanteSolicitud(eFirmanteSolicitud);
			UManejadorLog.log(" Guardar: " + oEMensaje.getDescMensaje());
			RequestContext.getCurrentInstance().execute("PF('dlgMensajeOperacionAjax').show();");
		}
	}
	 
	public void validarFirmaFirmante(){
		switch(oEFirmanteSolicitudData.getFlagFirmado()){
		case 0 : deshabilitarCampoFechaHoraFirmante = true; break;
		case 1 : deshabilitarCampoFechaHoraFirmante = false; break;
		default:
			deshabilitarCampoFechaHoraFirmante = true; 
		}
		
	}
	
	public void validarFirmaConyugue(){
		switch(oEFirmanteSolicitudData.getFlagFirmadoConyugue()){
		case 0 : deshabilitarCampoFechaHoraConyugue = true; break;
		case 1 : deshabilitarCampoFechaHoraConyugue = false; break;
		default:
			deshabilitarCampoFechaHoraConyugue = true; 
		}
		
	}
	
	public void visualizarDetalleFirmanteFirma(EFirmanteSolicitud oEFirmanteSolicitudItem){
		if(oEFirmanteSolicitudItem != null){
			oEFirmanteSolicitudData = oEFirmanteSolicitudItem;
			switch(oEFirmanteSolicitudData.getFirmaConyugue()){
			case "S": 
				oEFirmanteSolicitudData.setCodigoFirmaConyugue(1); break;
			case "N":
				oEFirmanteSolicitudData.setCodigoFirmaConyugue(0); break;
			default:
			}
			deshabilitarCampoDetalleFirmaContrato = true;
			validarFirmaFirmante();
			validarFirmaConyugue();
			RequestContext.getCurrentInstance().execute("PF('dlgRegistroFirmaFirmante').show();");
		}
		
	}
	
	
	
	public void procesarFirmaContrato(){
		EFirmanteSolicitud eFirmanteSolicitud = new EFirmanteSolicitud();
		if(oEFirmanteSolicitudData != null){
			eFirmanteSolicitud = oEFirmanteSolicitudData;
			eFirmanteSolicitud.setUsuarioRegistro(oEUsuario);
			if(eFirmanteSolicitud.getHoraRegistroFirmaSocio() == null){
				eFirmanteSolicitud.setHoraRegistroFirmaSocio(UFuncionesGenerales.convertirCadenaAFecha("00:00:00", "HH:mm:ss"));
			}
			if(eFirmanteSolicitud.getHoraRegistroFirmaConyugue() == null){
				eFirmanteSolicitud.setHoraRegistroFirmaConyugue(UFuncionesGenerales.convertirCadenaAFecha("00:00:00", "HH:mm:ss"));
			}
			oEMensaje = oBOGarantia.modificarFirmaContratoFirmanteSolicitud(eFirmanteSolicitud);
			UManejadorLog.log(" Guardar: " + oEMensaje.getDescMensaje());
			RequestContext.getCurrentInstance().execute("PF('dlgMensajeOperacionAjax').show();");
		}
	}
	
	public void visualizarDetalleGarantiaAsociada(EGarantiaSolicitud oEGarantiaSolicitudItem){
		if(oEGarantiaSolicitudItem != null){
			oEGarantiaSolicitudData = oEGarantiaSolicitudItem;
			renderizarBotonConfirmaDatoGarantia = true;
			deshabilitarCampoDetGenGarantia = true;
			listarComprobantesGarantia(oEGarantiaSolicitudItem.getSecuenciaGarantia());
		}
	}
	
	public void procesarConfirmacionDatosGarantiaSolicitud(){
		EGarantiaSolicitud eGarantiaSolicitud = new EGarantiaSolicitud();
		if(oEGarantiaSolicitudData != null){
			if(oEGarantiaSolicitudData.getNumeroSolicitud() > 0 && oEGarantiaSolicitudData.getSecuenciaGarantia() > 0){
				eGarantiaSolicitud = oEGarantiaSolicitudData;
				eGarantiaSolicitud.setUsuarioRegistro(oEUsuario);
				oEMensaje = oBOGarantia.modificarConfirmaDatosGarantiaSolicitud(eGarantiaSolicitud);
				inicializarGarantia();
				UManejadorLog.log(" Guardar: " + oEMensaje.getDescMensaje());
				RequestContext.getCurrentInstance().execute("PF('dlgMensajeOperacionAjax').show();");
			}
			
		}
	}

	private void inicializarGarantia(){
		renderizarBotonConfirmaDatoGarantia = false;
		oEGarantiaSolicitudData = new EGarantiaSolicitud();
		lstComprobanteGarantia = new ArrayList<EComprobanteGarantia>();
	}
	public void visualizarDetalleComprobante(EComprobanteGarantia oEComprobanteGarantiaItem){
		if(oEComprobanteGarantiaItem != null){
			oEComprobanteGarantiaData = oEComprobanteGarantiaItem;
			deshabilitarCampoDetalleComprobante = true;
			RequestContext.getCurrentInstance().execute("PF('dlgDetalleComprobante').show();");
		}
	}
	
	
	public void guardarContratoGarantia(){
		EContrato oEContrato = new EContrato();
		if(oEContratoData != null){
			if(lstRepresentanteCIAContratoFiltro != null){
				if(lstRepresentanteCIAContratoFiltro.size()>0){
					oEContrato = oEContratoData;
					oEContrato.setCodigoSolicitud(oEOperacionSolicitudLoad.getCodigoSolicitud());
					oEContrato.setCodigoRepresentante(lstRepresentanteCIAContratoFiltro.size()>1 ? 999999999 : 
						lstRepresentanteCIAContratoFiltro.get(0).getCodigoRepresentante() ); 
					oEContrato.setUsuarioRegistro(oEUsuario);
					if(oEContrato.getNumeroContrato() > 0){
						oEMensaje = oBOGarantia.modificarContratoyRepresentanteGarantia(oEContrato,lstRepresentanteCIAContrato);
					}else{
						oEContrato.setCodigoCompania(1);
						oEMensaje = oBOGarantia.registrarContratoyRepresentanteGarantia(oEContrato,lstRepresentanteCIAContrato);
					}
			
					if(oEMensaje.getIdMensaje() == 0){
						renderizarBotonGenerarContrato = true;
						renderizarBotonGrabarContrato = false;
						visualizarBotonAñadir = false;
						visualizarBotonEliminar = false;
						habilitarAccionGuardarContrato = true;
					}else{
						renderizarBotonGenerarContrato = false;
						renderizarBotonGrabarContrato = true;
						visualizarBotonAñadir = true;
						visualizarBotonEliminar = true;
					}
					
					UManejadorLog.log(" Guardar: " + oEMensaje.getDescMensaje());
					RequestContext.getCurrentInstance().execute("PF('dlgMensajeOperacionAjax').show();");
				}else{
					oEMensaje.setDescMensaje("Debe Registrar por lo menos a un representante para grabar el contrato.");
					RequestContext.getCurrentInstance().execute("PF('dlgMensaje').show();");
				}
			}
		}
		
		
	}
	
	public void generarContratoGarantia(){
		
		if(oEContratoData != null){
			EContrato eContrato = new EContrato();
			eContrato = oEContratoData;
			eContrato.setUsuarioRegistro(oEUsuario);
			eContrato.setCodigoSolicitud(oEOperacionSolicitudLoad.getCodigoSolicitud());
			eContrato.setTipoPlantilla(18);
			eContrato.setMonedaPlantilla(oEContratoData.getCodigoMonedaPagare());
			eContrato.setTipovariante(1);
			eContrato.setUsuarioRegistro(oEUsuario);
			eContrato.setIndicadorConsulta(1);
			oEMensaje = oBOGarantia.modificarImpresionContratoGarantia(eContrato);
			
			listarDetalleFlagsSolicitudCredito();
			if(oEMensaje.getIdMensaje() == 0){
				renderizarBotonGenerarContrato = false;
				renderizarBotonGrabarContrato = true;
				visualizarBotonAñadir = true;
				visualizarBotonEliminar = true;
				listarRepresentantesCIAContrato();
				UManejadorLog.log(" Guardar: " + oEMensaje.getDescMensaje());
				RequestContext.getCurrentInstance().execute("PF('dlgConfirmacionImpresion').show();");
			}else{
				renderizarBotonGenerarContrato = false;
				renderizarBotonGrabarContrato = true;
				visualizarBotonAñadir = true;
				visualizarBotonEliminar = true;
				listarRepresentantesCIAContrato();
				UManejadorLog.log(" Guardar: " + oEMensaje.getDescMensaje());
				RequestContext.getCurrentInstance().execute("PF('dlgMensajeOperacionAjax').show();");
			}
			
			
			
			
			
			
		}


		
	}
	
	public void cancelarGenerarContratoGarantia(){
		if(oEContratoData != null){
			visualizarBotonAñadir = true;
			visualizarBotonEliminar = true;
			renderizarBotonGenerarContrato = false;
			renderizarBotonGrabarContrato = true;
			listarRepresentantesCIAContrato();
		}
		
	}
	
	//Metodo para Dar NO Conformidad a Datos de Garantía y devolver la solicitud
	//al Ejecutivo de Negocios
	public void grabarNoConformidadDatos(){
		if(oEOperacionSolicitudData != null){
			EOperacionSolicitud eOperacionSolicitud =  oEOperacionSolicitudData;
			eOperacionSolicitud.setObservacionConformidad(observacionConformidad);
			eOperacionSolicitud.setUsuarioRegistro(oEUsuario);
			oEMensaje = oBOGarantia.modificarNoConformidadDatosGarantiayObservacionSolicitud(eOperacionSolicitud);
			UManejadorLog.log(" Guardar: " + oEMensaje.getDescMensaje());
			RequestContext.getCurrentInstance().execute("PF('dlgObservacionSolicitud').hide();");
			RequestContext.getCurrentInstance().execute("PF('dlgMensajeOperacion').show();");
			
		}
	}
	
	
	public void actualizarDatosAjax(){
		listarDetalleFlagsSolicitudCredito();
		listarFirmantesSolicitud();
		
		if(oEContratoData.getNumeroContrato() == 0){
			oEContratoData = oBOGarantia.buscarMaestroContratoF7401(oBOGarantia.buscarInstruccionAprobacionOperacionesF7320(oEOperacionSolicitudLoad.getCodigoSolicitud(),
                    ultimaRevisionSolicitud).getNumeroContratoVehicular());
			
			if(oEContratoData == null){
				oEContratoData = new EContrato();
				oEOperacionSolicitudContratoData =  oBOGarantia.buscarInstruccionAprobacionOperacionesF7320(oEOperacionSolicitudLoad.getCodigoSolicitud(), ultimaRevisionSolicitud);
				//Para Nuevo Contrato se asigna Valores por Defecto
				if(oEOperacionSolicitudContratoData != null){
					oEContratoData.setNumeroContrato(0);
					oEContratoData.setCodigoMonedaPagare(oEOperacionSolicitudContratoData.getCodigoMoneda());
					oEContratoData.setCodigoCliente(oEOperacionSolicitudContratoData.getCodigoClientePersona());
					oEContratoData.setCodigoAsociado(0);
					oEContratoData.setFirmaConyugue("N");
					oEContratoData.setNombreSocioCorto(oEOperacionSolicitudContratoData.getNombrePersona());
					oEContratoData.setCodigoTipoContrato("C");
					oEContratoData.setCodigoRepresentante(0);
					oEContratoData.setCodigoServicio(oEOperacionSolicitudContratoData.getCodigoMoneda() == 1 ? 78 : 79);
					oEContratoData.setNumeroOperacion(0);
					oEContratoData.setNumeroLista(0);
					oEContratoData.setImporteAprobado(oEOperacionSolicitudContratoData.getMontoSolicitado());
					oEContratoData.setTasaInteresMostrada(oEOperacionSolicitudContratoData.getTasaSolicitada());
					oEContratoData.setCodigoPeriodo(oEOperacionSolicitudContratoData.getTipoTasaSolicitada());
					oEContratoData.setPlazoDias(oEOperacionSolicitudContratoData.getPlazoTotalSolicitado());
				}
				
			}
			
			
		}
		listarRepresentantesCIAContrato();
		if(habilitarAccionGuardarContrato){
			visualizarBotonAñadir = false;
			visualizarBotonEliminar = false;
			habilitarAccionGuardarContrato = false;
		}
	}
	
	public void salir() {
		String ruta = "";
		
		/*Cerrar Sesion*/
		UManejadorSesionWeb.eliminaVariableSesion(UVariablesSesion.OPERACION_SESION);
		UManejadorSesionWeb.eliminaVariableSesion(UVariablesSesion.ACCION_EXTERNA);
		UManejadorSesionWeb.eliminaVariableSesion(UVariablesSesion.FICHA_PARAMETRO);
				
		if(oEUsuario.getCodigoArea() == UArea.LEGAL){
			ruta = "ListaTramiteOperativoSolicitud.xhtml";
		}
				
		inicializar();
		UGeneradorQueryString objUGeneradorQueryString = new UGeneradorQueryString(ruta);
		UManejadorSesionWeb.redirigePagina(objUGeneradorQueryString.obtieneUrlConParametros());
	}
	
	
	//Generar Documento de Garantia Mobiliaria 
	public void generarDocumentoGarantiaMobiliaria() {
		if (USistemaOperativo.ES_WINDOWS) {
			rutaBaseFormato = URutaCarpetaCompartida.rutaBaseWindows;
		}else if (USistemaOperativo.ES_LINUX) {
			rutaBaseFormato = URutaCarpetaCompartida.rutaBaseLinux;
		}
		RequestContext.getCurrentInstance().execute("PF('dlgConfirmacionImpresion').hide();");
		//Instancia de Atributos Locales
	
		String plantilla = "";
		String nombreArchivo = "";
		UNumeroLetra numLetra = new UNumeroLetra();
		oEDocumentoGeneradoData = new EDocumentoGenerado();
		oEDocumentoGeneradoData.setTipoPlantilla(18);
		oEDocumentoGeneradoData.setMonedaPlantilla(oEContratoData.getCodigoMonedaPagare());
		oEDocumentoGeneradoData.setTipoVariante(1);
		oEDocumentoGeneradoData.setNumeroDocumento(oEContratoData.getNumeroContrato());
		EFirmanteSolicitud eFirmanteContrato = new EFirmanteSolicitud();
		eFirmanteContrato.setTipoPlantilla(18);
		eFirmanteContrato.setCodigoMonedaPlantilla(oEContratoData.getCodigoMonedaPagare());
		eFirmanteContrato.setTipoVariante(1);
		eFirmanteContrato.setNumeroDocumento(oEContratoData.getNumeroContrato());
		
		long nroContrato = oEContratoData.getNumeroContrato();
		String codigoCliente = oEContratoData.getCodigoCliente()+"";
		String nombreSocio = oEContratoData.getNombreSocioCorto() != null ? oEContratoData.getNombreSocioCorto():"";
		
		//Obtener Datos del Socio(F5101) o Tercero(F5151)	 
		ECliente oECliente = oBOCliente.buscarSocio(Integer.parseInt(codigoCliente)) != null ? oBOCliente.buscarSocio(Integer.parseInt(codigoCliente)):null;
		ETercero oETercero = oBOCliente.buscarTercero(Integer.parseInt(codigoCliente)) != null ? oBOCliente.buscarTercero(Integer.parseInt(codigoCliente)):null;
		EDocumentoGenerado oEDocumentoGenerado = oBOGarantia.buscarMaestroDocumentoGeneradoF7420(oEDocumentoGeneradoData);
		List<EFirmanteSolicitud> lstFirmanteContrato = oBOGarantia.listarDetalleFirmantesContratoGarantia(eFirmanteContrato);

		eFirmanteContrato = lstFirmanteContrato.stream()
		         .filter(x -> x.getCodigoTipoFirmante().equals("DA")).findAny().orElse(new EFirmanteSolicitud());
	
		
		if(oECliente != null){
			if(oECliente.getCodigoTipoPersona().equals(UTipoPersona.NATURAL)){
				plantilla = "Formato_GarantiaMobiliariaVehiculo_001.docx";
				nombreArchivo = "CCG_PN_";
			}else if(oECliente.getCodigoTipoPersona().equals(UTipoPersona.JURIDICA_F_LUCRO) || 
					oECliente.getCodigoTipoPersona().equals(UTipoPersona.JURIDICA_S_LUCRO)){
				plantilla = "Formato_GarantiaMobiliariaVehiculo_001.docx";
				nombreArchivo = "CCG_PJ_";
				//Obtener Representantes de Cliente
			}
		}else if (oETercero != null){
			if(oETercero.getCodigoTipoPersona().equals(UTipoPersona.NATURAL)){
				plantilla = "Formato_GarantiaMobiliariaVehiculo_001.docx";
				nombreArchivo = "CCG_PN_";
			}else if(oETercero.getCodigoTipoPersona().equals(UTipoPersona.JURIDICA_F_LUCRO) ||
					oETercero.getCodigoTipoPersona().equals(UTipoPersona.JURIDICA_S_LUCRO)){
				plantilla = "Formato_GarantiaMobiliariaVehiculo_001.docx";
				nombreArchivo = "CCG_PJ_";
				//Obtener Representantes de Tercero
			}
		}
		
		
		rutaBasePlantilla = rutaBaseFormato + "Legal";
		String rutaPlantilla = rutaBasePlantilla + File.separator + plantilla;
	
		//Obtener la plantila del Documento Word 
		XWPFDocument documeWord = UtilPoi.obtenerArchivoWord(rutaPlantilla);
		
		// Lenado de Datos
		String texto1 = nroContrato + "";
		String texto2 = getFechaActual();
		String texto3 =  numLetra.convertir(oEDocumentoGenerado.getImporteDocumento()+"", true, oEDocumentoGenerado.getMonedaPlantilla()+"")+
				         " (" + oEDocumentoGenerado.getSimboloMoneda() + ". " + 
		                oEDocumentoGenerado.getImporteDocumento() + ") ";
		String texto4 = eFirmanteContrato.getDireccionFirmante() + " (Distrito de "+  
		                eFirmanteContrato.getDistritoFirmante() + " , Provincia de "+
		                eFirmanteContrato.getProvinciaFirmante() + " ,Departamento de "+
		                eFirmanteContrato.getDepartamentoFirmante() + ").";
		String texto5 = eFirmanteContrato.getNombreFirmante().trim();
		String texto6 = eFirmanteContrato.getDescripcionTipoDocIdentidadFirmante().trim();
		String texto7 = eFirmanteContrato.getNumeroDocumentoFirmante().trim();
		String texto8 = "";
		if(lstGarantiaSolicitud != null){
			for(int i = 0; i< lstGarantiaSolicitud.size();i++){
				String texto = "Características : VEHICULO " + (i+1)+ "\n"+
								"Placa  : " + lstGarantiaSolicitud.get(i).getPlaca() + "\n"  +
								"Marca  : " + lstGarantiaSolicitud.get(i).getMarca() + "\n"  + 
								"Modelo : " + lstGarantiaSolicitud.get(i).getModelo() + "\n" +
								"Clase  : " + lstGarantiaSolicitud.get(i).getClase() + "\n" +
								"Carrocería : " + lstGarantiaSolicitud.get(i).getCarroceria() + "\n" + 
								"Motor  : " + lstGarantiaSolicitud.get(i).getMotor() + "\n" + 
								"Chasis : " + lstGarantiaSolicitud.get(i).getSerie() + "\n" + 
								"Año Fabricación : " + lstGarantiaSolicitud.get(i).getAnioFabricacion() + "\n" + 
								"Gravamen : " + numLetra.convertir(lstGarantiaSolicitud.get(i).getMontoGarantia()+"", true, lstGarantiaSolicitud.get(i).getCodigoMonedaGarantia()+"") + "\n" + 
								"Valorización : " + numLetra.convertir(lstGarantiaSolicitud.get(i).getMontoValorizacion()+"", true, lstGarantiaSolicitud.get(i).getCodigoMonedaGarantia()+"") + "\n";
				listarComprobantesGarantia(lstGarantiaSolicitud.get(i).getSecuenciaGarantia());
				if(lstComprobanteGarantia != null){
					texto += "Comprobantes de Pago :" + "\n";
					for(int j=0 ; j<lstComprobanteGarantia.size();j++){
						texto += lstComprobanteGarantia.get(j).getSecuenciaDetalle()+  ". " + 
								 lstComprobanteGarantia.get(j).getNumeroComprobantePago()+ "  " + 
								 lstComprobanteGarantia.get(j).getFechaComprobantePago() + " " +
								 lstComprobanteGarantia.get(j).getDescripcionComprobantePago();
						if(j == (lstComprobanteGarantia.size()-1)){
							texto += "";
						}else{
							texto += "\n";
						}
					}
				}
				
				texto8 += texto;
				
			}
		}
		
		String texto9 = "";
		String texto10 = "";
		String texto11 = "";
		String texto12 = "";
		String texto13 = "";
		String texto14 = "";
		String texto15 = "";
		String texto16 = "";
		String texto17 = "";
		if(lstFirmanteContrato != null){
			for(int i = 0; i<lstFirmanteContrato.size(); i++){
				
				if(lstFirmanteContrato.get(i).getCodigoTipoFirmante().equals("CA")){
					texto9 = lstFirmanteContrato.get(i).getDescripcionTipoFirmanteLargo() +"\n" + 
							 lstFirmanteContrato.get(i).getNombreFirmante() + "\n" + 
							 lstFirmanteContrato.get(i).getDescripcionTipoDocIdentidadFirmante() + " " +
							 lstFirmanteContrato.get(i).getNumeroDocumentoFirmante() + "\n" + "Dirección \n"+
							 lstFirmanteContrato.get(i).getDireccionFirmante() + "\n" +
							 lstFirmanteContrato.get(i).getDistritoFirmante() + "\n";
					EFirmanteSolicitud eFirmanteRepresentante = lstFirmanteContrato.stream()
					         .filter(x -> x.getCodigoTipoFirmante().equals("CB")).findAny().orElse(null);
					if(eFirmanteRepresentante != null){
						texto9 += "Representante " +"\n" + 
								eFirmanteRepresentante.getNombreFirmante() + "\n" + 
								eFirmanteRepresentante.getDescripcionTipoDocIdentidadFirmante() + " " +
								eFirmanteRepresentante.getNumeroDocumentoFirmante() + "\n"  + "Inscripción de Poderes \n" + 
								eFirmanteRepresentante.getInscripcionPoderesFirmante()+ "\n" +
								 "-----------------------------------------------------------------" + "\n" +
								 "                               Firma                             " ;
					}else{
						texto9 += "-----------------------------------------------------------------" + "\n" +
								 "                               Firma                             " ;
					}
					
					if(!lstFirmanteContrato.get(i).getNombreConyugue().isEmpty()){
						texto10 = "Apellidos y nombre de EL/LA cónyugue de "+ lstFirmanteContrato.get(i).getDescripcionTipoFirmanteLargo() +"\n" + 
								 lstFirmanteContrato.get(i).getNombreConyugue() + "\n" + 
								 lstFirmanteContrato.get(i).getDescripcionTipoDocumentoIDConyugue() + " " +
								 lstFirmanteContrato.get(i).getNumeroDocumentoIDConyugue() + "\n" + "Dirección \n"+
								 lstFirmanteContrato.get(i).getDireccionFirmante() + "\n" +
								 lstFirmanteContrato.get(i).getDistritoFirmante() + "\n" +
								 "-----------------------------------------------------------------" + "\n" +
								 "                               Firma                             " ;
					}
					
					
				}
				if(lstFirmanteContrato.get(i).getCodigoTipoFirmante().equals("TA")){
					texto11 = "Apellidos y nombre y/o razón social de EL/LA "+ lstFirmanteContrato.get(i).getDescripcionTipoFirmanteLargo() +"\n" + 
							 lstFirmanteContrato.get(i).getNombreFirmante() + "\n" + 
							 lstFirmanteContrato.get(i).getDescripcionTipoDocIdentidadFirmante() + " " +
							 lstFirmanteContrato.get(i).getNumeroDocumentoFirmante() + "\n" + "Dirección \n"+
							 lstFirmanteContrato.get(i).getDireccionFirmante() + "\n" + "Socio(a) " + 
							 lstFirmanteContrato.get(i).getCodigoSocioFirmante() + "\n" +
							 lstFirmanteContrato.get(i).getDistritoFirmante() + "\n" ;
					EFirmanteSolicitud eFirmanteRepresentante = lstFirmanteContrato.stream()
					         .filter(x -> x.getCodigoTipoFirmante().equals("TB")).findAny().orElse(null);	 
					if(eFirmanteRepresentante != null){
					    texto11 += "Representante " +"\n" + 
								eFirmanteRepresentante.getNombreFirmante() + "\n" + 
								eFirmanteRepresentante.getDescripcionTipoDocIdentidadFirmante() + " " +
								eFirmanteRepresentante.getNumeroDocumentoFirmante() + "\n"  + "Inscripción de Poderes \n" + 
								eFirmanteRepresentante.getInscripcionPoderesFirmante()+ "\n" +
								 "-----------------------------------------------------------------" + "\n" +
								 "                               Firma                             " ;
					}else{
						texto11 += "-----------------------------------------------------------------" + "\n" +
								 "                               Firma                             " ;
					}	
					
					if(!lstFirmanteContrato.get(i).getNombreConyugue().isEmpty()){
						texto12 = "Apellidos y nombre de EL/LA cónyugue de EL/LA "+ lstFirmanteContrato.get(i).getDescripcionTipoFirmanteLargo() +"\n" + 
								 lstFirmanteContrato.get(i).getNombreConyugue() + "\n" + 
								 lstFirmanteContrato.get(i).getDescripcionTipoDocumentoIDConyugue() + " " +
								 lstFirmanteContrato.get(i).getNumeroDocumentoIDConyugue() + "\n" + "Dirección \n"+
								 lstFirmanteContrato.get(i).getDireccionFirmante() + "\n" +
								 lstFirmanteContrato.get(i).getDistritoFirmante() + "\n" +
								 "-----------------------------------------------------------------" + "\n" +
								 "                               Firma                             " ;
					}
							 
				}

				if(lstFirmanteContrato.get(i).getCodigoTipoFirmante().equals("DA")){
					texto13 = "Apellidos y nombre y/o razón social de EL/LA "+ lstFirmanteContrato.get(i).getDescripcionTipoFirmanteLargo() +"\n" + 
							 lstFirmanteContrato.get(i).getNombreFirmante() + "\n" + 
							 lstFirmanteContrato.get(i).getDescripcionTipoDocIdentidadFirmante() + " " +
							 lstFirmanteContrato.get(i).getNumeroDocumentoFirmante() + "\n" + "Dirección \n"+
							 lstFirmanteContrato.get(i).getDireccionFirmante() + "\n" + 
							 lstFirmanteContrato.get(i).getDistritoFirmante() + "\n" + "Socio(a) " + 
							 lstFirmanteContrato.get(i).getCodigoSocioFirmante() + "\n" ;	
					EFirmanteSolicitud eFirmanteRepresentante = lstFirmanteContrato.stream()
					         .filter(x -> x.getCodigoTipoFirmante().equals("DB")).findAny().orElse(null);	 
					if(eFirmanteRepresentante != null){
					    texto13 += "Representante " +"\n" + 
								eFirmanteRepresentante.getNombreFirmante() + "\n" + 
								eFirmanteRepresentante.getDescripcionTipoDocIdentidadFirmante() + " " +
								eFirmanteRepresentante.getNumeroDocumentoFirmante() + "\n"  + "Inscripción de Poderes \n" + 
								eFirmanteRepresentante.getInscripcionPoderesFirmante()+ "\n" +
								 "-----------------------------------------------------------------" + "\n" +
								 "                               Firma                             " ;
					}else{
						texto13 += "-----------------------------------------------------------------" + "\n" +
								 "                               Firma                             " ;
					}	
					
					if(!lstFirmanteContrato.get(i).getNombreConyugue().isEmpty()){
						texto14 = "Apellidos y nombre de EL/LA cónyugue de EL/LA "+ lstFirmanteContrato.get(i).getDescripcionTipoFirmanteLargo() +"\n" + 
								 lstFirmanteContrato.get(i).getNombreConyugue() + "\n" + 
								 lstFirmanteContrato.get(i).getDescripcionTipoDocumentoIDConyugue() + " " +
								 lstFirmanteContrato.get(i).getNumeroDocumentoIDConyugue() + "\n" + "Dirección \n"+
								 lstFirmanteContrato.get(i).getDireccionFirmante() + "\n" +
								 lstFirmanteContrato.get(i).getDistritoFirmante() + "\n" +
								 "-----------------------------------------------------------------" + "\n" +
								 "                               Firma                             " ;
					}
				}
				if(lstFirmanteContrato.get(i).getCodigoTipoFirmante().equals("AA")){
					if(lstFirmanteContrato.get(i).getSecuenciaFirmante() == 1){
						texto15 = "Cooperativa de Ahorro y Crédito ABACO" +"\n" + 
								 lstFirmanteContrato.get(i).getNombreFirmante() + "\n" + 
								 lstFirmanteContrato.get(i).getDescripcionTipoDocIdentidadFirmante() + " " +
								 lstFirmanteContrato.get(i).getNumeroDocumentoFirmante() + "\n" + "Dirección \n"+
								 lstFirmanteContrato.get(i).getDireccionFirmante() + "\n" + "Inscripción Poderes \n" + 
								 lstFirmanteContrato.get(i).getInscripcionPoderesFirmante()+ "\n" +
								 lstFirmanteContrato.get(i).getDistritoFirmante() + "\n" +
								 "-----------------------------------------------------------------" + "\n" +
								 "                               Firma                             " ;
					}else if(lstFirmanteContrato.get(i).getSecuenciaFirmante() == 2){
						texto16 = "Cooperativa de Ahorro y Crédito ABACO" +"\n" + 
								 lstFirmanteContrato.get(i).getNombreFirmante() + "\n" + 
								 lstFirmanteContrato.get(i).getDescripcionTipoDocIdentidadFirmante() + " " +
								 lstFirmanteContrato.get(i).getNumeroDocumentoFirmante() + "\n" + "Dirección \n"+
								 lstFirmanteContrato.get(i).getDireccionFirmante() + "\n" + "Inscripción Poderes \n" + 
								 lstFirmanteContrato.get(i).getInscripcionPoderesFirmante()+ "\n" +
								 lstFirmanteContrato.get(i).getDistritoFirmante() + "\n" +
								 "-----------------------------------------------------------------" + "\n" +
								 "                               Firma                             " ;
					}else{
						texto17 = "Cooperativa de Ahorro y Crédito ABACO" +"\n" + 
								 lstFirmanteContrato.get(i).getNombreFirmante() + "\n" + 
								 lstFirmanteContrato.get(i).getDescripcionTipoDocIdentidadFirmante() + " " +
								 lstFirmanteContrato.get(i).getNumeroDocumentoFirmante() + "\n" + "Dirección \n"+
								 lstFirmanteContrato.get(i).getDireccionFirmante() + "\n" + "Inscripción Poderes \n" + 
								 lstFirmanteContrato.get(i).getInscripcionPoderesFirmante()+ "\n" +
								 lstFirmanteContrato.get(i).getDistritoFirmante() + "\n" +
								 "-----------------------------------------------------------------" + "\n" +
								 "                               Firma                             " ;
					}
					
				}

			}
		}
		

		UtilPoi.reemplazarPalabraenParrafoBold(documeWord, "@001", texto1,true);
		UtilPoi.reemplazarPalabraenParrafo(documeWord, "@002", texto2);
		UtilPoi.reemplazarPalabraenParrafo(documeWord, "@003", texto3);
		UtilPoi.reemplazarPalabraenParrafo(documeWord, "@004", texto4);
		UtilPoi.reemplazarPalabraenParrafo(documeWord, "@005", texto5);
		UtilPoi.reemplazarPalabraenParrafo(documeWord, "@006", texto6);
		UtilPoi.reemplazarPalabraenParrafo(documeWord, "@007", texto7);
		UtilPoi.reemplazarPalabraenParrafoConSaltoLinea(documeWord, "@008", texto8);
		UtilPoi.reemplazarPalabraenTabla2(documeWord, "@009", texto9, false);
		UtilPoi.reemplazarPalabraenTabla2(documeWord, "@010", texto10, false);
		UtilPoi.reemplazarPalabraenTabla2(documeWord, "@011", texto11, false);
		UtilPoi.reemplazarPalabraenTabla2(documeWord, "@012", texto12, false);
		UtilPoi.reemplazarPalabraenTabla2(documeWord, "@013", texto13, false);
		UtilPoi.reemplazarPalabraenTabla2(documeWord, "@014", texto14, false);
		UtilPoi.reemplazarPalabraenTabla2(documeWord, "@015", texto15, false);
		UtilPoi.reemplazarPalabraenTabla2(documeWord, "@016", texto16, false);
		
		String sufijo = UFuncionesGenerales.convertirFechaACadena(new Date(), "ddMMyyyyHHmmss");
		String nombreArchivoWord = nombreArchivo + UFuncionesGenerales.revisaCadena(nroContrato+"").concat("_").concat(UFuncionesGenerales.revisaCadena(codigoCliente)).concat("_").concat(sufijo).concat(".docx");
		String nombreArchivoPdf = nombreArchivo + UFuncionesGenerales.revisaCadena(nroContrato+"").concat("_").concat(UFuncionesGenerales.revisaCadena(codigoCliente)).concat("_").concat(sufijo).concat(".pdf");

		String rutaLinuxArchivoWord = rutaBaseFormato + nombreArchivoWord;
		String rutaLinuxWordGenerado = UtilPoi.generarArchivoWord(documeWord, rutaLinuxArchivoWord);
		String rutaWindowsWordGenerado = rutaBaseFormato + nombreArchivoWord;
		String rutaLinuxWordPdfGenerado = rutaBaseFormato + nombreArchivoPdf;
		
		

		//UManejadorArchivo.conviertirArchivoAPDF(rutaLinuxWordGenerado);
		UFuncionesGenerales.descargaArchivo(rutaLinuxWordGenerado);
		UFuncionesGenerales.borrarArchivo(rutaLinuxWordGenerado);
		UFuncionesGenerales.borrarArchivo(rutaLinuxWordPdfGenerado);
	}
	
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

	public EMensaje getoEMensaje() {
		return oEMensaje;
	}

	public void setoEMensaje(EMensaje oEMensaje) {
		this.oEMensaje = oEMensaje;
	}


	public EOperacionSolicitud getoEOperacionSolicitudData() {
		return oEOperacionSolicitudData;
	}

	public void setoEOperacionSolicitudData(
			EOperacionSolicitud oEOperacionSolicitudData) {
		this.oEOperacionSolicitudData = oEOperacionSolicitudData;
	}

	public EFirmanteSolicitud getoEFirmanteSolicitudData() {
		return oEFirmanteSolicitudData;
	}

	public void setoEFirmanteSolicitudData(
			EFirmanteSolicitud oEFirmanteSolicitudData) {
		this.oEFirmanteSolicitudData = oEFirmanteSolicitudData;
	}

	public EGarantiaSolicitud getoEGarantiaSolicitudData() {
		return oEGarantiaSolicitudData;
	}

	public void setoEGarantiaSolicitudData(
			EGarantiaSolicitud oEGarantiaSolicitudData) {
		this.oEGarantiaSolicitudData = oEGarantiaSolicitudData;
	}

	public EComprobanteGarantia getoEComprobanteGarantiaData() {
		return oEComprobanteGarantiaData;
	}

	public void setoEComprobanteGarantiaData(
			EComprobanteGarantia oEComprobanteGarantiaData) {
		this.oEComprobanteGarantiaData = oEComprobanteGarantiaData;
	}

	public EOperacionSolicitud getoEOperacionSolicitudContratoData() {
		return oEOperacionSolicitudContratoData;
	}

	public void setoEOperacionSolicitudContratoData(
			EOperacionSolicitud oEOperacionSolicitudContratoData) {
		this.oEOperacionSolicitudContratoData = oEOperacionSolicitudContratoData;
	}

	public EContrato getoEContratoData() {
		return oEContratoData;
	}

	public void setoEContratoData(EContrato oEContratoData) {
		this.oEContratoData = oEContratoData;
	}

	public ERepresentanteCIA getoERepresentanteCIASelected() {
		return oERepresentanteCIASelected;
	}

	public void setoERepresentanteCIASelected(
			ERepresentanteCIA oERepresentanteCIASelected) {
		this.oERepresentanteCIASelected = oERepresentanteCIASelected;
	}

	
	
	
	
	
	
	
}
