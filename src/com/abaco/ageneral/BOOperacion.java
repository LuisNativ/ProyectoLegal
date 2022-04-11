package com.abaco.ageneral;
import java.util.Date;
import java.util.List;

import com.abaco.entidad.EMensaje;
import com.abaco.entidad.EUsuario;
import com.abaco.ageneral.ESolicitudCredito;
import com.abaco.ageneral.ERevisionSolicitud;

public class BOOperacion {

	COperacion oCOperacion= new COperacion();
	
	public EMensaje agregarDocumentoGarantia(EGarantia eGarantia,List<EDocumentoCarga> lstDocumentoCarga){
		EMensaje resultado=oCOperacion.agregarDocumentoGarantia(eGarantia,lstDocumentoCarga);
		return resultado;
	}
	public EMensaje modificarDocumentoGarantia(EGarantia eOGarantia){
		EMensaje mensaje = oCOperacion.modificarDocumentoGarantia(eOGarantia);
		return mensaje;
	}
	public EMensaje modificarFirmaDocumentoGarantia(EOperacionDocumento eOperacionDocumento){
		EMensaje mensaje = oCOperacion.modificarFirmaDocumentoGarantia(eOperacionDocumento);
		return mensaje;
	}
	public List<EOperacionDocumento> listarDocumentoGarantia(EGarantia eOGarantia, int indicador){
		List<EOperacionDocumento> lista=oCOperacion.listarDocumentoGarantia(eOGarantia,indicador);
		return lista;
	}
	public EMensaje agregarEvaluacionLevantamientoGarantia(EOperacionLevantamientoGarantia eOperacionLevantamientoGarantia){
		EMensaje mensaje = oCOperacion.agregarEvaluacionLevantamientoGarantia(eOperacionLevantamientoGarantia);
		return mensaje;
	}
	public EMensaje modificarEvaluacionLevantamientoGarantia(EOperacionLevantamientoGarantia eOperacionLevantamientoGarantia, boolean indicadorLiberarGarantia){
		EMensaje mensaje = oCOperacion.modificarEvaluacionLevantamientoGarantia(eOperacionLevantamientoGarantia, indicadorLiberarGarantia);
		return mensaje;
	}
	public List<EOperacionLevantamientoGarantiaMensaje> listarMensajeLevantamientoGarantia(int codigoServicio, long codigoGarantia, int codigoMoneda){					
		List<EOperacionLevantamientoGarantiaMensaje> lista=oCOperacion.listarMensajeLevantamientoGarantia(codigoServicio, codigoGarantia, codigoMoneda);
		return lista;
	}
	public List<EGarantiaSolicitud> listarEvaluacionLevantamientoGarantia(int codigo, String descripcion, EUsuario eUsuario, int indicadorConsulta){					
		List<EGarantiaSolicitud> lista=oCOperacion.listarEvaluacionLevantamientoGarantia(codigo, descripcion, eUsuario, indicadorConsulta);
		return lista;
	}
	public EMensaje agregarModificarEvaluacionCliente(EOperacionCliente eOperacionCliente){
		EMensaje mensaje = oCOperacion.agregarModificarEvaluacionCliente(eOperacionCliente);
		return mensaje;
	}
	public EMensaje modificarEvaluacionCliente(EOperacionCliente eOperacionCliente){
		EMensaje mensaje = oCOperacion.modificarEvaluacionCliente(eOperacionCliente);
		return mensaje;
	}
	public List<EOperacionCliente> listarEvaluacionCliente(int codigo, String descripcion, EUsuario eUsuario){
		List<EOperacionCliente> lista=oCOperacion.listarEvaluacionCliente(codigo, descripcion, eUsuario);
		return lista;
	}
	public List<EOperacionClienteDocumento> listarEvaluacionClienteDocumento(long numeroSolicitud, int codigoTipoCliente, int codigoCliente){
		List<EOperacionClienteDocumento> lista=oCOperacion.listarEvaluacionClienteDocumento(numeroSolicitud, codigoTipoCliente, codigoCliente);
		return lista;
	}
	public EOperacionCliente buscarEvaluacionCliente(long numeroSolicitud, int codigoTipoCliente, int codigoCliente){
		EOperacionCliente resultado=oCOperacion.buscarEvaluacionCliente(numeroSolicitud, codigoTipoCliente, codigoCliente);
		return resultado;
	}
	public EMensaje agregarEvaluacionSolicitudCredito(){
		EMensaje mensaje = oCOperacion.agregarEvaluacionSolicitudCredito();
		return mensaje;
	}
	public EMensaje modificarEvaluacionSolicitudCredito(EOperacionSolicitudCredito eOperacionSolicitudCredito, ECliente eCliente, EClienteConstitucionEmpresa eClienteConstitucionEmpresa, EClienteAdicional eClienteAdicional){
		EMensaje mensaje = oCOperacion.modificarEvaluacionSolicitudCredito(eOperacionSolicitudCredito, eCliente, eClienteConstitucionEmpresa, eClienteAdicional);
		return mensaje;
	}
	public List<EOperacionSolicitudCredito> listarEvaluacionSolicitudCredito(EOperacionSolicitudCredito eOperacionSolicitudCredito, int indicadorConsulta){
		List<EOperacionSolicitudCredito> lista=oCOperacion.listarEvaluacionSolicitudCredito(eOperacionSolicitudCredito, indicadorConsulta);
		return lista;
	}
	public EOperacionSolicitudCredito buscarEvaluacionSolicitudCredito(long numeroSolicitud, int codigoTipoCliente, int codigoCliente){
		EOperacionSolicitudCredito resultado=oCOperacion.buscarEvaluacionSolicitudCredito(numeroSolicitud, codigoTipoCliente, codigoCliente);
		return resultado;
	}
	public List<EOperacionSolicitudCreditoMensaje> listarEvaluacionSolicitudCreditoMensaje(long numeroSolicitud, int codigoTipoCliente, int codigoCliente){
		List<EOperacionSolicitudCreditoMensaje> lista=oCOperacion.listarEvaluacionSolicitudCreditoMensaje(numeroSolicitud, codigoTipoCliente, codigoCliente);
		return lista;
	}
	public List<EOperacionSolicitudCreditoDocumento> listarEvaluacionSolicitudCreditoDocumento(long numeroSolicitud, int codigoTipoCliente, int codigoCliente){
		List<EOperacionSolicitudCreditoDocumento> lista=oCOperacion.listarEvaluacionSolicitudCreditoDocumento(numeroSolicitud, codigoTipoCliente, codigoCliente);
		return lista;
	}
	public List<EOperacionSolicitudCreditoDocumentoRevision> listarEvaluacionSolicitudCreditoDocumentoRevision(long numeroSolicitud, int codigoTipoCliente, int codigoCliente){
		List<EOperacionSolicitudCreditoDocumentoRevision> lista=oCOperacion.listarEvaluacionSolicitudCreditoDocumentoRevision(numeroSolicitud, codigoTipoCliente, codigoCliente);
		return lista;
	}
	public List<EOperacionSolicitudCreditoDocumentoRequerido> listarEvaluacionSolicitudCreditoDocumentoRequerido(){
		List<EOperacionSolicitudCreditoDocumentoRequerido> lista=oCOperacion.listarEvaluacionSolicitudCreditoDocumentoRequerido();
		return lista;
	}
}