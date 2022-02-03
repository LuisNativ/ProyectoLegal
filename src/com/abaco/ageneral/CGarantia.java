package com.abaco.ageneral;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import lombok.Data;

import com.abaco.entidad.EGeneral;
import com.abaco.entidad.EMensaje;
import com.abaco.entidad.EPersona;
import com.abaco.entidad.EUsuario;
import com.abaco.ageneral.ESolicitudCredito;
import com.abaco.ageneral.ESolicitudCredito;
import com.abaco.bo.BOGeneral;
import com.abaco.negocio.util.UConstante.UEstadoAutorizacionJefe;
import com.abaco.negocio.util.UConstante.UTipoCorrelativo;
import com.abaco.negocio.util.UFuncionesGenerales;
import com.abaco.negocio.util.UManejadorArchivo;
import com.abaco.negocio.util.UManejadorLog;
import com.abaco.negocio.util.UConstante.UAccionTabla;
import com.abaco.negocio.util.UConstante.UEstado;
import com.abaco.negocio.util.UConstante.UIndicadorSesion;
import com.abaco.negocio.util.UConstante.UMensajeOperacion;
import com.abaco.negocio.util.UConstante.UTipoGarantia;
import com.abaco.persistencia.acceso.FabricaConexion;
import com.abaco.persistencia.interfaces.IConexion;
import com.abaco.servicio.laserfiche.Mensaje;

public class CGarantia {
	
	public EMensaje agregarGarantiaPendienteRegistro(EGarantiaSolicitud eGarantiaSolicitud,EGarantia eGarantia){
		IConexion oIConexion = null;
		EMensaje mensaje = new EMensaje();
		DAOGarantia oDAOGarantia= null;
		try {
			oIConexion = FabricaConexion.creaConexion();
			oIConexion.iniciaTransaccion();
			oDAOGarantia= new DAOGarantia(oIConexion);
			
			mensaje = oDAOGarantia.modificarGarantiaSolicitud(eGarantiaSolicitud);
			if (!UFuncionesGenerales.validaMensaje(mensaje)) {
				throw new Exception(mensaje.getDescMensaje());
			}
			
			mensaje = oDAOGarantia.agregarGarantiaPendienteRegistro(eGarantiaSolicitud,eGarantia);
			if (!UFuncionesGenerales.validaMensaje(mensaje)) {
				throw new Exception(mensaje.getDescMensaje());
			}
			
			oIConexion.ejecutaCommit();
		} catch (Exception e) {
			if (oIConexion != null) {
				oIConexion.ejecutaRollback();
			}
			mensaje.setDescMensaje(UMensajeOperacion.MSJ_4 + mensaje.getDescMensaje());
			UManejadorLog.error("Control: Error al agregar garantía : " + e.getMessage());
		} finally {
			if (oIConexion != null) {
				oIConexion.cierraConexion();
			}
		}
		return mensaje;
	}
	
	public EMensaje agregarGarantiaMantenimiento(EGarantia eGarantia){
		IConexion oIConexion = null;
		EMensaje mensaje = new EMensaje();
		DAOGarantia oDAOGarantia= null;
		try {
			oIConexion = FabricaConexion.creaConexion();
			oIConexion.iniciaTransaccion();
			oDAOGarantia= new DAOGarantia(oIConexion);
			
			mensaje = oDAOGarantia.agregarGarantiaMantenimiento(eGarantia);
			if (!UFuncionesGenerales.validaMensaje(mensaje)) {
				throw new Exception(mensaje.getDescMensaje());
			}
			
			oIConexion.ejecutaCommit();
		} catch (Exception e) {
			if (oIConexion != null) {
				oIConexion.ejecutaRollback();
			}
			mensaje.setDescMensaje(UMensajeOperacion.MSJ_4 + mensaje.getDescMensaje());
			UManejadorLog.error("Control: Error al agregar garantía : " + e.getMessage());
		} finally {
			if (oIConexion != null) {
				oIConexion.cierraConexion();
			}
		}
		return mensaje;
	}
	
	public EMensaje agregarGarantiaMantenimientoyInmueblePredios(EGarantia eGarantia,List<EGarantia> lstInmueblesAdicionales){
		IConexion oIConexion = null;
		EMensaje mensaje = new EMensaje();
		EGarantia eUltGarantia = null;
		DAOGarantia oDAOGarantia= null;
		try {
			oIConexion = FabricaConexion.creaConexion();
			oIConexion.iniciaTransaccion();
			oDAOGarantia= new DAOGarantia(oIConexion);
			
			mensaje = oDAOGarantia.agregarGarantiaMantenimiento(eGarantia);
			if (!UFuncionesGenerales.validaMensaje(mensaje)) {
				throw new Exception(mensaje.getDescMensaje());
			}
			
			if(lstInmueblesAdicionales != null){
				if(lstInmueblesAdicionales.size()>0){
					eUltGarantia = oDAOGarantia.buscarUltimaGarantiaGenerada();
					
					if(eUltGarantia != null){
						for(EGarantia obj: lstInmueblesAdicionales){
							if(obj.getCodigoAccion() == UAccionTabla.INSERTAR && obj.getNumeroSecuenciaInmueble() == 0){
								obj.setCodigoGarantia(eUltGarantia.getCodigoGarantia());
								mensaje = oDAOGarantia.registrarInmueblesGarantiaPredios(obj);
								if (!UFuncionesGenerales.validaMensaje(mensaje)) {
									throw new Exception(mensaje.getDescMensaje());
								}
							}else if(obj.getCodigoAccion() == UAccionTabla.EDITAR && obj.getNumeroSecuenciaInmueble() != 0){
								mensaje = oDAOGarantia.modificarInmuebleGarantiaPredios(obj);
								if (!UFuncionesGenerales.validaMensaje(mensaje)) {
									throw new Exception(mensaje.getDescMensaje());
								}
							}else if(obj.getCodigoAccion() == UAccionTabla.ELIMINAR && obj.getNumeroSecuenciaInmueble() != 0){
								mensaje = oDAOGarantia.eliminarInmuebleGarantiaPredios(obj);
								if (!UFuncionesGenerales.validaMensaje(mensaje)) {
									throw new Exception(mensaje.getDescMensaje());
								}
							}
						}
					}
				}
			}
			
			
			oIConexion.ejecutaCommit();
		} catch (Exception e) {
			if (oIConexion != null) {
				oIConexion.ejecutaRollback();
			}
			mensaje.setDescMensaje(UMensajeOperacion.MSJ_4 + mensaje.getDescMensaje());
			UManejadorLog.error("Control: Error al agregar garantía : " + e.getMessage());
		} finally {
			if (oIConexion != null) {
				oIConexion.cierraConexion();
			}
		}
		return mensaje;
	}
	
	public EMensaje agregarGarantiaDetalle(EGarantia eGarantia){
		IConexion oIConexion = null;
		EMensaje mensaje = new EMensaje();
		DAOGarantia oDAOGarantia= null;
		try {
			oIConexion = FabricaConexion.creaConexion();
			oIConexion.iniciaTransaccion();
			oDAOGarantia= new DAOGarantia(oIConexion);
			
			mensaje = oDAOGarantia.agregarGarantiaDetalle(eGarantia);
			if (!UFuncionesGenerales.validaMensaje(mensaje)) {
				throw new Exception(mensaje.getDescMensaje());
			}
			
			oIConexion.ejecutaCommit();
		} catch (Exception e) {
			if (oIConexion != null) {
				oIConexion.ejecutaRollback();
			}
			mensaje.setDescMensaje(UMensajeOperacion.MSJ_4 + mensaje.getDescMensaje());
			UManejadorLog.error("Control: Error al agregar detalle garantía : " + e.getMessage());
		} finally {
			if (oIConexion != null) {
				oIConexion.cierraConexion();
			}
		}
		return mensaje;
	}
	
	
	public EMensaje registrarGarantiaSolicitudAnexoF7325(){
		IConexion oIConexion = null;
		EMensaje mensaje = new EMensaje();
		DAOGarantia oDAOGarantia= null;
		try {
			oIConexion = FabricaConexion.creaConexion();
			oIConexion.iniciaTransaccion();
			oDAOGarantia= new DAOGarantia(oIConexion);
			
			mensaje = oDAOGarantia.registrarGarantiaSolicitudAnexoF7325();
			if (!UFuncionesGenerales.validaMensaje(mensaje)) {
				throw new Exception(mensaje.getDescMensaje());
			}
			
			oIConexion.ejecutaCommit();
		} catch (Exception e) {
			if (oIConexion != null) {
				oIConexion.ejecutaRollback();
			}
			mensaje.setDescMensaje(UMensajeOperacion.MSJ_4 + mensaje.getDescMensaje());
			UManejadorLog.error("Control: Error al registrar Garantía Solicitud Anexo F7325: " + e.getMessage());
		} finally {
			if (oIConexion != null) {
				oIConexion.cierraConexion();
			}
		}
		return mensaje;
	}
	
	public EMensaje  registrarTipoGarantia(EGeneral tipoGarantia) {
		IConexion oIConexion = null;
		EMensaje mensaje = new EMensaje();
		DAOGarantia oDAOGarantia= null;
		try {
			oIConexion = FabricaConexion.creaConexion();
			oIConexion.iniciaTransaccion();
			oDAOGarantia= new DAOGarantia(oIConexion);
			
			mensaje = oDAOGarantia.registrarTipoGarantia(tipoGarantia);
			if (!UFuncionesGenerales.validaMensaje(mensaje)) {
				throw new Exception(mensaje.getDescMensaje());
			}
			
			oIConexion.ejecutaCommit();
		} catch (Exception e) {
			if (oIConexion != null) {
				oIConexion.ejecutaRollback();
			}
			mensaje.setDescMensaje(UMensajeOperacion.MSJ_4 + mensaje.getDescMensaje());
			UManejadorLog.error("Control: Error al registrar Tipo Garantía: " + e.getMessage());
		} finally {
			if (oIConexion != null) {
				oIConexion.cierraConexion();
			}
		}
		return mensaje;
	}
	
	public EMensaje  registrarPoliza(EPoliza ePoliza)  {
		IConexion oIConexion = null;
		EMensaje mensaje = new EMensaje();
		DAOGarantia oDAOGarantia= null;
		try {
			oIConexion = FabricaConexion.creaConexion();
			oIConexion.iniciaTransaccion();
			oDAOGarantia= new DAOGarantia(oIConexion);
			
			mensaje = oDAOGarantia.registrarPoliza(ePoliza);
			if (!UFuncionesGenerales.validaMensaje(mensaje)) {
				throw new Exception(mensaje.getDescMensaje());
			}
			
			oIConexion.ejecutaCommit();
		} catch (Exception e) {
			if (oIConexion != null) {
				oIConexion.ejecutaRollback();
			}
			mensaje.setDescMensaje(UMensajeOperacion.MSJ_4 + mensaje.getDescMensaje());
			UManejadorLog.error("Control: Error al registrar Poliza: " + e.getMessage());
		} finally {
			if (oIConexion != null) {
				oIConexion.cierraConexion();
			}
		}
		return mensaje;
	}
	
	public EMensaje  modificarTipoGarantia(EGeneral tipoGarantia) {
		IConexion oIConexion = null;
		EMensaje mensaje = new EMensaje();
		DAOGarantia oDAOGarantia= null;
		try {
			oIConexion = FabricaConexion.creaConexion();
			oIConexion.iniciaTransaccion();
			oDAOGarantia= new DAOGarantia(oIConexion);
			
			mensaje = oDAOGarantia.modificarTipoGarantia(tipoGarantia);
			if (!UFuncionesGenerales.validaMensaje(mensaje)) {
				throw new Exception(mensaje.getDescMensaje());
			}
			
			oIConexion.ejecutaCommit();
		} catch (Exception e) {
			if (oIConexion != null) {
				oIConexion.ejecutaRollback();
			}
			mensaje.setDescMensaje(UMensajeOperacion.MSJ_4 + mensaje.getDescMensaje());
			UManejadorLog.error("Control: Error al modificar Tipo Garantía: " + e.getMessage());
		} finally {
			if (oIConexion != null) {
				oIConexion.cierraConexion();
			}
		}
		return mensaje;
	}
	
	public EMensaje modificarGarantiaPoliza(EGarantia eGarantia)  {
		IConexion oIConexion = null;
		EMensaje mensaje = new EMensaje();
		DAOGarantia oDAOGarantia= null;
		try {
			oIConexion = FabricaConexion.creaConexion();
			oIConexion.iniciaTransaccion();
			oDAOGarantia= new DAOGarantia(oIConexion);
			
			mensaje = oDAOGarantia.modificarGarantiaPoliza(eGarantia);
			if (!UFuncionesGenerales.validaMensaje(mensaje)) {
				throw new Exception(mensaje.getDescMensaje());
			}
			
			oIConexion.ejecutaCommit();
		} catch (Exception e) {
			if (oIConexion != null) {
				oIConexion.ejecutaRollback();
			}
			mensaje.setDescMensaje(UMensajeOperacion.MSJ_4 + mensaje.getDescMensaje());
			UManejadorLog.error("Control: Error al modificar Poliza Seguro: " + e.getMessage());
		} finally {
			if (oIConexion != null) {
				oIConexion.cierraConexion();
			}
		}
		return mensaje;
	}
	
	public EMensaje modificarPoliza(EPoliza ePoliza)   {
		IConexion oIConexion = null;
		EMensaje mensaje = new EMensaje();
		DAOGarantia oDAOGarantia= null;
		try {
			oIConexion = FabricaConexion.creaConexion();
			oIConexion.iniciaTransaccion();
			oDAOGarantia= new DAOGarantia(oIConexion);
			
			mensaje = oDAOGarantia.modificarPoliza(ePoliza) ;
			if (!UFuncionesGenerales.validaMensaje(mensaje)) {
				throw new Exception(mensaje.getDescMensaje());
			}
			
			oIConexion.ejecutaCommit();
		} catch (Exception e) {
			if (oIConexion != null) {
				oIConexion.ejecutaRollback();
			}
			mensaje.setDescMensaje(UMensajeOperacion.MSJ_4 + mensaje.getDescMensaje());
			UManejadorLog.error("Control: Error al modificar Poliza: " + e.getMessage());
		} finally {
			if (oIConexion != null) {
				oIConexion.cierraConexion();
			}
		}
		return mensaje;
	}
	
	public EMensaje modificarTipoIngresoPorcentaje(EGarantiaSolicitud eGarantiaSolicitud)   {
		IConexion oIConexion = null;
		EMensaje mensaje = new EMensaje();
		DAOGarantia oDAOGarantia= null;
		try {
			oIConexion = FabricaConexion.creaConexion();
			oIConexion.iniciaTransaccion();
			oDAOGarantia= new DAOGarantia(oIConexion);
			
			mensaje = oDAOGarantia.modificarTipoIngresoPorcentaje(eGarantiaSolicitud) ;
			if (!UFuncionesGenerales.validaMensaje(mensaje)) {
				throw new Exception(mensaje.getDescMensaje());
			}
			
			oIConexion.ejecutaCommit();
		} catch (Exception e) {
			if (oIConexion != null) {
				oIConexion.ejecutaRollback();
			}
			mensaje.setDescMensaje(UMensajeOperacion.MSJ_4 + mensaje.getDescMensaje());
			UManejadorLog.error("Control: Error al modificar : " + e.getMessage());
		} finally {
			if (oIConexion != null) {
				oIConexion.cierraConexion();
			}
		}
		return mensaje;
	}
	
	public EMensaje modificarSolicitudDocumentoyDesembolsoGarantia(EGarantia eGarantia)  {
		IConexion oIConexion = null;
		EMensaje mensaje = new EMensaje();
		EOperacionDocumento oEOperacionDocumento = null;
		DAOGarantia oDAOGarantia= null;
		try {
			oIConexion = FabricaConexion.creaConexion();
			oIConexion.iniciaTransaccion();
			oDAOGarantia= new DAOGarantia(oIConexion);
			
			mensaje = oDAOGarantia.modificarSolicitudDocumentoGarantia(eGarantia) ;
			if (!UFuncionesGenerales.validaMensaje(mensaje)) {
				throw new Exception(mensaje.getDescMensaje());
			}
			/*
			oEOperacionDocumento = oDAOGarantia.buscarSolicitudDesembolsoGarantia(eGarantia);
			
			if(oEOperacionDocumento != null){
				eGarantia.setNumeroSolicitud(oEOperacionDocumento.getCodigoSolicitud());
				eGarantia.setCodigoGarantia(oEOperacionDocumento.getCodigoGarantia());
				eGarantia.setTipoDocumento(oEOperacionDocumento.getTipoDocumento());
				eGarantia.setEstadoDesembolso(oEOperacionDocumento.getEstadoDesembolso());
				eGarantia.setCondicionDesembolso1(UEstado.FIRMACONFIRMADA);
				eGarantia.setCondicionDesembolso2(oEOperacionDocumento.getCondicionDesembolso2());
				eGarantia.setCondicionDesembolso3(oEOperacionDocumento.getCondicionDesembolso3());
				eGarantia.setCondicionDesembolso4(oEOperacionDocumento.getCondicionDesembolso4());
				mensaje = oDAOGarantia.modificarSolicitudDesembolsoGarantia(eGarantia) ;
				if (!UFuncionesGenerales.validaMensaje(mensaje)) {
					throw new Exception(mensaje.getDescMensaje());
				}
			}
			*/
			
			oIConexion.ejecutaCommit();
		} catch (Exception e) {
			if (oIConexion != null) {
				oIConexion.ejecutaRollback();
			}
			mensaje.setDescMensaje(UMensajeOperacion.MSJ_4 + mensaje.getDescMensaje());
			UManejadorLog.error("Control: Error al modificar : " + e.getMessage());
		} finally {
			if (oIConexion != null) {
				oIConexion.cierraConexion();
			}
		}
		return mensaje;
	}  
	
	public EMensaje modificarDocumentoNotariayDesembolsoGarantia(EGarantia eGarantia)  {
		IConexion oIConexion = null;
		EMensaje mensaje = new EMensaje();
		EOperacionDocumento oEOperacionDocumento = null;
		DAOGarantia oDAOGarantia= null;
		try {
			oIConexion = FabricaConexion.creaConexion();
			oIConexion.iniciaTransaccion();
			oDAOGarantia= new DAOGarantia(oIConexion);
			
			mensaje = oDAOGarantia.modificarSolicitudDocumentoGarantia(eGarantia) ;
			if (!UFuncionesGenerales.validaMensaje(mensaje)) {
				throw new Exception(mensaje.getDescMensaje());
			}
			/*
			oEOperacionDocumento = oDAOGarantia.buscarSolicitudDesembolsoGarantia(eGarantia);
			
			if(oEOperacionDocumento != null){
				eGarantia.setNumeroSolicitud(oEOperacionDocumento.getCodigoSolicitud());
				eGarantia.setCodigoGarantia(oEOperacionDocumento.getCodigoGarantia());
				eGarantia.setTipoDocumento(oEOperacionDocumento.getTipoDocumento());
				eGarantia.setEstadoDesembolso(oEOperacionDocumento.getEstadoDesembolso());
				eGarantia.setCondicionDesembolso1(oEOperacionDocumento.getCondicionDesembolso1());
				eGarantia.setCondicionDesembolso2(oEOperacionDocumento.getCondicionDesembolso2());
				eGarantia.setCondicionDesembolso3(UEstado.FIRMACONFIRMADA);
				eGarantia.setCondicionDesembolso4(oEOperacionDocumento.getCondicionDesembolso4());
				mensaje = oDAOGarantia.modificarSolicitudDesembolsoGarantia(eGarantia) ;
				if (!UFuncionesGenerales.validaMensaje(mensaje)) {
					throw new Exception(mensaje.getDescMensaje());
				}
			}*/
			
			
			oIConexion.ejecutaCommit();
		} catch (Exception e) {
			if (oIConexion != null) {
				oIConexion.ejecutaRollback();
			}
			mensaje.setDescMensaje(UMensajeOperacion.MSJ_4 + mensaje.getDescMensaje());
			UManejadorLog.error("Control: Error al modificar : " + e.getMessage());
		} finally {
			if (oIConexion != null) {
				oIConexion.cierraConexion();
			}
		}
		return mensaje;
	}  
	
	public EMensaje modificarSolicitudDocumentoGarantia(EGarantia eGarantia)  {
		IConexion oIConexion = null;
		EMensaje mensaje = new EMensaje();
		DAOGarantia oDAOGarantia= null;
		try {
			oIConexion = FabricaConexion.creaConexion();
			oIConexion.iniciaTransaccion();
			oDAOGarantia= new DAOGarantia(oIConexion);
			
			mensaje = oDAOGarantia.modificarSolicitudDocumentoGarantia(eGarantia) ;
			if (!UFuncionesGenerales.validaMensaje(mensaje)) {
				throw new Exception(mensaje.getDescMensaje());
			}
			
			oIConexion.ejecutaCommit();
		} catch (Exception e) {
			if (oIConexion != null) {
				oIConexion.ejecutaRollback();
			}
			mensaje.setDescMensaje(UMensajeOperacion.MSJ_4 + mensaje.getDescMensaje());
			UManejadorLog.error("Control: Error al modificar : " + e.getMessage());
		} finally {
			if (oIConexion != null) {
				oIConexion.cierraConexion();
			}
		}
		return mensaje;
	}  
	
	public EMensaje modificarDetalleSolicitudDocumentoGarantia(EGarantia eGarantia, int indicador)   {
		IConexion oIConexion = null;
		EMensaje mensaje = new EMensaje();
		DAOGarantia oDAOGarantia= null;
		try {
			oIConexion = FabricaConexion.creaConexion();
			oIConexion.iniciaTransaccion();
			oDAOGarantia= new DAOGarantia(oIConexion);
			
			mensaje = oDAOGarantia.modificarDetalleSolicitudDocumentoGarantia(eGarantia,indicador) ;
			if (!UFuncionesGenerales.validaMensaje(mensaje)) {
				throw new Exception(mensaje.getDescMensaje());
			}
			
			oIConexion.ejecutaCommit();
		} catch (Exception e) {
			if (oIConexion != null) {
				oIConexion.ejecutaRollback();
			}
			mensaje.setDescMensaje(UMensajeOperacion.MSJ_4 + mensaje.getDescMensaje());
			UManejadorLog.error("Control: Error al modificar : " + e.getMessage());
		} finally {
			if (oIConexion != null) {
				oIConexion.cierraConexion();
			}
		}
		return mensaje;
	} 
	
	public EMensaje modificarSolicitudDesembolsoGarantia(EGarantia eGarantia) {
		IConexion oIConexion = null;
		EMensaje mensaje = new EMensaje();
		DAOGarantia oDAOGarantia= null;
		try {
			oIConexion = FabricaConexion.creaConexion();
			oIConexion.iniciaTransaccion();
			oDAOGarantia= new DAOGarantia(oIConexion);
			
			mensaje = oDAOGarantia.modificarSolicitudDesembolsoGarantia(eGarantia) ;
			if (!UFuncionesGenerales.validaMensaje(mensaje)) {
				throw new Exception(mensaje.getDescMensaje());
			}
			
			oIConexion.ejecutaCommit();
		} catch (Exception e) {
			if (oIConexion != null) {
				oIConexion.ejecutaRollback();
			}
			mensaje.setDescMensaje(UMensajeOperacion.MSJ_4 + mensaje.getDescMensaje());
			UManejadorLog.error("Control: Error al modificar : " + e.getMessage());
		} finally {
			if (oIConexion != null) {
				oIConexion.cierraConexion();
			}
		}
		return mensaje;
	}  
	
	public EMensaje modificarInmuebleGarantiaPredios(EGarantia eGarantia) {
		IConexion oIConexion = null;
		EMensaje mensaje = new EMensaje();
		DAOGarantia oDAOGarantia= null;
		try {
			oIConexion = FabricaConexion.creaConexion();
			oIConexion.iniciaTransaccion();
			oDAOGarantia= new DAOGarantia(oIConexion);
			
			mensaje = oDAOGarantia.modificarInmuebleGarantiaPredios(eGarantia) ;
			if (!UFuncionesGenerales.validaMensaje(mensaje)) {
				throw new Exception(mensaje.getDescMensaje());
			}
			
			oIConexion.ejecutaCommit();
		} catch (Exception e) {
			if (oIConexion != null) {
				oIConexion.ejecutaRollback();
			}
			mensaje.setDescMensaje(UMensajeOperacion.MSJ_4 + mensaje.getDescMensaje());
			UManejadorLog.error("Control: Error al modificar : " + e.getMessage());
		} finally {
			if (oIConexion != null) {
				oIConexion.cierraConexion();
			}
		}
		return mensaje;
	}  
	
	public EMensaje modificarConfirmacionFirmanteSolicitud(EFirmanteSolicitud eFirmanteSolicitud) {
		IConexion oIConexion = null;
		EMensaje mensaje = new EMensaje();
		DAOGarantia oDAOGarantia= null;
		try {
			oIConexion = FabricaConexion.creaConexion();
			oIConexion.iniciaTransaccion();
			oDAOGarantia= new DAOGarantia(oIConexion);
			
			mensaje = oDAOGarantia.modificarConfirmacionFirmanteSolicitud(eFirmanteSolicitud) ;
			if (!UFuncionesGenerales.validaMensaje(mensaje)) {
				throw new Exception(mensaje.getDescMensaje());
			}
			
			oIConexion.ejecutaCommit();
		} catch (Exception e) {
			if (oIConexion != null) {
				oIConexion.ejecutaRollback();
			}
			mensaje.setDescMensaje(UMensajeOperacion.MSJ_4 + mensaje.getDescMensaje());
			UManejadorLog.error("Control: Error al modificar : " + e.getMessage());
		} finally {
			if (oIConexion != null) {
				oIConexion.cierraConexion();
			}
		}
		return mensaje;
	}  
	
	public EMensaje modificarFirmaContratoFirmanteSolicitud(EFirmanteSolicitud eFirmanteSolicitud) {
		IConexion oIConexion = null;
		EMensaje mensaje = new EMensaje();
		DAOGarantia oDAOGarantia= null;
		try {
			oIConexion = FabricaConexion.creaConexion();
			oIConexion.iniciaTransaccion();
			oDAOGarantia= new DAOGarantia(oIConexion);
			
			mensaje = oDAOGarantia.modificarFirmaContratoFirmanteSolicitud(eFirmanteSolicitud) ;
			if (!UFuncionesGenerales.validaMensaje(mensaje)) {
				throw new Exception(mensaje.getDescMensaje());
			}
			
			oIConexion.ejecutaCommit();
		} catch (Exception e) {
			if (oIConexion != null) {
				oIConexion.ejecutaRollback();
			}
			mensaje.setDescMensaje(UMensajeOperacion.MSJ_4 + mensaje.getDescMensaje());
			UManejadorLog.error("Control: Error al modificar : " + e.getMessage());
		} finally {
			if (oIConexion != null) {
				oIConexion.cierraConexion();
			}
		}
		return mensaje;
	}  
	
	public EMensaje modificarConfirmaDatosGarantiaSolicitud(EGarantiaSolicitud eGarantiaSolicitud) {
		IConexion oIConexion = null;
		EMensaje mensaje = new EMensaje();
		DAOGarantia oDAOGarantia= null;
		try {
			oIConexion = FabricaConexion.creaConexion();
			oIConexion.iniciaTransaccion();
			oDAOGarantia= new DAOGarantia(oIConexion);
			
			mensaje = oDAOGarantia.modificarConfirmaDatosGarantiaSolicitud(eGarantiaSolicitud) ;
			if (!UFuncionesGenerales.validaMensaje(mensaje)) {
				throw new Exception(mensaje.getDescMensaje());
			}
			
			oIConexion.ejecutaCommit();
		} catch (Exception e) {
			if (oIConexion != null) {
				oIConexion.ejecutaRollback();
			}
			mensaje.setDescMensaje(UMensajeOperacion.MSJ_4 + mensaje.getDescMensaje());
			UManejadorLog.error("Control: Error al modificar : " + e.getMessage());
		} finally {
			if (oIConexion != null) {
				oIConexion.cierraConexion();
			}
		}
		return mensaje;
	}  
	
	public EMensaje modificarNoConformidadDatosGarantia(long codigoSolicitud,String nombreUusario) {
		IConexion oIConexion = null;
		EMensaje mensaje = new EMensaje();
		DAOGarantia oDAOGarantia= null;
		try {
			oIConexion = FabricaConexion.creaConexion();
			oIConexion.iniciaTransaccion();
			oDAOGarantia= new DAOGarantia(oIConexion);
			
			mensaje = oDAOGarantia.modificarNoConformidadDatosGarantia(codigoSolicitud,nombreUusario) ;
			if (!UFuncionesGenerales.validaMensaje(mensaje)) {
				throw new Exception(mensaje.getDescMensaje());
			}
				
			oIConexion.ejecutaCommit();
		} catch (Exception e) {
			if (oIConexion != null) {
				oIConexion.ejecutaRollback();
			}
			mensaje.setDescMensaje(UMensajeOperacion.MSJ_4 + mensaje.getDescMensaje());
			UManejadorLog.error("Control: Error al modificar : " + e.getMessage());
		} finally {
			if (oIConexion != null) {
				oIConexion.cierraConexion();
			}
		}
		return mensaje;
	}  
	
	public EMensaje modificarNoConformidadDatosGarantiayObservacionSolicitud(EOperacionSolicitud oEOperacionSolicitud) {
		IConexion oIConexion = null;
		EMensaje mensaje = new EMensaje();
		DAOGarantia oDAOGarantia= null;
		try {
			oIConexion = FabricaConexion.creaConexion();
			oIConexion.iniciaTransaccion();
			oDAOGarantia= new DAOGarantia(oIConexion);
			
			mensaje = oDAOGarantia.modificarNoConformidadDatosGarantia(oEOperacionSolicitud.getCodigoSolicitud(),oEOperacionSolicitud.getUsuarioRegistro().getNombreUsuario()) ;
			if (!UFuncionesGenerales.validaMensaje(mensaje)) {
				throw new Exception(mensaje.getDescMensaje());
			}
			
			mensaje = oDAOGarantia.agregarObservacionTramiteOperativoSolicitud(oEOperacionSolicitud);
			if (!UFuncionesGenerales.validaMensaje(mensaje)) {
				throw new Exception(mensaje.getDescMensaje());
			}
			
			oIConexion.ejecutaCommit();
		} catch (Exception e) {
			if (oIConexion != null) {
				oIConexion.ejecutaRollback();
			}
			mensaje.setDescMensaje(UMensajeOperacion.MSJ_4 + mensaje.getDescMensaje());
			UManejadorLog.error("Control: Error al modificar : " + e.getMessage());
		} finally {
			if (oIConexion != null) {
				oIConexion.cierraConexion();
			}
		}
		return mensaje;
	}  
	
	public EMensaje modificarContratoGarantiaF7401(EContrato eContrato) {
		IConexion oIConexion = null;
		EMensaje mensaje = new EMensaje();
		DAOGarantia oDAOGarantia= null;
		try {
			oIConexion = FabricaConexion.creaConexion();
			oIConexion.iniciaTransaccion();
			oDAOGarantia= new DAOGarantia(oIConexion);
			
			mensaje = oDAOGarantia.modificarContratoGarantiaF7401(eContrato) ;
			if (!UFuncionesGenerales.validaMensaje(mensaje)) {
				throw new Exception(mensaje.getDescMensaje());
			}
			
			oIConexion.ejecutaCommit();
		} catch (Exception e) {
			if (oIConexion != null) {
				oIConexion.ejecutaRollback();
			}
			mensaje.setDescMensaje(UMensajeOperacion.MSJ_4 + mensaje.getDescMensaje());
			UManejadorLog.error("Control: Error al modificar : " + e.getMessage());
		} finally {
			if (oIConexion != null) {
				oIConexion.cierraConexion();
			}
		}
		return mensaje;
	}  
	
	public EMensaje modificarImpresionContratoGarantia(EContrato eContrato) {
		IConexion oIConexion = null;
		EMensaje mensaje = new EMensaje();
		DAOGarantia oDAOGarantia= null;
		try {
			oIConexion = FabricaConexion.creaConexion();
			oIConexion.iniciaTransaccion();
			oDAOGarantia= new DAOGarantia(oIConexion);
			
			mensaje = oDAOGarantia.modificarImpresionContratoGarantia(eContrato) ;
			if (!UFuncionesGenerales.validaMensaje(mensaje)) {
				throw new Exception(mensaje.getDescMensaje());
			}
			
			//Actualizar Flag 903
			mensaje = oDAOGarantia.modificarFlagImpresionContrato(eContrato);
			if (!UFuncionesGenerales.validaMensaje(mensaje)) {
				throw new Exception(mensaje.getDescMensaje());
			}

			oIConexion.ejecutaCommit();
		} catch (Exception e) {
			if (oIConexion != null) {
				oIConexion.ejecutaRollback();
			}
			mensaje.setDescMensaje(UMensajeOperacion.MSJ_4 + mensaje.getDescMensaje());
			UManejadorLog.error("Control: Error al modificar : " + e.getMessage());
		} finally {
			if (oIConexion != null) {
				oIConexion.cierraConexion();
			}
		}
		return mensaje;
	}  
	
	public EMensaje modificarFlagImpresionContrato(EContrato eContrato) {
		IConexion oIConexion = null;
		EMensaje mensaje = new EMensaje();
		DAOGarantia oDAOGarantia= null;
		try {
			oIConexion = FabricaConexion.creaConexion();
			oIConexion.iniciaTransaccion();
			oDAOGarantia= new DAOGarantia(oIConexion);
			
			mensaje = oDAOGarantia.modificarFlagImpresionContrato(eContrato) ;
			if (!UFuncionesGenerales.validaMensaje(mensaje)) {
				throw new Exception(mensaje.getDescMensaje());
			}
			
			oIConexion.ejecutaCommit();
		} catch (Exception e) {
			if (oIConexion != null) {
				oIConexion.ejecutaRollback();
			}
			mensaje.setDescMensaje(UMensajeOperacion.MSJ_4 + mensaje.getDescMensaje());
			UManejadorLog.error("Control: Error al modificar : " + e.getMessage());
		} finally {
			if (oIConexion != null) {
				oIConexion.cierraConexion();
			}
		}
		return mensaje;
	}  
	
	public EMensaje modificarDetalleFlagRequisitoLegal(EFlagReqLegal eFlagRequisitoLegal)  {
		IConexion oIConexion = null;
		EMensaje mensaje = new EMensaje();
		DAOGarantia oDAOGarantia= null;
		try {
			oIConexion = FabricaConexion.creaConexion();
			oIConexion.iniciaTransaccion();
			oDAOGarantia= new DAOGarantia(oIConexion);
			
			mensaje = oDAOGarantia.modificarDetalleFlagRequisitoLegal(eFlagRequisitoLegal) ;
			if (!UFuncionesGenerales.validaMensaje(mensaje)) {
				throw new Exception(mensaje.getDescMensaje());
			}
			
			oIConexion.ejecutaCommit();
		} catch (Exception e) {
			if (oIConexion != null) {
				oIConexion.ejecutaRollback();
			}
			mensaje.setDescMensaje(UMensajeOperacion.MSJ_4 + mensaje.getDescMensaje());
			UManejadorLog.error("Control: Error al modificar : " + e.getMessage());
		} finally {
			if (oIConexion != null) {
				oIConexion.cierraConexion();
			}
		}
		return mensaje;
	}  
	
	public EMensaje modificarContratoyRepresentanteGarantia(EContrato eContrato,List<ERepresentanteCIAContrato> lstRepresentanteCIAContrato) {
		IConexion oIConexion = null;
		EMensaje mensaje = new EMensaje();
		DAOGarantia oDAOGarantia= null;
		try {
			oIConexion = FabricaConexion.creaConexion();
			oIConexion.iniciaTransaccion();
			oDAOGarantia= new DAOGarantia(oIConexion);
			
			mensaje = oDAOGarantia.modificarContratoGarantiaF7401(eContrato) ;
			if (!UFuncionesGenerales.validaMensaje(mensaje)) {
				throw new Exception(mensaje.getDescMensaje());
			}
			
			if(lstRepresentanteCIAContrato != null){
				if(lstRepresentanteCIAContrato.size() > 0){
					for(ERepresentanteCIAContrato obj : lstRepresentanteCIAContrato){
						if(obj.getCodigoAccion() == UAccionTabla.INSERTAR && obj.getNumeroContrato()>0){
							mensaje = oDAOGarantia.registrarRepresentanteCompaniaContratoF7433(obj);
							if (!UFuncionesGenerales.validaMensaje(mensaje)) {
								throw new Exception(mensaje.getDescMensaje());
							}
						}else if (obj.getCodigoAccion() == UAccionTabla.ELIMINAR && obj.getNumeroContrato()>0){
							mensaje = oDAOGarantia.eliminarRepresentanteCompaniaContratoF7433(obj);
							if (!UFuncionesGenerales.validaMensaje(mensaje)) {
								throw new Exception(mensaje.getDescMensaje());
							}
						}
					}
				}
			}
			

			oIConexion.ejecutaCommit();
		} catch (Exception e) {
			if (oIConexion != null) {
				oIConexion.ejecutaRollback();
			}
			mensaje.setDescMensaje(UMensajeOperacion.MSJ_4 + mensaje.getDescMensaje());
			UManejadorLog.error("Control: Error al modificar : " + e.getMessage());
		} finally {
			if (oIConexion != null) {
				oIConexion.cierraConexion();
			}
		}
		return mensaje;
	}  
	
	
	public EMensaje modificarDocumentoGarantia(EGarantia eGarantia)   {
		IConexion oIConexion = null;
		EMensaje mensaje = new EMensaje();
		DAOGarantia oDAOGarantia= null;
		try {
			oIConexion = FabricaConexion.creaConexion();
			oIConexion.iniciaTransaccion();
			oDAOGarantia= new DAOGarantia(oIConexion);
			
			mensaje = oDAOGarantia.modificarSolicitudDocumentoGarantia(eGarantia) ;
			if (!UFuncionesGenerales.validaMensaje(mensaje)) {
				throw new Exception(mensaje.getDescMensaje());
			}
			
			mensaje = oDAOGarantia.modificarDetalleSolicitudDocumentoGarantia(eGarantia,1) ;
			if (!UFuncionesGenerales.validaMensaje(mensaje)) {
				throw new Exception(mensaje.getDescMensaje());
			}
			
			oIConexion.ejecutaCommit();
		} catch (Exception e) {
			if (oIConexion != null) {
				oIConexion.ejecutaRollback();
			}
			mensaje.setDescMensaje(UMensajeOperacion.MSJ_4 + mensaje.getDescMensaje());
			UManejadorLog.error("Control: Error al modificar : " + e.getMessage());
		} finally {
			if (oIConexion != null) {
				oIConexion.cierraConexion();
			}
		}
		return mensaje;
	} 
	
	public EMensaje agregarGarantiaTramite(EGarantiaTramite eGarantiaTramite) {
		IConexion oIConexion = null;
		EMensaje mensaje = new EMensaje();
		DAOGarantia oDAOGarantia= null;
		try {
			oIConexion = FabricaConexion.creaConexion();
			oIConexion.iniciaTransaccion();
			oDAOGarantia= new DAOGarantia(oIConexion);
			
			mensaje = oDAOGarantia.agregarGarantiaTramite(eGarantiaTramite);
			if (!UFuncionesGenerales.validaMensaje(mensaje)) {
				throw new Exception(mensaje.getDescMensaje());
			}
			
			oIConexion.ejecutaCommit();
		} catch (Exception e) {
			if (oIConexion != null) {
				oIConexion.ejecutaRollback();
			}
			mensaje.setDescMensaje(UMensajeOperacion.MSJ_4 + mensaje.getDescMensaje());
			UManejadorLog.error("Control CGarantia: Error al agregar garantía : " + e.getMessage());
		} finally {
			if (oIConexion != null) {
				oIConexion.cierraConexion();
			}
		}
		return mensaje;
	}
	
	public EMensaje agregarModificarGarantiaTramite(EGarantiaTramite eGarantiaTramite,EGarantiaTramite eGarantiaTramiteAsiento) {
		IConexion oIConexion = null;
		EMensaje mensaje = new EMensaje();
		DAOGarantia oDAOGarantia= null;
		try {
			oIConexion = FabricaConexion.creaConexion();
			oIConexion.iniciaTransaccion();
			oDAOGarantia= new DAOGarantia(oIConexion);
			
			mensaje = oDAOGarantia.agregarModificarGarantiaTramite(eGarantiaTramite);
			if (!UFuncionesGenerales.validaMensaje(mensaje)) {
				throw new Exception(mensaje.getDescMensaje());
			}
			/*
			if(eGarantiaTramiteAsiento != null){
				mensaje = oDAOGarantia.agregarAsientoGarantiaTramite(eGarantiaTramiteAsiento);
				if (!UFuncionesGenerales.validaMensaje(mensaje)) {
					throw new Exception(mensaje.getDescMensaje());
				}
			}*/
			
			
			oIConexion.ejecutaCommit();
		} catch (Exception e) {
			if (oIConexion != null) {
				oIConexion.ejecutaRollback();
			}
			mensaje.setDescMensaje(UMensajeOperacion.MSJ_4 + mensaje.getDescMensaje());
			UManejadorLog.error("Control CGarantia: Error al agregar/Modificar garantía : " + e.getMessage());
		} finally {
			if (oIConexion != null) {
				oIConexion.cierraConexion();
			}
		}
		return mensaje;
	}
	
	public EMensaje agregarAsientoGarantiaTramite(EGarantiaTramite eGarantiaTramiteAsiento) {
		IConexion oIConexion = null;
		EMensaje mensaje = new EMensaje();
		DAOGarantia oDAOGarantia= null;
		try {
			oIConexion = FabricaConexion.creaConexion();
			oIConexion.iniciaTransaccion();
			oDAOGarantia= new DAOGarantia(oIConexion);

			mensaje = oDAOGarantia.agregarAsientoGarantiaTramite(eGarantiaTramiteAsiento);
			if (!UFuncionesGenerales.validaMensaje(mensaje)) {
				throw new Exception(mensaje.getDescMensaje());
			}
			oIConexion.ejecutaCommit();
		} catch (Exception e) {
			if (oIConexion != null) {
				oIConexion.ejecutaRollback();
			}
			mensaje.setDescMensaje(UMensajeOperacion.MSJ_4 + mensaje.getDescMensaje());
			UManejadorLog.error("Control CGarantia: Error al agregar Asiento Tramite Garantia : " + e.getMessage());
		} finally {
			if (oIConexion != null) {
				oIConexion.cierraConexion();
			}
		}
		return mensaje;
	}
		
	public EMensaje modificarGarantiaMantenimiento(EGarantia eGarantia){
		IConexion oIConexion = null;
		EMensaje mensaje = new EMensaje();
		Mensaje mensajeLaserFiche = new Mensaje();
		DAOGarantia oDAOGarantia= null;
		UManejadorArchivo oUManejadorArchivo = new UManejadorArchivo();
		DAOOperacion oDAOOperacion= null;
		try {
			oIConexion = FabricaConexion.creaConexion();
			oIConexion.iniciaTransaccion();
			oDAOGarantia= new DAOGarantia(oIConexion);
			oDAOOperacion = new DAOOperacion(oIConexion);
			
			mensaje = oDAOGarantia.modificarGarantiaMantenimiento(eGarantia);
			if (!UFuncionesGenerales.validaMensaje(mensaje)) {
				throw new Exception(mensaje.getDescMensaje());
			}
			
			oIConexion.ejecutaCommit();
		} catch (Exception e) {
			if (oIConexion != null) {
				oIConexion.ejecutaRollback();
			}
			mensaje.setDescMensaje(UMensajeOperacion.MSJ_4 + mensaje.getDescMensaje());
			UManejadorLog.error("Control: Error al modificar garantía : " + e.getMessage());
		} finally {
			if (oIConexion != null) {
				oIConexion.cierraConexion();
			}
		}
		return mensaje;
	}
	
	public EMensaje modificarGarantiaMantenimientoyInmueblePredios(EGarantia eGarantia,List<EGarantia> lstInmueblesAdicionales){
		IConexion oIConexion = null;
		EMensaje mensaje = new EMensaje();
		Mensaje mensajeLaserFiche = new Mensaje();
		DAOGarantia oDAOGarantia= null;
		UManejadorArchivo oUManejadorArchivo = new UManejadorArchivo();
		DAOOperacion oDAOOperacion= null;
		try {
			oIConexion = FabricaConexion.creaConexion();
			oIConexion.iniciaTransaccion();
			oDAOGarantia= new DAOGarantia(oIConexion);
			oDAOOperacion = new DAOOperacion(oIConexion);
			
			mensaje = oDAOGarantia.modificarGarantiaMantenimiento(eGarantia);
			if (!UFuncionesGenerales.validaMensaje(mensaje)) {
				throw new Exception(mensaje.getDescMensaje());
			}
			
			if(lstInmueblesAdicionales != null){
				if(lstInmueblesAdicionales.size()>0){
					for(EGarantia obj: lstInmueblesAdicionales){
						if(obj.getCodigoAccion() == UAccionTabla.INSERTAR && obj.getNumeroSecuenciaInmueble() == 0){
							obj.setCodigoGarantia(eGarantia.getCodigoGarantia());
							mensaje = oDAOGarantia.registrarInmueblesGarantiaPredios(obj);
							if (!UFuncionesGenerales.validaMensaje(mensaje)) {
								throw new Exception(mensaje.getDescMensaje());
							}
						}else if(obj.getCodigoAccion() == UAccionTabla.EDITAR && obj.getNumeroSecuenciaInmueble() != 0){
							mensaje = oDAOGarantia.modificarInmuebleGarantiaPredios(obj);
							if (!UFuncionesGenerales.validaMensaje(mensaje)) {
								throw new Exception(mensaje.getDescMensaje());
							}
						}else if(obj.getCodigoAccion() == UAccionTabla.ELIMINAR && obj.getNumeroSecuenciaInmueble() != 0){
							mensaje = oDAOGarantia.eliminarInmuebleGarantiaPredios(obj);
							if (!UFuncionesGenerales.validaMensaje(mensaje)) {
								throw new Exception(mensaje.getDescMensaje());
							}
						}
					}
					
				}
			}
			
			oIConexion.ejecutaCommit();
		} catch (Exception e) {
			if (oIConexion != null) {
				oIConexion.ejecutaRollback();
			}
			mensaje.setDescMensaje(UMensajeOperacion.MSJ_4 + mensaje.getDescMensaje());
			UManejadorLog.error("Control: Error al modificar garantía : " + e.getMessage());
		} finally {
			if (oIConexion != null) {
				oIConexion.cierraConexion();
			}
		}
		return mensaje;
	}
	
	public EMensaje agregarDocumentoGarantia(EGarantia eGarantia,List<EDocumentoCarga> lstDocumentoCarga){
		IConexion oIConexion = null;
		EMensaje mensaje = new EMensaje();
		Mensaje mensajeLaserFiche = new Mensaje();
		DAOGarantia oDAOGarantia= null;
		UManejadorArchivo oUManejadorArchivo = new UManejadorArchivo();
		DAOOperacion oDAOOperacion= null;
		try {
			oIConexion = FabricaConexion.creaConexion();
			oIConexion.iniciaTransaccion();
			oDAOGarantia= new DAOGarantia(oIConexion);
			oDAOOperacion = new DAOOperacion(oIConexion);
			
			if(lstDocumentoCarga != null){
				if(lstDocumentoCarga.size() > 0){
					for(EDocumentoCarga oEDocumentoCarga: lstDocumentoCarga){
						mensajeLaserFiche = oUManejadorArchivo.guardarDocumento(eGarantia, oEDocumentoCarga);
						if (!UFuncionesGenerales.validaMensajeLaserFiche(mensajeLaserFiche)) {
							throw new Exception(mensajeLaserFiche.getDescripcion());
						}
						
						oEDocumentoCarga.setCodigoLaserFiche(mensajeLaserFiche.getDocumentoID());
						eGarantia.setFirmaDocumento("NO");
						eGarantia.setEstadoDocumento(UEstado.PENDIENTEFIRMA);
						mensaje = oDAOOperacion.agregarDocumentoGarantia(eGarantia, oEDocumentoCarga);
						if (!UFuncionesGenerales.validaMensaje(mensaje)) {
							throw new Exception(mensaje.getDescMensaje());
						}
					}
				}
			}	
			oIConexion.ejecutaCommit();
		} catch (Exception e) {
			if (oIConexion != null) {
				oIConexion.ejecutaRollback();
			}
			mensaje.setDescMensaje(UMensajeOperacion.MSJ_4 + mensaje.getDescMensaje());
			UManejadorLog.error("Control: Error al agregar Documento Garantia : " + e.getMessage());
		} finally {
			if (oIConexion != null) {
				oIConexion.cierraConexion();
			}
		}
		return mensaje;
	}
	
	
	public EMensaje agregarSolicitudDocumentoGarantia(EGarantia eGarantia,List<EDocumentoCarga> lstDocumentoCarga){
		IConexion oIConexion = null;
		EMensaje mensaje = new EMensaje();
		Mensaje mensajeLaserFiche = new Mensaje();
		DAOGarantia oDAOGarantia= null;
		UManejadorArchivo oUManejadorArchivo = new UManejadorArchivo();
		try {
			oIConexion = FabricaConexion.creaConexion();
			oIConexion.iniciaTransaccion();
			oDAOGarantia= new DAOGarantia(oIConexion);		
			if(lstDocumentoCarga != null){
				if(lstDocumentoCarga.size() > 0){
					for(EDocumentoCarga oEDocumentoCarga: lstDocumentoCarga){
						mensajeLaserFiche = oUManejadorArchivo.guardarDocumento(eGarantia, oEDocumentoCarga);
						if (!UFuncionesGenerales.validaMensajeLaserFiche(mensajeLaserFiche)) {
							throw new Exception(mensajeLaserFiche.getDescripcion());
						}
						
						oEDocumentoCarga.setCodigoLaserFiche(mensajeLaserFiche.getDocumentoID());
						mensaje = oDAOGarantia.registrarSolicitudDocumentoGarantia(eGarantia, oEDocumentoCarga);
						if (!UFuncionesGenerales.validaMensaje(mensaje)) {
							throw new Exception(mensaje.getDescMensaje());
						}
					}
				}
			}	
			oIConexion.ejecutaCommit();
		} catch (Exception e) {
			if (oIConexion != null) {
				oIConexion.ejecutaRollback();
			}
			mensaje.setDescMensaje(UMensajeOperacion.MSJ_4 + mensaje.getDescMensaje());
			UManejadorLog.error("Control: Error al agregar Documento Garantia : " + e.getMessage());
		} finally {
			if (oIConexion != null) {
				oIConexion.cierraConexion();
			}
		}
		return mensaje;
	}
	
	public EMensaje agregarDetalleSolicitudDocumentoGarantia(EGarantia eOGarantia,List<EDocumentoCarga> lstDocumentoCarga){
		IConexion oIConexion = null;
		EMensaje mensaje = new EMensaje();
		Mensaje mensajeLaserFiche = new Mensaje();
		DAOGarantia oDAOGarantia= null;
		UManejadorArchivo oUManejadorArchivo = new UManejadorArchivo();
		try {
			oIConexion = FabricaConexion.creaConexion();
			oIConexion.iniciaTransaccion();
			oDAOGarantia= new DAOGarantia(oIConexion);
			
			if(lstDocumentoCarga != null){
				if(lstDocumentoCarga.size() > 0){
					for(EDocumentoCarga oEDocumentoCarga: lstDocumentoCarga){
						mensajeLaserFiche = oUManejadorArchivo.guardarDocumento(eOGarantia, oEDocumentoCarga);
						if (!UFuncionesGenerales.validaMensajeLaserFiche(mensajeLaserFiche)) {
							throw new Exception(mensajeLaserFiche.getDescripcion());
						}
						
						oEDocumentoCarga.setCodigoLaserFiche(mensajeLaserFiche.getDocumentoID());
						mensaje = oDAOGarantia.registrarDetalleSolicitudDocumentoGarantia(eOGarantia, oEDocumentoCarga);
						if (!UFuncionesGenerales.validaMensaje(mensaje)) {
							throw new Exception(mensaje.getDescMensaje());
						}
					}
				}
			}	
			oIConexion.ejecutaCommit();
		} catch (Exception e) {
			if (oIConexion != null) {
				oIConexion.ejecutaRollback();
			}
			mensaje.setDescMensaje(UMensajeOperacion.MSJ_4 + mensaje.getDescMensaje());
			UManejadorLog.error("Control: Error al agregar Documento Garantia : " + e.getMessage());
		} finally {
			if (oIConexion != null) {
				oIConexion.cierraConexion();
			}
		}
		return mensaje;
	}
	
	public EMensaje registrarDetalleSolicitudDocumentoGarantia(EGarantia eOGarantia,EDocumentoCarga oEDocumentoCarga){
		IConexion oIConexion = null;
		EMensaje mensaje = new EMensaje();
		DAOGarantia oDAOGarantia= null;
		try {
			oIConexion = FabricaConexion.creaConexion();
			oIConexion.iniciaTransaccion();
			oDAOGarantia= new DAOGarantia(oIConexion);

			mensaje = oDAOGarantia.registrarDetalleSolicitudDocumentoGarantia(eOGarantia, oEDocumentoCarga);
			if (!UFuncionesGenerales.validaMensaje(mensaje)) {
				throw new Exception(mensaje.getDescMensaje());
			}
			
			oIConexion.ejecutaCommit();
		} catch (Exception e) {
			if (oIConexion != null) {
				oIConexion.ejecutaRollback();
			}
			mensaje.setDescMensaje(UMensajeOperacion.MSJ_4 + mensaje.getDescMensaje());
			UManejadorLog.error("Control: Error al agregar Documento Garantia : " + e.getMessage());
		} finally {
			if (oIConexion != null) {
				oIConexion.cierraConexion();
			}
		}
		return mensaje;
	}
	
	public EMensaje registrarInmueblesGarantiaPredios(EGarantia eGarantia){
		IConexion oIConexion = null;
		EMensaje mensaje = new EMensaje();
		DAOGarantia oDAOGarantia= null;
		try {
			oIConexion = FabricaConexion.creaConexion();
			oIConexion.iniciaTransaccion();
			oDAOGarantia= new DAOGarantia(oIConexion);

			mensaje = oDAOGarantia.registrarInmueblesGarantiaPredios(eGarantia);
			if (!UFuncionesGenerales.validaMensaje(mensaje)) {
				throw new Exception(mensaje.getDescMensaje());
			}
			
			oIConexion.ejecutaCommit();
		} catch (Exception e) {
			if (oIConexion != null) {
				oIConexion.ejecutaRollback();
			}
			mensaje.setDescMensaje(UMensajeOperacion.MSJ_4 + mensaje.getDescMensaje());
			UManejadorLog.error("Control: Error al agregar: " + e.getMessage());
		} finally {
			if (oIConexion != null) {
				oIConexion.cierraConexion();
			}
		}
		return mensaje;
	}
	
	public EMensaje registrarContratoGarantiaF7401(EContrato eContrato) {
		IConexion oIConexion = null;
		EMensaje mensaje = new EMensaje();
		DAOGarantia oDAOGarantia= null;
		try {
			oIConexion = FabricaConexion.creaConexion();
			oIConexion.iniciaTransaccion();
			oDAOGarantia= new DAOGarantia(oIConexion);

			mensaje = oDAOGarantia.registrarContratoGarantiaF7401(eContrato);
			if (!UFuncionesGenerales.validaMensaje(mensaje)) {
				throw new Exception(mensaje.getDescMensaje());
			}
			
			oIConexion.ejecutaCommit();
		} catch (Exception e) {
			if (oIConexion != null) {
				oIConexion.ejecutaRollback();
			}
			mensaje.setDescMensaje(UMensajeOperacion.MSJ_4 + mensaje.getDescMensaje());
			UManejadorLog.error("Control: Error al agregar: " + e.getMessage());
		} finally {
			if (oIConexion != null) {
				oIConexion.cierraConexion();
			}
		}
		return mensaje;
	}
	
	public EMensaje registrarContratoyRepresentanteGarantia(EContrato eContrato,List<ERepresentanteCIAContrato> lstRepresentanteCIAContrato) {
		IConexion oIConexion = null;
		EMensaje mensaje = new EMensaje();
		EContrato oEContrato = null;
		DAOGarantia oDAOGarantia= null;
		try {
			oIConexion = FabricaConexion.creaConexion();
			oIConexion.iniciaTransaccion();
			oDAOGarantia= new DAOGarantia(oIConexion);

			mensaje = oDAOGarantia.registrarContratoGarantiaF7401(eContrato);
			if (!UFuncionesGenerales.validaMensaje(mensaje)) {
				throw new Exception(mensaje.getDescMensaje());
			}
			
			
			if(lstRepresentanteCIAContrato != null){
				if(lstRepresentanteCIAContrato.size() > 0){
					oEContrato = oDAOGarantia.buscarUltimaContratoGarantiaGeneradoF7401();
					if(oEContrato != null ){
						for(ERepresentanteCIAContrato obj : lstRepresentanteCIAContrato){
							obj.setNumeroContrato(oEContrato.getNumeroContrato());
							if(obj.getCodigoAccion() == UAccionTabla.INSERTAR && obj.getNumeroContrato()>0){
								mensaje = oDAOGarantia.registrarRepresentanteCompaniaContratoF7433(obj);
								if (!UFuncionesGenerales.validaMensaje(mensaje)) {
									throw new Exception(mensaje.getDescMensaje());
								}
							}else if (obj.getCodigoAccion() == UAccionTabla.ELIMINAR && obj.getNumeroContrato()>0){
								mensaje = oDAOGarantia.eliminarRepresentanteCompaniaContratoF7433(obj);
								if (!UFuncionesGenerales.validaMensaje(mensaje)) {
									throw new Exception(mensaje.getDescMensaje());
								}
							}
						}
					}
					
				}
			}
			
			
	
			oIConexion.ejecutaCommit();
		} catch (Exception e) {
			if (oIConexion != null) {
				oIConexion.ejecutaRollback();
			}
			mensaje.setDescMensaje(UMensajeOperacion.MSJ_4 + mensaje.getDescMensaje());
			UManejadorLog.error("Control: Error al agregar: " + e.getMessage());
		} finally {
			if (oIConexion != null) {
				oIConexion.cierraConexion();
			}
		}
		return mensaje;
	}
	
	public EMensaje registrarRepresentanteCompaniaContratoF7433(ERepresentanteCIAContrato eRepresentanteCiaContrato) {
		IConexion oIConexion = null;
		EMensaje mensaje = new EMensaje();
		DAOGarantia oDAOGarantia= null;
		try {
			oIConexion = FabricaConexion.creaConexion();
			oIConexion.iniciaTransaccion();
			oDAOGarantia= new DAOGarantia(oIConexion);

			mensaje = oDAOGarantia.registrarRepresentanteCompaniaContratoF7433(eRepresentanteCiaContrato);
			if (!UFuncionesGenerales.validaMensaje(mensaje)) {
				throw new Exception(mensaje.getDescMensaje());
			}
			
			oIConexion.ejecutaCommit();
		} catch (Exception e) {
			if (oIConexion != null) {
				oIConexion.ejecutaRollback();
			}
			mensaje.setDescMensaje(UMensajeOperacion.MSJ_4 + mensaje.getDescMensaje());
			UManejadorLog.error("Control: Error al agregar: " + e.getMessage());
		} finally {
			if (oIConexion != null) {
				oIConexion.cierraConexion();
			}
		}
		return mensaje;
	}
	
	public EMensaje agregarSolicitudDocumentoyDesembolsoGarantia(EGarantia eGarantia) {
		IConexion oIConexion = null;
		EMensaje mensaje = new EMensaje();
		DAOGarantia oDAOGarantia= null;
		try {
			oIConexion = FabricaConexion.creaConexion();
			oIConexion.iniciaTransaccion();
			oDAOGarantia= new DAOGarantia(oIConexion);

			mensaje = oDAOGarantia.agregarSolicitudDocumentoyDesembolsoGarantia(eGarantia);
			if (!UFuncionesGenerales.validaMensaje(mensaje)) {
				throw new Exception(mensaje.getDescMensaje());
			}
			
			oIConexion.ejecutaCommit();
		} catch (Exception e) {
			if (oIConexion != null) {
				oIConexion.ejecutaRollback();
			}
			mensaje.setDescMensaje(UMensajeOperacion.MSJ_4 + mensaje.getDescMensaje());
			UManejadorLog.error("Control: Error al agregar: " + e.getMessage());
		} finally {
			if (oIConexion != null) {
				oIConexion.cierraConexion();
			}
		}
		return mensaje;
	}
	
	public EMensaje agregarObservacionTramiteOperativoSolicitud(EOperacionSolicitud eOperacionSolicitud) {
		IConexion oIConexion = null;
		EMensaje mensaje = new EMensaje();
		DAOGarantia oDAOGarantia= null;
		try {
			oIConexion = FabricaConexion.creaConexion();
			oIConexion.iniciaTransaccion();
			oDAOGarantia= new DAOGarantia(oIConexion);

			mensaje = oDAOGarantia.agregarObservacionTramiteOperativoSolicitud(eOperacionSolicitud);
			if (!UFuncionesGenerales.validaMensaje(mensaje)) {
				throw new Exception(mensaje.getDescMensaje());
			}
			
			oIConexion.ejecutaCommit();
		} catch (Exception e) {
			if (oIConexion != null) {
				oIConexion.ejecutaRollback();
			}
			mensaje.setDescMensaje(UMensajeOperacion.MSJ_4 + mensaje.getDescMensaje());
			UManejadorLog.error("Control: Error al agregar: " + e.getMessage());
		} finally {
			if (oIConexion != null) {
				oIConexion.cierraConexion();
			}
		}
		return mensaje;
	}
	
	public EMensaje agregarDetalleFlagRequisitoLegal(EFlagReqLegal eFlagRequisitoLegal)  {
		IConexion oIConexion = null;
		EMensaje mensaje = new EMensaje();
		DAOGarantia oDAOGarantia= null;
		try {
			oIConexion = FabricaConexion.creaConexion();
			oIConexion.iniciaTransaccion();
			oDAOGarantia= new DAOGarantia(oIConexion);

			mensaje = oDAOGarantia.agregarDetalleFlagRequisitoLegal(eFlagRequisitoLegal);
			if (!UFuncionesGenerales.validaMensaje(mensaje)) {
				throw new Exception(mensaje.getDescMensaje());
			}
			
			oIConexion.ejecutaCommit();
		} catch (Exception e) {
			if (oIConexion != null) {
				oIConexion.ejecutaRollback();
			}
			mensaje.setDescMensaje(UMensajeOperacion.MSJ_4 + mensaje.getDescMensaje());
			UManejadorLog.error("Control: Error al agregar: " + e.getMessage());
		} finally {
			if (oIConexion != null) {
				oIConexion.cierraConexion();
			}
		}
		return mensaje;
	}
	
	public EMensaje modificarGarantiaTramite(EGarantiaTramite eGarantiaTramite) {
		IConexion oIConexion = null;
		EMensaje mensaje = new EMensaje();
		DAOGarantia oDAOGarantia= null;
		try {
			oIConexion = FabricaConexion.creaConexion();
			oIConexion.iniciaTransaccion();
			oDAOGarantia= new DAOGarantia(oIConexion);
			
			mensaje = oDAOGarantia.modificarGarantiaTramite(eGarantiaTramite);
			if (!UFuncionesGenerales.validaMensaje(mensaje)) {
				throw new Exception(mensaje.getDescMensaje());
			}
			
			oIConexion.ejecutaCommit();
		} catch (Exception e) {
			if (oIConexion != null) {
				oIConexion.ejecutaRollback();
			}
			mensaje.setDescMensaje(UMensajeOperacion.MSJ_4 + mensaje.getDescMensaje());
			UManejadorLog.error("Control CGarantia: Error al Modificar Trámite garantía : " + e.getMessage());
		} finally {
			if (oIConexion != null) {
				oIConexion.cierraConexion();
			}
		}
		return mensaje;
	}
	
	public EMensaje modificarGarantiaSolicitud(EGarantiaSolicitud eGarantiaSolicitud){
		IConexion oIConexion = null;
		EMensaje mensaje = new EMensaje();
		DAOGarantia oDAOGarantia= null;
		try {
			oIConexion = FabricaConexion.creaConexion();
			oIConexion.iniciaTransaccion();
			oDAOGarantia= new DAOGarantia(oIConexion);
			
			mensaje = oDAOGarantia.modificarGarantiaSolicitud(eGarantiaSolicitud);
			if (!UFuncionesGenerales.validaMensaje(mensaje)) {
				throw new Exception(mensaje.getDescMensaje());
			}
			
			oIConexion.ejecutaCommit();
		} catch (Exception e) {
			if (oIConexion != null) {
				oIConexion.ejecutaRollback();
			}
			mensaje.setDescMensaje(UMensajeOperacion.MSJ_4 + mensaje.getDescMensaje());
			UManejadorLog.error("Control CGarantia: Error al Modificar Trámite garantía : " + e.getMessage());
		} finally {
			if (oIConexion != null) {
				oIConexion.cierraConexion();
			}
		}
		return mensaje;
	}
	
	public EMensaje modificarSolicitudyGenerarAsientoyDocumentacionGarantia(EGarantiaTramite eGarantiaAsientoTramite, EGarantiaSolicitud eGarantiaAsociadaSolicitud,EGarantia eGarantia,EGarantia eSolicitudDesembolsoGarantia){
		IConexion oIConexion = null;
		EMensaje mensaje = new EMensaje();
		DAOGarantia oDAOGarantia= null;
		try {
			oIConexion = FabricaConexion.creaConexion();
			oIConexion.iniciaTransaccion();
			oDAOGarantia= new DAOGarantia(oIConexion);
			
			mensaje = oDAOGarantia.agregarAsientoGarantiaTramite(eGarantiaAsientoTramite);
			if (!UFuncionesGenerales.validaMensaje(mensaje)) {
				throw new Exception(mensaje.getDescMensaje());
			}
			
			mensaje = oDAOGarantia.modificarGarantiaSolicitud(eGarantiaAsociadaSolicitud);
			if (!UFuncionesGenerales.validaMensaje(mensaje)) {
				throw new Exception(mensaje.getDescMensaje());
			}
			
			mensaje = oDAOGarantia.modificarGarantiaMantenimiento(eGarantia);
			if (!UFuncionesGenerales.validaMensaje(mensaje)) {
				throw new Exception(mensaje.getDescMensaje());
			}
			
			mensaje = oDAOGarantia.agregarSolicitudDocumentoyDesembolsoGarantia(eSolicitudDesembolsoGarantia);
			if (!UFuncionesGenerales.validaMensaje(mensaje)) {
				throw new Exception(mensaje.getDescMensaje());
			}
			
			oIConexion.ejecutaCommit();
		} catch (Exception e) {
			if (oIConexion != null) {
				oIConexion.ejecutaRollback();
			}
			mensaje.setDescMensaje(UMensajeOperacion.MSJ_4 + mensaje.getDescMensaje());
			UManejadorLog.error("Control CGarantia: Error: " + e.getMessage());
		} finally {
			if (oIConexion != null) {
				oIConexion.cierraConexion();
			}
		}
		return mensaje;
	}
	
	public EMensaje modificarGarantiaSolicitudSIAF(EGarantiaSolicitud eGarantiaSolicitud,EGarantiaDetalleSolicitud eGarantiaDetalleSolicitud){
		IConexion oIConexion = null;
		EMensaje mensaje = new EMensaje();
		DAOGarantia oDAOGarantia= null;
		try {
			oIConexion = FabricaConexion.creaConexion();
			oIConexion.iniciaTransaccion();
			oDAOGarantia= new DAOGarantia(oIConexion);
			
			mensaje = oDAOGarantia.modificarGarantiaSolicitudSIAF(eGarantiaSolicitud,eGarantiaDetalleSolicitud);
			if (!UFuncionesGenerales.validaMensaje(mensaje)) {
				throw new Exception(mensaje.getDescMensaje());
			}
			
			oIConexion.ejecutaCommit();
		} catch (Exception e) {
			if (oIConexion != null) {
				oIConexion.ejecutaRollback();
			}
			mensaje.setDescMensaje(UMensajeOperacion.MSJ_4 + mensaje.getDescMensaje());
			UManejadorLog.error("Control CGarantia: Error al Modificar Trámite garantía : " + e.getMessage());
		} finally {
			if (oIConexion != null) {
				oIConexion.cierraConexion();
			}
		}
		return mensaje;
	}
	
	public EMensaje actualizarGarantiaSolicitud(EGarantiaSolicitud eGarantiaSolicitud,
			EGarantiaDetalleSolicitud eGarantiaDetalleSolicitud,EGarantiaSolicitud oEGarantiaAsociadaSolicitud){
		IConexion oIConexion = null;
		EMensaje mensaje = new EMensaje();
		DAOGarantia oDAOGarantia= null;
		try {
			oIConexion = FabricaConexion.creaConexion();
			oIConexion.iniciaTransaccion();
			oDAOGarantia= new DAOGarantia(oIConexion);
			
			mensaje = oDAOGarantia.modificarGarantiaSolicitudSIAF(eGarantiaSolicitud,eGarantiaDetalleSolicitud);
			if (!UFuncionesGenerales.validaMensaje(mensaje)) {
				throw new Exception(mensaje.getDescMensaje());
			}
			
			mensaje = oDAOGarantia.modificarGarantiaSolicitud(oEGarantiaAsociadaSolicitud);
			if (!UFuncionesGenerales.validaMensaje(mensaje)) {
				throw new Exception(mensaje.getDescMensaje());
			}
			
			oIConexion.ejecutaCommit();
		} catch (Exception e) {
			if (oIConexion != null) {
				oIConexion.ejecutaRollback();
			}
			mensaje.setDescMensaje(UMensajeOperacion.MSJ_4 + mensaje.getDescMensaje());
			UManejadorLog.error("Control CGarantia: Error al Modificar Garantia Solicitud : " + e.getMessage());
		} finally {
			if (oIConexion != null) {
				oIConexion.cierraConexion();
			}
		}
		return mensaje;
	}
	
	public List<EGarantiaSolicitud> listarGarantiaPorLiberar(int codigo, String descripcion){
		IConexion oIConexion = null;
		List<EGarantiaSolicitud> resultado = null;
		DAOGarantia oDAOGarantia= null;
		try {
			oIConexion = FabricaConexion.creaConexion();			
			oDAOGarantia = new DAOGarantia(oIConexion);
			resultado = oDAOGarantia.listarGarantiaPorLiberar(codigo, descripcion);			
		} catch (Exception e) {
			UManejadorLog.error("Control: Error al listar Garantia por Liberar " + e.getMessage());
		} finally {
			if (oIConexion != null) {
				oIConexion.cierraConexion();
			}
		}
		return resultado;
	}
	
	public List<EGarantiaSolicitud> listarGarantiaLiberada(int codigo, String descripcion){
		IConexion oIConexion = null;
		List<EGarantiaSolicitud> resultado = null;
		DAOGarantia oDAOGarantia= null;
		try {
			oIConexion = FabricaConexion.creaConexion();			
			oDAOGarantia = new DAOGarantia(oIConexion);
			resultado = oDAOGarantia.listarGarantiaLiberada(codigo, descripcion);			
		} catch (Exception e) {
			UManejadorLog.error("Control: Error al listar Garantia Liberadas " + e.getMessage());
		} finally {
			if (oIConexion != null) {
				oIConexion.cierraConexion();
			}
		}
		return resultado;
	}
	
	public List<EOperacionDocumento> listarSolicitudDocumentoGarantia(int codigo, String descripcion,EGarantia eOGarantia) {
		IConexion oIConexion = null;
		List<EOperacionDocumento> resultado = null;
		DAOGarantia oDAOGarantia= null;
		try {
			oIConexion = FabricaConexion.creaConexion();			
			oDAOGarantia = new DAOGarantia(oIConexion);
			resultado = oDAOGarantia.listarSolicitudDocumentoGarantia(codigo,descripcion,eOGarantia);			
		} catch (Exception e) {
			UManejadorLog.error("Control: Error al listar Documentos de Garantias " + e.getMessage());
		} finally {
			if (oIConexion != null) {
				oIConexion.cierraConexion();
			}
		}
		return resultado;
	}
	
	public List<EOperacionDocumento> listarSolicitudDocumentoXNroGarantia(int codigo, String descripcion,EGarantia eOGarantia) {
		IConexion oIConexion = null;
		List<EOperacionDocumento> resultado = null;
		DAOGarantia oDAOGarantia= null;
		try {
			oIConexion = FabricaConexion.creaConexion();			
			oDAOGarantia = new DAOGarantia(oIConexion);
			resultado = oDAOGarantia.listarSolicitudDocumentoXNroGarantia(codigo,descripcion,eOGarantia);			
		} catch (Exception e) {
			UManejadorLog.error("Control: Error al listar Documentos de Garantias " + e.getMessage());
		} finally {
			if (oIConexion != null) {
				oIConexion.cierraConexion();
			}
		}
		return resultado;
	}
	
	public List<EOperacionDocumento> listarSolicitudDocumentoGarantiaDetalle(EOperacionDocumento eOperacionDocumento){
		IConexion oIConexion = null;
		List<EOperacionDocumento> resultado = null;
		DAOGarantia oDAOGarantia= null;
		try {
			oIConexion = FabricaConexion.creaConexion();			
			oDAOGarantia = new DAOGarantia(oIConexion);
			resultado = oDAOGarantia.listarSolicitudDocumentoGarantiaDetalle(eOperacionDocumento);			
		} catch (Exception e) {
			UManejadorLog.error("Control: Error al listar Documentos de Garantias " + e.getMessage());
		} finally {
			if (oIConexion != null) {
				oIConexion.cierraConexion();
			}
		}
		return resultado;
	}
	
	public List<EOperacionDocumento> listarSolicitudDesembolsoGarantia(int codigo, String descripcion,EGarantia eOGarantia){
		IConexion oIConexion = null;
		List<EOperacionDocumento> resultado = null;
		DAOGarantia oDAOGarantia= null;
		try {
			oIConexion = FabricaConexion.creaConexion();			
			oDAOGarantia = new DAOGarantia(oIConexion);
			resultado = oDAOGarantia.listarSolicitudDesembolsoGarantia(codigo,descripcion,eOGarantia);			
		} catch (Exception e) {
			UManejadorLog.error("Control: Error al listar: " + e.getMessage());
		} finally {
			if (oIConexion != null) {
				oIConexion.cierraConexion();
			}
		}
		return resultado;
	}
	
	public List<EOperacionDocumento> listarSolicitudDesembolsoXNroGarantia(int codigo, String descripcion,EGarantia eOGarantia){
		IConexion oIConexion = null;
		List<EOperacionDocumento> resultado = null;
		DAOGarantia oDAOGarantia= null;
		try {
			oIConexion = FabricaConexion.creaConexion();			
			oDAOGarantia = new DAOGarantia(oIConexion);
			resultado = oDAOGarantia.listarSolicitudDesembolsoXNroGarantia(codigo,descripcion,eOGarantia);			
		} catch (Exception e) {
			UManejadorLog.error("Control: Error al listar: " + e.getMessage());
		} finally {
			if (oIConexion != null) {
				oIConexion.cierraConexion();
			}
		}
		return resultado;
	}
	
	public List<EOperacionSolicitud> listarSolicitudTramiteOperativoLegal(int codigo, String descripcion,EUsuario eUsuario){
		IConexion oIConexion = null;
		List<EOperacionSolicitud> resultado = null;
		DAOGarantia oDAOGarantia= null;
		try {
			oIConexion = FabricaConexion.creaConexion();			
			oDAOGarantia = new DAOGarantia(oIConexion);
			resultado = oDAOGarantia.listarSolicitudTramiteOperativoLegal(codigo,descripcion,eUsuario);			
		} catch (Exception e) {
			UManejadorLog.error("Control: Error al listar: " + e.getMessage());
		} finally {
			if (oIConexion != null) {
				oIConexion.cierraConexion();
			}
		}
		return resultado;
	}
	
	public List<EGarantia> listarInmueblesGarantiaPredios(long codigoGarantia){
		IConexion oIConexion = null;
		List<EGarantia> resultado = null;
		DAOGarantia oDAOGarantia= null;
		try {
			oIConexion = FabricaConexion.creaConexion();			
			oDAOGarantia = new DAOGarantia(oIConexion);
			resultado = oDAOGarantia.listarInmueblesGarantiaPredios(codigoGarantia);			
		} catch (Exception e) {
			UManejadorLog.error("Control: Error al listar: " + e.getMessage());
		} finally {
			if (oIConexion != null) {
				oIConexion.cierraConexion();
			}
		}
		return resultado;
	}
	
	public List<EOperacionSolicitud> listarDetalleFlagsSolicitudCredito(long codigoSolicitudCredito){
		IConexion oIConexion = null;
		List<EOperacionSolicitud> resultado = null;
		DAOGarantia oDAOGarantia= null;
		try {
			oIConexion = FabricaConexion.creaConexion();			
			oDAOGarantia = new DAOGarantia(oIConexion);
			resultado = oDAOGarantia.listarDetalleFlagsSolicitudCredito(codigoSolicitudCredito);			
		} catch (Exception e) {
			UManejadorLog.error("Control: Error al listar: " + e.getMessage());
		} finally {
			if (oIConexion != null) {
				oIConexion.cierraConexion();
			}
		}
		return resultado;
	}
	
	public List<EFirmanteSolicitud> listarFirmantesDocumentoSolicitud(long codigoSolicitudCredito){
		IConexion oIConexion = null;
		List<EFirmanteSolicitud> resultado = null;
		DAOGarantia oDAOGarantia= null;
		try {
			oIConexion = FabricaConexion.creaConexion();			
			oDAOGarantia = new DAOGarantia(oIConexion);
			resultado = oDAOGarantia.listarFirmantesDocumentoSolicitud(codigoSolicitudCredito);
			if(resultado != null){
				for(int i = 0 ; i < resultado.size() ; i++){
					if(resultado.get(i).getHoraReg().equals("00:00:00") && resultado.get(i).getFechaFirmaSocio() == null){
						resultado.get(i).setHoraReg("");
					}
				}
			}
		} catch (Exception e) {
			UManejadorLog.error("Control: Error al listar: " + e.getMessage());
		} finally {
			if (oIConexion != null) {
				oIConexion.cierraConexion();
			}
		}
		return resultado;
	}
	
	public List<EGarantiaSolicitud> listarGarantiaAsociadaSolicitudFirmante(long codigoSolicitud){
		IConexion oIConexion = null;
		List<EGarantiaSolicitud> resultado = null;
		DAOGarantia oDAOGarantia= null;
		try {
			oIConexion = FabricaConexion.creaConexion();			
			oDAOGarantia = new DAOGarantia(oIConexion);
			resultado = oDAOGarantia.listarGarantiaAsociadaSolicitudFirmante(codigoSolicitud);			
		} catch (Exception e) {
			UManejadorLog.error("Control: Error al listar: " + e.getMessage());
		} finally {
			if (oIConexion != null) {
				oIConexion.cierraConexion();
			}
		}
		return resultado;
	}
	
	public List<EComprobanteGarantia> listarComprobanteGarantiaSolicitud(long codigoSolicitud,int secuenciaGarantia){
		IConexion oIConexion = null;
		List<EComprobanteGarantia> resultado = null;
		DAOGarantia oDAOGarantia= null;
		try {
			oIConexion = FabricaConexion.creaConexion();			
			oDAOGarantia = new DAOGarantia(oIConexion);
			resultado = oDAOGarantia.listarComprobanteGarantiaSolicitud(codigoSolicitud,secuenciaGarantia);			
		} catch (Exception e) {
			UManejadorLog.error("Control: Error al listar: " + e.getMessage());
		} finally {
			if (oIConexion != null) {
				oIConexion.cierraConexion();
			}
		}
		return resultado;
	}
	
	public List<ERepresentanteCIA> listarMaestroRepresentantesCompania(){
		IConexion oIConexion = null;
		List<ERepresentanteCIA> resultado = null;
		DAOGarantia oDAOGarantia= null;
		try {
			oIConexion = FabricaConexion.creaConexion();			
			oDAOGarantia = new DAOGarantia(oIConexion);
			resultado = oDAOGarantia.listarMaestroRepresentantesCompania();			
		} catch (Exception e) {
			UManejadorLog.error("Control: Error al listar: " + e.getMessage());
		} finally {
			if (oIConexion != null) {
				oIConexion.cierraConexion();
			}
		}
		return resultado;
	}
	
	public List<ERepresentanteCIAContrato> listarRepresentantesCompaniaxContrato(int tipoContrato,long numeroContrato){
		IConexion oIConexion = null;
		List<ERepresentanteCIAContrato> resultado = null;
		DAOGarantia oDAOGarantia= null;
		try {
			oIConexion = FabricaConexion.creaConexion();			
			oDAOGarantia = new DAOGarantia(oIConexion);
			resultado = oDAOGarantia.listarRepresentantesCompaniaxContrato(tipoContrato,numeroContrato);			
		} catch (Exception e) {
			UManejadorLog.error("Control: Error al listar: " + e.getMessage());
		} finally {
			if (oIConexion != null) {
				oIConexion.cierraConexion();
			}
		}
		return resultado;
	}
	
	public List<EFirmanteSolicitud> listarDetalleFirmantesContratoGarantia(EFirmanteSolicitud eFirmanteContrato){
		IConexion oIConexion = null;
		List<EFirmanteSolicitud> resultado = null;
		DAOGarantia oDAOGarantia= null;
		try {
			oIConexion = FabricaConexion.creaConexion();			
			oDAOGarantia = new DAOGarantia(oIConexion);
			resultado = oDAOGarantia.listarDetalleFirmantesContratoGarantia(eFirmanteContrato);			
		} catch (Exception e) {
			UManejadorLog.error("Control: Error al listar: " + e.getMessage());
		} finally {
			if (oIConexion != null) {
				oIConexion.cierraConexion();
			}
		}
		return resultado;
	}
	
	public List<EOperacionSolicitud> listarObservacionSolicitudTramiteOperativoLegal(EOperacionSolicitud eOperacionSolicitud){
		IConexion oIConexion = null;
		List<EOperacionSolicitud> resultado = null;
		DAOGarantia oDAOGarantia= null;
		try {
			oIConexion = FabricaConexion.creaConexion();			
			oDAOGarantia = new DAOGarantia(oIConexion);
			resultado = oDAOGarantia.listarObservacionSolicitudTramiteOperativoLegal(eOperacionSolicitud);			
		} catch (Exception e) {
			UManejadorLog.error("Control: Error al listar: " + e.getMessage());
		} finally {
			if (oIConexion != null) {
				oIConexion.cierraConexion();
			}
		}
		return resultado;
	}
	
	public List<EFlagReqLegal> listarDetalleFlagRequisitoLegal(long numeroSolicitud){
		IConexion oIConexion = null;
		List<EFlagReqLegal> resultado = null;
		DAOGarantia oDAOGarantia= null;
		try {
			oIConexion = FabricaConexion.creaConexion();			
			oDAOGarantia = new DAOGarantia(oIConexion);
			resultado = oDAOGarantia.listarDetalleFlagRequisitoLegal(numeroSolicitud);			
		} catch (Exception e) {
			UManejadorLog.error("Control: Error al listar: " + e.getMessage());
		} finally {
			if (oIConexion != null) {
				oIConexion.cierraConexion();
			}
		}
		return resultado;
	}
	
	public EMensaje eliminarDetalleSolicitudDocumentoGarantia(EGarantia eGarantia) {
		IConexion oIConexion = null;
		EMensaje mensaje = new EMensaje();
		DAOGarantia oDAOGarantia= null;
		try {
			oIConexion = FabricaConexion.creaConexion();
			oIConexion.iniciaTransaccion();
			oDAOGarantia= new DAOGarantia(oIConexion);
			
			mensaje = oDAOGarantia.eliminarDetalleSolicitudDocumentoGarantia(eGarantia) ;
			if (!UFuncionesGenerales.validaMensaje(mensaje)) {
				throw new Exception(mensaje.getDescMensaje());
			}
			
			oIConexion.ejecutaCommit();
		} catch (Exception e) {
			if (oIConexion != null) {
				oIConexion.ejecutaRollback();
			}
			mensaje.setDescMensaje(UMensajeOperacion.MSJ_4 + mensaje.getDescMensaje());
			UManejadorLog.error("Control: Error al eliminar Documento: " + e.getMessage());
		} finally {
			if (oIConexion != null) {
				oIConexion.cierraConexion();
			}
		}
		return mensaje;
	}
	
	public EMensaje eliminarInmuebleGarantiaPredios(EGarantia eGarantia) {
		IConexion oIConexion = null;
		EMensaje mensaje = new EMensaje();
		DAOGarantia oDAOGarantia= null;
		try {
			oIConexion = FabricaConexion.creaConexion();
			oIConexion.iniciaTransaccion();
			oDAOGarantia= new DAOGarantia(oIConexion);
			
			mensaje = oDAOGarantia.eliminarInmuebleGarantiaPredios(eGarantia) ;
			if (!UFuncionesGenerales.validaMensaje(mensaje)) {
				throw new Exception(mensaje.getDescMensaje());
			}
			
			oIConexion.ejecutaCommit();
		} catch (Exception e) {
			if (oIConexion != null) {
				oIConexion.ejecutaRollback();
			}
			mensaje.setDescMensaje(UMensajeOperacion.MSJ_4 + mensaje.getDescMensaje());
			UManejadorLog.error("Control: Error al eliminar : " + e.getMessage());
		} finally {
			if (oIConexion != null) {
				oIConexion.cierraConexion();
			}
		}
		return mensaje;
	}
	
	public EMensaje eliminarRepresentanteCompaniaContratoF7433(ERepresentanteCIAContrato eRepresentanteCiaContrato) {
		IConexion oIConexion = null;
		EMensaje mensaje = new EMensaje();
		DAOGarantia oDAOGarantia= null;
		try {
			oIConexion = FabricaConexion.creaConexion();
			oIConexion.iniciaTransaccion();
			oDAOGarantia= new DAOGarantia(oIConexion);
			
			mensaje = oDAOGarantia.eliminarRepresentanteCompaniaContratoF7433(eRepresentanteCiaContrato) ;
			if (!UFuncionesGenerales.validaMensaje(mensaje)) {
				throw new Exception(mensaje.getDescMensaje());
			}
			
			oIConexion.ejecutaCommit();
		} catch (Exception e) {
			if (oIConexion != null) {
				oIConexion.ejecutaRollback();
			}
			mensaje.setDescMensaje(UMensajeOperacion.MSJ_4 + mensaje.getDescMensaje());
			UManejadorLog.error("Control: Error al eliminar : " + e.getMessage());
		} finally {
			if (oIConexion != null) {
				oIConexion.cierraConexion();
			}
		}
		return mensaje;
	}
	
	public EMensaje eliminarDetalleFlagRequisitoLegal(EFlagReqLegal eFlagRequisitoLegal) {
		IConexion oIConexion = null;
		EMensaje mensaje = new EMensaje();
		DAOGarantia oDAOGarantia= null;
		try {
			oIConexion = FabricaConexion.creaConexion();
			oIConexion.iniciaTransaccion();
			oDAOGarantia= new DAOGarantia(oIConexion);
			
			mensaje = oDAOGarantia.eliminarDetalleFlagRequisitoLegal(eFlagRequisitoLegal) ;
			if (!UFuncionesGenerales.validaMensaje(mensaje)) {
				throw new Exception(mensaje.getDescMensaje());
			}
			
			oIConexion.ejecutaCommit();
		} catch (Exception e) {
			if (oIConexion != null) {
				oIConexion.ejecutaRollback();
			}
			mensaje.setDescMensaje(UMensajeOperacion.MSJ_4 + mensaje.getDescMensaje());
			UManejadorLog.error("Control: Error al eliminar : " + e.getMessage());
		} finally {
			if (oIConexion != null) {
				oIConexion.cierraConexion();
			}
		}
		return mensaje;
	}
	
	public List<EGarantiaSolicitud> listarGarantiaConCreditoVigente(int codigo, String descripcion){
		IConexion oIConexion = null;
		List<EGarantiaSolicitud> resultado = null;
		DAOGarantia oDAOGarantia= null;
		try {
			oIConexion = FabricaConexion.creaConexion();			
			oDAOGarantia = new DAOGarantia(oIConexion);
			resultado = oDAOGarantia.listarGarantiaConCreditoVigente(codigo, descripcion);			
		} catch (Exception e) {
			UManejadorLog.error("Control: Error al listar Garantia por Liberar " + e.getMessage());
		} finally {
			if (oIConexion != null) {
				oIConexion.cierraConexion();
			}
		}
		return resultado;
	}
	
	public List<EGarantia> listarGarantiaVinculada(EGarantia eGarantia){
		IConexion oIConexion = null;
		List<EGarantia> resultado = null;
		DAOGarantia oDAOGarantia= null;
		try {
			oIConexion = FabricaConexion.creaConexion();			
			oDAOGarantia = new DAOGarantia(oIConexion);
			resultado = oDAOGarantia.listarGarantiaVinculada(eGarantia);			
		} catch (Exception e) {
			UManejadorLog.error("Control: Error al listar  " + e.getMessage());
		} finally {
			if (oIConexion != null) {
				oIConexion.cierraConexion();
			}
		}
		return resultado;
	}
	
	public List<EGarantia> listarGarantiaDetalle(EGarantia eGarantia ) {
		IConexion oIConexion = null;
		List<EGarantia> resultado = null;
		DAOGarantia oDAOGarantia= null;
		try {
			oIConexion = FabricaConexion.creaConexion();			
			oDAOGarantia = new DAOGarantia(oIConexion);
			resultado = oDAOGarantia.listarGarantiaDetalle(eGarantia);			
		} catch (Exception e) {
			UManejadorLog.error("Control: Error al listar  " + e.getMessage());
		} finally {
			if (oIConexion != null) {
				oIConexion.cierraConexion();
			}
		}
		return resultado;
	}
	
	public List<EAsignacionContratoGarantia> listarCreditosAsociadosGarantia(long codigoGarantia){
		IConexion oIConexion = null;
		List<EAsignacionContratoGarantia> resultado = null;
		DAOGarantia oDAOGarantia= null;
		try {
			oIConexion = FabricaConexion.creaConexion();			
			oDAOGarantia = new DAOGarantia(oIConexion);
			resultado = oDAOGarantia.listarCreditosAsociadosGarantia(codigoGarantia);			
			
		} catch (Exception e) {
			UManejadorLog.error("Control: Error al listar  " + e.getMessage());
		} finally {
			if (oIConexion != null) {
				oIConexion.cierraConexion();
			}
		}
		return resultado;
	}
	
	public EMensaje eliminarPoliza(EPoliza ePoliza) {
		IConexion oIConexion = null;
		EMensaje mensaje = new EMensaje();
		DAOGarantia oDAOGarantia= null;
		try {
			oIConexion = FabricaConexion.creaConexion();
			oIConexion.iniciaTransaccion();
			oDAOGarantia= new DAOGarantia(oIConexion);
			
			mensaje = oDAOGarantia.eliminarPoliza(ePoliza) ;
			if (!UFuncionesGenerales.validaMensaje(mensaje)) {
				throw new Exception(mensaje.getDescMensaje());
			}
			
			oIConexion.ejecutaCommit();
		} catch (Exception e) {
			if (oIConexion != null) {
				oIConexion.ejecutaRollback();
			}
			mensaje.setDescMensaje(UMensajeOperacion.MSJ_4 + mensaje.getDescMensaje());
			UManejadorLog.error("Control: Error al eliminar Poliza: " + e.getMessage());
		} finally {
			if (oIConexion != null) {
				oIConexion.cierraConexion();
			}
		}
		return mensaje;
	}
	
	public List<EAsignacionContratoGarantia> listarClientesGarantizados(EGarantia eGarantia ) {
		IConexion oIConexion = null;
		List<EAsignacionContratoGarantia> resultado = null;
		DAOGarantia oDAOGarantia= null;
		try {
			oIConexion = FabricaConexion.creaConexion();			
			oDAOGarantia = new DAOGarantia(oIConexion);
			resultado = oDAOGarantia.listarClientesGarantizados(eGarantia);			
		} catch (Exception e) {
			UManejadorLog.error("Control: Error al listar  " + e.getMessage());
		} finally {
			if (oIConexion != null) {
				oIConexion.cierraConexion();
			}
		}
		return resultado;
	}
	
	public List<EGarantiaTramite> listarHistoricoTramiteGarantia(long codigoGarantia){
		IConexion oIConexion = null;
		List<EGarantiaTramite> resultado = null;
		DAOGarantia oDAOGarantia= null;
		try {
			oIConexion = FabricaConexion.creaConexion();			
			oDAOGarantia = new DAOGarantia(oIConexion);
			resultado = oDAOGarantia.listarHistoricoTramiteGarantia(codigoGarantia);			
		} catch (Exception e) {
			UManejadorLog.error("Control: Error al Historico Tramite Garantia" + e.getMessage());
		} finally {
			if (oIConexion != null) {
				oIConexion.cierraConexion();
			}
		}
		return resultado;
	}
	
	public List<EGarantiaTramite> listarAsientosTramiteGarantia(long codigoGarantia){
		IConexion oIConexion = null;
		List<EGarantiaTramite> resultado = null;
		DAOGarantia oDAOGarantia= null;
		try {
			oIConexion = FabricaConexion.creaConexion();			
			oDAOGarantia = new DAOGarantia(oIConexion);
			resultado = oDAOGarantia.listarAsientosTramiteGarantia(codigoGarantia);			
		} catch (Exception e) {
			UManejadorLog.error("Control: Error al Historico Tramite Garantia" + e.getMessage());
		} finally {
			if (oIConexion != null) {
				oIConexion.cierraConexion();
			}
		}
		return resultado;
	}
	
	public List<EGarantiaSolicitud> listarGarantiaPorConstituir(int codigo, String descripcion){
		IConexion oIConexion = null;
		List<EGarantiaSolicitud> resultado = null;
		DAOGarantia oDAOGarantia= null;
		try {
			oIConexion = FabricaConexion.creaConexion();			
			oDAOGarantia = new DAOGarantia(oIConexion);
			resultado = oDAOGarantia.listarGarantiaPorConstituir(codigo, descripcion);			
		} catch (Exception e) {
			UManejadorLog.error("Control: Error al listar solicitud de credito " + e.getMessage());
		} finally {
			if (oIConexion != null) {
				oIConexion.cierraConexion();
			}
		}
		return resultado;
	}
	
	public List<EGarantiaSolicitud> listarHistoricoGarantiaSolicitud(int codigo, String descripcion){
		IConexion oIConexion = null;
		List<EGarantiaSolicitud> resultado = null;
		DAOGarantia oDAOGarantia= null;
		try {
			oIConexion = FabricaConexion.creaConexion();			
			oDAOGarantia = new DAOGarantia(oIConexion);
			resultado = oDAOGarantia.listarHistoricoGarantiaSolicitud(codigo, descripcion);			
		} catch (Exception e) {
			UManejadorLog.error("Control: Error al listar Historico Garantia Solicitud  " + e.getMessage());
		} finally {
			if (oIConexion != null) {
				oIConexion.cierraConexion();
			}
		}
		return resultado;
	}
	
	public EMensaje agregaGarantiaPendienteRegistro(EGarantiaSolicitud eOGarantiaSolicitud) {
		IConexion oIConexion = null;
		EMensaje mensaje = new EMensaje();
		DAOGarantia oDAOGarantia= null;
		try {
			oIConexion = FabricaConexion.creaConexion();
			oIConexion.iniciaTransaccion();
			oDAOGarantia= new DAOGarantia(oIConexion);
			
			mensaje = oDAOGarantia.agregaGarantiaPendienteRegistro(eOGarantiaSolicitud);
			if (!UFuncionesGenerales.validaMensaje(mensaje)) {
				throw new Exception(mensaje.getDescMensaje());
			}
			
			oIConexion.ejecutaCommit();
		} catch (Exception e) {
			if (oIConexion != null) {
				oIConexion.ejecutaRollback();
			}
			mensaje.setDescMensaje(UMensajeOperacion.MSJ_4 + mensaje.getDescMensaje());
			UManejadorLog.error("Control CGarantia: Error al agregar Garantía Pendiente de Registro : " + e.getMessage());
		} finally {
			if (oIConexion != null) {
				oIConexion.cierraConexion();
			}
		}
		return mensaje;
	}
	
	public List<EGarantia> listarGarantiaVigente(int codigo, String descripcion){
		IConexion oIConexion = null;
		List<EGarantia> resultado = null;
		DAOGarantia oDAOGarantia= null;
		try {
			oIConexion = FabricaConexion.creaConexion();			
			oDAOGarantia = new DAOGarantia(oIConexion);
			resultado = oDAOGarantia.listarGarantiaVigente(codigo, descripcion);			
		} catch (Exception e) {
			UManejadorLog.error("Control: Error al listar Garantias Vigentes " + e.getMessage());
		} finally {
			if (oIConexion != null) {
				oIConexion.cierraConexion();
			}
		}
		return resultado;
	}
	
	public List<EGarantia> listarGarantia(int codigo, String descripcion){
		IConexion oIConexion = null;
		List<EGarantia> resultado = null;
		DAOGarantia oDAOGarantia= null;
		try {
			oIConexion = FabricaConexion.creaConexion();			
			oDAOGarantia = new DAOGarantia(oIConexion);
			resultado = oDAOGarantia.listarGarantia(codigo, descripcion);			
		} catch (Exception e) {
			UManejadorLog.error("Control: Error al listar Garantias " + e.getMessage());
		} finally {
			if (oIConexion != null) {
				oIConexion.cierraConexion();
			}
		}
		return resultado;
	}
	
	public List<EGarantiaSolicitud> listarSolicitudAsociadaGarantia(int codigo, String descripcion){
		IConexion oIConexion = null;
		List<EGarantiaSolicitud> resultado = null;
		DAOGarantia oDAOGarantia= null;
		try {
			oIConexion = FabricaConexion.creaConexion();
			oDAOGarantia = new DAOGarantia(oIConexion);
			resultado = oDAOGarantia.listarSolicitudAsociadaGarantia(codigo, descripcion);			
		} catch (Exception e) {
			UManejadorLog.error("Control: Error al listar. " + e.getMessage());
		} finally {
			if (oIConexion != null) {
				oIConexion.cierraConexion();
			}
		}
		return resultado;
	}
	
	public List<EGarantiaSolicitud> listarGarantiaSolicitud(long numeroSolicitud){
		IConexion oIConexion = null;
		List<EGarantiaSolicitud> resultado = null;
		DAOGarantia oDAOGarantia= null;
		try {
			oIConexion = FabricaConexion.creaConexion();			
			oDAOGarantia = new DAOGarantia(oIConexion);
			resultado = oDAOGarantia.listarGarantiaSolicitud(numeroSolicitud);			
		} catch (Exception e) {
			UManejadorLog.error("Control: Error al listar garantia " + e.getMessage());
		} finally {
			if (oIConexion != null) {
				oIConexion.cierraConexion();
			}
		}
		return resultado;
	}
	
	public List<EPoliza> listarPolizaSeguro(int codigo, String descripcion, String descripcion2){
		IConexion oIConexion = null;
		List<EPoliza> resultado = null;
		DAOGarantia oDAOGarantia= null;
		try {
			oIConexion = FabricaConexion.creaConexion();			
			oDAOGarantia = new DAOGarantia(oIConexion);
			resultado = oDAOGarantia.listarPolizaSeguro(codigo, descripcion,descripcion2);			
		} catch (Exception e) {
			UManejadorLog.error("Control: Error al listar solicitud de credito " + e.getMessage());
		} finally {
			if (oIConexion != null) {
				oIConexion.cierraConexion();
			}
		}
		return resultado;
	}
	

	public List<EPoliza> buscarPolizaSeguro(EPoliza ePoliza){
		IConexion oIConexion = null;
		List<EPoliza> resultado = null;
		DAOGarantia oDAOGarantia= null;
		try {
			oIConexion = FabricaConexion.creaConexion();			
			oDAOGarantia = new DAOGarantia(oIConexion);
			resultado = oDAOGarantia.buscarPolizaSeguro(ePoliza);			
		} catch (Exception e) {
			UManejadorLog.error("Control: Error al obtener Póliza Seguro " + e.getMessage());
		} finally {
			if (oIConexion != null) {
				oIConexion.cierraConexion();
			}
		}
		return resultado;
	}
	
	public EPoliza buscarPoliza(EPoliza ePoliza){
		IConexion oIConexion = null;
		EPoliza resultado = null;
		DAOGarantia oDAOGarantia= null;
		try {
			oIConexion = FabricaConexion.creaConexion();			
			oDAOGarantia = new DAOGarantia(oIConexion);
			resultado = oDAOGarantia.buscarPoliza(ePoliza);			
		} catch (Exception e) {
			UManejadorLog.error("Control: Error al obtener Póliza " + e.getMessage());
		} finally {
			if (oIConexion != null) {
				oIConexion.cierraConexion();
			}
		}
		return resultado;
	}
	
	public EGarantia buscarUltimaGarantiaGenerada(){
		IConexion oIConexion = null;
		EGarantia resultado = null;
		DAOGarantia oDAOGarantia= null;
		try {
			oIConexion = FabricaConexion.creaConexion();			
			oDAOGarantia = new DAOGarantia(oIConexion);
			resultado = oDAOGarantia.buscarUltimaGarantiaGenerada();			
		} catch (Exception e) {
			UManejadorLog.error("Control: Error al obtener: " + e.getMessage());
		} finally {
			if (oIConexion != null) {
				oIConexion.cierraConexion();
			}
		}
		return resultado;
	}
	
	public EGarantiaSolicitud buscarSolicitudxGarantia(long codigoGarantia){
		IConexion oIConexion = null;
		EGarantiaSolicitud resultado = null;
		DAOGarantia oDAOGarantia= null;
		try {
			oIConexion = FabricaConexion.creaConexion();			
			oDAOGarantia = new DAOGarantia(oIConexion);
			resultado = oDAOGarantia.buscarSolicitudxGarantia(codigoGarantia);			
		} catch (Exception e) {
			UManejadorLog.error("Control: Error al obtener: " + e.getMessage());
		} finally {
			if (oIConexion != null) {
				oIConexion.cierraConexion();
			}
		}
		return resultado;
	}
	
	public EGarantia buscarAnexoGarantia(long codigoGarantia){
		IConexion oIConexion = null;
		EGarantia resultado = null;
		EPersona ePersona = null;
		List<EPersona> lstPersona = null;
		DAOGarantia oDAOGarantia= null;
		try {
			oIConexion = FabricaConexion.creaConexion();			
			oDAOGarantia = new DAOGarantia(oIConexion);
			resultado = oDAOGarantia.buscarAnexoGarantia(codigoGarantia);	
			if(resultado != null){
				lstPersona = new ArrayList<EPersona>();
				for(int i=1;i<6;i++){
					ePersona = new EPersona();
					ePersona.setCodigo(i== 1 ? resultado.getCodigoPropietario2(): 
						               i== 2 ? resultado.getCodigoPropietario3():
						               i== 3 ? resultado.getCodigoPropietario4():
						               i== 4 ? resultado.getCodigoPropietario5():
						               i== 5 ? resultado.getCodigoPropietario6():0);
					ePersona.setNombreCorte(i== 1 ? resultado.getDescripcionPropietario2(): 
			               i== 2 ? resultado.getDescripcionPropietario3():
			               i== 3 ? resultado.getDescripcionPropietario4():
			               i== 4 ? resultado.getDescripcionPropietario5():
			               i== 5 ? resultado.getDescripcionPropietario6():"");
					if(ePersona.getCodigo() != 0){
						lstPersona.add(ePersona);
					}
					
					
				}
				resultado.setLstPropietario(lstPersona);
				
			}
		} catch (Exception e) {
			UManejadorLog.error("Control: Error al obtener: " + e.getMessage());
		} finally {
			if (oIConexion != null) {
				oIConexion.cierraConexion();
			}
		}
		return resultado;
	}
	
	public EGarantiaTramite buscarUltimoAsientoGarantiaTramite(long codigoGarantia){
		IConexion oIConexion = null;
		EGarantiaTramite resultado = null;
		DAOGarantia oDAOGarantia= null;
		try {
			oIConexion = FabricaConexion.creaConexion();			
			oDAOGarantia = new DAOGarantia(oIConexion);
			resultado = oDAOGarantia.buscarUltimoAsientoGarantiaTramite(codigoGarantia);	
			
		} catch (Exception e) {
			UManejadorLog.error("Control: Error al obtener: " + e.getMessage());
		} finally {
			if (oIConexion != null) {
				oIConexion.cierraConexion();
			}
		}
		return resultado;
	}
	
	public EOperacionDocumento buscarSolicitudDesembolsoGarantia(EGarantia eGarantia){
		IConexion oIConexion = null;
		EOperacionDocumento resultado = null;
		DAOGarantia oDAOGarantia= null;
		try {
			oIConexion = FabricaConexion.creaConexion();			
			oDAOGarantia = new DAOGarantia(oIConexion);
			resultado = oDAOGarantia.buscarSolicitudDesembolsoGarantia(eGarantia);	
			
		} catch (Exception e) {
			UManejadorLog.error("Control: Error al obtener: " + e.getMessage());
		} finally {
			if (oIConexion != null) {
				oIConexion.cierraConexion();
			}
		}
		return resultado;
	}
	
	public EGarantia buscarCaracteristicaInmueblePredio(int nroSolicitud){
		IConexion oIConexion = null;
		EGarantia resultado = null;
		DAOGarantia oDAOGarantia= null;
		try {
			oIConexion = FabricaConexion.creaConexion();			
			oDAOGarantia = new DAOGarantia(oIConexion);
			resultado = oDAOGarantia.buscarCaracteristicaInmueblePredio(nroSolicitud);	
			
		} catch (Exception e) {
			UManejadorLog.error("Control: Error al obtener: " + e.getMessage());
		} finally {
			if (oIConexion != null) {
				oIConexion.cierraConexion();
			}
		}
		return resultado;
	}
	
	public EOperacionSolicitud buscarInstruccionAprobacionOperacionesF7320(long codigoSolicitud,int nroRevision){
		IConexion oIConexion = null;
		EOperacionSolicitud resultado = null;
		DAOGarantia oDAOGarantia= null;
		try {
			oIConexion = FabricaConexion.creaConexion();			
			oDAOGarantia = new DAOGarantia(oIConexion);
			resultado = oDAOGarantia.buscarInstruccionAprobacionOperacionesF7320(codigoSolicitud,nroRevision);	
			
		} catch (Exception e) {
			UManejadorLog.error("Control: Error al obtener: " + e.getMessage());
		} finally {
			if (oIConexion != null) {
				oIConexion.cierraConexion();
			}
		}
		return resultado;
	}
	
	public EOperacionSolicitud buscarUltimaRevisionSolicitudCredito(long codigoSolicitud){
		IConexion oIConexion = null;
		EOperacionSolicitud resultado = null;
		DAOGarantia oDAOGarantia= null;
		try {
			oIConexion = FabricaConexion.creaConexion();			
			oDAOGarantia = new DAOGarantia(oIConexion);
			resultado = oDAOGarantia.buscarUltimaRevisionSolicitudCredito(codigoSolicitud);	
			
		} catch (Exception e) {
			UManejadorLog.error("Control: Error al obtener: " + e.getMessage());
		} finally {
			if (oIConexion != null) {
				oIConexion.cierraConexion();
			}
		}
		return resultado;
	}
	
	public EOperacionSolicitud buscarRegistroSolicitudCreditoF7301(long codigoSolicitud){
		IConexion oIConexion = null;
		EOperacionSolicitud resultado = null;
		DAOGarantia oDAOGarantia= null;
		try {
			oIConexion = FabricaConexion.creaConexion();			
			oDAOGarantia = new DAOGarantia(oIConexion);
			resultado = oDAOGarantia.buscarRegistroSolicitudCreditoF7301(codigoSolicitud);	
			
		} catch (Exception e) {
			UManejadorLog.error("Control: Error al obtener: " + e.getMessage());
		} finally {
			if (oIConexion != null) {
				oIConexion.cierraConexion();
			}
		}
		return resultado;
	}
	
	public EContrato buscarMaestroContratoF7401(long numeroContrato){
		IConexion oIConexion = null;
		EContrato resultado = null;
		DAOGarantia oDAOGarantia= null;
		try {
			oIConexion = FabricaConexion.creaConexion();			
			oDAOGarantia = new DAOGarantia(oIConexion);
			resultado = oDAOGarantia.buscarMaestroContratoF7401(numeroContrato);	
			
		} catch (Exception e) {
			UManejadorLog.error("Control: Error al obtener: " + e.getMessage());
		} finally {
			if (oIConexion != null) {
				oIConexion.cierraConexion();
			}
		}
		return resultado;
	}
	
	public EDocumentoGenerado buscarMaestroDocumentoGeneradoF7420(EDocumentoGenerado eDocumentoGenerado){
		IConexion oIConexion = null;
		EDocumentoGenerado resultado = null;
		DAOGarantia oDAOGarantia= null;
		try {
			oIConexion = FabricaConexion.creaConexion();			
			oDAOGarantia = new DAOGarantia(oIConexion);
			resultado = oDAOGarantia.buscarMaestroDocumentoGeneradoF7420(eDocumentoGenerado);	
			
		} catch (Exception e) {
			UManejadorLog.error("Control: Error al obtener: " + e.getMessage());
		} finally {
			if (oIConexion != null) {
				oIConexion.cierraConexion();
			}
		}
		return resultado;
	}
	
	public List<ESaldoServicio> obtenerSaldosServiciosCliente(int codigoCliente){
		IConexion oIConexion = null;
		List<ESaldoServicio> resultado = null;
		DAOGarantia oDAOGarantia= null;
		try {
			oIConexion = FabricaConexion.creaConexion();			
			oDAOGarantia = new DAOGarantia(oIConexion);
			resultado = oDAOGarantia.obtenerSaldosServiciosCliente(codigoCliente);			
		} catch (Exception e) {
			UManejadorLog.error("Control: Error al Listar Saldos de Servicios por Clientes" + e.getMessage());
		} finally {
			if (oIConexion != null) {
				oIConexion.cierraConexion();
			}
		}
		return resultado;
	}
	
	public List<EGarantiaDocumentoSolicitado> listarDocumentoSolicitado(long numeroSolicitud){
		IConexion oIConexion = null;
		List<EGarantiaDocumentoSolicitado> resultado = null;
		DAOGarantia oDAOGarantia= null;
		try {
			oIConexion = FabricaConexion.creaConexion();			
			oDAOGarantia = new DAOGarantia(oIConexion);
			resultado = oDAOGarantia.listarDocumentoSolicitado(numeroSolicitud);			
		} catch (Exception e) {
			UManejadorLog.error("Control: Error al listar documento solicitado " + e.getMessage());
		} finally {
			if (oIConexion != null) {
				oIConexion.cierraConexion();
			}
		}
		return resultado;
	}
	
	public List<EGarantiaCreditoRelacionado> listarCreditoRelacionado(int codigo){
		IConexion oIConexion = null;
		List<EGarantiaCreditoRelacionado> resultado = null;
		DAOGarantia oDAOGarantia= null;
		try {
			oIConexion = FabricaConexion.creaConexion();			
			oDAOGarantia = new DAOGarantia(oIConexion);
			resultado = oDAOGarantia.listarCreditoRelacionado(codigo);			
		} catch (Exception e) {
			UManejadorLog.error("Control: Error al listar credito relacionado " + e.getMessage());
		} finally {
			if (oIConexion != null) {
				oIConexion.cierraConexion();
			}
		}
		return resultado;
	}
	
	public List<EGarantiaCreditoRelacionado> listarCreditoVigenteRelacionado(long codigo){
		IConexion oIConexion = null;
		List<EGarantiaCreditoRelacionado> resultado = null;
		DAOGarantia oDAOGarantia= null;
		try {
			oIConexion = FabricaConexion.creaConexion();			
			oDAOGarantia = new DAOGarantia(oIConexion);
			resultado = oDAOGarantia.listarCreditoVigenteRelacionado(codigo);			
		} catch (Exception e) {
			UManejadorLog.error("Control: Error al listar credito relacionado " + e.getMessage());
		} finally {
			if (oIConexion != null) {
				oIConexion.cierraConexion();
			}
		}
		return resultado;
	}
	
	public List<EGarantiaCreditoRelacionado> listarCreditoCanceladoRelacionado(long codigo){
		IConexion oIConexion = null;
		List<EGarantiaCreditoRelacionado> resultado = null;
		DAOGarantia oDAOGarantia= null;
		try {
			oIConexion = FabricaConexion.creaConexion();			
			oDAOGarantia = new DAOGarantia(oIConexion);
			resultado = oDAOGarantia.listarCreditoCanceladoRelacionado(codigo);			
		} catch (Exception e) {
			UManejadorLog.error("Control: Error al listar credito relacionado " + e.getMessage());
		} finally {
			if (oIConexion != null) {
				oIConexion.cierraConexion();
			}
		}
		return resultado;
	}
	
	public EGarantia buscarGarantia(long codigoGarantia){
		IConexion oIConexion = null;
		EGarantia resultado = null;
		DAOGarantia oDAOGarantia= null;
		try {
			oIConexion = FabricaConexion.creaConexion();			
			oDAOGarantia = new DAOGarantia(oIConexion);
			resultado = oDAOGarantia.buscarGarantia(codigoGarantia);
			
			if(resultado != null){
				switch(resultado.getCodigoTipoGarantia()){
				case UTipoGarantia.PREDIO:		
					resultado.setObervacionGarantia
					       (UFuncionesGenerales.revisaCadena(resultado.getUbicacion2())+
							UFuncionesGenerales.revisaCadena(resultado.getDescripcionB())+
							UFuncionesGenerales.revisaCadena(resultado.getDescripcionC())+
							UFuncionesGenerales.revisaCadena(resultado.getComentario())+
							UFuncionesGenerales.revisaCadena(resultado.getDescripcionD()));
					break;
				case UTipoGarantia.VEHICULAR:
					resultado.setObervacionGarantia
				       (UFuncionesGenerales.revisaCadena(resultado.getDescripcionA())+
						UFuncionesGenerales.revisaCadena(resultado.getDescripcionB())+
						UFuncionesGenerales.revisaCadena(resultado.getDescripcionC())+
						UFuncionesGenerales.revisaCadena(resultado.getDescripcionD()));
					break;
				default:
				}
			
				if(resultado.getCodigoTipoGarantia()>21 && resultado.getCodigoTipoGarantia()!=88){
					resultado.setObervacionGarantia
				       (UFuncionesGenerales.revisaCadena(resultado.getUbicacion2())+
						UFuncionesGenerales.revisaCadena(resultado.getDescripcionB())+
						UFuncionesGenerales.revisaCadena(resultado.getDescripcionC())+
						UFuncionesGenerales.revisaCadena(resultado.getComentario())+
						UFuncionesGenerales.revisaCadena(resultado.getDescripcionD()));
				}
			}
		} catch (Exception e) {
			UManejadorLog.error("Control: Error al obtener garantia " + e.getMessage());
		} finally {
			if (oIConexion != null) {
				oIConexion.cierraConexion();
			}
		}
		return resultado;
	}
	
	public ETipoCambio buscarTipoCambioDiario(){
		IConexion oIConexion = null;
		ETipoCambio resultado = null;
		DAOGarantia oDAOGarantia= null;
		try {
			oIConexion = FabricaConexion.creaConexion();			
			oDAOGarantia = new DAOGarantia(oIConexion);
			resultado = oDAOGarantia.buscarTipoCambioDiario();			
		} catch (Exception e) {
			UManejadorLog.error("Control: Error al obtener Tipo de Cambio Diario " + e.getMessage());
		} finally {
			if (oIConexion != null) {
				oIConexion.cierraConexion();
			}
		}
		return resultado;
	}
	
	public EGarantiaTramite buscarGarantiaTramite(long codigoGarantia){
		IConexion oIConexion = null;
		EGarantiaTramite resultado = null;
		DAOGarantia oDAOGarantia= null;
		try {
			oIConexion = FabricaConexion.creaConexion();			
			oDAOGarantia = new DAOGarantia(oIConexion);
			resultado = oDAOGarantia.buscarGarantiaTramite(codigoGarantia);			
		} catch (Exception e) {
			UManejadorLog.error("Control: Error al obtener garantia tramite " + e.getMessage());
		} finally {
			if (oIConexion != null) {
				oIConexion.cierraConexion();
			}
		}
		return resultado;
	}
	
	public EGarantia buscarGarantiaSolicitudCompleto(long numeroSolicitud, int secuenciaGarantia){
		IConexion oIConexion = null;
		EGarantiaSolicitud eGarantiaSolicitudF7325 = null;
		EGarantiaDetalleSolicitud eGarantiaSolicitudF7363 = null;
		DAOGarantia oDAOGarantia= null;
		EGarantia resultado = null;
		try {
			oIConexion = FabricaConexion.creaConexion();			
			oDAOGarantia = new DAOGarantia(oIConexion);
			eGarantiaSolicitudF7325 = oDAOGarantia.buscarGarantiaSolicitud(numeroSolicitud, secuenciaGarantia) != null ?
					oDAOGarantia.buscarGarantiaSolicitud(numeroSolicitud, secuenciaGarantia) : new EGarantiaSolicitud();
			eGarantiaSolicitudF7363 = oDAOGarantia.buscarGarantiaDetalleSolicitud(numeroSolicitud, secuenciaGarantia) != null ?
					oDAOGarantia.buscarGarantiaDetalleSolicitud(numeroSolicitud, secuenciaGarantia) : new EGarantiaDetalleSolicitud();
			resultado = new EGarantia();
			resultado.setCodigoTipoGarantia(eGarantiaSolicitudF7325.getCodigoTipoGarantiaReal());
			resultado.setCodigoMoneda(eGarantiaSolicitudF7325.getCodigoMonedaGarantia());
			resultado.setCuenta(eGarantiaSolicitudF7325.getCuentaGarantia());
			resultado.setNumeroDocumento(eGarantiaSolicitudF7325.getDocumentoGarantia()+"");
			resultado.setMontoOriginalGarantia(eGarantiaSolicitudF7325.getMontoGarantia());
			resultado.setMontoGarantia(eGarantiaSolicitudF7325.getMontoGarantia());
			resultado.setMontoGravamen(eGarantiaSolicitudF7325.getMontoGarantia());
			resultado.setMontoVariable1(eGarantiaSolicitudF7325.getMontoGarantia());
			resultado.setMontoValoracion(eGarantiaSolicitudF7325.getMontoValorizacion());
			resultado.setDescripcionMoneda(eGarantiaSolicitudF7325.getDescripcionMonedaGarantia());
			resultado.setAbreviacionMoneda(eGarantiaSolicitudF7325.getAbreviacionMonedaGarantia());
			switch(eGarantiaSolicitudF7325.getCodigoTipoGarantiaReal()){
			 case UTipoGarantia.MAQUINARIA:
				 resultado.setDescripcionA(eGarantiaSolicitudF7363.getMotor());
				 resultado.setDescripcionB(eGarantiaSolicitudF7363.getSerie());
				 resultado.setCodigoTipoBien(eGarantiaSolicitudF7363.getCodigoTipoPrenda());
				 resultado.setMarca(eGarantiaSolicitudF7363.getMarca());
				 resultado.setModelo(eGarantiaSolicitudF7363.getModelo());
				 resultado.setNumeroVariable2(eGarantiaSolicitudF7363.getCantidad()); 
				 //resultado.setDescripcionC("");
				 break;
			 case UTipoGarantia.PREDIO:
				 resultado.setDescripcionA(eGarantiaSolicitudF7363.getUsoPredio());
				 resultado.setCodigoUbigeo(eGarantiaSolicitudF7363.getCodigoUbigeo());
				 resultado.setMontoVariable2(eGarantiaSolicitudF7363.getAreaTerreno());
				 resultado.setMontoVariable3(eGarantiaSolicitudF7363.getAreaConstruida());
				 resultado.setCodigoTipoBien(eGarantiaSolicitudF7363.getCodigoTipoPrenda2());
				 resultado.setNumeroVariable1(eGarantiaSolicitudF7363.getNumeroPisos());
				 resultado.setUbicacion1(eGarantiaSolicitudF7363.getDireccion());	
				// resultado.setDescripcionB("");resultado.setDescripcionC("");
				 break;
			 case UTipoGarantia.VEHICULAR:
				 //resultado.setDescripcionA(""); resultado.setDescripcionB(""); resultado.setDescripcionC("");
					resultado.setPlaca(eGarantiaSolicitudF7325.getPlaca());
					resultado.setClase(eGarantiaSolicitudF7325.getClase());
					resultado.setMarca(eGarantiaSolicitudF7325.getMarca());
					resultado.setModelo(eGarantiaSolicitudF7325.getModelo());
					resultado.setUbicacion2(eGarantiaSolicitudF7363.getMotor());
					resultado.setCarroceria(eGarantiaSolicitudF7325.getCarroceria());
					resultado.setComentario(eGarantiaSolicitudF7325.getColor());
					resultado.setAnioFabricacion(eGarantiaSolicitudF7325.getAnioFabricacion()); 
					resultado.setUbicacion1(eGarantiaSolicitudF7363.getSerie());	
				 break;
			}



		} catch (Exception e) {
			UManejadorLog.error("Control: Error al obtener garantia solicitud " + e.getMessage());
		} finally {
			if (oIConexion != null) {
				oIConexion.cierraConexion();
			}
		}
		return resultado;
	}
	
	
	public EGarantiaSolicitud buscarGarantiaSolicitud(long numeroSolicitud, int secuenciaGarantia){
		IConexion oIConexion = null;
		EGarantiaSolicitud resultado = null;
		DAOGarantia oDAOGarantia= null;
		try {
			oIConexion = FabricaConexion.creaConexion();			
			oDAOGarantia = new DAOGarantia(oIConexion);
			resultado = oDAOGarantia.buscarGarantiaSolicitud(numeroSolicitud, secuenciaGarantia);			
		} catch (Exception e) {
			UManejadorLog.error("Control: Error al obtener garantia solicitud " + e.getMessage());
		} finally {
			if (oIConexion != null) {
				oIConexion.cierraConexion();
			}
		}
		return resultado;
	}
	
	public EGarantiaSolicitud buscarGarantiaAsociadaSolicitud(long numeroSolicitud, int secuenciaGarantia){
		IConexion oIConexion = null;
		EGarantiaSolicitud resultado = null;
		DAOGarantia oDAOGarantia= null;
		try {
			oIConexion = FabricaConexion.creaConexion();			
			oDAOGarantia = new DAOGarantia(oIConexion);
			resultado = oDAOGarantia.buscarGarantiaAsociadaSolicitud(numeroSolicitud, secuenciaGarantia);			
		} catch (Exception e) {
			UManejadorLog.error("Control: Error al obtener garantia solicitud " + e.getMessage());
		} finally {
			if (oIConexion != null) {
				oIConexion.cierraConexion();
			}
		}
		return resultado;
	}
	
	public EGarantiaDetalleSolicitud buscarGarantiaDetalleSolicitud(long numeroSolicitud, int secuenciaGarantia){
		IConexion oIConexion = null;
		EGarantiaDetalleSolicitud resultado = null;
		DAOGarantia oDAOGarantia= null;
		try {
			oIConexion = FabricaConexion.creaConexion();			
			oDAOGarantia = new DAOGarantia(oIConexion);
			resultado = oDAOGarantia.buscarGarantiaDetalleSolicitud(numeroSolicitud, secuenciaGarantia);			
		} catch (Exception e) {
			UManejadorLog.error("Control: Error al obtener garantia detalle solicitud " + e.getMessage());
		} finally {
			if (oIConexion != null) {
				oIConexion.cierraConexion();
			}
		}
		return resultado;
	}
}
