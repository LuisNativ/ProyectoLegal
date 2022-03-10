package com.abaco.ageneral;
import java.util.List;

import com.abaco.entidad.EGeneral;
import com.abaco.entidad.EMensaje;
import com.abaco.entidad.EUsuario;
import com.abaco.ageneral.ESolicitudCredito;

public class BOGarantia {

	CGarantia oCGarantia= new CGarantia();
	
	public EMensaje agregarGarantiaPendienteRegistro(EGarantiaSolicitud eGarantiaSolicitud, EGarantia eGarantia){
		EMensaje resultado=oCGarantia.agregarGarantiaPendienteRegistro(eGarantiaSolicitud,eGarantia);
		return resultado;
	}
	public EMensaje agregarGarantiaMantenimiento(EGarantia eGarantia){
		EMensaje resultado=oCGarantia.agregarGarantiaMantenimiento(eGarantia);
		return resultado;
	}
	
	public EMensaje agregarGarantiaMantenimientoyInmueblePredios(EGarantia eGarantia,List<EGarantia> lstInmueblesAdicionales){
		EMensaje resultado=oCGarantia.agregarGarantiaMantenimientoyInmueblePredios(eGarantia,lstInmueblesAdicionales);
		return resultado;
	}
	
	
	public EMensaje registrarGarantiaSolicitudAnexoF7325(){
		EMensaje resultado=oCGarantia.registrarGarantiaSolicitudAnexoF7325();
		return resultado;
	}
	
	public EMensaje registrarTipoGarantia(EGeneral tipoGarantia){
		EMensaje resultado=oCGarantia.registrarTipoGarantia(tipoGarantia);
		return resultado;
	}
	
	public EMensaje registrarPoliza(EPoliza ePoliza) {
		EMensaje resultado=oCGarantia.registrarPoliza(ePoliza);
		return resultado;
	}
	
	public EMensaje modificarTipoGarantia(EGeneral tipoGarantia){
		EMensaje resultado=oCGarantia.modificarTipoGarantia(tipoGarantia);
		return resultado;
	}
	public EMensaje modificarGarantiaPoliza(EGarantia eGarantia){
		EMensaje resultado=oCGarantia.modificarGarantiaPoliza(eGarantia);
		return resultado;
	}
	public EMensaje modificarPoliza(EPoliza ePoliza) {
		EMensaje resultado=oCGarantia.modificarPoliza(ePoliza) ;
		return resultado;
	}
	public EMensaje modificarTipoIngresoPorcentaje(EGarantiaSolicitud eGarantiaSolicitud){
		EMensaje resultado=oCGarantia.modificarTipoIngresoPorcentaje(eGarantiaSolicitud) ;
		return resultado;
	}
	public EMensaje modificarSolicitudDocumentoyDesembolsoGarantia(EGarantia eGarantia){
		EMensaje resultado=oCGarantia.modificarSolicitudDocumentoyDesembolsoGarantia(eGarantia) ;
		return resultado;
	}
	public EMensaje modificarDocumentoNotariayDesembolsoGarantia(EGarantia eGarantia){
		EMensaje resultado=oCGarantia.modificarDocumentoNotariayDesembolsoGarantia(eGarantia) ;
		return resultado;
	}
	public EMensaje modificarSolicitudDocumentoGarantia(EGarantia eGarantia){
		EMensaje resultado=oCGarantia.modificarSolicitudDocumentoGarantia(eGarantia) ;
		return resultado;
	}
	public EMensaje modificarDetalleSolicitudDocumentoGarantia(EGarantia eGarantia, int indicador){
		EMensaje resultado=oCGarantia.modificarDetalleSolicitudDocumentoGarantia(eGarantia,indicador) ;
		return resultado;
	}
	public EMensaje modificarSolicitudDesembolsoGarantia(EGarantia eGarantia){
		EMensaje resultado=oCGarantia.modificarSolicitudDesembolsoGarantia(eGarantia) ;
		return resultado;
	}
	public EMensaje modificarInmuebleGarantiaPredios(EGarantia eGarantia){
		EMensaje resultado=oCGarantia.modificarInmuebleGarantiaPredios(eGarantia) ;
		return resultado;
	}
	public EMensaje modificarConfirmacionFirmanteSolicitud(EFirmanteSolicitud eFirmanteSolicitud){
		EMensaje resultado=oCGarantia.modificarConfirmacionFirmanteSolicitud(eFirmanteSolicitud) ;
		return resultado;
	}
	public EMensaje modificarFirmaContratoFirmanteSolicitud(EFirmanteSolicitud eFirmanteSolicitud){
		EMensaje resultado=oCGarantia.modificarFirmaContratoFirmanteSolicitud(eFirmanteSolicitud) ;
		return resultado;
	}
	public EMensaje modificarConfirmaDatosGarantiaSolicitud(EGarantiaSolicitud eGarantiaSolicitud){
		EMensaje resultado=oCGarantia.modificarConfirmaDatosGarantiaSolicitud(eGarantiaSolicitud) ;
		return resultado;
	}
	public EMensaje modificarNoConformidadDatosGarantia(long codigoSolicitud,String nombreUusario){
		EMensaje resultado=oCGarantia.modificarNoConformidadDatosGarantia(codigoSolicitud,nombreUusario) ;
		return resultado;
	}
	public EMensaje modificarNoConformidadDatosGarantiayObservacionSolicitud(EOperacionSolicitud oEOperacionSolicitud){
		EMensaje resultado=oCGarantia.modificarNoConformidadDatosGarantiayObservacionSolicitud(oEOperacionSolicitud) ;
		return resultado;
	}
	public EMensaje modificarContratoGarantiaF7401(EContrato eContrato){
		EMensaje resultado=oCGarantia.modificarContratoGarantiaF7401(eContrato) ;
		return resultado;
	}
	public EMensaje modificarImpresionContratoGarantia(EContrato eContrato){
		EMensaje resultado=oCGarantia.modificarImpresionContratoGarantia(eContrato) ;
		return resultado;
	}
	public EMensaje modificarFlagImpresionContrato(EContrato eContrato){
		EMensaje resultado=oCGarantia.modificarFlagImpresionContrato(eContrato) ;
		return resultado;
	}
	public EMensaje modificarDetalleFlagRequisitoLegal(EFlagReqLegal eFlagRequisitoLegal){
		EMensaje resultado=oCGarantia.modificarDetalleFlagRequisitoLegal(eFlagRequisitoLegal) ;
		return resultado;
	}
	public EMensaje modificarContratoyRepresentanteGarantia(EContrato eContrato,List<ERepresentanteCIAContrato> lstRepresentanteCIAContrato){
		EMensaje resultado=oCGarantia.modificarContratoyRepresentanteGarantia(eContrato,lstRepresentanteCIAContrato) ;
		return resultado;
	}
	public EMensaje modificarDocumentoGarantia(EGarantia eGarantia){
		EMensaje resultado=oCGarantia.modificarDocumentoGarantia(eGarantia) ;
		return resultado;
	}
	public EMensaje agregarModificarGarantiaTramite(EGarantiaTramite eGarantiaTramite,EGarantiaTramite eGarantiaTramiteAsiento) {
		EMensaje resultado=oCGarantia.agregarModificarGarantiaTramite(eGarantiaTramite,eGarantiaTramiteAsiento);
		return resultado;
	}
	public EMensaje agregarAsientoGarantiaTramite(EGarantiaTramite eGarantiaTramiteAsiento){
		EMensaje resultado=oCGarantia.agregarAsientoGarantiaTramite(eGarantiaTramiteAsiento);
		return resultado;
	}
	public EMensaje modificarGarantiaMantenimiento(EGarantia eGarantia ){
		EMensaje resultado=oCGarantia.modificarGarantiaMantenimiento(eGarantia);
		return resultado;
	}
	public EMensaje modificarGarantiaMantenimientoyInmueblePredios(EGarantia eGarantia,List<EGarantia> lstInmueblesAdicionales){
		EMensaje resultado=oCGarantia.modificarGarantiaMantenimientoyInmueblePredios(eGarantia,lstInmueblesAdicionales);
		return resultado;
	}
	
	public EMensaje agregarDocumentoGarantia(EGarantia eGarantia,List<EDocumentoCarga> lstDocumentoCarga){
		EMensaje resultado=oCGarantia.agregarDocumentoGarantia(eGarantia,lstDocumentoCarga);
		return resultado;
	}
	
	public EMensaje agregarSolicitudDocumentoGarantia(EGarantia eGarantia,List<EDocumentoCarga> lstDocumentoCarga){
		EMensaje resultado=oCGarantia.agregarSolicitudDocumentoGarantia(eGarantia,lstDocumentoCarga);
		return resultado;
	}
	
	public EMensaje agregarDetalleSolicitudDocumentoGarantia(EGarantia eGarantia,List<EDocumentoCarga> lstDocumentoCarga){
		EMensaje resultado=oCGarantia.agregarDetalleSolicitudDocumentoGarantia(eGarantia,lstDocumentoCarga);
		return resultado;
	}
	
	public EMensaje registrarDetalleSolicitudDocumentoGarantia(EGarantia eGarantia,EDocumentoCarga oEDocumentoCarga){
		EMensaje resultado=oCGarantia.registrarDetalleSolicitudDocumentoGarantia(eGarantia,oEDocumentoCarga);
		return resultado;
	}
	
	public EMensaje registrarInmueblesGarantiaPredios(EGarantia eGarantia){
		EMensaje resultado=oCGarantia.registrarInmueblesGarantiaPredios(eGarantia);
		return resultado;
	}
	
	public EMensaje registrarContratoGarantiaF7401(EContrato eContrato){
		EMensaje resultado=oCGarantia.registrarContratoGarantiaF7401(eContrato);
		return resultado;
	}
	
	public EMensaje registrarContratoyRepresentanteGarantia(EContrato eContrato,List<ERepresentanteCIAContrato> lstRepresentanteCIAContrato){
		EMensaje resultado=oCGarantia.registrarContratoyRepresentanteGarantia(eContrato,lstRepresentanteCIAContrato);
		return resultado;
	}
	
	public EMensaje registrarRepresentanteCompaniaContratoF7433(ERepresentanteCIAContrato eRepresentanteCiaContrato){
		EMensaje resultado=oCGarantia.registrarRepresentanteCompaniaContratoF7433(eRepresentanteCiaContrato);
		return resultado;
	}
	
	public EMensaje agregarSolicitudDocumentoyDesembolsoGarantia(EGarantia eGarantia){
		EMensaje resultado=oCGarantia.agregarSolicitudDocumentoyDesembolsoGarantia(eGarantia);
		return resultado;
	}
	
	public EMensaje agregarObservacionTramiteOperativoSolicitud(EOperacionSolicitud eOperacionSolicitud){
		EMensaje resultado=oCGarantia.agregarObservacionTramiteOperativoSolicitud(eOperacionSolicitud);
		return resultado;
	}
	public EMensaje agregarDetalleFlagRequisitoLegal(EFlagReqLegal eFlagRequisitoLegal){
		EMensaje resultado=oCGarantia.agregarDetalleFlagRequisitoLegal(eFlagRequisitoLegal);
		return resultado;
	}
	
	public EMensaje modificarGarantiaSolicitud(EGarantiaSolicitud eGarantiaSolicitud){
		EMensaje resultado=oCGarantia.modificarGarantiaSolicitud(eGarantiaSolicitud);
		return resultado;
	}
	
	public EMensaje modificarSolicitudyGenerarAsientoyDocumentacionGarantia(EGarantiaTramite eGarantiaAsientoTramite, EGarantiaSolicitud eGarantiaAsociadaSolicitud,EGarantia eGarantia,EGarantia eSolicitudDesembolsoGarantia){
		EMensaje resultado=oCGarantia.modificarSolicitudyGenerarAsientoyDocumentacionGarantia(eGarantiaAsientoTramite,eGarantiaAsociadaSolicitud,eGarantia,eSolicitudDesembolsoGarantia);
		return resultado;
	}
	
	public EMensaje modificarGarantiaSolicitudSIAF(EGarantiaSolicitud eGarantiaSolicitud,EGarantiaDetalleSolicitud eGarantiaDetalleSolicitud){
		EMensaje resultado=oCGarantia.modificarGarantiaSolicitudSIAF(eGarantiaSolicitud,eGarantiaDetalleSolicitud);
		return resultado;
	}
	
	public EMensaje actualizarGarantiaSolicitud(EGarantiaSolicitud eGarantiaSolicitud,
			EGarantiaDetalleSolicitud eGarantiaDetalleSolicitud,EGarantiaSolicitud oEGarantiaAsociadaSolicitud){
		EMensaje resultado=oCGarantia.actualizarGarantiaSolicitud(eGarantiaSolicitud,eGarantiaDetalleSolicitud,oEGarantiaAsociadaSolicitud);
		return resultado;
	}

	public List<EGarantiaSolicitud> listarGarantiaPorLiberar(int codigo, String descripcion){					
		List<EGarantiaSolicitud> lista=oCGarantia.listarGarantiaPorLiberar(codigo, descripcion);
		return lista;
	}
	public List<EGarantiaSolicitud> listarGarantiaLiberada(int codigo, String descripcion){					
		List<EGarantiaSolicitud> lista=oCGarantia.listarGarantiaLiberada(codigo, descripcion);
		return lista;
	}
	public List<EOperacionDocumento> listarSolicitudDocumentoGarantia(int codigo, String descripcion,EGarantia eOGarantia){					
		List<EOperacionDocumento> lista=oCGarantia.listarSolicitudDocumentoGarantia(codigo,descripcion,eOGarantia);
		return lista;
	}
	public List<EOperacionDocumento> listarSolicitudDocumentoXNroGarantia(int codigo, String descripcion,EGarantia eOGarantia){					
		List<EOperacionDocumento> lista=oCGarantia.listarSolicitudDocumentoXNroGarantia(codigo,descripcion,eOGarantia);
		return lista;
	}
	public List<EOperacionDocumento> listarSolicitudDocumentoGarantiaDetalle(EOperacionDocumento eOperacionDocumento){					
		List<EOperacionDocumento> lista=oCGarantia.listarSolicitudDocumentoGarantiaDetalle(eOperacionDocumento);
		return lista;
	}
	public List<EOperacionDocumento> listarSolicitudDesembolsoGarantia(int codigo, String descripcion,EGarantia eOGarantia){					
		List<EOperacionDocumento> lista=oCGarantia.listarSolicitudDesembolsoGarantia(codigo,descripcion,eOGarantia);
		return lista;
	}
	public List<EOperacionDocumento> listarSolicitudDesembolsoXNroGarantia(int codigo, String descripcion,EGarantia eOGarantia){					
		List<EOperacionDocumento> lista=oCGarantia.listarSolicitudDesembolsoXNroGarantia(codigo,descripcion,eOGarantia);
		return lista;
	}
	
	public List<EOperacionSolicitud> listarSolicitudTramiteOperativoLegal(int codigo, String descripcion,EUsuario eUsuario){					
		List<EOperacionSolicitud> lista=oCGarantia.listarSolicitudTramiteOperativoLegal(codigo,descripcion,eUsuario);
		return lista;
	}
	
	public List<EGarantia> listarInmueblesGarantiaPredios(long codigoGarantia){					
		List<EGarantia> lista=oCGarantia.listarInmueblesGarantiaPredios(codigoGarantia);
		return lista;
	}
	
	public List<EOperacionSolicitud> listarDetalleFlagsSolicitudCredito(long codigoSolicitudCredito){					
		List<EOperacionSolicitud> lista=oCGarantia.listarDetalleFlagsSolicitudCredito(codigoSolicitudCredito);
		return lista;
	}
	
	public List<EFirmanteSolicitud> listarFirmantesDocumentoSolicitud(long codigoSolicitudCredito){					
		List<EFirmanteSolicitud> lista=oCGarantia.listarFirmantesDocumentoSolicitud(codigoSolicitudCredito);
		return lista;
	}
	public List<EGarantiaSolicitud> listarGarantiaAsociadaSolicitudFirmante(long codigoSolicitud){					
		List<EGarantiaSolicitud> lista=oCGarantia.listarGarantiaAsociadaSolicitudFirmante(codigoSolicitud);
		return lista;
	}
	public List<EComprobanteGarantia> listarComprobanteGarantiaSolicitud(long codigoSolicitud,int secuenciaGarantia){					
		List<EComprobanteGarantia> lista=oCGarantia.listarComprobanteGarantiaSolicitud(codigoSolicitud,secuenciaGarantia);
		return lista;
	}
	public List<ERepresentanteCIA> listarMaestroRepresentantesCompania(){					
		List<ERepresentanteCIA> lista=oCGarantia.listarMaestroRepresentantesCompania();
		return lista;
	}
	public List<ERepresentanteCIAContrato> listarRepresentantesCompaniaxContrato(int tipoContrato,long numeroContrato){					
		List<ERepresentanteCIAContrato> lista=oCGarantia.listarRepresentantesCompaniaxContrato(tipoContrato,numeroContrato);
		return lista;
	}
	public List<EFirmanteSolicitud> listarDetalleFirmantesContratoGarantia(EFirmanteSolicitud eFirmanteContrato){					
		List<EFirmanteSolicitud> lista=oCGarantia.listarDetalleFirmantesContratoGarantia(eFirmanteContrato);
		return lista;
	}
	public List<EOperacionSolicitud> listarObservacionSolicitudTramiteOperativoLegal(EOperacionSolicitud eOperacionSolicitud){					
		List<EOperacionSolicitud> lista=oCGarantia.listarObservacionSolicitudTramiteOperativoLegal(eOperacionSolicitud);
		return lista;
	}
	public List<EFlagReqLegal> listarDetalleFlagRequisitoLegal(long numeroSolicitud){					
		List<EFlagReqLegal> lista=oCGarantia.listarDetalleFlagRequisitoLegal(numeroSolicitud);
		return lista;
	}
	public EMensaje eliminarDetalleSolicitudDocumentoGarantia(EGarantia eGarantia){
		EMensaje resultado = oCGarantia.eliminarDetalleSolicitudDocumentoGarantia(eGarantia);
		return resultado;
	}
	public EMensaje eliminarInmuebleGarantiaPredios(EGarantia eGarantia){
		EMensaje resultado = oCGarantia.eliminarInmuebleGarantiaPredios(eGarantia);
		return resultado;
	}
	public EMensaje eliminarRepresentanteCompaniaContratoF7433(ERepresentanteCIAContrato eRepresentanteCiaContrato){
		EMensaje resultado = oCGarantia.eliminarRepresentanteCompaniaContratoF7433(eRepresentanteCiaContrato);
		return resultado;
	}
	public EMensaje eliminarDetalleFlagRequisitoLegal(EFlagReqLegal eFlagRequisitoLegal) {
		EMensaje resultado = oCGarantia.eliminarDetalleFlagRequisitoLegal(eFlagRequisitoLegal);
		return resultado;
	}
	public List<EAsignacionContratoGarantia> listarCreditosAsociadosGarantia(long codigoGarantia) {					
		List<EAsignacionContratoGarantia> lista=oCGarantia.listarCreditosAsociadosGarantia(codigoGarantia);
		return lista;
	}
	public EMensaje eliminarPoliza(EPoliza ePoliza) {
		EMensaje resultado=oCGarantia.eliminarPoliza(ePoliza);
		return resultado;
	}
	public List<EGarantiaTramite> listarHistoricoTramiteGarantia(long codigoGarantia){					
		List<EGarantiaTramite> lista=oCGarantia.listarHistoricoTramiteGarantia(codigoGarantia);
		return lista;
	}
	public List<EGarantiaTramite> listarAsientosTramiteGarantia(long codigoGarantia){					
		List<EGarantiaTramite> lista=oCGarantia.listarAsientosTramiteGarantia(codigoGarantia);
		return lista;
	}
	public List<EGarantiaSolicitud> listarGarantiaPorConstituir(int codigo, String descripcion){					
		List<EGarantiaSolicitud> lista=oCGarantia.listarGarantiaPorConstituir(codigo, descripcion);
		return lista;
	}
	public List<EGarantiaSolicitud> listarHistoricoGarantiaSolicitud(int codigo, String descripcion){					
		List<EGarantiaSolicitud> lista=oCGarantia.listarHistoricoGarantiaSolicitud(codigo, descripcion);
		return lista;
	}
	
	public List<EGarantiaSolicitud> listarSolicitudAsociadaGarantia(int codigo, String descripcion){					
		List<EGarantiaSolicitud> lista=oCGarantia.listarSolicitudAsociadaGarantia(codigo, descripcion);
		return lista;
	}
	public List<EGarantia> listarGarantiaVigente(int codigo, String descripcion){					
		List<EGarantia> lista=oCGarantia.listarGarantiaVigente(codigo, descripcion);
		return lista;
	}
	public List<EGarantia> listarGarantia(int codigo, String descripcion){					
		List<EGarantia> lista=oCGarantia.listarGarantia(codigo, descripcion);
		return lista;
	}
	public List<EGarantiaSolicitud> listarGarantiaSolicitud(long numeroSolicitud){					
		List<EGarantiaSolicitud> lista=oCGarantia.listarGarantiaSolicitud(numeroSolicitud);
		return lista;
	}
	public List<EPoliza> listarPolizaSeguro(int codigo, String descripcion,String descripcion2){					
		List<EPoliza> lista=oCGarantia.listarPolizaSeguro(codigo, descripcion,descripcion2);
		return lista;
	}
	public List<EPoliza> buscarPolizaSeguro(EPoliza ePoliza){					
		List<EPoliza> lista=oCGarantia.buscarPolizaSeguro(ePoliza);
		return lista;
	}
	public EPoliza buscarPoliza(EPoliza ePoliza){					
		EPoliza lista=oCGarantia.buscarPoliza(ePoliza);
		return lista;
	}
	public  EGarantia buscarUltimaGarantiaGenerada(){					
		EGarantia lista=oCGarantia.buscarUltimaGarantiaGenerada();
		return lista;
	}
	public  EGarantiaSolicitud buscarSolicitudxGarantia(long codigoGarantia){					
		EGarantiaSolicitud lista=oCGarantia.buscarSolicitudxGarantia( codigoGarantia);
		return lista;
	}
	public  EGarantia buscarAnexoGarantia(long codigoGarantia){					
		EGarantia lista=oCGarantia.buscarAnexoGarantia(codigoGarantia);
		return lista;
	}
	public  EGarantiaTramite buscarUltimoAsientoGarantiaTramite(long codigoGarantia){					
		EGarantiaTramite lista=oCGarantia.buscarUltimoAsientoGarantiaTramite(codigoGarantia);
		return lista;
	}
	public  EGarantia buscarCaracteristicaInmueblePredio(int nroSolicitud){					
		EGarantia lista=oCGarantia.buscarCaracteristicaInmueblePredio(nroSolicitud);
		return lista;
	}
	public  EOperacionSolicitud buscarInstruccionAprobacionOperacionesF7320(long codigoSolicitud,int nroRevision){					
		EOperacionSolicitud lista=oCGarantia.buscarInstruccionAprobacionOperacionesF7320(codigoSolicitud,nroRevision);
		return lista;
	}
	public  EOperacionSolicitud buscarUltimaRevisionSolicitudCredito(long codigoSolicitud){					
		EOperacionSolicitud lista=oCGarantia.buscarUltimaRevisionSolicitudCredito(codigoSolicitud);
		return lista;
	}
	public  EOperacionSolicitud buscarRegistroSolicitudCreditoF7301(long codigoSolicitud){					
		EOperacionSolicitud lista=oCGarantia.buscarRegistroSolicitudCreditoF7301(codigoSolicitud);
		return lista;
	}
	public EContrato buscarMaestroContratoF7401(long numeroContrato){					
		EContrato lista=oCGarantia.buscarMaestroContratoF7401(numeroContrato);
		return lista;
	}
	public EDocumentoGenerado buscarMaestroDocumentoGeneradoF7420(EDocumentoGenerado eDocumentoGenerado){					
		EDocumentoGenerado lista=oCGarantia.buscarMaestroDocumentoGeneradoF7420(eDocumentoGenerado);
		return lista;
	}
	public EPoliza buscarPolizaAsociadoPrestamoMaxCorrelativo(long numeroOperacion){					
		EPoliza lista=oCGarantia.buscarPolizaAsociadoPrestamoMaxCorrelativo(numeroOperacion);
		return lista;
	}
	public List<ESaldoServicio> obtenerSaldosServiciosCliente(int codigoCliente){
		List<ESaldoServicio> lista=oCGarantia.obtenerSaldosServiciosCliente(codigoCliente);
		return lista;
	}
	public List<EGarantiaDocumentoSolicitado> listarDocumentoSolicitado(long numeroSolicitud){
		List<EGarantiaDocumentoSolicitado> lista=oCGarantia.listarDocumentoSolicitado(numeroSolicitud);
		return lista;
	}
	public List<EGarantiaCreditoRelacionado> listarCreditoVigenteRelacionado(long codigo){
		List<EGarantiaCreditoRelacionado> lista=oCGarantia.listarCreditoVigenteRelacionado(codigo);
		return lista;
	}
	public List<EGarantiaCreditoRelacionado> listarCreditoCanceladoRelacionado(long codigo){
		List<EGarantiaCreditoRelacionado> lista=oCGarantia.listarCreditoCanceladoRelacionado(codigo);
		return lista;
	}
	public EGarantia buscarGarantia(long codigoGarantia){
		EGarantia resultado=oCGarantia.buscarGarantia(codigoGarantia);
		return resultado;
	}
	public ETipoCambio buscarTipoCambioDiario(){
		ETipoCambio resultado=oCGarantia.buscarTipoCambioDiario();
		return resultado;
	}
	public EGarantiaTramite buscarGarantiaTramite(long codigoGarantia){
		EGarantiaTramite resultado=oCGarantia.buscarGarantiaTramite(codigoGarantia);
		return resultado;
	}
	public EGarantia buscarGarantiaSolicitudCompleto(long numeroSolicitud,int secuenciaGarantia){
		EGarantia resultado=oCGarantia.buscarGarantiaSolicitudCompleto(numeroSolicitud,secuenciaGarantia);
		return resultado;
	}
	public EGarantiaSolicitud buscarGarantiaSolicitud(long numeroSolicitud, int secuenciaGarantia){
		EGarantiaSolicitud resultado=oCGarantia.buscarGarantiaSolicitud(numeroSolicitud, secuenciaGarantia);
		return resultado;
	}
	public EGarantiaSolicitud buscarGarantiaAsociadaSolicitud(long numeroSolicitud, int secuenciaGarantia){
		EGarantiaSolicitud resultado=oCGarantia.buscarGarantiaAsociadaSolicitud(numeroSolicitud, secuenciaGarantia);
		return resultado;
	}
	public EGarantiaDetalleSolicitud buscarGarantiaDetalleSolicitud(long numeroSolicitud, int secuenciaGarantia){					
		EGarantiaDetalleSolicitud resultado=oCGarantia.buscarGarantiaDetalleSolicitud(numeroSolicitud, secuenciaGarantia);
		return resultado;
	}
}
