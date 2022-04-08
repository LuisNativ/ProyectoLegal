package com.abaco.ageneral;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import lombok.Getter;
import lombok.Setter;

import org.primefaces.component.tabview.TabView;
import org.primefaces.context.RequestContext;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.event.TabChangeEvent;
import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;
import org.primefaces.model.UploadedFile;

import com.abaco.entidad.EGeneral;
import com.abaco.entidad.EMensaje;
import com.abaco.entidad.EPersona;
import com.abaco.entidad.EUsuario;
import com.abaco.negocio.util.UConstante.UAccionExterna;
import com.abaco.negocio.util.UConstante.UArea;
import com.abaco.negocio.util.UConstante.UCantidadCaracteres;
import com.abaco.negocio.util.UConstante.UClaseGarantia;
import com.abaco.negocio.util.UConstante.UCriterioBusqueda;
import com.abaco.negocio.util.UConstante.UEstado;
import com.abaco.negocio.util.UConstante.UFiltroGarantia;
import com.abaco.negocio.util.UConstante.UTipoCredito;
import com.abaco.negocio.util.UConstante.UTipoDocumentoGarantia;
import com.abaco.negocio.util.UConstante.UVariablesSesion;
import com.abaco.negocio.util.UFuncionesGenerales;
import com.abaco.negocio.util.UGeneradorQueryString;
import com.abaco.negocio.util.UManejadorArchivo;
import com.abaco.negocio.util.UManejadorListaDesplegable;
import com.abaco.negocio.util.UManejadorLog;
import com.abaco.negocio.util.UManejadorSesionWeb;
import com.abaco.servicio.laserfiche.Documento;

@ManagedBean(name = "mblistagarantiaporconstituir")
@ViewScoped
public class MBListaGarantiaPorConstituir implements Serializable {
	private static final long serialVersionUID = 1L;
	private EMensaje oEMensaje;
	private EUsuario oEUsuario;
	
	private EGarantia oEGarantiaData;
	private EGeneral oEGeneralData;
	private EGarantia oEGarantiaSelected;
	
	private BOGarantia oBOGarantia;
	private BOCliente oBOCliente;
	private BOOperacion oBOOperacion;
	
	private UManejadorListaDesplegable oUManejadorListaDesplegable;
	
	
	@Getter @Setter private List<EGarantiaSolicitud> lstGarantiaSolicitud;
	@Getter @Setter private List<EGarantiaSolicitud> lstGarantiaSolicitudNueva;
	@Getter @Setter private List<EGarantiaSolicitud> lstGarantiaSolicitudExistente;
	@Getter @Setter private List<EGarantia> lstGarantiaVigente;
	@Getter @Setter private List<ESaldoServicio> lstSaldoServicio;
	@Getter @Setter private List<EGeneral> lstTipoGarantia;
	@Getter @Setter private List<EGeneral> lstTipoGarantiaReal;
	@Getter @Setter private List<EGeneral> lstTipoGarantiaOtros;
	@Getter @Setter private List<EGeneral> lstTipoGarantiaFiltro;
	@Getter @Setter private List<EPersona> lstPersona;
	

	@Getter @Setter private int codigoBuscarGarantia,codigoBuscarSolicitud,codigoBuscarDocumento,codigoBuscar;
	@Getter @Setter private String descripcionBuscarGarantia,descripcionBuscarSolicitud,descripcionBuscarDocumento,descripcionBuscar;
	@Getter @Setter private int codigoTabview2Index,codigoTabview3Index;
	@Getter @Setter private String tipoGarantiaBuscar;
	@Getter @Setter private String nombreTipoGarantia;
	@Getter @Setter private String mensajeValidacion;
	@Getter @Setter private int cantidadCaracteresBusquedaGarantia;
	@Getter @Setter private int cantidadCaracteresBusquedaSolicitud;
	@Getter @Setter private int cantidadCaracteresBusquedaSolicitudDocumento;
	
	//Para mostrar los Créditos Directos, Indirectos y Garantia
	@Getter @Setter private double creditoDirMonedaNacional;
	@Getter @Setter private double creditoDirMonedaExtranjera;
	@Getter @Setter private double creditoIndMonedaNacional;
	@Getter @Setter private double creditoIndMonedaExtranjera;
	@Getter @Setter private double garantiaMonedaNacional;
	@Getter @Setter private double garantiaMonedaExtranjera;
	
	//Indicadores
	@Getter @Setter private boolean indicadorBoton; 
	@Getter @Setter private boolean indicadorNuevoTipoGarantia;
	@Getter @Setter private boolean indicadorNuevoDocumentoSolicitud;
	@Getter @Setter private boolean visualizarCodigoGarantiaDocumento;
	@Getter @Setter private int indicadorTabView;
	
	/* Variables de carga File */
	private EOperacionDocumento oEOperacionDocumento,oEOperacionDocumentoDetalle;
	@Getter @Setter private StreamedContent fileDownload;
	@Getter @Setter private List<UploadedFile> files;
	@Getter @Setter private List<EDocumentoCarga> lstDocumentoCarga;
	@Getter @Setter private int codigoFirmaSiNo;
	private int codigoGarantiaDocumento;
	
	@Getter @Setter private List<EOperacionDocumento> lstOperacionDocumento,lstDocumentoGarantia;
	@Getter @Setter private List<EOperacionDocumento> lstDocumentoGarantiaPendiente;
	@Getter @Setter private List<EOperacionDocumento> lstDocumentoGarantiaHistorico;
	@Getter @Setter private List<EOperacionDocumento> lstOperacionDocumentoFiltro; 
	@Getter @Setter private boolean deshabilitarGrabarDocumento,deshabilitarBotonFirma;
	@Getter @Setter private boolean deshabilitarSolFirma;
	@Getter @Setter private boolean estadoDocumento; 
	@Getter @Setter private boolean deshabilitarObservacion;
	@Getter @Setter private boolean deshabilitarAdjuntaDocumento;
	@Getter @Setter private List<EOperacionDocumento> lstSolicitudDocumento;
	@Getter @Setter private List<EOperacionDocumento> lstOperacionDocumentoLegalFiltro;
	@Getter @Setter private List<EOperacionDocumento> lstOperacionDocumentoNegociosFiltro;
	@Getter @Setter private List<EGeneral> lstValorSiNo;
	@Getter @Setter private boolean  deshabilitarObservacionDocumento;
	
	@PostConstruct
	public void inicio() {
		this.oEMensaje = new EMensaje();	
		this.oEGarantiaData = new EGarantia();
		this.oEGeneralData = new EGeneral();	
		this.oEGarantiaSelected = new EGarantia();
		
		oBOGarantia = new BOGarantia();
		oBOCliente = new BOCliente();
		oBOOperacion = new BOOperacion();
		
		oEOperacionDocumento = new EOperacionDocumento();
		oEOperacionDocumentoDetalle = new EOperacionDocumento();
		oUManejadorListaDesplegable = new UManejadorListaDesplegable();
		
		lstGarantiaSolicitud = new ArrayList<EGarantiaSolicitud>();
		lstGarantiaSolicitudNueva = new ArrayList<EGarantiaSolicitud>();
		lstGarantiaSolicitudExistente = new ArrayList<EGarantiaSolicitud>();
		lstTipoGarantia = new ArrayList<EGeneral>();
		lstTipoGarantiaReal = new ArrayList<EGeneral>();
		lstTipoGarantiaOtros = new ArrayList<EGeneral>();
		lstTipoGarantiaFiltro = new ArrayList<EGeneral>();
		lstValorSiNo = new ArrayList<EGeneral>();
		lstDocumentoCarga = new ArrayList<EDocumentoCarga>();
		files = new ArrayList<UploadedFile>(); 
		lstOperacionDocumento = new ArrayList<EOperacionDocumento>();
		lstOperacionDocumentoFiltro = new ArrayList<EOperacionDocumento>();
		lstOperacionDocumentoLegalFiltro = new ArrayList<EOperacionDocumento>();
		lstOperacionDocumentoNegociosFiltro = new ArrayList<EOperacionDocumento>();
		lstDocumentoGarantia = new ArrayList<EOperacionDocumento>();
		lstDocumentoGarantiaPendiente = new ArrayList<EOperacionDocumento>();
		lstDocumentoGarantiaHistorico = new ArrayList<EOperacionDocumento>();

		
		oEUsuario = (EUsuario) UManejadorSesionWeb.obtieneVariableSesion(UVariablesSesion.USUARIO);
		indicadorTabView= UManejadorSesionWeb.obtieneVariableSesion(UVariablesSesion.TABVIEWINDEX) != null ? (int) UManejadorSesionWeb.obtieneVariableSesion(UVariablesSesion.TABVIEWINDEX): 0 ;
		UManejadorSesionWeb.eliminaVariableSesion(UVariablesSesion.TABVIEWINDEX);
		inicializar();
		
		listarGarantiaSolicitudNueva();
		listarGarantiaSolicitudExistente();
		listarDesplegable();
		listarSolicitudDocumento();
	}
	
	public void inicializar(){
		indicadorBoton = true;
		nombreTipoGarantia = "";
		codigoTabview2Index = 0;
		codigoTabview3Index = 0;
		indicadorNuevoDocumentoSolicitud = true;
		visualizarCodigoGarantiaDocumento = true;
		deshabilitarBotonFirma = true;
		deshabilitarAdjuntaDocumento = false;
	}
	
	public void listarDesplegable(){
		lstTipoGarantiaReal = oUManejadorListaDesplegable.obtieneTipoGarantiaF9205();
		lstTipoGarantiaOtros = oUManejadorListaDesplegable.obtieneTipoGarantiaNuevoF9205();
		lstValorSiNo = oUManejadorListaDesplegable.obtieneValorSiNo();
	}
	
	
	/*
	 * MÉTODOS PARA SOLICITUDES ASOCIADAS A UNA GARANTIA 
	 * PENDIENTES DE REGISTRO (TAB = 1)
	 *  
	 */
	
	
	//Método para Identificar el Cambio de un Tab dentro de un Tabview
	
	public void onTabChangeGarantiaRE(TabChangeEvent event){
		TabView tv = (TabView) event.getTab().getParent();		
		if(tv.getActiveIndex() == 0) codigoTabview2Index = 0;
		else codigoTabview2Index = 1;
	}

	/*Metodo para Obtener la cantidad maxima de caracteres por cada opcion
	 * de busqueda*/
	public void validarLongitudCaracteresSolicitud(){
		descripcionBuscarSolicitud = "";
		switch(codigoBuscarSolicitud){
		  case 1: cantidadCaracteresBusquedaSolicitud = UCantidadCaracteres.CODIGO_SOLICITUD; 
		          break;
		  case 2: cantidadCaracteresBusquedaSolicitud = UCantidadCaracteres.CODIGO_SOCIO; 
		          break;
		  case 3: cantidadCaracteresBusquedaSolicitud = UCantidadCaracteres.NOMBRE_SOCIO; 
		          break;
		  default: cantidadCaracteresBusquedaGarantia= UCantidadCaracteres.POR_DEFECTO;
		}
	}
	
	//Buscar Solicitudes según Codigo y Descripcion
	public void buscarGarantiaSolicitud(){
		List<EGarantiaSolicitud> lstGarantiaSol = oBOGarantia.listarSolicitudAsociadaGarantia(codigoBuscarSolicitud, descripcionBuscarSolicitud.trim());
		if(codigoTabview2Index == 0){
			lstGarantiaSolicitudNueva = new ArrayList<EGarantiaSolicitud>();
			if(lstGarantiaSol != null){
				for(EGarantiaSolicitud obj: lstGarantiaSol){
					if((obj.getCodigoEstadoGarantiaSolicitud() == UEstado.SOLICITAGARANTIAREGISTRO 
							|| obj.getCodigoTipoCredito() == UTipoCredito.ABAUTOSCOMERCIAL 
							|| obj.getCodigoTipoCredito() == UTipoCredito.ABAUTOSCONSUMO) &&
							obj.getCodigoTipoGarantia() == UClaseGarantia.REALNUEVAS ){
						lstGarantiaSolicitudNueva.add(obj);
					}
				}
			}
		}else if (codigoTabview2Index == 1){
			lstGarantiaSolicitudExistente = new ArrayList<EGarantiaSolicitud>();
			if(lstGarantiaSol != null){
				for(EGarantiaSolicitud obj: lstGarantiaSol){
					if((obj.getCodigoEstadoGarantiaSolicitud() == UEstado.SOLICITAGARANTIAREGISTRO 
							|| obj.getCodigoTipoCredito() == UTipoCredito.ABAUTOSCOMERCIAL 
							|| obj.getCodigoTipoCredito() == UTipoCredito.ABAUTOSCONSUMO)&& 
					   obj.getCodigoEstadoGarantiaSolicitud() != UEstado.EVALUADOGARANTIA &&
					   obj.getCodigoTipoGarantia() == UClaseGarantia.REALEXISTENTES ){
						lstGarantiaSolicitudExistente.add(obj);
					}
				}
			}
		}else{
			lstGarantiaSolicitudNueva = new ArrayList<EGarantiaSolicitud>();
			lstGarantiaSolicitudExistente = new ArrayList<EGarantiaSolicitud>();
		}
	}
	
	//Listado de Solicitudes con Garantia Real Nueva
	public void listarGarantiaSolicitudNueva() {
		List<EGarantiaSolicitud> lstGarantiaSol = oBOGarantia.listarSolicitudAsociadaGarantia(0, "");
		if(lstGarantiaSol != null){
			for(EGarantiaSolicitud obj: lstGarantiaSol){
				if((obj.getCodigoEstadoGarantiaSolicitud() == UEstado.SOLICITAGARANTIAREGISTRO 
						|| obj.getCodigoTipoCredito() == UTipoCredito.ABAUTOSCOMERCIAL 
						|| obj.getCodigoTipoCredito() == UTipoCredito.ABAUTOSCONSUMO) &&
						obj.getCodigoTipoGarantia() == UClaseGarantia.REALNUEVAS ){
					lstGarantiaSolicitudNueva.add(obj);
				}
			}
		}
	}
	
	//Listado de Solicitudes con Garantia Real Existente
	public void listarGarantiaSolicitudExistente() {
		List<EGarantiaSolicitud> lstGarantiaSol = oBOGarantia.listarSolicitudAsociadaGarantia(0, "");
		if(lstGarantiaSol != null){
			for(EGarantiaSolicitud obj: lstGarantiaSol){
				if((obj.getCodigoEstadoGarantiaSolicitud() == UEstado.SOLICITAGARANTIAREGISTRO 
						|| obj.getCodigoTipoCredito() == UTipoCredito.ABAUTOSCOMERCIAL 
						|| obj.getCodigoTipoCredito() == UTipoCredito.ABAUTOSCONSUMO)&& 
				   obj.getCodigoEstadoGarantiaSolicitud() != UEstado.EVALUADOGARANTIA &&
				   obj.getCodigoTipoGarantia() == UClaseGarantia.REALEXISTENTES ){
					lstGarantiaSolicitudExistente.add(obj);
				}
			}
		}
		
	}
	
	public void evaluarSolicitud(EGarantiaSolicitud oEGarantiaSolicitudItem) {
		String ruta = "";
		if (oEGarantiaSolicitudItem != null) {
			UManejadorSesionWeb.registraVariableSesion(UVariablesSesion.ACCION_EXTERNA, UAccionExterna.NUEVO);
			UManejadorSesionWeb.registraVariableSesion(UVariablesSesion.FICHA_PARAMETRO, oEGarantiaSolicitudItem);
			
			ruta = "RegistroOperacionGarantiaSolicitud.xhtml";
			UGeneradorQueryString objUGeneradorQueryString = new UGeneradorQueryString(ruta);
			UManejadorSesionWeb.redirigePagina(objUGeneradorQueryString.obtieneUrlConParametros());
		}
	}
	
	
	
	/*
	 * MÉTODOS PARA EL MANTENIMIENTO DE GARANTÍAS (TAB = 2)
	 *  
	 */
	
    //Listar Garantias Vigentes y Saldo de Servicios del Cliente
	public void buscarGarantiaVigente(){
		if(codigoBuscarGarantia!=0){
			if(descripcionBuscarGarantia.length()!=0){
				creditoDirMonedaNacional =  0;
				creditoDirMonedaExtranjera = 0;
				creditoIndMonedaNacional =  0;
				creditoIndMonedaExtranjera = 0;
				garantiaMonedaNacional = 0;
				garantiaMonedaExtranjera = 0; 
				
				lstGarantiaVigente = oBOGarantia.listarGarantia(codigoBuscarGarantia, descripcionBuscarGarantia.trim(),UFiltroGarantia.VIGENTE);
				if(lstGarantiaVigente == null || lstGarantiaVigente.isEmpty()){
					lstGarantiaVigente = new ArrayList<EGarantia>();
					lstPersona = new ArrayList<EPersona>();
					if(codigoBuscarGarantia == UCriterioBusqueda.CODIGO_SOCIO){
						lstPersona = oBOCliente.listarSocioyTercero(1, descripcionBuscarGarantia);
					}
					if(lstPersona == null || lstPersona.isEmpty()){
						oEGarantiaData = new EGarantia();
						indicadorBoton=true;
					}else{
						oEGarantiaData.setCodigoCliente(lstPersona.get(0).getCodigo());
						oEGarantiaData.setNombreCorto(lstPersona.get(0).getNombreCorte());
						indicadorBoton = false;
					}				
				}else{
					oEGarantiaData.setCodigoCliente(lstGarantiaVigente.get(0).getCodigoCliente());
					oEGarantiaData.setNombreCorto(lstGarantiaVigente.get(0).getNombreCorto());
					indicadorBoton = false;
					if(lstGarantiaVigente.get(0).getCodigoCliente() > 0){
						lstSaldoServicio = oBOGarantia.obtenerSaldosServiciosCliente(lstGarantiaVigente.get(0).getCodigoCliente());
						if(lstSaldoServicio != null){
							for (ESaldoServicio eSaldoServicio : lstSaldoServicio) {
								switch (eSaldoServicio.getClaseServicio()) {
								case 2://Para Credito Directo	
									if(creditoDirMonedaNacional == 0) creditoDirMonedaNacional =  eSaldoServicio.getCodigoMoneda() == 1 ? eSaldoServicio.getMontoSaldo() : 0;
									if(creditoDirMonedaExtranjera == 0) creditoDirMonedaExtranjera = eSaldoServicio.getCodigoMoneda() == 2 ? eSaldoServicio.getMontoSaldo() : 0;
									break;
								case 3://Para Credito Indirecto
									if(creditoIndMonedaNacional == 0) creditoIndMonedaNacional =  eSaldoServicio.getCodigoMoneda() == 1 ? eSaldoServicio.getMontoSaldo() : 0;
									if(creditoIndMonedaExtranjera == 0) creditoIndMonedaExtranjera = eSaldoServicio.getCodigoMoneda() == 2 ? eSaldoServicio.getMontoSaldo() : 0;
									break;
								case 5: //Para Garantia
									if(garantiaMonedaNacional == 0) garantiaMonedaNacional =  eSaldoServicio.getCodigoMoneda() == 1 ? eSaldoServicio.getMontoSaldo() : 0;
									if(garantiaMonedaExtranjera == 0) garantiaMonedaExtranjera = eSaldoServicio.getCodigoMoneda() == 2 ? eSaldoServicio.getMontoSaldo() : 0;
									break;
								default:
								}							
							}
						}
					}
				}
			}else{
				oEMensaje.setDescMensaje("Complete el Campo Descripción");
				RequestContext.getCurrentInstance().execute("PF('dlgMensaje').show();");
			}
		}else{
			oEMensaje.setDescMensaje("Seleccione un Tipo de Búsqueda");
			RequestContext.getCurrentInstance().execute("PF('dlgMensaje').show();");
		}
	}
	
	/*Metodo para Obtener la cantidad maxima de caracteres por cada opcion
	 * de busqueda*/
	public void validarLongitudCaracteresGarantia(){
		descripcionBuscarGarantia = "";
		switch(codigoBuscarGarantia){
		  case 1: cantidadCaracteresBusquedaGarantia = UCantidadCaracteres.CODIGO_SOCIO; 
		          break;
		  case 2: cantidadCaracteresBusquedaGarantia = UCantidadCaracteres.NOMBRE_SOCIO; 
		          break;
		  case 3: cantidadCaracteresBusquedaGarantia = UCantidadCaracteres.CODIGO_GARANTIA; 
		          break;
		  case 4: cantidadCaracteresBusquedaGarantia = UCantidadCaracteres.PARTIDA_REGISTRAL; 
				  break;
		  default: cantidadCaracteresBusquedaGarantia= UCantidadCaracteres.POR_DEFECTO;
		}
	}
	
	//Listar Tipos de Garantia y llevarlo a un Dialog
	public void buscarTiposGarantia(){
		lstTipoGarantia = oUManejadorListaDesplegable.obtieneTipoGarantiaF9205();
		lstTipoGarantiaFiltro = lstTipoGarantia;
		RequestContext.getCurrentInstance().execute("PF('dlgBuscarTipoGarantia').show();");
	}
	
	//Filtrar Tipo de Garantia segun Descripcion
	public void filtrarTipoGarantia(){
		lstTipoGarantiaFiltro = lstTipoGarantia.stream()
				   .filter(x -> x.getDescripcion().matches("(?i).*"+ tipoGarantiaBuscar +".*"))
				   .collect(Collectors.toList());
	}
	

	//Metodo para visualizar Vista de Registro de Nueva Garantia con Datos del Cliente
	public void nuevaGarantia(EGeneral eTipoGarantiaItem){
		if(eTipoGarantiaItem != null){
			String ruta = "";
			eTipoGarantiaItem.setCodigo3(oEGarantiaData.getCodigoCliente());
			UManejadorSesionWeb.registraVariableSesion(UVariablesSesion.ACCION_EXTERNA, UAccionExterna.NUEVO);
			UManejadorSesionWeb.registraVariableSesion(UVariablesSesion.FICHA_PARAMETRO, eTipoGarantiaItem);
			ruta = "MantenimientoOperacionGarantia.xhtml";
			UGeneradorQueryString objUGeneradorQueryString = new UGeneradorQueryString(ruta);
			UManejadorSesionWeb.redirigePagina(objUGeneradorQueryString.obtieneUrlConParametros());
		}
	}
	
	//Metodo para visualizar Vista de Modificación de una Garantía 
	public void modificarGarantia(EGarantia oEGarantiaItem){
		if (oEGarantiaItem != null) {
			oEGarantiaSelected = oEGarantiaItem;
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
	
	//Método para Visualizar Vista de Trámite de una Garantía
	public void tramiteGarantia(EGarantia oEGarantiaItem){
		if (oEGarantiaItem != null) {
			oEGarantiaSelected = oEGarantiaItem;
			String ruta = "";
			if (oEGarantiaSelected != null) {
				Object oEGarantiaTramite = oBOGarantia.buscarGarantiaTramite(oEGarantiaSelected.getCodigoGarantia());
				
				if(oEGarantiaTramite == null){
					UManejadorSesionWeb.registraVariableSesion(UVariablesSesion.ACCION_EXTERNA, UAccionExterna.NUEVO);
				}else{
					UManejadorSesionWeb.registraVariableSesion(UVariablesSesion.ACCION_EXTERNA, UAccionExterna.EDITAR);
				}
				UManejadorSesionWeb.registraVariableSesion(UVariablesSesion.FICHA_PARAMETRO, oEGarantiaSelected);
				
				ruta = "TramiteOperacionGarantia.xhtml";
				UGeneradorQueryString objUGeneradorQueryString = new UGeneradorQueryString(ruta);
				UManejadorSesionWeb.redirigePagina(objUGeneradorQueryString.obtieneUrlConParametros());
			}
		}
	}
	
	
	/*
	 * MÉTODOS PARA LA GESTIÓN DE TIPOS DE GARANTÍAS (TAB = 4) 
	 *  
	 */
	
	//Método para mostrar Dialog de Registro de un Nuevo Tipo de Garantía
	public void nuevoTipoGarantia(){
		oEGeneralData=new EGeneral();
		RequestContext.getCurrentInstance().execute("PF('dlgNuevoTipoGarantia').show();");
		mensajeValidacion = "";
		indicadorNuevoTipoGarantia = true;
	}
	
	//Método para mostrar Dialog de Modificación de un Tipo de Garantía
	public void modificarTipoGarantia(EGeneral oEGeneralItem){
		if(oEGeneralItem != null){
			oEGeneralData = oEGeneralItem;
			mensajeValidacion = "";
			indicadorNuevoTipoGarantia = false;
			RequestContext.getCurrentInstance().execute("PF('dlgNuevoTipoGarantia').show();");
		}
	}
	
	//Método para Registrar un Nuevo Tipo de Garantía
	public void guardarTipoGarantia(){
		EGeneral eTipoGarantia = new EGeneral();
		eTipoGarantia = oEGeneralData;
		
		if(!oEGeneralData.getDescripcion().trim().isEmpty()){
			if(eTipoGarantia.getCodigo2() == 0){
				oEMensaje = oBOGarantia.registrarTipoGarantia(eTipoGarantia);
			}else{
				oEMensaje = oBOGarantia.modificarTipoGarantia(eTipoGarantia);
				
			}
			UManejadorLog.log(" Guardar: " + oEMensaje.getDescMensaje());
			RequestContext.getCurrentInstance().execute("PF('dlgMensajeOperacion').show();");
			RequestContext.getCurrentInstance().execute("PF('dlgNuevoTipoGarantia').hide();");
			
		}else{
			mensajeValidacion = "Ingrese Campo Descripción";
		}
	}
	
	public void salir(){
		listarDesplegable();
	}
	
	//DOCUMENTACION
	//*************************************//
	//Metodos para documento de carga
    //*************************************//
	
	public void onTabChangeDocumentoGarantia(TabChangeEvent event){
		TabView tv = (TabView) event.getTab().getParent();		
		if(tv.getActiveIndex() == 0) codigoTabview3Index = 0;
		else codigoTabview3Index = 1;
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
	
	/*Metodo para Obtener la cantidad maxima de caracteres por cada opcion
	 * de busqueda*/
	public void validarLongitudCaracteresSolicitudDocumento(){
		descripcionBuscarDocumento = "";
		switch(codigoBuscarDocumento){
		  case 1: cantidadCaracteresBusquedaSolicitudDocumento = UCantidadCaracteres.CODIGO_SOLICITUD; 
		          break;
		  case 2: cantidadCaracteresBusquedaSolicitudDocumento = UCantidadCaracteres.CODIGO_GARANTIA; 
		          break;
		  default: cantidadCaracteresBusquedaSolicitudDocumento= UCantidadCaracteres.POR_DEFECTO;
		}
	}

	public void buscarSolicitudDocumento(){
		EGarantia eGarantia = new EGarantia();
		eGarantia.setUsuarioRegistro(oEUsuario);
		eGarantia.setTipoDocumento(UTipoDocumentoGarantia.CONSTITUCION);
		lstDocumentoGarantia = oBOGarantia.listarSolicitudDocumentoGarantia(codigoBuscarDocumento, descripcionBuscarDocumento.trim(), eGarantia);
		
		if(codigoTabview3Index == 0){
			lstDocumentoGarantiaPendiente = new ArrayList<EOperacionDocumento>();
			if(lstDocumentoGarantia != null){
				for(EOperacionDocumento obj: lstDocumentoGarantia){
					if(obj.getEstadoDocumento() != UEstado.DOCUMENTOFIRMADO ){
						lstDocumentoGarantiaPendiente.add(obj);
					}
				}
			}
		}else if (codigoTabview3Index == 1){
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

	public void visualizarObservacionDocumento(EOperacionDocumento oEOperacionDocumentoItem){
		if(oEOperacionDocumentoItem != null){
			deshabilitarObservacionDocumento = oEOperacionDocumentoItem.isValidarObservacion();
			oEOperacionDocumentoDetalle = oEOperacionDocumentoItem;
			RequestContext.getCurrentInstance().execute("PF('dlgObservacionDocumento').show();");
		}
		
	}
	
	public void visualizarFirmaDocumento(EOperacionDocumento oEOperacionDocumentoItem){
		if(oEOperacionDocumentoItem != null){
			if(oEOperacionDocumentoItem.getFirmaDocumento().equals("SI")) codigoFirmaSiNo = 1;		
			else codigoFirmaSiNo = 2;
			oEOperacionDocumentoDetalle = oEOperacionDocumentoItem;
			RequestContext.getCurrentInstance().execute("PF('dlgFirma').show();");
		}
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
	
	
	public void visualizarDocumentoSolicitud(EOperacionDocumento oEOperacionDocumentoItem){
		if(oEOperacionDocumentoItem != null){
			listarSolicitudDetalleDocumento(oEOperacionDocumentoItem);
			lstOperacionDocumentoFiltro = oBOGarantia.listarSolicitudDocumentoGarantiaDetalle(oEOperacionDocumentoItem);
			oEOperacionDocumento = oEOperacionDocumentoItem;
			lstDocumentoCarga = new ArrayList<EDocumentoCarga>();
			RequestContext.getCurrentInstance().execute("PF('dlgDocumentacion').show();");
		}
	}
	

	
	
	public void evaluarSolicitudDocumento(EOperacionDocumento oEOperacionDocumentoItem){
		oEGarantiaSelected = new EGarantia();
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
	
	public void actualizarDatoasAjax(){
		listarSolicitudDocumento();
		lstOperacionDocumentoFiltro = oBOGarantia.listarSolicitudDocumentoGarantiaDetalle(oEOperacionDocumento);
	}


	public EMensaje getoEMensaje() {
		return oEMensaje;
	}

	public void setoEMensaje(EMensaje oEMensaje) {
		this.oEMensaje = oEMensaje;
	}

	public EGarantia getoEGarantiaData() {
		return oEGarantiaData;
	}

	public void setoEGarantiaData(EGarantia oEGarantiaData) {
		this.oEGarantiaData = oEGarantiaData;
	}

	public EGeneral getoEGeneralData() {
		return oEGeneralData;
	}

	public void setoEGeneralData(EGeneral oEGeneralData) {
		this.oEGeneralData = oEGeneralData;
	}
	
	public int getCodigoGarantiaDocumento() {
		return codigoGarantiaDocumento;
	}

	public void setCodigoGarantiaDocumento(int codigoGarantiaDocumento) {
		this.codigoGarantiaDocumento = codigoGarantiaDocumento;
	}

	public EOperacionDocumento getoEOperacionDocumento() {
		return oEOperacionDocumento;
	}

	public void setoEOperacionDocumento(EOperacionDocumento oEOperacionDocumento) {
		this.oEOperacionDocumento = oEOperacionDocumento;
	}

	public EOperacionDocumento getoEOperacionDocumentoDetalle() {
		return oEOperacionDocumentoDetalle;
	}

	public void setoEOperacionDocumentoDetalle(
			EOperacionDocumento oEOperacionDocumentoDetalle) {
		this.oEOperacionDocumentoDetalle = oEOperacionDocumentoDetalle;
	}
	
	
}
