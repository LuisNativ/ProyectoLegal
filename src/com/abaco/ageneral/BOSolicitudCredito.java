package com.abaco.ageneral;
import java.util.List;

import com.abaco.ageneral.CSolicitudCredito;
import com.abaco.entidad.EMensaje;
import com.abaco.ageneral.ESolicitudCredito;

public class BOSolicitudCredito {

	CSolicitudCredito oCSolicitudCredito= new CSolicitudCredito();
	
	public List<ESolicitudLogMovimiento> listarLogMovimiento(long numeroSolicitud){
		List<ESolicitudLogMovimiento> lista=oCSolicitudCredito.listarLogMovimiento(numeroSolicitud);
		return lista;
	}
	public List<EDeudor> listarDeudor(int codigoCliente, long numeroSolicitud){
		List<EDeudor> lista=oCSolicitudCredito.listarDeudor(codigoCliente, numeroSolicitud);
		return lista;
	}
	public List<EAval> listarAval(int codigoCliente,long numeroSolicitud){
		List<EAval> lista=oCSolicitudCredito.listarAval(codigoCliente,numeroSolicitud);
		return lista;
	}
	public ETercero buscarAval(int codigoAval,long nroSolicitud){
		ETercero resultado=oCSolicitudCredito.buscarAval(codigoAval,nroSolicitud);
		return resultado;
	}
	public EEmail buscarEmailCliente(int codigoCliente){
		EEmail resultado=oCSolicitudCredito.buscarEmailCliente(codigoCliente);
		return resultado;
	}
	public ESolicitudCredito buscarSolicitudCredito(long numeroSolicitud){
		ESolicitudCredito resultado=oCSolicitudCredito.buscarSolicitudCredito(numeroSolicitud);
		return resultado;
	}
	public ESolicitudCredito buscarSolicitudCartaFianza(long numeroSolicitud){
		ESolicitudCredito resultado=oCSolicitudCredito.buscarSolicitudCartaFianza(numeroSolicitud);
		return resultado;
	}
	public List<ESolicitudCredito> listarSolicitudCredito(int codigoCliente){
		List<ESolicitudCredito> lista=oCSolicitudCredito.listarSolicitudCredito(codigoCliente);
		return lista;
	}
	public List<EEvaluacionSolicitudCreditoLegal> listarEvaluacionSolicitudCreditoLegal(int codigoCliente, String documento){
		List<EEvaluacionSolicitudCreditoLegal> lista=oCSolicitudCredito.listarEvaluacionSolicitudCreditoLegal(codigoCliente, documento);
		return lista;
	}
	public List<EObservacionNegocios> listarObservacionNegociosDetalle(long numeroSolicitud){
		List<EObservacionNegocios> lista=oCSolicitudCredito.listarObservacionNegociosDetalle(numeroSolicitud);
		return lista;
	}
	public List<ESuscripcion> listarSolicitudSuscripcion(long numeroSolicitud, int codigoCliente, int codigoTipoCliente){
		List<ESuscripcion> lista=oCSolicitudCredito.listarSolicitudSuscripcion(numeroSolicitud, codigoCliente, codigoTipoCliente);
		return lista;
	}
	public List<ESuscripcion> listarClienteSuscripcion(String numeroDocumento){
		List<ESuscripcion> lista=oCSolicitudCredito.listarClienteSuscripcion(numeroDocumento);
		return lista;
	}
	public EInformeLegalAdicional buscarInformeLegalAdicional(long numeroSolicitud, int codigoCliente, int codigoTipoCliente){
		EInformeLegalAdicional resultado=oCSolicitudCredito.buscarInformeLegalAdicional(numeroSolicitud, codigoCliente, codigoTipoCliente);
		return resultado;
	}
	public EDeudorAdicional buscarDeudorAdicional(int codigoDeudor){
		EDeudorAdicional resultado=oCSolicitudCredito.buscarDeudorAdicional(codigoDeudor);
		return resultado;
	}
	public EDeudorEstado buscarDeudorEstado(int codigoDeudor){
		EDeudorEstado resultado=oCSolicitudCredito.buscarDeudorEstado(codigoDeudor);
		return resultado;
	}
	public String buscarObservacionDetalle(long numeroSolicitud, int ubicacion, int secuencia){
		String resultado=oCSolicitudCredito.buscarObservacionDetalle(numeroSolicitud, ubicacion, secuencia);
		return resultado;
	}
	public String buscarObservacionNegocios(long numeroSolicitud, int secuencia){
		String resultado=oCSolicitudCredito.buscarObservacionNegocios(numeroSolicitud, secuencia);
		return resultado;
	}
	public ECredito buscarCreditoPrestamo(int codigoServicio, long numeroOperacion) {
		ECredito resultado=oCSolicitudCredito.buscarCreditoPrestamo(codigoServicio, numeroOperacion);
		return resultado;
	}
	public ECredito buscarCreditoAbamoshi(int codigoServicio, long numeroOperacion, int numeroGrupo) {
		ECredito resultado=oCSolicitudCredito.buscarCreditoAbamoshi(codigoServicio, numeroOperacion, numeroGrupo);
		return resultado;
	}
	public ECredito buscarCreditoCartaFianza(int codigoServicio, int codigoMoneda, long numeroOperacion){
		ECredito resultado=oCSolicitudCredito.buscarCreditoCartaFianza(codigoServicio, codigoMoneda, numeroOperacion);
		return resultado;
	}	
}
