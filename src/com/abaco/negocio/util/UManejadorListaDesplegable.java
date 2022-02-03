package com.abaco.negocio.util;

import java.util.List;

import com.abaco.entidad.EGeneral;
import com.abaco.entidad.EUsuario;
import com.abaco.negocio.util.UConstante.UF0901;
import com.abaco.negocio.util.UConstante.UGeneral;
import com.abaco.negocio.util.UConstante.UData;
import com.abaco.negocio.util.UConstante.UGeneral2;
import com.abaco.persistencia.control.CListaDesplegable;

public class UManejadorListaDesplegable {
	
	public List<EGeneral> obtieneListaDesplegableData(int codigo, int indicador) {
		CListaDesplegable objCListaDesplegable = new CListaDesplegable();
		return objCListaDesplegable.obtieneListaDesplegableData(codigo, indicador);
	}
	
	public List<EGeneral> obtieneListaDesplegableGeneral(int codigo, int indicador) {
		CListaDesplegable objCListaDesplegable = new CListaDesplegable();
		return objCListaDesplegable.obtieneListaDesplegableGeneral(codigo, indicador);
	}
	
	public List<EGeneral> obtieneListaDesplegableF0901(int codigo, int indicador) {
		CListaDesplegable objCListaDesplegable = new CListaDesplegable();
		return objCListaDesplegable.obtieneListaDesplegableF0901(codigo, indicador);
	}
	
	public List<EGeneral> obtieneTipoDocumento() {
		return obtieneListaDesplegableF0901(UF0901.TIPODOCUMENTO, 1);
	}
	
	public List<EGeneral> obtieneOtorgamiento() {
		return obtieneListaDesplegableF0901(UF0901.OTORGAMIENTO, 2);
	}
	
	public List<EGeneral> obtieneTipoRelacionEntreCliente() {
		return obtieneListaDesplegableF0901(UF0901.TIPORELACIONENTRECLIENTE, 2);
	}
	
	public List<EGeneral> obtieneTipoSexo() {
		return obtieneListaDesplegableF0901(UF0901.TIPOSEXO, 1);
	}
	
	public List<EGeneral> obtieneTipoPersona() {
		return obtieneListaDesplegableF0901(UF0901.TIPOPERSONA, 1);
	}
	
	public List<EGeneral> obtieneEstadoCivil() {
		return obtieneListaDesplegableF0901(UF0901.ESTADOCIVIL, 1);
	}
	
	
	public List<EGeneral> obtienePais() {
		return obtieneListaDesplegableF0901(UF0901.PAIS, 2);
	}
	
	public List<EGeneral> obtieneNivelInstruccion() {
		return obtieneListaDesplegableF0901(UF0901.NIVELINSTRUCCION, 2);
	}
	
	public List<EGeneral> obtieneProfesion() {
		return obtieneListaDesplegableF0901(UF0901.PROFESION, 2);
	}
	
	public List<EGeneral> obtieneTipoPago() {
		return obtieneListaDesplegableF0901(UF0901.TIPOPAGO, 2);
	}
	
	public List<EGeneral> obtieneFormaPago() {
		return obtieneListaDesplegableF0901(UF0901.FORMAPAGO, 2);
	}
	
	public List<EGeneral> obtieneOcupacion() {
		return obtieneListaDesplegableF0901(UF0901.OCUPACION, 1);
	}
	
	public List<EGeneral> obtieneTipoVivienda() {
		return obtieneListaDesplegableF0901(UF0901.TIPOVIVIENDA, 1);
	}
	
	public List<EGeneral> obtieneTipoDireccion() {
		return obtieneListaDesplegableF0901(UF0901.TIPODIRECCION, 1);
	}
	
	public List<EGeneral> obtieneCargoLaboral() {
		return obtieneListaDesplegableF0901(UF0901.CARGOLABORAL, 2);
	}
	
	public List<EGeneral> obtieneFrecuencia() {
		return obtieneListaDesplegableF0901(UF0901.FRECUENCIA, 1);
	}
	
	public List<EGeneral> obtieneSubUnidad() {
		return obtieneListaDesplegableF0901(UF0901.SUBUNIDAD, 1);
	}
	
	public List<EGeneral> obtieneTipoPatrimonio() {
		return obtieneListaDesplegableF0901(UF0901.TIPOPATRIMONIO, 1);
	}
	
	public List<EGeneral> obtieneInstitucion() {
		return obtieneListaDesplegableF0901(UF0901.INSTITUCION, 1);
	}
	
	public List<EGeneral> obtieneTipoCuentaBanco() {
		return obtieneListaDesplegableF0901(UF0901.TIPOCUENTABANCO, 1);
	}
	
	public List<EGeneral> obtieneGravedad() {
		return obtieneListaDesplegableF0901(UF0901.GRAVEDAD, 1);
	}
	
	public List<EGeneral> obtieneNaturalezaJuridica() {
		return obtieneListaDesplegableF0901(UF0901.NATURALEZAJURIDICA, 1);
	}
	
	public List<EGeneral> obtieneTamanioEmpresa() {
		return obtieneListaDesplegableF0901(UF0901.TAMANIOEMPRESA, 1);
	}
	
	public List<EGeneral> obtieneDivision() {
		return obtieneListaDesplegableF0901(UF0901.DIVISION, 1);
	}
	
	public List<EGeneral> obtieneProveedor() {
		return obtieneListaDesplegableF0901(UF0901.PROVEEDOR, 2);
	}
	
	public List<EGeneral> obtieneAceptante() {
		return obtieneListaDesplegableF0901(UF0901.ACEPTANTE, 2);
	}
	
	public List<EGeneral> obtieneAportacion() {
		return obtieneListaDesplegableF0901(UF0901.APORTACION, 1);
	}
	
	public List<EGeneral> obtieneComunidadOrigen() {
		return obtieneListaDesplegableF0901(UF0901.COMUNIDADORIGEN, 1);
	}
	
	public List<EGeneral> obtieneTipoRelacion() {
		return obtieneListaDesplegableF0901(UF0901.TIPORELACION, 1);
	}
	
	public List<EGeneral> obtieneFacultadOperar() {
		return obtieneListaDesplegableF0901(UF0901.FACULTADOPERAR, 2);
	}
	
	public List<EGeneral> obtieneSuscripcionPago() {
		return obtieneListaDesplegableF0901(UF0901.SUSCRIPCIONPAGO, 1);
	}
	
	public List<EGeneral> obtieneTipoGarantia() {
		return obtieneListaDesplegableF0901(UF0901.TIPOGARANTIA, 2);
	}
	
	public List<EGeneral> obtieneTipoPrendaGarantia() {
		return obtieneListaDesplegableF0901(UF0901.TIPOPRENDAGARANTIA, 2);
	}
	
	public List<EGeneral> obtieneTipoMoneda() {
		return obtieneListaDesplegableF0901(UF0901.TIPOMONEDA, 2);
	}
	
	public List<EGeneral> obtieneTipoCiaSeguro() {
		return obtieneListaDesplegableF0901(UF0901.TIPOCIASEGURO, 1);
	}
	
	public List<EGeneral> obtieneTipoFiador() {
		return obtieneListaDesplegableF0901(UF0901.TIPOFIADOR, 2);
	}
	
	public List<EGeneral> obtieneTipoFianza() {
		return obtieneListaDesplegableF0901(UF0901.TIPOFIANZA, 2);
	}
	
	public List<EGeneral> obtieneTipoPlazo() {
		return obtieneListaDesplegableF0901(UF0901.TIPOPLAZO, 2);
	}
	
	public List<EGeneral> obtieneEstadoGarantia() {
		return obtieneListaDesplegableF0901(UF0901.ESTADOGARANTIA, 2);
	}
	
	public List<EGeneral> obtieneSituacionGarantia() {
		return obtieneListaDesplegableF0901(UF0901.SITUACIONGARANTIA, 2);
	}
	
	public List<EGeneral> obtieneEstadoLegal() {
		return obtieneListaDesplegableF0901(UF0901.ESTADOLEGAL, 1);
	}
	
	public List<EGeneral> obtieneClaseVehiculo() {
		return obtieneListaDesplegableF0901(UF0901.CLASEVEHICULO, 2);
	}
	
	public List<EGeneral> obtieneMarcaVehiculo() {
		return obtieneListaDesplegableF0901(UF0901.MARCAVEHICULO, 2);
	}
	
	public List<EGeneral> obtieneModeloVehiculo() {
		return obtieneListaDesplegableF0901(UF0901.MODELOVEHICULO, 2);
	}
	
	public List<EGeneral> obtieneCombustibleVehiculo() {
		return obtieneListaDesplegableF0901(UF0901.COMBUSTIBLEVEHICULO, 1);
	}
	
	public List<EGeneral> obtieneRiesgoVehiculo() {
		return obtieneListaDesplegableF0901(UF0901.RIESGOVEHICULO, 1);
	}
	
	public List<EGeneral> obtieneValorSiNo() {
		return obtieneListaDesplegableF0901(UF0901.VALOR_SI_NO, 2);
	}
	
	public List<EGeneral> obtieneTipoContrato() {
		return obtieneListaDesplegableF0901(UF0901.TIPOCONTRATO, 1);
	}
	
	public List<EGeneral> obtieneTipoAplicacion() {
		return obtieneListaDesplegableF0901(UF0901.TIPOAPLICACION, 2);
	}
	
	public List<EGeneral> obtieneTipoPeriodo() {
		return obtieneListaDesplegableF0901(UF0901.TIPOPERIODO, 2);
	}
	
	public List<EGeneral> obtieneTipoAlmacen() {
		return obtieneListaDesplegableF0901(UF0901.TIPOALMACEN, 2);
	}
	
	public List<EGeneral> obtieneTipoMercaderiaWarrant() {
		return obtieneListaDesplegableF0901(UF0901.TIPOMERCADERIAWARRANT, 2);
	}
	
	public List<EGeneral> obtieneUnidad() {
		return obtieneListaDesplegableF0901(UF0901.UNIDAD, 1);
	}
	
	public List<EGeneral> obtieneBrockerSeguros() {
		return obtieneListaDesplegableF0901(UF0901.BROCKERSEGURO, 2);
	}
	
	public List<EGeneral> obtieneTipoPoliza() {
		return obtieneListaDesplegableF0901(UF0901.TIPOPOLIZA, 2);
	}
	
	public List<EGeneral> obtieneTipoRegistral() {
		return obtieneListaDesplegableF0901(UF0901.TIPOREGISTRAL, 2);
	}
	
	public List<EGeneral> obtieneEstadoDeudor() {
		return obtieneListaDesplegableGeneral(UGeneral.ESTADODEUDOR, 1);
	}
	
	public List<EGeneral> obtieneNivel() {
		return obtieneListaDesplegableGeneral(UGeneral.NIVEL, 2);
	}
	
	public List<EGeneral> obtieneMotivo() {
		return obtieneListaDesplegableGeneral(UGeneral.MOTIVO, 2);
	}
	
	public List<EGeneral> obtieneTipoServicio() {
		return obtieneListaDesplegableGeneral(UGeneral.SERVICIO, 2);
	}
	
	public List<EGeneral> obtieneTipoEnvio() {
		return obtieneListaDesplegableGeneral(UGeneral.ENVIO, 2);
	}
	
	public List<EGeneral> obtieneTipoPersonaRelacion() {
		return obtieneListaDesplegableGeneral(UGeneral.PERSONARELACION, 2);
	}
	/*
	public List<EGeneral> obtieneOficinaRegistral() {
		return obtieneListaDesplegableGeneral(UGeneral.OFICINAREGITRAL, 2);
	}*/
	/*
	public List<EGeneral> obtieneTipoRegistral() {
		return obtieneListaDesplegableGeneral(UGeneral.TIPOREGISTRAL, 2);
	}*/
	
	public List<EGeneral> obtieneTipoAutonomia() {
		return obtieneListaDesplegableGeneral(UGeneral.AUTONOMIA, 2);
	}
	
	public List<EGeneral> obtieneTipoDocumentoGeneral() {
		return obtieneListaDesplegableGeneral(UGeneral.TIPODOCUMENTO, 1);
	}
	
	public List<EGeneral> obtieneAutorizaJefe() {
		return obtieneListaDesplegableGeneral(UGeneral.AUTORIZAJEFE, 2);
	}
	
	public List<EGeneral> obtieneTipoDuracionPartida() {
		return obtieneListaDesplegableGeneral(UGeneral.TIPODURACIONPARTIDA, 2);
	}
	
	public List<EGeneral> obtieneTipoNumeracionEstatuto() {
		return obtieneListaDesplegableGeneral(UGeneral.TIPONUMERACIONESTATUTO, 2);
	}
	public List<EGeneral> obtieneEstadoOperacionCliente() {
		return obtieneListaDesplegableGeneral(UGeneral.ESTADOOPERACIONCLIENTE, 2);
	}
	public List<EGeneral> obtieneTipoDocumentoGarantia() {
		return obtieneListaDesplegableGeneral(UGeneral.TIPODOCUMENTOGARANTIA, 2);
	}
	
	public List<EGeneral> obtieneZonaGeograficaF0713() {
		return obtieneListaDesplegableData(UData.ZONAGEO, 2);
	}
	
	public List<EGeneral> obtieneEstado() {
		return obtieneListaDesplegableData(UData.ESTADO, 2);
	}
	
	public List<EGeneral> obtieneTipoGarantiaF9205() {
		return obtieneListaDesplegableData(UData.TIPOGARANTIA, 2);
	}
	public List<EGeneral> obtieneTipoGarantiaNuevoF9205() {
		return obtieneListaDesplegableData(UData.TIPOGARANTIANUEVO, 2);
	}
	
	public List<EGeneral> obtieneOficinaRegistral() {
		return obtieneListaDesplegableData(UData.OFICINAREGISTRAL, 2);
	}
	public List<EGeneral> obtieneArea() {
		return obtieneListaDesplegableData(UData.AREA, 2);
	}
	
	public List<EGeneral> obtieneAreaEmisor() {
		return obtieneListaDesplegableData(UData.AREAEMISOR, 2);
	}
	
	public List<EGeneral> obtieneAreaReceptor() {
		return obtieneListaDesplegableData(UData.AREARECEPTOR, 2);
	}
	
	public List<EGeneral> obtieneTipoPermiso() {
		return obtieneListaDesplegableData(UData.PERMISO, 2);
	}
	/*
	public List<EGeneral> obtieneUsuarioReceptor() {
		return obtieneListaDesplegableData(UData.USUARIORECEPTOR, 2);
	}
	*/
	public List<EGeneral> obtieneFacultad() {
		return obtieneListaDesplegableData(UData.FACULTAD, 2);
	}
	
	public List<EGeneral> obtieneTercero() {
		return obtieneListaDesplegableData(UData.TERCERO, 2);
	}
	
	public List<EGeneral> obtieneCIUU() {
		return obtieneListaDesplegableData(UData.CIUU, 1);
	}
	
	public List<EGeneral> obtieneTipoPersonaJuridica() {
		return obtieneListaDesplegableData(UData.TIPOPERSONAJURIDICA, 1);
	}
	
	public List<EGeneral> obtieneTipoEvaluacion() {
		return obtieneListaDesplegableData(UData.TIPOEVALUACION, 2);
	}
	
	public List<EGeneral> obtieneTipoBanco() {
		return obtieneListaDesplegableData(UData.TIPOBANCO, 2);
	}
	
	public List<EGeneral> obtieneEstadoOperacion() {
		return obtieneListaDesplegableData(UData.ESTADOOPERACION, 2);
	}
	
	public List<EGeneral> obtieneEstadoGarantiaSolicitud() {
		return obtieneListaDesplegableData(UData.ESTADOSOLICITUDGARANTIA, 2);
	}
	
	public List<EGeneral> obtieneEstadoLevantamientoGarantia() {
		return obtieneListaDesplegableData(UData.ESTADOLEVANTAMIENTO, 2);
	}
	
	public List<EGeneral> obtieneUsuario(int indicadorEstado){
		CListaDesplegable objCListaDesplegable = new CListaDesplegable();
		return objCListaDesplegable.obtieneUsuario(indicadorEstado);
	}
	
	public List<EGeneral> obtieneUsuarioPorArea(int codigoArea, int indicadorEstado){
		CListaDesplegable objCListaDesplegable = new CListaDesplegable();
		return objCListaDesplegable.obtieneUsuarioPorArea(codigoArea, indicadorEstado);
	}
	
	public List<EGeneral> obtieneUsuarioPorAreaTipoEnvio(int codigoArea){
		CListaDesplegable objCListaDesplegable = new CListaDesplegable();
		return objCListaDesplegable.obtieneUsuarioPorAreaTipoEnvio(codigoArea);
	}
	
	public List<EGeneral> obtieneUsuarioPorAreaReasignacion(EUsuario eUsuario){
		CListaDesplegable objCListaDesplegable = new CListaDesplegable();
		return objCListaDesplegable.obtieneUsuarioPorAreaReasignacion(eUsuario);
	}
	
	public List<EGeneral> obtieneUsuarioPorPermisoUsuario(int indicadorEstado){
		CListaDesplegable objCListaDesplegable = new CListaDesplegable();
		return objCListaDesplegable.obtieneUsuarioPorPermisoUsuario(indicadorEstado);
	}
	
	public List<EGeneral> obtieneTipoEnvio(int accionExterna, long codigoUsuario){
		CListaDesplegable objCListaDesplegable = new CListaDesplegable();
		return objCListaDesplegable.obtieneTipoEnvio(accionExterna, codigoUsuario);
	}
	
	public List<EGeneral> obtieneCtaAbacoBanco(int idMoneda) {
		CListaDesplegable objCListaDesplegable = new CListaDesplegable();
		return objCListaDesplegable.obtieneCtaAbacoBanco(idMoneda);
	}
	
	public List<EGeneral> obtieneDepartamento() {
		CListaDesplegable objCListaDesplegable = new CListaDesplegable();
		return objCListaDesplegable.obtieneDepartamento();
	}
	
	public List<EGeneral> obtieneProvincia(int idDepartamento) {
		CListaDesplegable objCListaDesplegable = new CListaDesplegable();
		return objCListaDesplegable.obtieneProvincia(idDepartamento);
	}
	
	public List<EGeneral> obtieneDistrito(int idDepartamento, int idProvincia) {
		CListaDesplegable objCListaDesplegable = new CListaDesplegable();
		return objCListaDesplegable.obtieneDistrito(idDepartamento, idProvincia);
	}
	
	public List<EGeneral> obtieneActividad(int codigoCiu, String desCiu) {
		CListaDesplegable objCListaDesplegable = new CListaDesplegable();
		return objCListaDesplegable.obtieneActividad(codigoCiu, desCiu);
	}
	
	public List<EGeneral> obtieneTipoDocumentoFichaSocio() {	
		CListaDesplegable objCListaDesplegable = new CListaDesplegable();
		return objCListaDesplegable.obtieneTipoDocumentoFichaSocio();
	}
	
	public List<EGeneral> obtienePersonalAutorizar(int codRol) {
		CListaDesplegable objCListaDesplegable = new CListaDesplegable();
		return objCListaDesplegable.obtienePersonalAutorizar(codRol);
	}
	
	public List<EGeneral> obtieneGrupoEconomico() {
		CListaDesplegable objCListaDesplegable = new CListaDesplegable();
		return objCListaDesplegable.obtieneGrupoEconomico();
	}
	
	public List<EGeneral> obtieneGrupoFamiliar() {
		CListaDesplegable objCListaDesplegable = new CListaDesplegable();
		return objCListaDesplegable.obtieneGrupoFamiliar();
	}
	
	public List<EGeneral> obtieneZonaGeografica() {
		CListaDesplegable objCListaDesplegable = new CListaDesplegable();
		return objCListaDesplegable.obtieneZonaGeografica();
	}
	
	public List<EGeneral> obtieneEmailGerencia(int codRol) {
		CListaDesplegable objCListaDesplegable = new CListaDesplegable();
		return objCListaDesplegable.obtieneEmailGerencia(codRol);
	}
	
	public List<EGeneral> obtieneUnidad(String idDivision) {
		CListaDesplegable objCListaDesplegable = new CListaDesplegable();
		return objCListaDesplegable.obtieneUnidad(idDivision);
	}
	
	public List<EGeneral> obtieneSubUnidad(String idDivision, String idUnidad) {
		CListaDesplegable objCListaDesplegable = new CListaDesplegable();
		return objCListaDesplegable.obtieneSubUnidad(idDivision, idUnidad);
	}
	
	public List<EGeneral> obtieneCentroTrabajo(int codigoCentra, String desCentra) {
		CListaDesplegable objCListaDesplegable = new CListaDesplegable();
		return objCListaDesplegable.obtieneCentroTrabajo(codigoCentra, desCentra);
	}
	
	public List<EGeneral> obtieneFuncionario() {
		CListaDesplegable objCListaDesplegable = new CListaDesplegable();
		return objCListaDesplegable.obtieneFuncionario();
	}
	
	public List<EGeneral> obtieneFuenteLoteProspecto() {
		CListaDesplegable objCListaDesplegable = new CListaDesplegable();
		return objCListaDesplegable.obtieneFuenteLoteProspecto();
	}
	
	/*
	public List<EGeneral> obtieneMoneda() {
		return obtieneListaGeneral(ENListaDesplegable.Moneda); //AHM MANUAL
	}
	
	public List<EGeneral> obtieneRutaFormato() {
		CListaDesplegable objCListaDesplegable = new CListaDesplegable();
		return objCListaDesplegable.obtieneListaGeneral(UGeneral2.RUTAFORMATO);
		return obtieneListaGeneral(ENListaDesplegable.RutaFormato);   //AHM MANUAL
	}
	
	public List<EGeneral> obtieneEmailControl() {
		return obtieneListaGeneral(ENListaDesplegable.EmailControl); //AHM MANUAL
	}
	
	public List<EGeneral> obtieneTamanioEmpresaxVentas() {
		return obtieneListaGeneral(ENListaDesplegable.TamanioEmpresaxVenta); //AHM MANUAL
	}
	
	public List<EGeneral> obtieneTipoContribuyente() {
		return obtieneListaGeneral(ENListaDesplegable.TipoContribuyente); //AHM MANUAL
	}
	*/
}
