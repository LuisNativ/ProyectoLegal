package com.abaco.ageneral;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.abaco.dao.DAOGeneral;
import com.abaco.entidad.EMensaje;
import com.abaco.entidad.EUsuario;
import com.abaco.ageneral.ESolicitudCredito;
import com.abaco.ageneral.EOperacionSolicitud;
import com.abaco.bo.BOGeneral;
import com.abaco.negocio.util.UConstante.UAccionTabla;
import com.abaco.negocio.util.UConstante.UArea;
import com.abaco.negocio.util.UConstante.UIndicadorSesion;
import com.abaco.negocio.util.UConstante.UIndicadorTemporal;
import com.abaco.negocio.util.UConstante.UMensajeOperacion;
import com.abaco.negocio.util.UConstante.UTipoCorrelativo;
import com.abaco.negocio.util.UConstante.UTipologMovimiento;
import com.abaco.negocio.util.UFuncionesGenerales;
import com.abaco.negocio.util.ULectorDeParametros;
import com.abaco.negocio.util.UManejadorArchivo;
import com.abaco.negocio.util.UManejadorCorreo;
import com.abaco.negocio.util.UManejadorLog;
import com.abaco.negocio.util.UConstante.UEstado;
import com.abaco.negocio.util.UConstante.UIndicadorDigitalizacion;
import com.abaco.persistencia.acceso.FabricaConexion;
import com.abaco.persistencia.interfaces.IConexion;
import com.abaco.servicio.laserfiche.Mensaje;

public class COperacion {	
	public EMensaje agregarEvaluacionSolicitudCredito(EOperacionSolicitud eOperacionSolicitud){
		IConexion oIConexion = null;
		EMensaje mensaje = new EMensaje();
		Mensaje mensajeLaserFiche = new Mensaje();
		UManejadorArchivo oUManejadorArchivo = new UManejadorArchivo();
		DAOOperacion oDAOOperacion= null;
		BOGeneral oBOGeneral  = null;
		try {
			oIConexion = FabricaConexion.creaConexion();
			oIConexion.iniciaTransaccion();
			oDAOOperacion = new DAOOperacion(oIConexion);
			oBOGeneral = new BOGeneral();
			
			mensaje = oDAOOperacion.agregarSolicitud2(eOperacionSolicitud);
			if (!UFuncionesGenerales.validaMensaje(mensaje)) {
				throw new Exception(mensaje.getDescMensaje());
			}
			
			oIConexion.ejecutaCommit();
		} catch (Exception e) {
			if (oIConexion != null) {
				oIConexion.ejecutaRollback();
			}
			mensaje.setDescMensaje(UMensajeOperacion.MSJ_4 + mensaje.getDescMensaje());
			UManejadorLog.error("Control: Error al modificar solicitud: " + e.getMessage());
		} finally {
			if (oIConexion != null) {
				oIConexion.cierraConexion();
			}
		}
		return mensaje;
	}
	
	public EMensaje agregarDocumentoGarantia(EGarantia eOGarantia, EDocumentoCarga eDocumentoCarga){
		IConexion oIConexion = null;
		EMensaje mensaje = new EMensaje();
		DAOOperacion oDAOOperacion= null;

		try {
			oIConexion = FabricaConexion.creaConexion();
			oIConexion.iniciaTransaccion();
			oDAOOperacion = new DAOOperacion(oIConexion);

			mensaje = oDAOOperacion.agregarDocumentoGarantia(eOGarantia, eDocumentoCarga);
			if (!UFuncionesGenerales.validaMensaje(mensaje)) {
				throw new Exception(mensaje.getDescMensaje());
			}
	
			oIConexion.ejecutaCommit();
			
		} catch (Exception e) {
			if (oIConexion != null) {
				oIConexion.ejecutaRollback();
			}
			mensaje.setDescMensaje(UMensajeOperacion.MSJ_4 + mensaje.getDescMensaje());
			UManejadorLog.error("Control: Error al modificar solicitud: " + e.getMessage());
		} finally {
			if (oIConexion != null) {
				oIConexion.cierraConexion();
			}
		}
		return mensaje;
	}
	
	public EMensaje modificarDocumentoGarantia(EGarantia eOGarantia){
		IConexion oIConexion = null;
		EMensaje mensaje = new EMensaje();
		DAOOperacion oDAOOperacion= null;

		try {
			oIConexion = FabricaConexion.creaConexion();
			oIConexion.iniciaTransaccion();
			oDAOOperacion = new DAOOperacion(oIConexion);

			mensaje = oDAOOperacion.modificarDocumentoGarantia(eOGarantia, eOGarantia.getOperacionDocumento());
			if (!UFuncionesGenerales.validaMensaje(mensaje)) {
				throw new Exception(mensaje.getDescMensaje());
			}
			
			oIConexion.ejecutaCommit();
					
			
		} catch (Exception e) {
			if (oIConexion != null) {
				oIConexion.ejecutaRollback();
			}
			mensaje.setDescMensaje(UMensajeOperacion.MSJ_4 + mensaje.getDescMensaje());
			UManejadorLog.error("Control: Error al modificar solicitud: " + e.getMessage());
		} finally {
			if (oIConexion != null) {
				oIConexion.cierraConexion();
			}
		}
		return mensaje;
	}
	
	public EMensaje modificarFirmaDocumentoGarantia(EOperacionDocumento eOperacionDocumento){
		IConexion oIConexion = null;
		EMensaje mensaje = new EMensaje();
		DAOOperacion oDAOOperacion= null;

		try {
			oIConexion = FabricaConexion.creaConexion();
			oIConexion.iniciaTransaccion();
			oDAOOperacion = new DAOOperacion(oIConexion);

			mensaje = oDAOOperacion.modificarFirmaDocumentoGarantia(eOperacionDocumento);
			if (!UFuncionesGenerales.validaMensaje(mensaje)) {
				throw new Exception(mensaje.getDescMensaje());
			}
			
			oIConexion.ejecutaCommit();
					
			
		} catch (Exception e) {
			if (oIConexion != null) {
				oIConexion.ejecutaRollback();
			}
			mensaje.setDescMensaje(UMensajeOperacion.MSJ_4 + mensaje.getDescMensaje());
			UManejadorLog.error("Control: Error al modificar Firma Documento: " + e.getMessage());
		} finally {
			if (oIConexion != null) {
				oIConexion.cierraConexion();
			}
		}
		return mensaje;
	}
	
	public EMensaje modificarEvaluacionSolicitudCredito(EOperacionSolicitud eOperacionSolicitud, EEvaluacionSolicitudCreditoLegal eEvaluacionSolicitudCreditoLegal, ECliente eCliente, EClienteConstitucionEmpresa eClienteConstitucionEmpresa, EClienteAdicional eClienteAdicional, int codigoTipoCliente){
		IConexion oIConexion = null;
		EMensaje mensaje = new EMensaje();
		Mensaje mensajeLaserFiche = new Mensaje();
		UManejadorArchivo oUManejadorArchivo = new UManejadorArchivo();
		DAOOperacion oDAOOperacion= null;
		DAOSolicitudCredito oDAOSolicitudCredito= null;
		DAORepresentanteLegal oDAORepresentanteLegal= null;
		DAOCliente oDAOCliente= null;
		BOGeneral oBOGeneral  = null;
		try {
			oIConexion = FabricaConexion.creaConexion();
			oIConexion.iniciaTransaccion();
			oDAOOperacion = new DAOOperacion(oIConexion);
			oDAOSolicitudCredito = new DAOSolicitudCredito(oIConexion);
			oDAORepresentanteLegal = new DAORepresentanteLegal(oIConexion);
			oDAOCliente = new DAOCliente(oIConexion);
			oBOGeneral = new BOGeneral();
			
			eOperacionSolicitud.setNumeroMensaje(oBOGeneral.generarCorrelativo(UTipoCorrelativo.OPERACIONMENSAJE, eOperacionSolicitud.getCodigoSolicitud()+"", "", ""));
			
			//Agregar Log Movimiento
			if(eEvaluacionSolicitudCreditoLegal.getLstSolicitudLogMovimiento() != null){
				Date fecha = new Date();
				for(ESolicitudLogMovimiento oESolicitudLogMovimiento: eEvaluacionSolicitudCreditoLegal.getLstSolicitudLogMovimiento()){
					fecha.setSeconds(fecha.getSeconds()+1);
					oESolicitudLogMovimiento.setNumeroSolicitud(eEvaluacionSolicitudCreditoLegal.getNumeroSolicitud());
					oESolicitudLogMovimiento.setCodigoAccion(oESolicitudLogMovimiento.getCodigoAccion());
					oESolicitudLogMovimiento.setUsuarioRegistro(eEvaluacionSolicitudCreditoLegal.getUsuarioRegistro());
					oESolicitudLogMovimiento.setFechaRegistro(fecha);
					mensaje = oDAOSolicitudCredito.agregarLogMovimiento(oESolicitudLogMovimiento);
					if (!UFuncionesGenerales.validaMensaje(mensaje)) {
						throw new Exception(mensaje.getDescMensaje());
					}
					
					/*
					if(oESolicitudLogMovimiento.getCodigoAccion() == UTipologMovimiento.CAMBIADTOSOCIO){
						mensaje = oDAOCliente.agregarHistoricoCliente(eEvaluacionSolicitudCreditoLegal, eEvaluacionSolicitudCreditoLegal.getInformeLegalAdicional(), eCliente, eClienteConstitucionEmpresa, eClienteAdicional);
						if (!UFuncionesGenerales.validaMensaje(mensaje)) {
							throw new Exception(mensaje.getDescMensaje());
						}
					}
					*/
				}
			}
			
			mensaje = oDAOSolicitudCredito.modificarSolicitudCredito(eEvaluacionSolicitudCreditoLegal, eOperacionSolicitud);
			if (!UFuncionesGenerales.validaMensaje(mensaje)) {
				throw new Exception(mensaje.getDescMensaje());
			}
			
			mensaje = oDAOSolicitudCredito.agregarModificarInformeLegalAdicional(eEvaluacionSolicitudCreditoLegal, eEvaluacionSolicitudCreditoLegal.getInformeLegalAdicional());
			if (!UFuncionesGenerales.validaMensaje(mensaje)) {
				throw new Exception(mensaje.getDescMensaje());
			}
			
			//Eliminar Suscripcion
			mensaje = oDAOSolicitudCredito.eliminarSuscripcion(eEvaluacionSolicitudCreditoLegal.getNumeroSolicitud(), eEvaluacionSolicitudCreditoLegal.getCodigoCliente(),eEvaluacionSolicitudCreditoLegal.getCodigoTipoCliente());
			if (!UFuncionesGenerales.validaMensaje(mensaje)) {
				throw new Exception(mensaje.getDescMensaje());
			}
			mensaje = oDAOSolicitudCredito.eliminarClienteSuscripcion(eEvaluacionSolicitudCreditoLegal.getNumeroDocumento());
			if (!UFuncionesGenerales.validaMensaje(mensaje)) {
				throw new Exception(mensaje.getDescMensaje());
			}
			
			//Agregar Suscripcion
			if(eEvaluacionSolicitudCreditoLegal.getLstSuscripcion() != null){
			for(ESuscripcion oESuscripcion: eEvaluacionSolicitudCreditoLegal.getLstSuscripcion()){
				oESuscripcion.setNumeroSolicitud(eEvaluacionSolicitudCreditoLegal.getNumeroSolicitud());
				oESuscripcion.setCodigoCliente(eEvaluacionSolicitudCreditoLegal.getCodigoCliente());
				oESuscripcion.setCodigoTipoCliente(eEvaluacionSolicitudCreditoLegal.getCodigoTipoCliente());
				oESuscripcion.setNumeroDocumento(eEvaluacionSolicitudCreditoLegal.getNumeroDocumento());
				oESuscripcion.setUsuarioRegistro(eEvaluacionSolicitudCreditoLegal.getUsuarioRegistro());
				oESuscripcion.setFechaRegistro(eEvaluacionSolicitudCreditoLegal.getFechaRegistro());
				mensaje = oDAOSolicitudCredito.agregarSuscripcion(oESuscripcion);
				if (!UFuncionesGenerales.validaMensaje(mensaje)) {
					throw new Exception(mensaje.getDescMensaje());
				}
				mensaje = oDAOSolicitudCredito.agregarClienteSuscripcion(oESuscripcion);
				if (!UFuncionesGenerales.validaMensaje(mensaje)) {
					throw new Exception(mensaje.getDescMensaje());
				}
			}
			}
			
			if(eOperacionSolicitud.getUsuarioRegistro().getCodigoArea() == UArea.LEGAL){
				EObservacionLegal oEObservacionLegal = new EObservacionLegal();
				oEObservacionLegal.setNumeroSolicitud(eEvaluacionSolicitudCreditoLegal.getNumeroSolicitud());
				oEObservacionLegal.setNombreUsuario(eEvaluacionSolicitudCreditoLegal.getUsuarioRegistro().getNombreUsuario());
				oEObservacionLegal.setSecuencia(1);
				oEObservacionLegal.setFechaRegistro(eEvaluacionSolicitudCreditoLegal.getFechaRegistro());
				
				//Agregar observacion maestra
				mensaje = oDAOSolicitudCredito.agregarObservacionMaestra(oEObservacionLegal);
				if (!UFuncionesGenerales.validaMensaje(mensaje)) {
					throw new Exception(mensaje.getDescMensaje());
				}
				
				//Eliminar observacion anterior
				mensaje = oDAOSolicitudCredito.eliminarObservacionDetalle(oEObservacionLegal);
				if (!UFuncionesGenerales.validaMensaje(mensaje)) {
					throw new Exception(mensaje.getDescMensaje());
				}
				
				//Agregar observacion linea por linea
				String mensaje_observacion = eOperacionSolicitud.getDescripcionMensaje();
				String lista_observacion[] = mensaje_observacion.split("\r\n");
				
				for(int i=0;i<lista_observacion.length;i++){
					oEObservacionLegal.setDescripcionMensaje(lista_observacion[i]);
					oEObservacionLegal.setLineaObservacion(i+1);
					mensaje = oDAOSolicitudCredito.agregarObservacionDetalle(oEObservacionLegal);
					if (!UFuncionesGenerales.validaMensaje(mensaje)) {
						throw new Exception(mensaje.getDescMensaje());
					}
				}
			}else {
				if (eEvaluacionSolicitudCreditoLegal.getObservacionNegocios() != null){
					if (eEvaluacionSolicitudCreditoLegal.getObservacionNegocios() != ""){
						EObservacionNegocios oEObservacionNegocios = new EObservacionNegocios();
						oEObservacionNegocios.setNumeroSolicitud(eEvaluacionSolicitudCreditoLegal.getNumeroSolicitud());
						oEObservacionNegocios.setSecuencia(0);
						oEObservacionNegocios.setObservacion(eEvaluacionSolicitudCreditoLegal.getObservacionNegocios());
						oEObservacionNegocios.setNombreUsuario(eEvaluacionSolicitudCreditoLegal.getUsuarioRegistro().getNombreUsuario());
						oEObservacionNegocios.setFechaEvaluacion(eEvaluacionSolicitudCreditoLegal.getFechaRegistro());
						
						//Agregar observacion maestra
						mensaje = oDAOSolicitudCredito.agregarObservacionNegociosMaestra(oEObservacionNegocios);
						if (!UFuncionesGenerales.validaMensaje(mensaje)) {
							throw new Exception(mensaje.getDescMensaje());
						}
					}
				}
			}

			
			//Agregar, Modificar y Eliminar Representante Legal
			if(eEvaluacionSolicitudCreditoLegal.getLstRepresentanteLegal() != null){
				for(ERepresentanteLegal oERepresentanteLegal: eEvaluacionSolicitudCreditoLegal.getLstRepresentanteLegal()){
					oERepresentanteLegal.setUsuarioRegistro(eEvaluacionSolicitudCreditoLegal.getUsuarioRegistro());
					oERepresentanteLegal.setFechaRegistro(eEvaluacionSolicitudCreditoLegal.getFechaRegistro());
					
					if(oERepresentanteLegal.getCodigoAccion() == UAccionTabla.INSERTAR  && oERepresentanteLegal.getCodigoRepresentante() == 0){
						//Generar codigo representante
						oERepresentanteLegal.setCodigoRepresentante(oBOGeneral.generarCorrelativo(UTipoCorrelativo.REPRESENTATELEGAL, "", "", ""));
						//Setear codigo cliente
						oERepresentanteLegal.setCodigoTipoCliente(codigoTipoCliente);
						oERepresentanteLegal.setCodigoCliente(eOperacionSolicitud.getCodigoClientePersona());
						
						mensaje = oDAORepresentanteLegal.agregarRepresentanteLegal(oERepresentanteLegal);
						if (!UFuncionesGenerales.validaMensaje(mensaje)) {
							throw new Exception(mensaje.getDescMensaje());
						}
						
						mensaje = oDAORepresentanteLegal.agregarRepresentanteLegalSolicitudCredito(oERepresentanteLegal,eEvaluacionSolicitudCreditoLegal.getNumeroSolicitud());
						if (!UFuncionesGenerales.validaMensaje(mensaje)) {
							throw new Exception(mensaje.getDescMensaje());
							
						}
						
						//Agregar facultad
						if(oERepresentanteLegal.getLstFacultadPoder() != null){
						for(EFacultadPoder oEFacultadPoder: oERepresentanteLegal.getLstFacultadPoder()){
							oEFacultadPoder.setUsuarioRegistro(eEvaluacionSolicitudCreditoLegal.getUsuarioRegistro());
							oEFacultadPoder.setFechaRegistro(eEvaluacionSolicitudCreditoLegal.getFechaRegistro());
							mensaje = oDAORepresentanteLegal.agregarFacultadPoder(oERepresentanteLegal, oEFacultadPoder);
							if (!UFuncionesGenerales.validaMensaje(mensaje)) {
								throw new Exception(mensaje.getDescMensaje());
							}
						}
						}
					}else if(oERepresentanteLegal.getCodigoAccion() == UAccionTabla.EDITAR  && oERepresentanteLegal.getCodigoRepresentante() != 0){
						mensaje = oDAORepresentanteLegal.modificarRepresentanteLegal(oERepresentanteLegal);
						if (!UFuncionesGenerales.validaMensaje(mensaje)) {
							throw new Exception(mensaje.getDescMensaje());
						}
						
						mensaje = oDAORepresentanteLegal.modificarRepresentanteLegalSolicitudCredito(oERepresentanteLegal,eEvaluacionSolicitudCreditoLegal.getNumeroSolicitud());
						if (!UFuncionesGenerales.validaMensaje(mensaje)) {
							throw new Exception(mensaje.getDescMensaje());
							
						}
						
						//Eliminar facultad por representante
						mensaje = oDAORepresentanteLegal.eliminarFacultadPoder(oERepresentanteLegal);
						if (!UFuncionesGenerales.validaMensaje(mensaje)) {
							throw new Exception(mensaje.getDescMensaje());
						}
						
						//Agregar facultad
						if(oERepresentanteLegal.getLstFacultadPoder() != null){
						for(EFacultadPoder oEFacultadPoder: oERepresentanteLegal.getLstFacultadPoder()){
							oEFacultadPoder.setUsuarioRegistro(eEvaluacionSolicitudCreditoLegal.getUsuarioRegistro());
							oEFacultadPoder.setFechaRegistro(eEvaluacionSolicitudCreditoLegal.getFechaRegistro());
							if(oEFacultadPoder.getCodigoRepresentanteRelacion() == 0){
								oEFacultadPoder.setCodigoRepresentanteRelacion(oBOGeneral.generarCorrelativo(UTipoCorrelativo.REPRESENTATELEGAL, "", "", ""));
							}
							mensaje = oDAORepresentanteLegal.agregarFacultadPoder(oERepresentanteLegal, oEFacultadPoder);
							if (!UFuncionesGenerales.validaMensaje(mensaje)) {
								throw new Exception(mensaje.getDescMensaje());
							}
						}
						}
					}else if(oERepresentanteLegal.getCodigoAccion() == UAccionTabla.ELIMINAR && oERepresentanteLegal.getCodigoRepresentante() != 0){
						mensaje = oDAORepresentanteLegal.eliminarRepresentanteLegal(oERepresentanteLegal);
						if (!UFuncionesGenerales.validaMensaje(mensaje)) {
							throw new Exception(mensaje.getDescMensaje());
						}
						mensaje = oDAORepresentanteLegal.eliminarRepresentanteLegalSolicitudCredito(oERepresentanteLegal, eEvaluacionSolicitudCreditoLegal.getNumeroSolicitud());
						if (!UFuncionesGenerales.validaMensaje(mensaje)) {
							throw new Exception(mensaje.getDescMensaje());
						}
					}
				}
			}
			
			//Eliminar Deudor
			if(eEvaluacionSolicitudCreditoLegal.getLstDeudorRecycle().size() > 0){
				for(EDeudor oEDeudor: eEvaluacionSolicitudCreditoLegal.getLstDeudorRecycle()){
					oEDeudor.setCodigoCliente(eEvaluacionSolicitudCreditoLegal.getCodigoCliente());
					oEDeudor.setNumeroSolicitud(eEvaluacionSolicitudCreditoLegal.getNumeroSolicitud());
					mensaje = oDAOSolicitudCredito.eliminarDeudor(oEDeudor);
					if (!UFuncionesGenerales.validaMensaje(mensaje)) {
						throw new Exception(mensaje.getDescMensaje());
					}
				}
			}
			
			//Modificar Deudor
			if(eEvaluacionSolicitudCreditoLegal.getLstDeudor().size() > 0){
				for(EDeudor oEDeudor: eEvaluacionSolicitudCreditoLegal.getLstDeudor()){
					oEDeudor.setCodigoCliente(eEvaluacionSolicitudCreditoLegal.getCodigoCliente());
					oEDeudor.setNumeroSolicitud(eEvaluacionSolicitudCreditoLegal.getNumeroSolicitud());
					oEDeudor.setUsuarioRegistro(eEvaluacionSolicitudCreditoLegal.getUsuarioRegistro());
					oEDeudor.setFechaRegistro(eEvaluacionSolicitudCreditoLegal.getFechaRegistro());
					mensaje = oDAOSolicitudCredito.modificarDeudor(oEDeudor);
					if (!UFuncionesGenerales.validaMensaje(mensaje)) {
						throw new Exception(mensaje.getDescMensaje());
					}
				}
			}
			
			/*
			mensaje = oDAOOperacion.modificarSesion(eOperacionSesion);
			if (!UFuncionesGenerales.validaMensaje(mensaje)) {
				throw new Exception(mensaje.getDescMensaje());
			}
			*/
			mensaje = oDAOOperacion.modificarSolicitud(eOperacionSolicitud);
			if (!UFuncionesGenerales.validaMensaje(mensaje)) {
				throw new Exception(mensaje.getDescMensaje());
			}
			
			if(eOperacionSolicitud.getIndicadorTemporal() == UIndicadorTemporal.SI){
				mensaje = oDAOOperacion.eliminarDocumentoTemporal(eOperacionSolicitud);
				if (!UFuncionesGenerales.validaMensaje(mensaje)) {
					throw new Exception(mensaje.getDescMensaje());
				}
				
				if(eOperacionSolicitud.getLstDocumentoCarga().size() > 0){
					mensaje = oDAOOperacion.agregarDocumentoTemporal(eOperacionSolicitud);
					if (!UFuncionesGenerales.validaMensaje(mensaje)) {
						throw new Exception(mensaje.getDescMensaje());
					}
				}
			}else{
				mensaje = oDAOOperacion.eliminarDocumentoTemporal(eOperacionSolicitud);
				if (!UFuncionesGenerales.validaMensaje(mensaje)) {
					throw new Exception(mensaje.getDescMensaje());
				}
				
				if(eOperacionSolicitud.getLstDocumentoCarga().size() > 0){
					for(EDocumentoCarga oEDocumentoCarga: eOperacionSolicitud.getLstDocumentoCarga()){
						mensajeLaserFiche = oUManejadorArchivo.guardarDocumentoOperacionSolicitud(eEvaluacionSolicitudCreditoLegal, oEDocumentoCarga);
						if (!UFuncionesGenerales.validaMensajeLaserFiche(mensajeLaserFiche)) {
							throw new Exception(mensajeLaserFiche.getDescripcion());
						}
						
						oEDocumentoCarga.setCodigoLaserFiche(mensajeLaserFiche.getDocumentoID());
						mensaje = oDAOOperacion.agregarDocumento(eOperacionSolicitud, oEDocumentoCarga);
						if (!UFuncionesGenerales.validaMensaje(mensaje)) {
							throw new Exception(mensaje.getDescMensaje());
						}
					}
				}
			}
			
			if(eOperacionSolicitud.getLstOperacionDocumentoRevision().size() > 0){
				mensaje = oDAOOperacion.eliminarDocumentoRevision(eOperacionSolicitud);
				if (!UFuncionesGenerales.validaMensaje(mensaje)) {
					throw new Exception(mensaje.getDescMensaje());
				}
				
				for(EOperacionDocumentoRevision oEOperacionDocumentoRevision: eOperacionSolicitud.getLstOperacionDocumentoRevision()){
					if(oEOperacionDocumentoRevision.getDataDocumento() != null){
						mensajeLaserFiche = oUManejadorArchivo.guardarDocumentoRevisionOperacionSolicitud(eEvaluacionSolicitudCreditoLegal, oEOperacionDocumentoRevision);
						if (!UFuncionesGenerales.validaMensajeLaserFiche(mensajeLaserFiche)) {
							throw new Exception(mensajeLaserFiche.getDescripcion());
						}
						oEOperacionDocumentoRevision.setCodigoDocumentoLaserFiche(mensajeLaserFiche.getDocumentoID());
					}
					
					oEOperacionDocumentoRevision.setDataDocumento(null);
					mensaje = oDAOOperacion.agregarDocumentoRevision(eOperacionSolicitud, oEOperacionDocumentoRevision);
					if (!UFuncionesGenerales.validaMensaje(mensaje)) {
						throw new Exception(mensaje.getDescMensaje());
					}
				}
			}
			
			/*
			mensaje = oDAOOperacion.modificarSolicitudSesion(eOperacionSolicitud.getCodigoSolicitud(), UIndicadorSesion.NOOCUPADO, 0);
			if (!UFuncionesGenerales.validaMensaje(mensaje)) {
				throw new Exception(mensaje.getDescMensaje());
			}
			*/
			
			oIConexion.ejecutaCommit();
		} catch (Exception e) {
			if (oIConexion != null) {
				oIConexion.ejecutaRollback();
			}
			mensaje.setDescMensaje(UMensajeOperacion.MSJ_4 + mensaje.getDescMensaje());
			UManejadorLog.error("Control: Error al modificar solicitud: " + e.getMessage());
		} finally {
			if (oIConexion != null) {
				oIConexion.cierraConexion();
			}
		}
		return mensaje;
	}
	
	public EMensaje agregarSesion(EOperacionSesion eOperacionSesion){
		IConexion oIConexion = null;
		EMensaje mensaje = new EMensaje();
		DAOOperacion oDAOOperacion= null;
		try {
			oIConexion = FabricaConexion.creaConexion();			
			oDAOOperacion= new DAOOperacion(oIConexion);
			mensaje = oDAOOperacion.agregarSesion(eOperacionSesion);
		} catch (Exception e) {
			UManejadorLog.error("Control: Error al agregar sesion: " + e.getMessage());
		} finally {
			if (oIConexion != null) {
				oIConexion.cierraConexion();
			}
		}
		return mensaje;
	}
	
	public EMensaje modificarSesion(EOperacionSesion eOperacionSesion){
		IConexion oIConexion = null;
		EMensaje mensaje = new EMensaje();
		DAOOperacion oDAOOperacion= null;
		try {
			oIConexion = FabricaConexion.creaConexion();			
			oDAOOperacion= new DAOOperacion(oIConexion);
			mensaje = oDAOOperacion.modificarSesion(eOperacionSesion);
		} catch (Exception e) {
			UManejadorLog.error("Control: Error al agregar sesion: " + e.getMessage());
		} finally {
			if (oIConexion != null) {
				oIConexion.cierraConexion();
			}
		}
		return mensaje;
	}
	
	public EMensaje liberarSolicitudSesion(long codigoSolicitud){
		IConexion oIConexion = null;
		EMensaje mensaje = new EMensaje();
		DAOOperacion oDAOOperacion= null;
		try {
			oIConexion = FabricaConexion.creaConexion();			
			oDAOOperacion= new DAOOperacion(oIConexion);
			mensaje = oDAOOperacion.modificarSolicitudSesion(codigoSolicitud, UIndicadorSesion.NOOCUPADO, 0);
		} catch (Exception e) {
			UManejadorLog.error("Control: Error al modificar indicador sesion: " + e.getMessage());
		} finally {
			if (oIConexion != null) {
				oIConexion.cierraConexion();
			}
		}
		return mensaje;
	}
	
	public EMensaje modificarSolicitudSesion(long codigoSolicitud, int indicadorSesion, int codigoUsuario){
		IConexion oIConexion = null;
		EMensaje mensaje = new EMensaje();
		DAOOperacion oDAOOperacion= null;
		try {
			oIConexion = FabricaConexion.creaConexion();			
			oDAOOperacion= new DAOOperacion(oIConexion);
			mensaje = oDAOOperacion.modificarSolicitudSesion(codigoSolicitud, indicadorSesion, codigoUsuario);
		} catch (Exception e) {
			UManejadorLog.error("Control: Error al modificar indicador sesion: " + e.getMessage());
		} finally {
			if (oIConexion != null) {
				oIConexion.cierraConexion();
			}
		}
		return mensaje;
	}
	
	public EMensaje verificarSolicitudSesion(){
		IConexion oIConexion = null;
		EMensaje mensaje = new EMensaje();
		DAOOperacion oDAOOperacion= null;
		try {
			oIConexion = FabricaConexion.creaConexion();			
			oDAOOperacion= new DAOOperacion(oIConexion);
			mensaje = oDAOOperacion.verificarSolicitudSesion();
		} catch (Exception e) {
			UManejadorLog.error("Control: Error al verificar indicador sesion: " + e.getMessage());
		} finally {
			if (oIConexion != null) {
				oIConexion.cierraConexion();
			}
		}
		return mensaje;
	}
	
	public EMensaje verificarSolicitudSiaf(){
		IConexion oIConexion = null;
		EMensaje mensaje = new EMensaje();
		DAOOperacion oDAOOperacion= null;
		try {
			oIConexion = FabricaConexion.creaConexion();			
			oDAOOperacion= new DAOOperacion(oIConexion);
			mensaje = oDAOOperacion.verificarSolicitudSiaf();
		} catch (Exception e) {
			UManejadorLog.error("Control: Error al verificar estado: " + e.getMessage());
		} finally {
			if (oIConexion != null) {
				oIConexion.cierraConexion();
			}
		}
		return mensaje;
	}
	
	public List<EOperacionSolicitud> listarSolicitud(EOperacionSolicitud eOperacionSolicitud, int indicadorConsulta){
		IConexion oIConexion = null;
		List<EOperacionSolicitud> resultado = null;
		DAOOperacion oDAOOperacion= null;
		try {
			oIConexion = FabricaConexion.creaConexion();
			oDAOOperacion = new DAOOperacion(oIConexion);
			resultado = oDAOOperacion.listarSolicitud(eOperacionSolicitud, indicadorConsulta);	
		} catch (Exception e) {
			UManejadorLog.error("Control: Error al listar solicitud: " + e.getMessage());
		} finally {
			if (oIConexion != null) {
				oIConexion.cierraConexion();
			}
		}
		return resultado;
	}
	
	public List<EOperacionMensaje> listarMensaje(long codigoSolicitud){
		IConexion oIConexion = null;
		DAOOperacion oDAOOperacion= null;
		List<EOperacionMensaje> resultado = null;
		try {
			oIConexion = FabricaConexion.creaConexion();			
			oDAOOperacion = new DAOOperacion(oIConexion);
			resultado = oDAOOperacion.listarMensaje(codigoSolicitud);
		} catch (Exception e) {
			UManejadorLog.error("Control: Error al buscar mensaje: " + e.getMessage());
		} finally {
			if (oIConexion != null) {
				oIConexion.cierraConexion();
			}
		}
		return resultado;
	}
	
	public List<EOperacionDocumento> listarDocumento(long codigoSolicitud){
		IConexion oIConexion = null;
		DAOOperacion oDAOOperacion= null;
		List<EOperacionDocumento> resultado = null;
		try {
			oIConexion = FabricaConexion.creaConexion();			
			oDAOOperacion = new DAOOperacion(oIConexion);
			resultado = oDAOOperacion.listarDocumento(codigoSolicitud);
		} catch (Exception e) {
			UManejadorLog.error("Control: Error al buscar documento: " + e.getMessage());
		} finally {
			if (oIConexion != null) {
				oIConexion.cierraConexion();
			}
		}
		return resultado;
	}
	
	public List<EOperacionDocumento> listarDocumentoGarantia(EGarantia eOGarantia,int indicador){
		IConexion oIConexion = null;
		DAOOperacion oDAOOperacion= null;
		List<EOperacionDocumento> resultado = null;
		try {
			oIConexion = FabricaConexion.creaConexion();			
			oDAOOperacion = new DAOOperacion(oIConexion);
			resultado = oDAOOperacion.listarDocumentoGarantia(eOGarantia,indicador);
		} catch (Exception e) {
			UManejadorLog.error("Control: Error al buscar documento: " + e.getMessage());
		} finally {
			if (oIConexion != null) {
				oIConexion.cierraConexion();
			}
		}
		return resultado;
	}
	
	public List<EOperacionDocumentoRequerido> listarDocumentoRequerido(long codigoSolicitud){
		IConexion oIConexion = null;
		DAOOperacion oDAOOperacion= null;
		List<EOperacionDocumentoRequerido> resultado = null;
		try {
			oIConexion = FabricaConexion.creaConexion();			
			oDAOOperacion = new DAOOperacion(oIConexion);
			resultado = oDAOOperacion.listarDocumentoRequerido(codigoSolicitud);
		} catch (Exception e) {
			UManejadorLog.error("Control: Error al buscar documento: " + e.getMessage());
		} finally {
			if (oIConexion != null) {
				oIConexion.cierraConexion();
			}
		}
		return resultado;
	}
	
	public List<EDocumentoCarga> listarDocumentoTemporal(long codigoSolicitud){
		IConexion oIConexion = null;
		DAOOperacion oDAOOperacion= null;
		List<EDocumentoCarga> resultado = null;
		try {
			oIConexion = FabricaConexion.creaConexion();			
			oDAOOperacion = new DAOOperacion(oIConexion);
			resultado = oDAOOperacion.listarDocumentoTemporal(codigoSolicitud);
		} catch (Exception e) {
			UManejadorLog.error("Control: Error al buscar documento: " + e.getMessage());
		} finally {
			if (oIConexion != null) {
				oIConexion.cierraConexion();
			}
		}
		return resultado;
	}
	
	public List<EOperacionDocumentoRevision> listarDocumentoRevision(long codigoSolicitud){
		IConexion oIConexion = null;
		DAOOperacion oDAOOperacion= null;
		List<EOperacionDocumentoRevision> resultado = null;
		try {
			oIConexion = FabricaConexion.creaConexion();			
			oDAOOperacion = new DAOOperacion(oIConexion);
			resultado = oDAOOperacion.listarDocumentoRevision(codigoSolicitud);
		} catch (Exception e) {
			UManejadorLog.error("Control: Error al buscar documento: " + e.getMessage());
		} finally {
			if (oIConexion != null) {
				oIConexion.cierraConexion();
			}
		}
		return resultado;
	}
	
	public List<EEstado> listarEstadoPorSolicitud(long codigoSolicitud, EUsuario eUsuario){
		IConexion oIConexion = null;
		List<EEstado> resultado = null;
		DAOOperacion oDAOOperacion= null;
		try {
			oIConexion = FabricaConexion.creaConexion();
			oDAOOperacion = new DAOOperacion(oIConexion);
			resultado = oDAOOperacion.listarEstadoPorSolicitud(codigoSolicitud, eUsuario);	
		} catch (Exception e) {
			UManejadorLog.error("Control: Error al listar estado: " + e.getMessage());
		} finally {
			if (oIConexion != null) {
				oIConexion.cierraConexion();
			}
		}
		return resultado;
	}
	
	public EOperacionSolicitud buscarSolicitud(long codigoSolicitud){
		IConexion oIConexion = null;
		EOperacionSolicitud resultado = null;
		DAOOperacion oDAOOperacion= null;
		try {
			oIConexion = FabricaConexion.creaConexion();
			oDAOOperacion = new DAOOperacion(oIConexion);
			resultado = oDAOOperacion.buscarSolicitud(codigoSolicitud);
		} catch (Exception e) {
			UManejadorLog.error("Control: Error al buscar: " + e.getMessage());
		} finally {
			if (oIConexion != null) {
				oIConexion.cierraConexion();
			}
		}
		return resultado;
	}
	
	public String buscarMensajeTemporal(long codigoSolicitud){
		IConexion oIConexion = null;
		DAOOperacion oDAOOperacion= null;
		String resultado = null;
		try {
			oIConexion = FabricaConexion.creaConexion();			
			oDAOOperacion = new DAOOperacion(oIConexion);
			resultado = oDAOOperacion.buscarMensajeTemporal(codigoSolicitud);
		} catch (Exception e) {
			UManejadorLog.error("Control: Error al buscar mensaje: " + e.getMessage());
		} finally {
			if (oIConexion != null) {
				oIConexion.cierraConexion();
			}
		}
		return resultado;
	}
	
	public EOpcion buscarOpcionPorSolicitud(long codigoSolicitud, int codigoTipoEvaluacion, EUsuario eUsuario){
		IConexion oIConexion = null;
		EOpcion resultado = null;
		DAOOperacion oDAOOperacion= null;
		try {
			oIConexion = FabricaConexion.creaConexion();
			oDAOOperacion = new DAOOperacion(oIConexion);
			resultado = oDAOOperacion.buscarOpcionPorSolicitud(codigoSolicitud, codigoTipoEvaluacion, eUsuario);
		} catch (Exception e) {
			UManejadorLog.error("Control: Error al buscar: " + e.getMessage());
		} finally {
			if (oIConexion != null) {
				oIConexion.cierraConexion();
			}
		}
		return resultado;
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	public EMensaje agregarEvaluacionLevantamientoGarantia(EOperacionLevantamientoGarantia eOperacionLevantamientoGarantia){
		IConexion oIConexion = null;
		EMensaje mensaje = new EMensaje();
		DAOOperacion oDAOOperacion= null;
		try {
			oIConexion = FabricaConexion.creaConexion();
			oIConexion.iniciaTransaccion();
			oDAOOperacion = new DAOOperacion(oIConexion);
			
			mensaje = oDAOOperacion.agregarEvaluacionLevantamientoGarantia(eOperacionLevantamientoGarantia);
			if (!UFuncionesGenerales.validaMensaje(mensaje)) {
				throw new Exception(mensaje.getDescMensaje());
			}
			
			oIConexion.ejecutaCommit();
		} catch (Exception e) {
			if (oIConexion != null) {
				oIConexion.ejecutaRollback();
			}
			mensaje.setDescMensaje(UMensajeOperacion.MSJ_4 + mensaje.getDescMensaje());
			UManejadorLog.error("Control: Error al agregar solicitud levantamiento: " + e.getMessage());
		} finally {
			if (oIConexion != null) {
				oIConexion.cierraConexion();
			}
		}
		return mensaje;
	}
	
	public EMensaje modificarEvaluacionLevantamientoGarantia(EOperacionLevantamientoGarantia eOperacionLevantamientoGarantia, boolean indicadorLiberarGarantia){
		IConexion oIConexion = null;
		EMensaje mensaje = new EMensaje();
		DAOOperacion oDAOOperacion= null;
		DAOGarantia oDAOGarantia = null;
		try {
			oIConexion = FabricaConexion.creaConexion();
			oIConexion.iniciaTransaccion();
			oDAOOperacion = new DAOOperacion(oIConexion);
			oDAOGarantia = new DAOGarantia(oIConexion);
			
			if(indicadorLiberarGarantia == true){
				EGarantia oEGarantia = new EGarantia();
				oEGarantia.setCodigoGarantia(eOperacionLevantamientoGarantia.getCodigoGarantia());
				oEGarantia.setUsuarioRegistro(eOperacionLevantamientoGarantia.getUsuarioRegistro());
				oEGarantia.setFechaRegistro(eOperacionLevantamientoGarantia.getFechaRegistro());
				mensaje = oDAOGarantia.liberarGarantia(oEGarantia);
				if (!UFuncionesGenerales.validaMensaje(mensaje)) {
					throw new Exception(mensaje.getDescMensaje());
				}
			}
			
			mensaje = oDAOOperacion.modificarEvaluacionLevantamientoGarantia(eOperacionLevantamientoGarantia);
			if (!UFuncionesGenerales.validaMensaje(mensaje)) {
				throw new Exception(mensaje.getDescMensaje());
			}
			
			if(indicadorLiberarGarantia == true){
				if (UFuncionesGenerales.validaMensaje(mensaje)) {
					mensaje.setDescMensaje(UMensajeOperacion.MSJ_21);
				}
			}
			
			oIConexion.ejecutaCommit();
		} catch (Exception e) {
			if (oIConexion != null) {
				oIConexion.ejecutaRollback();
			}
			mensaje.setDescMensaje(UMensajeOperacion.MSJ_4 + mensaje.getDescMensaje());
			UManejadorLog.error("Control: Error al modificar solicitud levantamiento: " + e.getMessage());
		} finally {
			if (oIConexion != null) {
				oIConexion.cierraConexion();
			}
		}
		return mensaje;
	}
	
	public List<EOperacionLevantamientoGarantiaMensaje> listarMensajeLevantamientoGarantia(int codigoServicio, long codigoGarantia, int codigoMoneda){
		IConexion oIConexion = null;
		List<EOperacionLevantamientoGarantiaMensaje> resultado = null;
		DAOOperacion oDAOOperacion= null;
		try {
			oIConexion = FabricaConexion.creaConexion();			
			oDAOOperacion = new DAOOperacion(oIConexion);
			resultado = oDAOOperacion.listarMensajeLevantamientoGarantia(codigoServicio, codigoGarantia, codigoMoneda);			
		} catch (Exception e) {
			UManejadorLog.error("Control: Error al listar Garantia por revisar " + e.getMessage());
		} finally {
			if (oIConexion != null) {
				oIConexion.cierraConexion();
			}
		}
		return resultado;
	}
	
	public List<EGarantiaSolicitud> listarEvaluacionLevantamientoGarantia(int codigo, String descripcion, EUsuario eUsuario, int indicadorConsulta){
		IConexion oIConexion = null;
		List<EGarantiaSolicitud> resultado = null;
		DAOOperacion oDAOOperacion= null;
		try {
			oIConexion = FabricaConexion.creaConexion();			
			oDAOOperacion = new DAOOperacion(oIConexion);
			resultado = oDAOOperacion.listarEvaluacionLevantamientoGarantia(codigo, descripcion, eUsuario, indicadorConsulta);			
		} catch (Exception e) {
			UManejadorLog.error("Control: Error al listar Garantia por revisar " + e.getMessage());
		} finally {
			if (oIConexion != null) {
				oIConexion.cierraConexion();
			}
		}
		return resultado;
	}
	
	public EMensaje agregarModificarEvaluacionCliente(EOperacionCliente eOperacionCliente){
		IConexion oIConexion = null;
		EMensaje mensaje = new EMensaje();
		DAOOperacion oDAOOperacion= null;
		try {
			oIConexion = FabricaConexion.creaConexion();
			oIConexion.iniciaTransaccion();
			oDAOOperacion = new DAOOperacion(oIConexion);
			
			mensaje = oDAOOperacion.agregarModificarEvaluacionCliente(eOperacionCliente);
			if (!UFuncionesGenerales.validaMensaje(mensaje)) {
				throw new Exception(mensaje.getDescMensaje());
			}
			
			oIConexion.ejecutaCommit();
		} catch (Exception e) {
			if (oIConexion != null) {
				oIConexion.ejecutaRollback();
			}
			mensaje.setDescMensaje(UMensajeOperacion.MSJ_4 + mensaje.getDescMensaje());
			UManejadorLog.error("Control: Error al agregar o modificar evaluacion cliente: " + e.getMessage());
		} finally {
			if (oIConexion != null) {
				oIConexion.cierraConexion();
			}
		}
		return mensaje;
	}
	
	public EMensaje modificarEvaluacionCliente(EOperacionCliente eOperacionCliente){
		IConexion oIConexion = null;
		EMensaje mensaje = new EMensaje();
		DAOOperacion oDAOOperacion= null;
		DAOSolicitudCredito oDAOSolicitudCredito = null;
		DAORepresentanteLegal oDAORepresentanteLegal= null;
		BOGeneral oBOGeneral  = null;
		try {
			oIConexion = FabricaConexion.creaConexion();
			oIConexion.iniciaTransaccion();
			oDAOOperacion = new DAOOperacion(oIConexion);
			oDAOSolicitudCredito = new DAOSolicitudCredito(oIConexion);
			oDAORepresentanteLegal = new DAORepresentanteLegal(oIConexion);
			oBOGeneral = new BOGeneral();
			
			mensaje = oDAOOperacion.modificarEvaluacionCliente(eOperacionCliente);
			if (!UFuncionesGenerales.validaMensaje(mensaje)) {
				throw new Exception(mensaje.getDescMensaje());
			}
			
			mensaje = oDAOSolicitudCredito.agregarModificarInformeLegalAdicional2(eOperacionCliente, eOperacionCliente.getInformeLegalAdicional());
			if (!UFuncionesGenerales.validaMensaje(mensaje)) {
				throw new Exception(mensaje.getDescMensaje());
			}
			
			/*
			mensaje = oDAOSolicitudCredito.agregarModificarInformeLegalAdicional2(eOperacionCliente.getInformeLegalAdicional());
			if (!UFuncionesGenerales.validaMensaje(mensaje)) {
				throw new Exception(mensaje.getDescMensaje());
			}
			*/
			
			//Eliminar Suscripcion
			mensaje = oDAOSolicitudCredito.eliminarSuscripcion(eOperacionCliente.getNumeroSolicitud(), eOperacionCliente.getCodigoCliente(), eOperacionCliente.getCodigoTipoCliente());
			if (!UFuncionesGenerales.validaMensaje(mensaje)) {
				throw new Exception(mensaje.getDescMensaje());
			}
			mensaje = oDAOSolicitudCredito.eliminarClienteSuscripcion(eOperacionCliente.getNumeroDocumento());
			if (!UFuncionesGenerales.validaMensaje(mensaje)) {
				throw new Exception(mensaje.getDescMensaje());
			}
			
			//Agregar Suscripcion
			if(eOperacionCliente.getLstSuscripcion() != null){
			for(ESuscripcion oESuscripcion: eOperacionCliente.getLstSuscripcion()){
				oESuscripcion.setNumeroSolicitud(eOperacionCliente.getNumeroSolicitud());
				oESuscripcion.setCodigoCliente(eOperacionCliente.getCodigoCliente());
				oESuscripcion.setCodigoTipoCliente(eOperacionCliente.getCodigoTipoCliente());
				oESuscripcion.setNumeroDocumento(eOperacionCliente.getNumeroDocumento());
				oESuscripcion.setUsuarioRegistro(eOperacionCliente.getUsuarioRegistro());
				oESuscripcion.setFechaRegistro(eOperacionCliente.getFechaRegistro());
				mensaje = oDAOSolicitudCredito.agregarSuscripcion(oESuscripcion);
				if (!UFuncionesGenerales.validaMensaje(mensaje)) {
					throw new Exception(mensaje.getDescMensaje());
				}
				mensaje = oDAOSolicitudCredito.agregarClienteSuscripcion(oESuscripcion);
				if (!UFuncionesGenerales.validaMensaje(mensaje)) {
					throw new Exception(mensaje.getDescMensaje());
				}
			}
			}
			
			//Agregar, Modificar y Eliminar Representante Legal
			if(eOperacionCliente.getLstRepresentanteLegal() != null){
				for(ERepresentanteLegal oERepresentanteLegal: eOperacionCliente.getLstRepresentanteLegal()){
					oERepresentanteLegal.setUsuarioRegistro(eOperacionCliente.getUsuarioRegistro());
					oERepresentanteLegal.setFechaRegistro(eOperacionCliente.getFechaRegistro());
					
					if(oERepresentanteLegal.getCodigoAccion() == UAccionTabla.INSERTAR  && oERepresentanteLegal.getCodigoRepresentante() == 0){
						//Generar codigo representante
						oERepresentanteLegal.setCodigoRepresentante(oBOGeneral.generarCorrelativo(UTipoCorrelativo.REPRESENTATELEGAL, "", "", ""));
						//Setear codigo cliente
						oERepresentanteLegal.setCodigoTipoCliente(eOperacionCliente.getCodigoTipoCliente());
						oERepresentanteLegal.setCodigoCliente(eOperacionCliente.getCodigoCliente());
						
						mensaje = oDAORepresentanteLegal.agregarRepresentanteLegal(oERepresentanteLegal);
						if (!UFuncionesGenerales.validaMensaje(mensaje)) {
							throw new Exception(mensaje.getDescMensaje());
						}
						
						mensaje = oDAORepresentanteLegal.agregarRepresentanteLegalSolicitudCredito(oERepresentanteLegal,eOperacionCliente.getNumeroSolicitud());
						if (!UFuncionesGenerales.validaMensaje(mensaje)) {
							throw new Exception(mensaje.getDescMensaje());
							
						}
						
						//Agregar facultad
						if(oERepresentanteLegal.getLstFacultadPoder() != null){
						for(EFacultadPoder oEFacultadPoder: oERepresentanteLegal.getLstFacultadPoder()){
							oEFacultadPoder.setUsuarioRegistro(eOperacionCliente.getUsuarioRegistro());
							oEFacultadPoder.setFechaRegistro(eOperacionCliente.getFechaRegistro());
							mensaje = oDAORepresentanteLegal.agregarFacultadPoder(oERepresentanteLegal, oEFacultadPoder);
							if (!UFuncionesGenerales.validaMensaje(mensaje)) {
								throw new Exception(mensaje.getDescMensaje());
							}
						}
						}
					}else if(oERepresentanteLegal.getCodigoAccion() == UAccionTabla.EDITAR  && oERepresentanteLegal.getCodigoRepresentante() != 0){
						mensaje = oDAORepresentanteLegal.modificarRepresentanteLegal(oERepresentanteLegal);
						if (!UFuncionesGenerales.validaMensaje(mensaje)) {
							throw new Exception(mensaje.getDescMensaje());
						}
						
						mensaje = oDAORepresentanteLegal.modificarRepresentanteLegalSolicitudCredito(oERepresentanteLegal,eOperacionCliente.getNumeroSolicitud());
						if (!UFuncionesGenerales.validaMensaje(mensaje)) {
							throw new Exception(mensaje.getDescMensaje());
							
						}
						//Eliminar facultad por representante
						mensaje = oDAORepresentanteLegal.eliminarFacultadPoder(oERepresentanteLegal);
						if (!UFuncionesGenerales.validaMensaje(mensaje)) {
							throw new Exception(mensaje.getDescMensaje());
						}
						
						//Agregar facultad
						if(oERepresentanteLegal.getLstFacultadPoder() != null){
						for(EFacultadPoder oEFacultadPoder: oERepresentanteLegal.getLstFacultadPoder()){
							oEFacultadPoder.setUsuarioRegistro(eOperacionCliente.getUsuarioRegistro());
							oEFacultadPoder.setFechaRegistro(eOperacionCliente.getFechaRegistro());
							if(oEFacultadPoder.getCodigoRepresentanteRelacion() == 0){
								oEFacultadPoder.setCodigoRepresentanteRelacion(oBOGeneral.generarCorrelativo(UTipoCorrelativo.REPRESENTATELEGAL, "", "", ""));
							}
							mensaje = oDAORepresentanteLegal.agregarFacultadPoder(oERepresentanteLegal, oEFacultadPoder);
							if (!UFuncionesGenerales.validaMensaje(mensaje)) {
								throw new Exception(mensaje.getDescMensaje());
							}
						}
						}
					}else if(oERepresentanteLegal.getCodigoAccion() == UAccionTabla.ELIMINAR && oERepresentanteLegal.getCodigoRepresentante() != 0){
						mensaje = oDAORepresentanteLegal.eliminarRepresentanteLegal(oERepresentanteLegal);
						if (!UFuncionesGenerales.validaMensaje(mensaje)) {
							throw new Exception(mensaje.getDescMensaje());
						}
						mensaje = oDAORepresentanteLegal.eliminarRepresentanteLegalSolicitudCredito(oERepresentanteLegal, eOperacionCliente.getNumeroSolicitud());
						if (!UFuncionesGenerales.validaMensaje(mensaje)) {
							throw new Exception(mensaje.getDescMensaje());
						}
					}
				}
			}
			
			//Eliminar Deudor
			if(eOperacionCliente.getLstDeudorRecycle().size() > 0){
				for(EDeudor oEDeudor: eOperacionCliente.getLstDeudorRecycle()){
					oEDeudor.setCodigoCliente(eOperacionCliente.getCodigoCliente());
					oEDeudor.setNumeroSolicitud(eOperacionCliente.getNumeroSolicitud());
					mensaje = oDAOSolicitudCredito.eliminarDeudor(oEDeudor);
					if (!UFuncionesGenerales.validaMensaje(mensaje)) {
						throw new Exception(mensaje.getDescMensaje());
					}
				}
			}
			
			//Modificar Deudor
			if(eOperacionCliente.getLstDeudor().size() > 0){
				for(EDeudor oEDeudor: eOperacionCliente.getLstDeudor()){
					oEDeudor.setCodigoCliente(eOperacionCliente.getCodigoCliente());
					oEDeudor.setNumeroSolicitud(eOperacionCliente.getNumeroSolicitud());
					oEDeudor.setUsuarioRegistro(eOperacionCliente.getUsuarioRegistro());
					oEDeudor.setFechaRegistro(eOperacionCliente.getFechaRegistro());
					mensaje = oDAOSolicitudCredito.modificarDeudor(oEDeudor);
					if (!UFuncionesGenerales.validaMensaje(mensaje)) {
						throw new Exception(mensaje.getDescMensaje());
					}
				}
			}
			
			//Agregar Documento
			if(eOperacionCliente.getLstDocumentoCarga().size() > 0){
				for(EDocumentoCarga oEDocumentoCarga: eOperacionCliente.getLstDocumentoCarga()){
					/*
					mensajeLaserFiche = oUManejadorArchivo.guardarDocumento(eOperacionCliente, oEDocumentoCarga);
					if (!UFuncionesGenerales.validaMensajeLaserFiche(mensajeLaserFiche)) {
						throw new Exception(mensajeLaserFiche.getDescripcion());
					}
					
					oEDocumentoCarga.setCodigoLaserFiche(mensajeLaserFiche.getDocumentoID());
					*/
					mensaje = oDAOOperacion.agregarEvaluacionClienteDocumento(eOperacionCliente, oEDocumentoCarga);
					if (!UFuncionesGenerales.validaMensaje(mensaje)) {
						throw new Exception(mensaje.getDescMensaje());
					}
				}
			}
			
			oIConexion.ejecutaCommit();
		} catch (Exception e) {
			if (oIConexion != null) {
				oIConexion.ejecutaRollback();
			}
			mensaje.setDescMensaje(UMensajeOperacion.MSJ_4 + mensaje.getDescMensaje());
			UManejadorLog.error("Control: Error al modificar evaluacion cliente: " + e.getMessage());
		} finally {
			if (oIConexion != null) {
				oIConexion.cierraConexion();
			}
		}
		return mensaje;
	}
	
	public List<EOperacionCliente> listarEvaluacionCliente(int codigo, String descripcion, EUsuario eUsuario){
		IConexion oIConexion = null;
		List<EOperacionCliente> resultado = null;
		DAOOperacion oDAOOperacion= null;
		try {
			oIConexion = FabricaConexion.creaConexion();		
			oDAOOperacion = new DAOOperacion(oIConexion);
			resultado = oDAOOperacion.listarEvaluacionCliente(codigo, descripcion, eUsuario);			
		} catch (Exception e) {
			UManejadorLog.error("Control: Error al listar evaluacion cliente " + e.getMessage());
		} finally {
			if (oIConexion != null) {
				oIConexion.cierraConexion();
			}
		}
		return resultado;
	}
	
	public List<EOperacionClienteDocumento> listarEvaluacionClienteDocumento(long numeroSolicitud, int codigoTipoCliente, int codigoCliente){
		IConexion oIConexion = null;
		List<EOperacionClienteDocumento> resultado = null;
		DAOOperacion oDAOOperacion= null;
		try {
			oIConexion = FabricaConexion.creaConexion();		
			oDAOOperacion = new DAOOperacion(oIConexion);
			resultado = oDAOOperacion.listarEvaluacionClienteDocumento(numeroSolicitud, codigoTipoCliente, codigoCliente);			
		} catch (Exception e) {
			UManejadorLog.error("Control: Error al listar documento " + e.getMessage());
		} finally {
			if (oIConexion != null) {
				oIConexion.cierraConexion();
			}
		}
		return resultado;
	}
	
	public EOperacionCliente buscarEvaluacionCliente(long numeroSolicitud, int codigoTipoCliente, int codigoCliente){
		IConexion oIConexion = null;
		EOperacionCliente resultado = null;
		DAOOperacion oDAOOperacion= null;
		try {
			oIConexion = FabricaConexion.creaConexion();			
			oDAOOperacion = new DAOOperacion(oIConexion);
			resultado = oDAOOperacion.buscarEvaluacionCliente(numeroSolicitud, codigoTipoCliente, codigoCliente);			
		} catch (Exception e) {
			UManejadorLog.error("Control: Error al obtener evaluacion cliente " + e.getMessage());
		} finally {
			if (oIConexion != null) {
				oIConexion.cierraConexion();
			}
		}
		return resultado;
	}
	
	public EMensaje agregarEvaluacionSolicitudCredito(){
		IConexion oIConexion = null;
		EMensaje mensaje = new EMensaje();
		DAOOperacion oDAOOperacion= null;
		try {
			oIConexion = FabricaConexion.creaConexion();
			oIConexion.iniciaTransaccion();
			oDAOOperacion = new DAOOperacion(oIConexion);
			
			mensaje = oDAOOperacion.agregarEvaluacionSolicitudCredito();
			if (!UFuncionesGenerales.validaMensaje(mensaje)) {
				throw new Exception(mensaje.getDescMensaje());
			}
			
			oIConexion.ejecutaCommit();
		} catch (Exception e) {
			if (oIConexion != null) {
				oIConexion.ejecutaRollback();
			}
			mensaje.setDescMensaje(UMensajeOperacion.MSJ_4 + mensaje.getDescMensaje());
			UManejadorLog.error("Control: Error al agregar solicitud: " + e.getMessage());
		} finally {
			if (oIConexion != null) {
				oIConexion.cierraConexion();
			}
		}
		return mensaje;
	}
	

	public EMensaje modificarEvaluacionSolicitudCredito2(EOperacionSolicitudCredito eOperacionSolicitudCredito, ECliente eCliente, EClienteConstitucionEmpresa eClienteConstitucionEmpresa, EClienteAdicional eClienteAdicional, int codigoTipoCliente){
		IConexion oIConexion = null;
		EMensaje mensaje = new EMensaje();
		Mensaje mensajeLaserFiche = new Mensaje();
		UManejadorArchivo oUManejadorArchivo = new UManejadorArchivo();
		DAOOperacion oDAOOperacion= null;
		DAOSolicitudCredito oDAOSolicitudCredito= null;
		DAORepresentanteLegal oDAORepresentanteLegal= null;
		DAOCliente oDAOCliente= null;
		BOGeneral oBOGeneral  = null;
		try {
			oIConexion = FabricaConexion.creaConexion();
			oIConexion.iniciaTransaccion();
			oDAOOperacion = new DAOOperacion(oIConexion);
			oDAOSolicitudCredito = new DAOSolicitudCredito(oIConexion);
			oDAORepresentanteLegal = new DAORepresentanteLegal(oIConexion);
			oDAOCliente = new DAOCliente(oIConexion);
			oBOGeneral = new BOGeneral();
			
			eOperacionSolicitudCredito.setNumeroMensaje(oBOGeneral.generarCorrelativo(UTipoCorrelativo.OPERACIONSOLICITUDCREDITOMENSAJE, eOperacionSolicitudCredito.getNumeroSolicitud()+"", eOperacionSolicitudCredito.getCodigoTipoCliente()+"", eOperacionSolicitudCredito.getCodigoCliente()+""));
			
			//Agregar Log Movimiento
			if(eOperacionSolicitudCredito.getLstSolicitudLogMovimiento() != null){
				Date fecha = new Date();
				for(ESolicitudLogMovimiento oESolicitudLogMovimiento: eOperacionSolicitudCredito.getLstSolicitudLogMovimiento()){
					fecha.setSeconds(fecha.getSeconds()+1);
					oESolicitudLogMovimiento.setNumeroSolicitud(eOperacionSolicitudCredito.getNumeroSolicitud());
					oESolicitudLogMovimiento.setCodigoAccion(oESolicitudLogMovimiento.getCodigoAccion());
					oESolicitudLogMovimiento.setUsuarioRegistro(eOperacionSolicitudCredito.getUsuarioRegistro());
					oESolicitudLogMovimiento.setFechaRegistro(fecha);
					mensaje = oDAOSolicitudCredito.agregarLogMovimiento(oESolicitudLogMovimiento);
					if (!UFuncionesGenerales.validaMensaje(mensaje)) {
						throw new Exception(mensaje.getDescMensaje());
					}
					
					/*
					if(oESolicitudLogMovimiento.getCodigoAccion() == UTipologMovimiento.CAMBIADTOSOCIO){
						mensaje = oDAOCliente.agregarHistoricoCliente(eEvaluacionSolicitudCreditoLegal, eEvaluacionSolicitudCreditoLegal.getInformeLegalAdicional(), eCliente, eClienteConstitucionEmpresa, eClienteAdicional);
						if (!UFuncionesGenerales.validaMensaje(mensaje)) {
							throw new Exception(mensaje.getDescMensaje());
						}
					}
					*/
				}
			}
			
			mensaje = oDAOOperacion.modificarEvaluacionSolicitudCredito(eOperacionSolicitudCredito);
			if (!UFuncionesGenerales.validaMensaje(mensaje)) {
				throw new Exception(mensaje.getDescMensaje());
			}
			
			mensaje = oDAOOperacion.agregarModificarInformeLegalAdicional(eOperacionSolicitudCredito, eOperacionSolicitudCredito.getInformeLegalAdicional());
			if (!UFuncionesGenerales.validaMensaje(mensaje)) {
				throw new Exception(mensaje.getDescMensaje());
			}
			
			//Eliminar Suscripcion
			mensaje = oDAOSolicitudCredito.eliminarSuscripcion(eOperacionSolicitudCredito.getNumeroSolicitud(), eOperacionSolicitudCredito.getCodigoCliente(), eOperacionSolicitudCredito.getCodigoTipoCliente());
			if (!UFuncionesGenerales.validaMensaje(mensaje)) {
				throw new Exception(mensaje.getDescMensaje());
			}
			mensaje = oDAOSolicitudCredito.eliminarClienteSuscripcion(eOperacionSolicitudCredito.getNumeroDocumento());
			if (!UFuncionesGenerales.validaMensaje(mensaje)) {
				throw new Exception(mensaje.getDescMensaje());
			}
			
			//Agregar Suscripcion
			if(eOperacionSolicitudCredito.getLstSuscripcion() != null){
			for(ESuscripcion oESuscripcion: eOperacionSolicitudCredito.getLstSuscripcion()){
				oESuscripcion.setNumeroSolicitud(eOperacionSolicitudCredito.getNumeroSolicitud());
				oESuscripcion.setCodigoCliente(eOperacionSolicitudCredito.getCodigoCliente());
				oESuscripcion.setCodigoTipoCliente(eOperacionSolicitudCredito.getCodigoTipoCliente());
				oESuscripcion.setNumeroDocumento(eOperacionSolicitudCredito.getNumeroDocumento());
				oESuscripcion.setUsuarioRegistro(eOperacionSolicitudCredito.getUsuarioRegistro());
				oESuscripcion.setFechaRegistro(eOperacionSolicitudCredito.getFechaRegistro());
				mensaje = oDAOSolicitudCredito.agregarSuscripcion(oESuscripcion);
				if (!UFuncionesGenerales.validaMensaje(mensaje)) {
					throw new Exception(mensaje.getDescMensaje());
				}
				mensaje = oDAOSolicitudCredito.agregarClienteSuscripcion(oESuscripcion);
				if (!UFuncionesGenerales.validaMensaje(mensaje)) {
					throw new Exception(mensaje.getDescMensaje());
				}
			}
			}
			
			if(eOperacionSolicitudCredito.getUsuarioRegistro().getCodigoArea() == UArea.LEGAL){
				EObservacionLegal oEObservacionLegal = new EObservacionLegal();
				oEObservacionLegal.setNumeroSolicitud(eOperacionSolicitudCredito.getNumeroSolicitud());
				oEObservacionLegal.setNombreUsuario(eOperacionSolicitudCredito.getUsuarioRegistro().getNombreUsuario());
				oEObservacionLegal.setSecuencia(1);
				oEObservacionLegal.setFechaRegistro(eOperacionSolicitudCredito.getFechaRegistro());
				
				//Agregar observacion maestra
				mensaje = oDAOSolicitudCredito.agregarObservacionMaestra(oEObservacionLegal);
				if (!UFuncionesGenerales.validaMensaje(mensaje)) {
					throw new Exception(mensaje.getDescMensaje());
				}
				
				//Eliminar observacion anterior
				mensaje = oDAOSolicitudCredito.eliminarObservacionDetalle(oEObservacionLegal);
				if (!UFuncionesGenerales.validaMensaje(mensaje)) {
					throw new Exception(mensaje.getDescMensaje());
				}
				
				//Agregar observacion linea por linea
				String mensaje_observacion = eOperacionSolicitudCredito.getDescripcionMensaje();
				String lista_observacion[] = mensaje_observacion.split("\r\n");
				
				for(int i=0;i<lista_observacion.length;i++){
					oEObservacionLegal.setDescripcionMensaje(lista_observacion[i]);
					oEObservacionLegal.setLineaObservacion(i+1);
					mensaje = oDAOSolicitudCredito.agregarObservacionDetalle(oEObservacionLegal);
					if (!UFuncionesGenerales.validaMensaje(mensaje)) {
						throw new Exception(mensaje.getDescMensaje());
					}
				}
			}else {
				if (eOperacionSolicitudCredito.getObservacionNegocios() != null){
					if (eOperacionSolicitudCredito.getObservacionNegocios() != ""){
						EObservacionNegocios oEObservacionNegocios = new EObservacionNegocios();
						oEObservacionNegocios.setNumeroSolicitud(eOperacionSolicitudCredito.getNumeroSolicitud());
						oEObservacionNegocios.setSecuencia(0);
						oEObservacionNegocios.setObservacion(eOperacionSolicitudCredito.getObservacionNegocios());
						oEObservacionNegocios.setNombreUsuario(eOperacionSolicitudCredito.getUsuarioRegistro().getNombreUsuario());
						oEObservacionNegocios.setFechaEvaluacion(eOperacionSolicitudCredito.getFechaRegistro());
						
						//Agregar observacion maestra
						mensaje = oDAOSolicitudCredito.agregarObservacionNegociosMaestra(oEObservacionNegocios);
						if (!UFuncionesGenerales.validaMensaje(mensaje)) {
							throw new Exception(mensaje.getDescMensaje());
						}
					}
				}
			}

			
			//Agregar, Modificar y Eliminar Representante Legal
			if(eOperacionSolicitudCredito.getLstRepresentanteLegal() != null){
				for(ERepresentanteLegal oERepresentanteLegal: eOperacionSolicitudCredito.getLstRepresentanteLegal()){
					oERepresentanteLegal.setUsuarioRegistro(eOperacionSolicitudCredito.getUsuarioRegistro());
					oERepresentanteLegal.setFechaRegistro(eOperacionSolicitudCredito.getFechaRegistro());
					
					if(oERepresentanteLegal.getCodigoAccion() == UAccionTabla.INSERTAR  && oERepresentanteLegal.getCodigoRepresentante() == 0){
						//Generar codigo representante
						oERepresentanteLegal.setCodigoRepresentante(oBOGeneral.generarCorrelativo(UTipoCorrelativo.REPRESENTATELEGAL, "", "", ""));
						//Setear codigo cliente
						oERepresentanteLegal.setCodigoTipoCliente(codigoTipoCliente);
						oERepresentanteLegal.setCodigoCliente(eOperacionSolicitudCredito.getCodigoCliente());
						
						mensaje = oDAORepresentanteLegal.agregarRepresentanteLegal(oERepresentanteLegal);
						if (!UFuncionesGenerales.validaMensaje(mensaje)) {
							throw new Exception(mensaje.getDescMensaje());
						}
						
						mensaje = oDAORepresentanteLegal.agregarRepresentanteLegalSolicitudCredito(oERepresentanteLegal, eOperacionSolicitudCredito.getNumeroSolicitud());
						if (!UFuncionesGenerales.validaMensaje(mensaje)) {
							throw new Exception(mensaje.getDescMensaje());
							
						}
						
						//Agregar facultad
						if(oERepresentanteLegal.getLstFacultadPoder() != null){
						for(EFacultadPoder oEFacultadPoder: oERepresentanteLegal.getLstFacultadPoder()){
							oEFacultadPoder.setUsuarioRegistro(eOperacionSolicitudCredito.getUsuarioRegistro());
							oEFacultadPoder.setFechaRegistro(eOperacionSolicitudCredito.getFechaRegistro());
							mensaje = oDAORepresentanteLegal.agregarFacultadPoder(oERepresentanteLegal, oEFacultadPoder);
							if (!UFuncionesGenerales.validaMensaje(mensaje)) {
								throw new Exception(mensaje.getDescMensaje());
							}
						}
						}
					}else if(oERepresentanteLegal.getCodigoAccion() == UAccionTabla.EDITAR  && oERepresentanteLegal.getCodigoRepresentante() != 0){
						mensaje = oDAORepresentanteLegal.modificarRepresentanteLegal(oERepresentanteLegal);
						if (!UFuncionesGenerales.validaMensaje(mensaje)) {
							throw new Exception(mensaje.getDescMensaje());
						}
						
						mensaje = oDAORepresentanteLegal.modificarRepresentanteLegalSolicitudCredito(oERepresentanteLegal, eOperacionSolicitudCredito.getNumeroSolicitud());
						if (!UFuncionesGenerales.validaMensaje(mensaje)) {
							throw new Exception(mensaje.getDescMensaje());
							
						}
						
						//Eliminar facultad por representante
						mensaje = oDAORepresentanteLegal.eliminarFacultadPoder(oERepresentanteLegal);
						if (!UFuncionesGenerales.validaMensaje(mensaje)) {
							throw new Exception(mensaje.getDescMensaje());
						}
						
						//Agregar facultad
						if(oERepresentanteLegal.getLstFacultadPoder() != null){
						for(EFacultadPoder oEFacultadPoder: oERepresentanteLegal.getLstFacultadPoder()){
							oEFacultadPoder.setUsuarioRegistro(eOperacionSolicitudCredito.getUsuarioRegistro());
							oEFacultadPoder.setFechaRegistro(eOperacionSolicitudCredito.getFechaRegistro());
							if(oEFacultadPoder.getCodigoRepresentanteRelacion() == 0){
								oEFacultadPoder.setCodigoRepresentanteRelacion(oBOGeneral.generarCorrelativo(UTipoCorrelativo.REPRESENTATELEGAL, "", "", ""));
							}
							mensaje = oDAORepresentanteLegal.agregarFacultadPoder(oERepresentanteLegal, oEFacultadPoder);
							if (!UFuncionesGenerales.validaMensaje(mensaje)) {
								throw new Exception(mensaje.getDescMensaje());
							}
						}
						}
					}else if(oERepresentanteLegal.getCodigoAccion() == UAccionTabla.ELIMINAR && oERepresentanteLegal.getCodigoRepresentante() != 0){
						mensaje = oDAORepresentanteLegal.eliminarRepresentanteLegal(oERepresentanteLegal);
						if (!UFuncionesGenerales.validaMensaje(mensaje)) {
							throw new Exception(mensaje.getDescMensaje());
						}
						mensaje = oDAORepresentanteLegal.eliminarRepresentanteLegalSolicitudCredito(oERepresentanteLegal, eOperacionSolicitudCredito.getNumeroSolicitud());
						if (!UFuncionesGenerales.validaMensaje(mensaje)) {
							throw new Exception(mensaje.getDescMensaje());
						}
					}
				}
			}
			
			//Eliminar Deudor
			if(eOperacionSolicitudCredito.getLstDeudorRecycle().size() > 0){
				for(EDeudor oEDeudor: eOperacionSolicitudCredito.getLstDeudorRecycle()){
					oEDeudor.setCodigoCliente(eOperacionSolicitudCredito.getCodigoCliente());
					oEDeudor.setNumeroSolicitud(eOperacionSolicitudCredito.getNumeroSolicitud());
					mensaje = oDAOSolicitudCredito.eliminarDeudor(oEDeudor);
					if (!UFuncionesGenerales.validaMensaje(mensaje)) {
						throw new Exception(mensaje.getDescMensaje());
					}
				}
			}
			
			//Modificar Deudor
			if(eOperacionSolicitudCredito.getLstDeudor().size() > 0){
				for(EDeudor oEDeudor: eOperacionSolicitudCredito.getLstDeudor()){
					oEDeudor.setCodigoCliente(eOperacionSolicitudCredito.getCodigoCliente());
					oEDeudor.setNumeroSolicitud(eOperacionSolicitudCredito.getNumeroSolicitud());
					oEDeudor.setUsuarioRegistro(eOperacionSolicitudCredito.getUsuarioRegistro());
					oEDeudor.setFechaRegistro(eOperacionSolicitudCredito.getFechaRegistro());
					mensaje = oDAOSolicitudCredito.modificarDeudor(oEDeudor);
					if (!UFuncionesGenerales.validaMensaje(mensaje)) {
						throw new Exception(mensaje.getDescMensaje());
					}
				}
			}
			
			if(eOperacionSolicitudCredito.getLstDocumentoCarga().size() > 0){
				for(EDocumentoCarga oEDocumentoCarga: eOperacionSolicitudCredito.getLstDocumentoCarga()){
					mensajeLaserFiche = oUManejadorArchivo.guardarDocumentoOperacionSolicitud(eOperacionSolicitudCredito, oEDocumentoCarga);
					if (!UFuncionesGenerales.validaMensajeLaserFiche(mensajeLaserFiche)) {
						throw new Exception(mensajeLaserFiche.getDescripcion());
					}
					
					oEDocumentoCarga.setCodigoLaserFiche(mensajeLaserFiche.getDocumentoID());
					mensaje = oDAOOperacion.agregarEvaluacionSolicitudCreditoDocumento(eOperacionSolicitudCredito, oEDocumentoCarga);
					if (!UFuncionesGenerales.validaMensaje(mensaje)) {
						throw new Exception(mensaje.getDescMensaje());
					}
				}
			}
			
			/*
			if(eOperacionSolicitud.getIndicadorTemporal() == UIndicadorTemporal.SI){
				mensaje = oDAOOperacion.eliminarDocumentoTemporal(eOperacionSolicitud);
				if (!UFuncionesGenerales.validaMensaje(mensaje)) {
					throw new Exception(mensaje.getDescMensaje());
				}
				
				if(eOperacionSolicitud.getLstDocumentoCarga().size() > 0){
					mensaje = oDAOOperacion.agregarDocumentoTemporal(eOperacionSolicitud);
					if (!UFuncionesGenerales.validaMensaje(mensaje)) {
						throw new Exception(mensaje.getDescMensaje());
					}
				}
			}else{
				mensaje = oDAOOperacion.eliminarDocumentoTemporal(eOperacionSolicitud);
				if (!UFuncionesGenerales.validaMensaje(mensaje)) {
					throw new Exception(mensaje.getDescMensaje());
				}
				
				if(eOperacionSolicitud.getLstDocumentoCarga().size() > 0){
					for(EDocumentoCarga oEDocumentoCarga: eOperacionSolicitud.getLstDocumentoCarga()){
						mensajeLaserFiche = oUManejadorArchivo.guardarDocumentoOperacionSolicitud(eEvaluacionSolicitudCreditoLegal, oEDocumentoCarga);
						if (!UFuncionesGenerales.validaMensajeLaserFiche(mensajeLaserFiche)) {
							throw new Exception(mensajeLaserFiche.getDescripcion());
						}
						
						oEDocumentoCarga.setCodigoLaserFiche(mensajeLaserFiche.getDocumentoID());
						mensaje = oDAOOperacion.agregarDocumento(eOperacionSolicitud, oEDocumentoCarga);
						if (!UFuncionesGenerales.validaMensaje(mensaje)) {
							throw new Exception(mensaje.getDescMensaje());
						}
					}
				}
			}
			*/
			
			if(eOperacionSolicitudCredito.getLstOperacionSolicitudCreditoDocumentoRevision().size() > 0){
				mensaje = oDAOOperacion.eliminarEvaluacionSolicitudCreditoDocumentoRevision(eOperacionSolicitudCredito);
				if (!UFuncionesGenerales.validaMensaje(mensaje)) {
					throw new Exception(mensaje.getDescMensaje());
				}
				
				for(EOperacionSolicitudCreditoDocumentoRevision oEOperacionSolicitudCreditoDocumentoRevision: eOperacionSolicitudCredito.getLstOperacionSolicitudCreditoDocumentoRevision()){
					if(oEOperacionSolicitudCreditoDocumentoRevision.getDataDocumento() != null){
						mensajeLaserFiche = oUManejadorArchivo.guardarDocumentoRevisionOperacionSolicitud(eOperacionSolicitudCredito, oEOperacionSolicitudCreditoDocumentoRevision);
						if (!UFuncionesGenerales.validaMensajeLaserFiche(mensajeLaserFiche)) {
							throw new Exception(mensajeLaserFiche.getDescripcion());
						}
						oEOperacionSolicitudCreditoDocumentoRevision.setCodigoDocumentoLaserFiche(mensajeLaserFiche.getDocumentoID());
					}
					
					oEOperacionSolicitudCreditoDocumentoRevision.setDataDocumento(null);
					mensaje = oDAOOperacion.agregarEvaluacionSolicitudCreditoDocumentoRevision(eOperacionSolicitudCredito, oEOperacionSolicitudCreditoDocumentoRevision);
					if (!UFuncionesGenerales.validaMensaje(mensaje)) {
						throw new Exception(mensaje.getDescMensaje());
					}
				}
			}
			
			oIConexion.ejecutaCommit();
		} catch (Exception e) {
			if (oIConexion != null) {
				oIConexion.ejecutaRollback();
			}
			mensaje.setDescMensaje(UMensajeOperacion.MSJ_4 + mensaje.getDescMensaje());
			UManejadorLog.error("Control: Error al modificar solicitud: " + e.getMessage());
		} finally {
			if (oIConexion != null) {
				oIConexion.cierraConexion();
			}
		}
		return mensaje;
	}
	
	public List<EOperacionSolicitudCredito> listarEvaluacionSolicitudCredito(EOperacionSolicitudCredito eOperacionSolicitudCredito, int indicadorConsulta){
		IConexion oIConexion = null;
		List<EOperacionSolicitudCredito> resultado = null;
		DAOOperacion oDAOOperacion= null;
		try {
			oIConexion = FabricaConexion.creaConexion();		
			oDAOOperacion = new DAOOperacion(oIConexion);
			resultado = oDAOOperacion.listarEvaluacionSolicitudCredito(eOperacionSolicitudCredito, indicadorConsulta);			
		} catch (Exception e) {
			UManejadorLog.error("Control: Error al listar evaluacion solicitud credito " + e.getMessage());
		} finally {
			if (oIConexion != null) {
				oIConexion.cierraConexion();
			}
		}
		return resultado;
	}
	
	public EOperacionSolicitudCredito buscarEvaluacionSolicitudCredito(long numeroSolicitud, int codigoTipoCliente, int codigoCliente){
		IConexion oIConexion = null;
		EOperacionSolicitudCredito resultado = null;
		DAOOperacion oDAOOperacion= null;
		try {
			oIConexion = FabricaConexion.creaConexion();			
			oDAOOperacion = new DAOOperacion(oIConexion);
			resultado = oDAOOperacion.buscarEvaluacionSolicitudCredito(numeroSolicitud, codigoTipoCliente, codigoCliente);			
		} catch (Exception e) {
			UManejadorLog.error("Control: Error al obtener solicitud de credito " + e.getMessage());
		} finally {
			if (oIConexion != null) {
				oIConexion.cierraConexion();
			}
		}
		return resultado;
	}
	
	public List<EOperacionSolicitudCreditoMensaje> listarEvaluacionSolicitudCreditoMensaje(long numeroSolicitud, int codigoTipoCliente, int codigoCliente){
		IConexion oIConexion = null;
		List<EOperacionSolicitudCreditoMensaje> resultado = null;
		DAOOperacion oDAOOperacion= null;
		try {
			oIConexion = FabricaConexion.creaConexion();		
			oDAOOperacion = new DAOOperacion(oIConexion);
			resultado = oDAOOperacion.listarEvaluacionSolicitudCreditoMensaje(numeroSolicitud, codigoTipoCliente, codigoCliente);			
		} catch (Exception e) {
			UManejadorLog.error("Control: Error al listar mensaje " + e.getMessage());
		} finally {
			if (oIConexion != null) {
				oIConexion.cierraConexion();
			}
		}
		return resultado;
	}
	
	public List<EOperacionSolicitudCreditoDocumento> listarEvaluacionSolicitudCreditoDocumento(long numeroSolicitud, int codigoTipoCliente, int codigoCliente){
		IConexion oIConexion = null;
		List<EOperacionSolicitudCreditoDocumento> resultado = null;
		DAOOperacion oDAOOperacion= null;
		try {
			oIConexion = FabricaConexion.creaConexion();		
			oDAOOperacion = new DAOOperacion(oIConexion);
			resultado = oDAOOperacion.listarEvaluacionSolicitudCreditoDocumento(numeroSolicitud, codigoTipoCliente, codigoCliente);			
		} catch (Exception e) {
			UManejadorLog.error("Control: Error al listar documento " + e.getMessage());
		} finally {
			if (oIConexion != null) {
				oIConexion.cierraConexion();
			}
		}
		return resultado;
	}
	
	public List<EOperacionSolicitudCreditoDocumentoRevision> listarEvaluacionSolicitudCreditoDocumentoRevision(long numeroSolicitud, int codigoTipoCliente, int codigoCliente){
		IConexion oIConexion = null;
		List<EOperacionSolicitudCreditoDocumentoRevision> resultado = null;
		DAOOperacion oDAOOperacion= null;
		try {
			oIConexion = FabricaConexion.creaConexion();		
			oDAOOperacion = new DAOOperacion(oIConexion);
			resultado = oDAOOperacion.listarEvaluacionSolicitudCreditoDocumentoRevision(numeroSolicitud, codigoTipoCliente, codigoCliente);			
		} catch (Exception e) {
			UManejadorLog.error("Control: Error al listar documento " + e.getMessage());
		} finally {
			if (oIConexion != null) {
				oIConexion.cierraConexion();
			}
		}
		return resultado;
	}
}
