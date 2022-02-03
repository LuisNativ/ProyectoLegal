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

@ManagedBean(name = "mbbandejaoperacionlegal")
@ViewScoped
public class MBBandejaOperacionLegal implements Serializable {
	private static final long serialVersionUID = 1L;
	private EMensaje oEMensaje;
	private EUsuario oEUsuario;
	
	private ECliente oEClienteData;
	
	private EPersona oEPersonaSelected;
	
	private BOOperacion oBOOperacion;
	private BOSolicitudCredito oBOSolicitudCredito;
	private BOGeneral oBOGeneral;
	private BOCliente oBOCliente;
	private UManejadorListaDesplegable oUManejadorListaDesplegable;
	
	@Getter @Setter private List<EOperacionSolicitud> lstOperacionSolicitud;
	@Getter @Setter private List<EOperacionSolicitud> lstOperacionSolicitudPendiente;
	@Getter @Setter private List<EOperacionSolicitud> lstOperacionSolicitudHistorico;
	@Getter @Setter private List<EGeneral> lstEstado;
	@Getter @Setter private List<EGeneral> lstAutorizaJefe;
	@Getter @Setter private List<EPersona> lstPersona;
	
	/* Variables Interfaz */
	@Getter @Setter private int numeroSolicitud;
	@Getter @Setter private int codigoEstado;
	@Getter @Setter private int codigoAutorizaJefe;
	@Getter @Setter private String nombrePersona;

	@Getter @Setter private int codigoBuscar;
	@Getter @Setter private String descripcionBuscar;
	@Getter @Setter private int codigoTipoClienteBuscar;
	@Getter @Setter private int codigoPersonaBuscar;
	@Getter @Setter private String nombrePersonaBuscar;
	
	/* Variables Internas */
	private String rutaBaseFormato;
	
	@PostConstruct
	public void inicio() {
		this.oEMensaje = new EMensaje();
		
		this.oEClienteData = new ECliente();
		
		oEPersonaSelected = new EPersona();
		
		oBOOperacion = new BOOperacion();
		oBOSolicitudCredito = new BOSolicitudCredito();
		oBOGeneral = new BOGeneral();
		oBOCliente = new BOCliente();
		oUManejadorListaDesplegable = new UManejadorListaDesplegable();
		
		lstOperacionSolicitud = new ArrayList<EOperacionSolicitud>();
		lstOperacionSolicitudPendiente = new ArrayList<EOperacionSolicitud>();
		lstOperacionSolicitudHistorico = new ArrayList<EOperacionSolicitud>();
		lstEstado = new ArrayList<EGeneral>();
		lstAutorizaJefe = new ArrayList<EGeneral>();

		oEUsuario = (EUsuario) UManejadorSesionWeb.obtieneVariableSesion(UVariablesSesion.USUARIO);
		
		/*
		if (UManejadorSesionWeb.obtieneVariableSesion(UVariablesSesion.OPERACION_SESION) != null) {
			EOperacionSolicitud oEOperacionSolicitud = (EOperacionSolicitud) UManejadorSesionWeb.obtieneVariableSesion(UVariablesSesion.OPERACION_SESION);
			oBOOperacion.liberarSolicitudSesion(oEOperacionSolicitud.getCodigoSolicitud());
			UManejadorSesionWeb.eliminaVariableSesion(UVariablesSesion.OPERACION_SESION);
		}
		*/
		
		inicializar();
		verificarSolicitudCredito();
		verificarSolicitudSiaf();
		listarSolicitud();
		listarDesplegable();
	}
	
	public void modificar(EOperacionSolicitud oEOperacionSolicitudItem) {
		String ruta = "";
		if (oEOperacionSolicitudItem != null) {
			oEOperacionSolicitudItem.setUsuarioRegistro(oEUsuario);
			
			UManejadorSesionWeb.registraVariableSesion(UVariablesSesion.OPERACION_SESION, oEOperacionSolicitudItem);
			UManejadorSesionWeb.registraVariableSesion(UVariablesSesion.ACCION_EXTERNA, UAccionExterna.EDITAR);
			UManejadorSesionWeb.registraVariableSesion(UVariablesSesion.FICHA_PARAMETRO, oEOperacionSolicitudItem);
			
			if(oEOperacionSolicitudItem.getCodigoTipoEvaluacion() == UTipoEvaluacion.SOLICITUDCREDITO){
				ruta = "RegistroOperacionSolicitudCredito.xhtml";
			}else if(oEOperacionSolicitudItem.getCodigoTipoEvaluacion() == UTipoEvaluacion.PODERES){
				ruta = "RegistroOperacionEvaluacionPoder.xhtml";
			}else if(oEOperacionSolicitudItem.getCodigoTipoEvaluacion() == UTipoEvaluacion.LEVANTAMIENTO){
				ruta = "RegistroOperacionLevantamientoGarantia.xhtml";
			}else if(oEOperacionSolicitudItem.getCodigoTipoEvaluacion() == UTipoEvaluacion.CONSTITUCION){
				ruta = "RegistroOperacionConstitucionGarantia.xhtml";
			}else if(oEOperacionSolicitudItem.getCodigoTipoEvaluacion() == UTipoEvaluacion.CARTAFIANZA){
				ruta = "RegistroOperacionCreditoIndirecto.xhtml";
			}
			
			verificarSolicitudSiaf();
			
			UGeneradorQueryString objUGeneradorQueryString = new UGeneradorQueryString(ruta);
			UManejadorSesionWeb.redirigePagina(objUGeneradorQueryString.obtieneUrlConParametros());
			/*
			this.oEMensaje = new EMensaje();
			oEMensaje = oBOOperacion.redigirSolicitud(oEOperacionSolicitudSelected);
			
			if(oEMensaje.getIdMensaje()<0){
				RequestContext.getCurrentInstance().execute("PF('dlgMensajeOperacion').show();");
			}else{
				UManejadorSesionWeb.registraVariableSesion(UVariablesSesion.OPERACION_SESION, oEOperacionSolicitudSelected);
				UManejadorSesionWeb.registraVariableSesion(UVariablesSesion.ACCION_EXTERNA, UAccionExterna.EDITAR);
				UManejadorSesionWeb.registraVariableSesion(UVariablesSesion.FICHA_PARAMETRO, oEOperacionSolicitudSelected);
				
				if(oEOperacionSolicitudSelected.getCodigoEstado() == UEstado.PENDIENTEDEREGISTROMANUAL){
					ruta = "ir_RegistroOperacionManual";
					//ruta = "RegistroOperacionManual";
				}else{
					ruta = "ir_RegistroOperacion";
					//ruta = "RegistroOperacion";
				}
			}
			*/
		}
	}
	
	public void visualizar(EOperacionSolicitud oEOperacionSolicitudItem) {
		String ruta = "";
		if (oEOperacionSolicitudItem != null) {
			UManejadorSesionWeb.registraVariableSesion(UVariablesSesion.ACCION_EXTERNA, UAccionExterna.VER);
			UManejadorSesionWeb.registraVariableSesion(UVariablesSesion.FICHA_PARAMETRO, oEOperacionSolicitudItem);
			
			if(oEOperacionSolicitudItem.getCodigoTipoEvaluacion() == UTipoEvaluacion.SOLICITUDCREDITO){
				ruta = "RegistroOperacionSolicitudCredito.xhtml";
			}else if(oEOperacionSolicitudItem.getCodigoTipoEvaluacion() == UTipoEvaluacion.PODERES){
				//ruta = "RegistroEvaluacionPoder.xhtml";
			}else if(oEOperacionSolicitudItem.getCodigoTipoEvaluacion() == UTipoEvaluacion.LEVANTAMIENTO){
				ruta = "ConsultaOperacionLevantamientoGarantia.xhtml";
			}else if(oEOperacionSolicitudItem.getCodigoTipoEvaluacion() == UTipoEvaluacion.CONSTITUCION){
				ruta = "ConsultaOperacionConstitucionGarantia.xhtml";
			}else if(oEOperacionSolicitudItem.getCodigoTipoEvaluacion() == UTipoEvaluacion.CARTAFIANZA){
				ruta = "ConsultaOperacionCreditoIndirecto.xhtml";
			}
			
			UGeneradorQueryString objUGeneradorQueryString = new UGeneradorQueryString(ruta);
			UManejadorSesionWeb.redirigePagina(objUGeneradorQueryString.obtieneUrlConParametros());
		}
	}
	
	public void verificarSolicitudCredito() {
		List<EEvaluacionSolicitudCreditoLegal> lstEvaluacionSolicitudCreditoLegal = oBOSolicitudCredito.listarInformeLegalPorRegistrar();
		SimpleDateFormat formato = new SimpleDateFormat("HH:mm:ss");
		
		for (EEvaluacionSolicitudCreditoLegal obj: lstEvaluacionSolicitudCreditoLegal) {
			this.oEMensaje = new EMensaje();
			
			EOperacionSolicitud oEOperacionSolicitud = new EOperacionSolicitud();
			oEOperacionSolicitud.setCodigoSolicitud(oBOGeneral.generarCorrelativo(UTipoCorrelativo.OPERACIONSOLICITUD, "", "", ""));
			oEOperacionSolicitud.setCodigoTipoEvaluacion(UTipoEvaluacion.SOLICITUDCREDITO);
			oEOperacionSolicitud.setCodigoSolicitudCredito(obj.getNumeroSolicitud());
			oEOperacionSolicitud.setCodigoClientePersona(obj.getCodigoCliente());
			oEOperacionSolicitud.setCodigoTipoPersona(obj.getCodigoTipoPersona());
			oEOperacionSolicitud.setCodigoTipoClientePersona(obj.getCodigoTipoCliente());
			oEOperacionSolicitud.setCodigoTipoDocumentoPersona(obj.getCodigoTipoDocumento());
			oEOperacionSolicitud.setNumeroDocumentoPersona(obj.getNumeroDocumento());
			oEOperacionSolicitud.setNombrePersona(obj.getNombreCorto());
			
			oEOperacionSolicitud.setCodigoAreaEmisor(obj.getCodigoAreaRegistroLegal());
			oEOperacionSolicitud.setCodigoEmisor(obj.getCodigoUsuarioRegistroLegal());
			oEOperacionSolicitud.setDescripcionEmisor(obj.getDescripcionUsuarioRegistroLegal());
			
			oEOperacionSolicitud.setCodigoTipoEnvio(UTipoEnvio.PUBLICO);
			oEOperacionSolicitud.setCodigoAreaReceptor(UArea.LEGAL);
			oEOperacionSolicitud.setCodigoReceptor(0);
			oEOperacionSolicitud.setDescripcionReceptor("");
			
			oEOperacionSolicitud.setDescripcionAsunto("-");
			//Buscar ultimo observacion de negocios
			oEOperacionSolicitud.setDescripcionMensaje(oBOSolicitudCredito.buscarObservacionNegocios(obj.getNumeroSolicitud(), 1));
			oEOperacionSolicitud.setFechaOrigen(obj.getFechaRegistroLegal());
			oEOperacionSolicitud.setFechaInicio(obj.getFechaRegistroLegal());
			
			try {
				oEOperacionSolicitud.setHoraOrigen(formato.parse(UFuncionesGenerales.convertirCadenaATime(obj.getHoraRegistroLegal())));
				oEOperacionSolicitud.setHoraInicio(formato.parse(UFuncionesGenerales.convertirCadenaATime(obj.getHoraRegistroLegal())));
			} catch (ParseException e) {
				e.printStackTrace();
			}
			
			oEOperacionSolicitud.setFechaRegistro(new Date());
			oEOperacionSolicitud.setUsuarioRegistro(oEUsuario);
			
			oEMensaje = oBOOperacion.agregarEvaluacionSolicitudCredito(oEOperacionSolicitud);
		}
	}
	
	/*
	public void verificarSolicitudCredito() {
		List<ESolicitudCredito> lstSolicitudCredito = oBOSolicitudCredito.listarSolicitudCreditoPorRegistrar();
		
		for (ESolicitudCredito obj: lstSolicitudCredito) {
			this.oEMensaje = new EMensaje();
			
			EOperacionSolicitud oEOperacionSolicitud = new EOperacionSolicitud();
			oEOperacionSolicitud.setCodigoSolicitud(oBOGeneral.generarCorrelativo(UTipoCorrelativo.OPERACIONSOLICITUD, "", "", ""));
			oEOperacionSolicitud.setCodigoTipoEvaluacion(UTipoEvaluacion.SOLICITUDCREDITO);
			oEOperacionSolicitud.setCodigoSolicitudCredito(obj.getNumeroSolicitud());
			oEOperacionSolicitud.setCodigoClientePersona(obj.getCodigoCliente());
			oEOperacionSolicitud.setCodigoTipoPersona(obj.getCodigoTipoPersona());
			oEOperacionSolicitud.setCodigoTipoClientePersona(obj.getCodigoTipoCliente());
			oEOperacionSolicitud.setCodigoTipoDocumentoPersona(obj.getCodigoTipoDocumento());
			oEOperacionSolicitud.setNumeroDocumentoPersona(obj.getNumeroDocumento());
			oEOperacionSolicitud.setNombrePersona(obj.getNombreLargo());
			
			oEOperacionSolicitud.setCodigoAreaEmisor(obj.getCodigoAreaPromotor());
			oEOperacionSolicitud.setCodigoEmisor(obj.getCodigoUsuarioPromotor());
			oEOperacionSolicitud.setDescripcionEmisor(obj.getDescripcionUsuarioPromotor());
			
			oEOperacionSolicitud.setCodigoTipoEnvio(UTipoEnvio.PUBLICO);
			oEOperacionSolicitud.setCodigoAreaReceptor(UArea.LEGAL);
			oEOperacionSolicitud.setCodigoReceptor(0);
			oEOperacionSolicitud.setDescripcionReceptor("");
			
			oEOperacionSolicitud.setDescripcionAsunto("-");
			//Buscar ultimo observacion de negocios
			oEOperacionSolicitud.setDescripcionMensaje(oBOSolicitudCredito.buscarObservacionNegocios(obj.getNumeroSolicitud(), 1));
			oEOperacionSolicitud.setFechaOrigen(obj.getFechaOrigen());
			oEOperacionSolicitud.setHoraOrigen(obj.getHoraOrigen());
			oEOperacionSolicitud.setFechaInicio(obj.getFechaInicio());
			oEOperacionSolicitud.setHoraInicio(obj.getHoraInicio());
			oEOperacionSolicitud.setFechaRegistro(new Date());
			oEOperacionSolicitud.setUsuarioRegistro(oEUsuario);
			
			oEMensaje = oBOOperacion.agregarEvaluacionSolicitudCredito(oEOperacionSolicitud);
		}
	}
	*/
	
	public void verificarSolicitudSiaf() {
		oBOOperacion.verificarSolicitudSiaf();
	}
	
	public void listarSolicitud() {
		EOperacionSolicitud oEOperacionSolicitud = new EOperacionSolicitud();
		oEOperacionSolicitud.setUsuarioRegistro(oEUsuario);
		oEOperacionSolicitud.setNombrePersona(nombrePersona);
		oEOperacionSolicitud.setCodigoSolicitudCredito(numeroSolicitud);
		oEOperacionSolicitud.setCodigoEstado(codigoEstado);
		oEOperacionSolicitud.setCodigoAutorizaJefe(codigoAutorizaJefe);
		oEOperacionSolicitud.setCodigoTipoEvaluacion(UTipoEvaluacion.SOLICITUDCREDITO);
		
		lstOperacionSolicitud = oBOOperacion.listarSolicitud(oEOperacionSolicitud, 2);
		
		if (lstOperacionSolicitud != null){
			lstOperacionSolicitudPendiente = new ArrayList<EOperacionSolicitud>();
	        for (EOperacionSolicitud obj: lstOperacionSolicitud) {
	            if (obj.getCodigoEstado() == UEstado.PENDIENTEDEEVALUACION ||
	            	obj.getCodigoEstado() == UEstado.ENEVALUACION ) {
	            	lstOperacionSolicitudPendiente.add(obj);
	            }
	        }
	        
	        lstOperacionSolicitudHistorico = new ArrayList<EOperacionSolicitud>();
	        for (EOperacionSolicitud obj: lstOperacionSolicitud) {
	            if (obj.getCodigoEstado() == UEstado.OBSERVADO ||
	            	obj.getCodigoEstado() == UEstado.CERRADO ||
	            	obj.getCodigoEstado() == UEstado.DESAPROBADO ||
	            	obj.getCodigoEstado() == UEstado.APROBADO) {
	            	lstOperacionSolicitudHistorico.add(obj);
	            }
	        }
		}
	}
	
	public void listarDesplegable(){
		lstEstado = oUManejadorListaDesplegable.obtieneEstadoOperacion();
		lstAutorizaJefe = oUManejadorListaDesplegable.obtieneAutorizaJefe();
	}
	
	public void generarInformeCliente(){
		this.oEClienteData = new ECliente();
		RequestContext.getCurrentInstance().execute("PF('dlgGenerarInforme').show();");
	}
	
	public void buscarPersona() {
		EPersonaParametro oEPersonaParametro = new EPersonaParametro();
		
		oEPersonaParametro.setCodPersona(codigoPersonaBuscar);
		oEPersonaParametro.setNombrePersona(nombrePersonaBuscar);
		
		if(codigoTipoClienteBuscar == UTipoCliente.COD_SOCIO) {
			lstPersona = oBOCliente.listarSocio(oEPersonaParametro);
		}else if(codigoTipoClienteBuscar == UTipoCliente.COD_POSTULANTE) {
			lstPersona = oBOCliente.listarPostulante(oEPersonaParametro);
		}else if(codigoTipoClienteBuscar == UTipoCliente.COD_TERCERO) {
			lstPersona = oBOCliente.listarTercero(oEPersonaParametro, UTipoBusquedaTercero.TERCERO);
		}else if(codigoTipoClienteBuscar == UTipoCliente.COD_NO_SOCIO) {
			lstPersona = oBOCliente.listarNoSocio(oEPersonaParametro);
		}
	}
	
	public void asignarPersona() {
		if(oEPersonaSelected != null){
			oEClienteData.setCodigoCliente(oEPersonaSelected.getCodigo());
			oEClienteData.setCodigoTipoCliente(codigoTipoClienteBuscar);
			oEClienteData.setCodigoTipoDocumento(oEPersonaSelected.getDocumentoIdentidad().getTipoDocumento().getCodigo());
			oEClienteData.setCodigoTipoPersona(oEPersonaSelected.getClasePersona());
			oEClienteData.setDocumento(oEPersonaSelected.getDocumentoIdentidad().getDocumento());
			oEClienteData.setNombreCorto(oEPersonaSelected.getNombreCorte());
			oEClienteData.setNombreLargo(oEPersonaSelected.getNombreLargo());
		}
	}
	
	public void generarExcel() {
		if (USistemaOperativo.ES_WINDOWS) {
			rutaBaseFormato = URutaCarpetaCompartida.rutaBaseWindows2;
		}else if (USistemaOperativo.ES_LINUX) {
			rutaBaseFormato = URutaCarpetaCompartida.rutaBaseLinux;
		}
		
        List<EEvaluacionSolicitudCreditoLegal> lstEvaluacionSolicitudCreditoLegal =  new ArrayList<EEvaluacionSolicitudCreditoLegal>();
        lstEvaluacionSolicitudCreditoLegal = oBOSolicitudCredito.listarEvaluacionSolicitudCreditoLegal(oEClienteData.getCodigoCliente(), oEClienteData.getDocumento());
        
        String rutaPlantillaAnexo = rutaBaseFormato + "Legal" + File.separator + "layout_bd01.xls";
        Date fechaProceso = new Date();
        
        //SXSSFWorkbook libroTrabajo = UtilPoi.obtenerArchivoExcelGrande(rutaPlantillaAnexo);
        //SXSSFSheet hoja = libroTrabajo.getSheetAt(0);
        HSSFWorkbook libroTrabajo = UtilPoi.obtenerArchivoExcelGrande2(rutaPlantillaAnexo);
        HSSFSheet hoja = libroTrabajo.getSheetAt(0);

        DataFormat formato = libroTrabajo.createDataFormat();
        SimpleDateFormat formato2 = new SimpleDateFormat(UDatePattern.dd_MM_yyyy);
        
        CellStyle csEstiloMoneda = libroTrabajo.createCellStyle();
        csEstiloMoneda.setDataFormat(formato.getFormat("#0.00"));
        csEstiloMoneda.setAlignment(HorizontalAlignment.RIGHT);

        CellStyle csEstiloNumero = libroTrabajo.createCellStyle();
        csEstiloNumero.setVerticalAlignment(VerticalAlignment.CENTER);
        csEstiloNumero.setAlignment(HorizontalAlignment.RIGHT);

        CellStyle csEstiloCadena = libroTrabajo.createCellStyle();
        csEstiloCadena.setAlignment(HorizontalAlignment.LEFT);
        
        int row = 1;
        
        for (EEvaluacionSolicitudCreditoLegal bd01 : lstEvaluacionSolicitudCreditoLegal) {
        	String observacionLegal = "";
        	String observacionNegocios = "";
        	
        	observacionLegal = oBOSolicitudCredito.buscarObservacionDetalle(bd01.getNumeroSolicitud(), UUbicacion.LEGAL, 1);
        	observacionNegocios = oBOSolicitudCredito.buscarObservacionNegocios(bd01.getNumeroSolicitud(), 0);
        	String fechaRegistroLegal = (bd01.getFechaRegistroLegal() != null ? formato2.format(bd01.getFechaRegistroLegal()):"");
        	String fechaUltimaRevision = (bd01.getFechaUltimaRevision() != null ? formato2.format(bd01.getFechaUltimaRevision()):"");
        	
            UtilPoi.crearCeldaSXConTamaño2(libroTrabajo, hoja, row, 0, bd01.getNumeroSolicitud(), csEstiloCadena, 10);
            UtilPoi.crearCeldaSXConTamaño2(libroTrabajo, hoja, row, 1, bd01.getCodigoCliente(), csEstiloNumero, 6);
            UtilPoi.crearCeldaSXConTamaño2(libroTrabajo, hoja, row, 2, bd01.getDescripcionTipoCliente(), csEstiloCadena, 13);
            UtilPoi.crearCeldaSXConTamaño2(libroTrabajo, hoja, row, 3, bd01.getNombreLargo(), csEstiloCadena, 13);
            UtilPoi.crearCeldaSXConTamaño2(libroTrabajo, hoja, row, 4, bd01.getDescripcionEstadoActual(), csEstiloCadena, 13);
            UtilPoi.crearCeldaSXConTamaño2(libroTrabajo, hoja, row, 5, fechaRegistroLegal, csEstiloCadena, 13);
            UtilPoi.crearCeldaSXConTamaño2(libroTrabajo, hoja, row, 6, bd01.getHoraRegistroLegal(), csEstiloCadena, 13);
            UtilPoi.crearCeldaSXConTamaño2(libroTrabajo, hoja, row, 7, bd01.getUsuarioRegistroLegal(), csEstiloCadena, 13);
            UtilPoi.crearCeldaSXConTamaño2(libroTrabajo, hoja, row, 8, fechaUltimaRevision, csEstiloCadena, 13);
            UtilPoi.crearCeldaSXConTamaño2(libroTrabajo, hoja, row, 9, bd01.getHoraUltimaRevision(), csEstiloCadena, 13);
            UtilPoi.crearCeldaSXConTamaño2(libroTrabajo, hoja, row, 10, bd01.getUsuarioUltimaRevision(), csEstiloCadena, 13);
            UtilPoi.crearCeldaSXConTamaño2(libroTrabajo, hoja, row, 11, bd01.getObservacionSolicitud(), csEstiloCadena, 13);
            UtilPoi.crearCeldaSXConTamaño2(libroTrabajo, hoja, row, 12, observacionNegocios, csEstiloCadena, 13);
            UtilPoi.crearCeldaSXConTamaño2(libroTrabajo, hoja, row, 13, observacionLegal, csEstiloCadena, 13);
			
            row++;
        }
        
        String anio = UFuncionesGenerales.convertirFechaACadena(fechaProceso, UDatePattern.yyyy);
        String mes = UFuncionesGenerales.convertirFechaACadena(fechaProceso, UDatePattern.MM);
        String sufijo = anio.concat(mes);
        
        String rutaExcelGenerado = rutaBaseFormato + "INF_".concat(sufijo).concat(UTipoArchivo.xls);
        
        UtilPoi.generarArchivoGrande_XLSX2(libroTrabajo, rutaExcelGenerado);
        UFuncionesGenerales.descargaArchivo(rutaExcelGenerado);
        UFuncionesGenerales.borrarArchivo(rutaExcelGenerado);
	}
	
	public void inicializar() {
		nombrePersona= "";
		codigoAutorizaJefe = 0;
		codigoTipoClienteBuscar = UTipoCliente.COD_SOCIO;
	}
	
	public EMensaje getoEMensaje() {
		return oEMensaje;
	}

	public void setoEMensaje(EMensaje oEMensaje) {
		this.oEMensaje = oEMensaje;
	}

	public ECliente getoEClienteData() {
		return oEClienteData;
	}

	public void setoEClienteData(ECliente oEClienteData) {
		this.oEClienteData = oEClienteData;
	}

	public EPersona getoEPersonaSelected() {
		return oEPersonaSelected;
	}

	public void setoEPersonaSelected(EPersona oEPersonaSelected) {
		this.oEPersonaSelected = oEPersonaSelected;
	}
	
}
