package com.abaco.ageneral;

import java.sql.ResultSet;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import com.abaco.entidad.EDireccion;
import com.abaco.entidad.EDocumentoIdentidad;
import com.abaco.entidad.EFichaNatural;
import com.abaco.entidad.EGeneral;
import com.abaco.entidad.EHijo;
import com.abaco.entidad.EMensaje;
import com.abaco.entidad.ENombre;
import com.abaco.entidad.EPersona;
import com.abaco.entidad.EPersonaParametro;
import com.abaco.entidad.EProductoCredito;
import com.abaco.entidad.EUsuario;
import com.abaco.ageneral.ESolicitudCredito;
import com.abaco.entidad.ETipoCliente;
import com.abaco.entidad.ETipoDocumentoPersona;
import com.abaco.negocio.util.UFuncionesGenerales;
import com.abaco.negocio.util.UManejadorLog;
import com.abaco.negocio.util.UConstante.UEstadoCivil;
import com.abaco.negocio.util.UConstante.UFlagResultado;
import com.abaco.persistencia.acceso.InstanciaAcceso;
import com.abaco.persistencia.interfaces.IConexion;

public class DAOCliente extends InstanciaAcceso{
	private static final String SP_ABACOINLEGAL_INS_TERCERO="{ CALL GESTIONDOC.SP_ABACOINLEGAL_INS_TERCERO("+parametrosSP(29)+") }";
	private static final String SP_ABACOINLEGAL_INS_CLIENTE_HISTORICO="{ CALL GESTIONDOC.SP_ABACOINLEGAL_INS_CLIENTE_HISTORICO("+parametrosSP(122)+") }";
	private static final String SP_ABACOINLEGAL_UPD_SOCIO="{ CALL GESTIONDOC.SP_ABACOINLEGAL_UPD_SOCIO("+parametrosSP(14)+") }";
	private static final String SP_ABACOINLEGAL_UPD_POSTULANTE="{ CALL GESTIONDOC.SP_ABACOINLEGAL_UPD_POSTULANTE("+parametrosSP(14)+") }";
	private static final String SP_ABACOINLEGAL_UPD_TERCERO="{ CALL GESTIONDOC.SP_ABACOINLEGAL_UPD_TERCERO("+parametrosSP(30)+") }";
	private static final String SP_ABACOINLEGAL_SEL_SOCIO = "{ CALL GESTIONDOC.SP_ABACOINLEGAL_SEL_SOCIO(?,?) }";
	private static final String SP_ABACOINLEGAL_SEL_POSTULANTE = "{ CALL GESTIONDOC.SP_ABACOINLEGAL_SEL_POSTULANTE(?,?) }";
	private static final String SP_ABACOINLEGAL_SEL_TERCERO = "{ CALL GESTIONDOC.SP_ABACOINLEGAL_SEL_TERCERO(?,?,?) }";
	private static final String SP_ABACOINLEGAL_SEL_NOSOCIO = "{ CALL GESTIONDOC.SP_ABACOINLEGAL_SEL_NOSOCIO(?,?) }";
	private static final String SP_ABACOINLEGAL_SEL_SOCIOTERCERO = "{ CALL GESTIONDOC.SP_ABACOINLEGAL_SEL_SOCIOTERCERO(?,?) }";
	private static final String SP_ABACOINLEGAL_SEL_NOTARIA = "{ CALL GESTIONDOC.SP_ABACOINLEGAL_SEL_NOTARIA("+parametrosSP(2)+") }";
	private static final String SP_ABACOINLEGAL_SEL_CLIENTE_HISTORICO = "{ CALL GESTIONDOC.SP_ABACOINLEGAL_SEL_CLIENTE_HISTORICO("+parametrosSP(2)+") }";
	private static final String SP_ABACOINLEGAL_BUS_SOCIO="{ CALL GESTIONDOC.SP_ABACOINLEGAL_BUS_SOCIO("+parametrosSP(1)+") }";
	private static final String SP_ABACOINLEGAL_BUS_POSTULANTE="{ CALL GESTIONDOC.SP_ABACOINLEGAL_BUS_POSTULANTE("+parametrosSP(1)+") }";
	private static final String SP_ABACOINLEGAL_BUS_TERCERO ="{ CALL GESTIONDOC.SP_ABACOINLEGAL_BUS_TERCERO(?) }";
	private static final String SP_ABACOINLEGAL_BUS_NOSOCIO ="{ CALL GESTIONDOC.SP_ABACOINLEGAL_BUS_NOSOCIO(?) }";
	private static final String SP_ABACOINLEGAL_BUS_TERCERO_ANEXO ="{ CALL GESTIONDOC.SP_ABACOINLEGAL_BUS_TERCERO_ANEXO("+parametrosSP(1)+") }";
	private static final String SP_ABACOINLEGAL_BUS_CLIENTE_INFO_PERSONA_NATURAL ="{ CALL GESTIONDOC.SP_ABACOINLEGAL_BUS_CLIENTE_INFO_PERSONA_NATURAL("+parametrosSP(1)+") }";
	private static final String SP_ABACOINLEGAL_BUS_USUARIO_DETALLE ="{ CALL GESTIONDOC.SP_ABACOINLEGAL_BUS_USUARIO_DETALLE("+parametrosSP(1)+") }";
	private static final String SP_ABACOINLEGAL_BUS_SOCIO_CONSTITUCIONEMPRESA="{ CALL GESTIONDOC.SP_ABACOINLEGAL_BUS_SOCIO_CONSTITUCIONEMPRESA("+parametrosSP(1)+") }";
	private static final String SP_ABACOINLEGAL_BUS_CLIENTE_ADICIONAL="{ CALL GESTIONDOC.SP_ABACOINLEGAL_BUS_CLIENTE_ADICIONAL("+parametrosSP(2)+") }";

	
	private static String parametrosSP(int numeroDeParametros) {
        StringBuilder cadena = new StringBuilder();

        for (int i = 1; i <= numeroDeParametros; i++) {
            cadena.append("?,");
        }

        int longitudCadena = cadena.length();
        StringBuilder removeUltimoCaracter = new StringBuilder(cadena);
        StringBuilder cadenaFinal = removeUltimoCaracter.deleteCharAt(longitudCadena - 1);
        return cadenaFinal.toString();
    }
	
	public DAOCliente(IConexion objConexion) {
		super(objConexion);
	}
	
	public EMensaje agregarClienteHistorico(EEvaluacionSolicitudCreditoLegal eEvaluacionSolicitudCreditoLegal, EInformeLegalAdicional eEInformeLegalAdicional, ECliente eCliente, EClienteConstitucionEmpresa eClienteConstitucionEmpresa, EClienteAdicional eClienteAdicional) {
		EMensaje mensaje = new EMensaje();
		List<Object> lstParametrosEntrada;
		SimpleDateFormat formato = new SimpleDateFormat("HH:mm:ss");
		
		try {
			lstParametrosEntrada = new ArrayList<Object>();
			lstParametrosEntrada.add(eEvaluacionSolicitudCreditoLegal.getNumeroSolicitud());
			lstParametrosEntrada.add(eEvaluacionSolicitudCreditoLegal.getCodigoTipoCliente());
			lstParametrosEntrada.add(eEvaluacionSolicitudCreditoLegal.getCodigoCliente());
			
			lstParametrosEntrada.add(eEvaluacionSolicitudCreditoLegal.getCodigoTipoDocumento());
			lstParametrosEntrada.add(eEvaluacionSolicitudCreditoLegal.getNumeroDocumento());
			lstParametrosEntrada.add(eEvaluacionSolicitudCreditoLegal.getRuc());
			lstParametrosEntrada.add(eEvaluacionSolicitudCreditoLegal.getApellidoPaterno());
			lstParametrosEntrada.add(eEvaluacionSolicitudCreditoLegal.getApellidoMaterno());
			lstParametrosEntrada.add(eEvaluacionSolicitudCreditoLegal.getNombre());
			lstParametrosEntrada.add(eEvaluacionSolicitudCreditoLegal.getNombreLargo());
			lstParametrosEntrada.add(eEvaluacionSolicitudCreditoLegal.getDireccionReal());
			lstParametrosEntrada.add(eEvaluacionSolicitudCreditoLegal.getCodigoUbigeoReal());
			lstParametrosEntrada.add(eEvaluacionSolicitudCreditoLegal.getDireccionContractual());
			lstParametrosEntrada.add(eEvaluacionSolicitudCreditoLegal.getCodigoUbigeoContractual());
			
			lstParametrosEntrada.add(eEvaluacionSolicitudCreditoLegal.getCodigoTipoDocumentoConyugue());
			lstParametrosEntrada.add(eEvaluacionSolicitudCreditoLegal.getDocumentoConyugue());
			lstParametrosEntrada.add(eEvaluacionSolicitudCreditoLegal.getApellidoPaternoConyugue());
			lstParametrosEntrada.add(eEvaluacionSolicitudCreditoLegal.getApellidoMaternoConyugue());
			lstParametrosEntrada.add(eEvaluacionSolicitudCreditoLegal.getNombreConyugue());
			lstParametrosEntrada.add(eEvaluacionSolicitudCreditoLegal.getNombreLargoConyuge());
			
			lstParametrosEntrada.add(eEvaluacionSolicitudCreditoLegal.getCodigoTipoPersonaJuridica());
			lstParametrosEntrada.add(eEvaluacionSolicitudCreditoLegal.getMontoCapitalSocialRegistroPublicos());
			lstParametrosEntrada.add(eEvaluacionSolicitudCreditoLegal.getMontoCapitalSocialActual());
			lstParametrosEntrada.add(eEvaluacionSolicitudCreditoLegal.getCodigoFacultadOperar());
			lstParametrosEntrada.add(eEvaluacionSolicitudCreditoLegal.getCodigoTipoSuscripcionPago());
			lstParametrosEntrada.add(eEvaluacionSolicitudCreditoLegal.getNumeroSuscripcionPago());
			lstParametrosEntrada.add(eEvaluacionSolicitudCreditoLegal.getNumeroAcciones());
			lstParametrosEntrada.add(eEvaluacionSolicitudCreditoLegal.getIndicadorAvalarTercero());
			lstParametrosEntrada.add(eEvaluacionSolicitudCreditoLegal.getIndicadorGrabarBien());
			lstParametrosEntrada.add(eEvaluacionSolicitudCreditoLegal.getDescripcionAvalarTercero());
			lstParametrosEntrada.add(UFuncionesGenerales.obtieneDescripcionPorSaltoLinea(eEvaluacionSolicitudCreditoLegal.getObservacionAvalarTercero(),0));
			lstParametrosEntrada.add(UFuncionesGenerales.obtieneDescripcionPorSaltoLinea(eEvaluacionSolicitudCreditoLegal.getObservacionAvalarTercero(),1));
			lstParametrosEntrada.add(UFuncionesGenerales.obtieneDescripcionPorSaltoLinea(eEvaluacionSolicitudCreditoLegal.getObservacionAvalarTercero(),2));
			lstParametrosEntrada.add(UFuncionesGenerales.obtieneDescripcionPorSaltoLinea(eEvaluacionSolicitudCreditoLegal.getObservacionGrabarBien(),0));
			lstParametrosEntrada.add(UFuncionesGenerales.obtieneDescripcionPorSaltoLinea(eEvaluacionSolicitudCreditoLegal.getObservacionGrabarBien(),1));
			lstParametrosEntrada.add(UFuncionesGenerales.obtieneDescripcionPorSaltoLinea(eEvaluacionSolicitudCreditoLegal.getObservacionGrabarBien(),2));
			lstParametrosEntrada.add(UFuncionesGenerales.obtieneDescripcionPorSaltoLinea(eEvaluacionSolicitudCreditoLegal.getObservacionConstanciaSocial(),0));
			lstParametrosEntrada.add(UFuncionesGenerales.obtieneDescripcionPorSaltoLinea(eEvaluacionSolicitudCreditoLegal.getObservacionConstanciaSocial(),1));
			lstParametrosEntrada.add(UFuncionesGenerales.obtieneDescripcionPorSaltoLinea(eEvaluacionSolicitudCreditoLegal.getObservacionConstanciaSocial(),2));
			lstParametrosEntrada.add(UFuncionesGenerales.obtieneDescripcionPorSaltoLinea(eEvaluacionSolicitudCreditoLegal.getObservacionConstanciaSocial(),3));
			lstParametrosEntrada.add(UFuncionesGenerales.obtieneDescripcionPorSaltoLinea(eEvaluacionSolicitudCreditoLegal.getObservacionConstanciaSocial(),4));
			
			lstParametrosEntrada.add(eEInformeLegalAdicional.getCodigoEstadoCivil());
			lstParametrosEntrada.add(eEInformeLegalAdicional.getOficinaRegistral());
			lstParametrosEntrada.add(eEInformeLegalAdicional.getNumeroPartida());
			lstParametrosEntrada.add(eEInformeLegalAdicional.getFechaConstitucion());
			lstParametrosEntrada.add(eEInformeLegalAdicional.getCodigoNotario());
			lstParametrosEntrada.add(eEInformeLegalAdicional.getDescripcionNotario());
			lstParametrosEntrada.add(eEInformeLegalAdicional.getNumeroAcciones());
			
			lstParametrosEntrada.add(eEInformeLegalAdicional.getCodigoTipoDuracionPartida());
			lstParametrosEntrada.add(eEInformeLegalAdicional.getRegistroPartida());
			lstParametrosEntrada.add(eEInformeLegalAdicional.getDescripcionConstitucion());
			lstParametrosEntrada.add(eEInformeLegalAdicional.getCodigoTipoValorSuscripcion());
			lstParametrosEntrada.add(eEInformeLegalAdicional.getMontoValorNominal());
			lstParametrosEntrada.add(eEInformeLegalAdicional.getEstatuto());
			lstParametrosEntrada.add(eEInformeLegalAdicional.getDescripcionPatrimonio());
			lstParametrosEntrada.add(eEInformeLegalAdicional.getCodigoTipoNumeracionEstatuto());
			lstParametrosEntrada.add(eEInformeLegalAdicional.getNumeracionEstatuto());
			lstParametrosEntrada.add(eEInformeLegalAdicional.getAsiento());
			lstParametrosEntrada.add(eEInformeLegalAdicional.getFechaPeriodoInicio());
			lstParametrosEntrada.add(eEInformeLegalAdicional.getFechaPeriodoVencimiento());
			
			//HISTORICO
			lstParametrosEntrada.add(eCliente.getCodigoTipoDocumento());
			lstParametrosEntrada.add(eCliente.getDocumento());
			lstParametrosEntrada.add(eCliente.getRuc());
			lstParametrosEntrada.add(eCliente.getApellidoPaterno());
			lstParametrosEntrada.add(eCliente.getApellidoMaterno());
			lstParametrosEntrada.add(eCliente.getNombre());
			lstParametrosEntrada.add(eCliente.getNombreLargo());
			lstParametrosEntrada.add(eCliente.getDireccion());
			lstParametrosEntrada.add(eCliente.getCodigoUbigeo());
			lstParametrosEntrada.add(eCliente.getDireccionPostal());
			lstParametrosEntrada.add(eCliente.getCodigoUbigeoPostal());
			
			lstParametrosEntrada.add(eCliente.getCodigoTipoDocumentoConyugue());
			lstParametrosEntrada.add(eCliente.getDocumentoConyugue());
			lstParametrosEntrada.add(eCliente.getApellidoPaternoConyugue());
			lstParametrosEntrada.add(eCliente.getApellidoMaternoConyugue());
			lstParametrosEntrada.add(eCliente.getNombreConyugue());
			lstParametrosEntrada.add(eCliente.getNombreSuperLargoConyugue());
			
			lstParametrosEntrada.add(eClienteAdicional.getCodigoTipoPersonaJuridica());
			lstParametrosEntrada.add(eClienteAdicional.getMontoCapitalSocialRegistroPublicos());
			lstParametrosEntrada.add(eClienteAdicional.getMontoCapitalSocialActual());
			lstParametrosEntrada.add(eClienteAdicional.getCodigoFacultadOperar());
			lstParametrosEntrada.add(eClienteAdicional.getCodigoTipoSuscripcionPago());
			lstParametrosEntrada.add(eClienteAdicional.getNumeroSuscripcionPago());
			lstParametrosEntrada.add(eClienteAdicional.getNumeroAcciones());
			lstParametrosEntrada.add(eClienteAdicional.getIndicadorAvalarTercero());
			lstParametrosEntrada.add(eClienteAdicional.getIndicadorGrabarBien());
			lstParametrosEntrada.add(eClienteAdicional.getDescripcionAvalarTercero());
			lstParametrosEntrada.add(UFuncionesGenerales.obtieneDescripcionPorSaltoLinea(eClienteAdicional.getObservacionAvalarTercero(),0));
			lstParametrosEntrada.add(UFuncionesGenerales.obtieneDescripcionPorSaltoLinea(eClienteAdicional.getObservacionAvalarTercero(),1));
			lstParametrosEntrada.add(UFuncionesGenerales.obtieneDescripcionPorSaltoLinea(eClienteAdicional.getObservacionAvalarTercero(),2));
			lstParametrosEntrada.add(UFuncionesGenerales.obtieneDescripcionPorSaltoLinea(eClienteAdicional.getObservacionGrabarBien(),0));
			lstParametrosEntrada.add(UFuncionesGenerales.obtieneDescripcionPorSaltoLinea(eClienteAdicional.getObservacionGrabarBien(),1));
			lstParametrosEntrada.add(UFuncionesGenerales.obtieneDescripcionPorSaltoLinea(eClienteAdicional.getObservacionGrabarBien(),2));
			lstParametrosEntrada.add(UFuncionesGenerales.obtieneDescripcionPorSaltoLinea(eClienteAdicional.getObservacionConstanciaSocial(),0));
			lstParametrosEntrada.add(UFuncionesGenerales.obtieneDescripcionPorSaltoLinea(eClienteAdicional.getObservacionConstanciaSocial(),1));
			lstParametrosEntrada.add(UFuncionesGenerales.obtieneDescripcionPorSaltoLinea(eClienteAdicional.getObservacionConstanciaSocial(),2));
			lstParametrosEntrada.add(UFuncionesGenerales.obtieneDescripcionPorSaltoLinea(eClienteAdicional.getObservacionConstanciaSocial(),3));
			lstParametrosEntrada.add(UFuncionesGenerales.obtieneDescripcionPorSaltoLinea(eClienteAdicional.getObservacionConstanciaSocial(),4));
			
			lstParametrosEntrada.add(eCliente.getCodigoEstadoCivil());
			lstParametrosEntrada.add(eClienteConstitucionEmpresa.getOficinaRegistral());
			lstParametrosEntrada.add(eClienteConstitucionEmpresa.getInscripcionRegistroPublico());
			lstParametrosEntrada.add(eClienteConstitucionEmpresa.getFechaConstitucion());
			lstParametrosEntrada.add(eClienteConstitucionEmpresa.getCodigoNotario());
			lstParametrosEntrada.add(eClienteConstitucionEmpresa.getDescripcionNotario());
			lstParametrosEntrada.add(eClienteConstitucionEmpresa.getNumeroAcciones());
			
			lstParametrosEntrada.add(eClienteAdicional.getCodigoTipoDuracionPartida());
			lstParametrosEntrada.add(eClienteAdicional.getRegistroPartida());
			lstParametrosEntrada.add(eClienteAdicional.getDescripcionConstitucion());
			lstParametrosEntrada.add(eClienteAdicional.getCodigoTipoValorSuscripcion());
			lstParametrosEntrada.add(eClienteAdicional.getMontoValorNominal());
			lstParametrosEntrada.add(eClienteAdicional.getEstatuto());
			lstParametrosEntrada.add(eClienteAdicional.getDescripcionPatrimonio());
			lstParametrosEntrada.add(eClienteAdicional.getCodigoTipoNumeracionEstatuto());
			lstParametrosEntrada.add(eClienteAdicional.getNumeracionEstatuto());
			lstParametrosEntrada.add(eClienteAdicional.getAsiento());
			lstParametrosEntrada.add(eClienteAdicional.getFechaPeriodoInicio());
			lstParametrosEntrada.add(eClienteAdicional.getFechaPeriodoVencimiento());
			
			lstParametrosEntrada.add(eEvaluacionSolicitudCreditoLegal.getUsuarioRegistro().getNombreUsuario());
			lstParametrosEntrada.add(eEvaluacionSolicitudCreditoLegal.getFechaRegistro());
			lstParametrosEntrada.add(formato.format(eEvaluacionSolicitudCreditoLegal.getFechaRegistro()));
			
			mensaje = objConexion.ejecutaTransaccion(SP_ABACOINLEGAL_INS_CLIENTE_HISTORICO, lstParametrosEntrada);
		} catch(Exception objEx) {
			mensaje.setIdMensaje(-1);
			mensaje.setDescMensaje(objEx.getMessage());
			UManejadorLog.error("Acceso: Problemas al registrar.", objEx);
		}
		return mensaje;
	}
	
	public EMensaje registrarTercero(ETercero eTercero) {
		EMensaje mensaje = new EMensaje();
		List<Object> lstParametrosEntrada;
		
		try {
			lstParametrosEntrada = new ArrayList<Object>();
			lstParametrosEntrada.add(eTercero.getCodigoTipoDocumento());
			lstParametrosEntrada.add(eTercero.getDocumento());
			lstParametrosEntrada.add(eTercero.getApellidoPaterno());
			lstParametrosEntrada.add(eTercero.getApellidoMaterno());
			lstParametrosEntrada.add(eTercero.getNombres());
			lstParametrosEntrada.add(eTercero.getNombreSuperLargo());
			lstParametrosEntrada.add(eTercero.getRuc());
			lstParametrosEntrada.add(eTercero.getDireccion());
			lstParametrosEntrada.add(eTercero.getCodigoUbigeo());
			lstParametrosEntrada.add(eTercero.getCodigoZonaGeografica());
			lstParametrosEntrada.add(eTercero.getTelefono1());
			lstParametrosEntrada.add(Integer.parseInt(eTercero.getAnexo().isEmpty() ? "0": eTercero.getAnexo() ));
			lstParametrosEntrada.add(eTercero.getFax());
			lstParametrosEntrada.add(eTercero.getDireccionPostal());
			lstParametrosEntrada.add(eTercero.getCodigoUbigeoPostal());
			lstParametrosEntrada.add(eTercero.getCodigoZonaGeograficaPostal());
			lstParametrosEntrada.add(eTercero.getEmail1());
			lstParametrosEntrada.add(eTercero.getEmail2());
			lstParametrosEntrada.add(eTercero.getCodigoTipoPersona());
			lstParametrosEntrada.add(eTercero.getCodigoEstadoCivil());
			lstParametrosEntrada.add(eTercero.getCodigoSexo());
			lstParametrosEntrada.add(eTercero.getCodigoComunidad());
			lstParametrosEntrada.add(eTercero.getCodigoBanca());
			lstParametrosEntrada.add(eTercero.getTipoProveedor());
			lstParametrosEntrada.add(eTercero.getTipoAceptante());
			lstParametrosEntrada.add(eTercero.getIndicadorTipoPersona());
			lstParametrosEntrada.add(eTercero.getUsuarioRegistro().getNombreUsuario());

			
			mensaje = objConexion.ejecutaTransaccion(SP_ABACOINLEGAL_INS_TERCERO, lstParametrosEntrada);
		} catch(Exception objEx) {
			mensaje.setIdMensaje(-1);
			mensaje.setDescMensaje(objEx.getMessage());
			UManejadorLog.error("Acceso: Problemas al registrar.", objEx);
		}
		return mensaje;
	}
	
	public EMensaje modificarTercero(ETercero eTercero) {
		EMensaje mensaje = new EMensaje();
		List<Object> lstParametrosEntrada;
		
		try {
			lstParametrosEntrada = new ArrayList<Object>();
			lstParametrosEntrada.add(eTercero.getCodigoCliente());
			lstParametrosEntrada.add(eTercero.getCodigoTipoDocumento());
			lstParametrosEntrada.add(eTercero.getDocumento());
			lstParametrosEntrada.add(eTercero.getApellidoPaterno());
			lstParametrosEntrada.add(eTercero.getApellidoMaterno());
			lstParametrosEntrada.add(eTercero.getNombres());
			lstParametrosEntrada.add(eTercero.getNombreSuperLargo());
			lstParametrosEntrada.add(eTercero.getRuc());
			lstParametrosEntrada.add(eTercero.getDireccion());
			lstParametrosEntrada.add(eTercero.getCodigoUbigeo());
			lstParametrosEntrada.add(eTercero.getCodigoZonaGeografica());
			lstParametrosEntrada.add(eTercero.getTelefono1());
			lstParametrosEntrada.add(Integer.parseInt(eTercero.getAnexo().isEmpty() ? "0": eTercero.getAnexo() ));
			lstParametrosEntrada.add(eTercero.getFax());
			lstParametrosEntrada.add(eTercero.getDireccionPostal());
			lstParametrosEntrada.add(eTercero.getCodigoUbigeoPostal());
			lstParametrosEntrada.add(eTercero.getCodigoZonaGeograficaPostal());
			lstParametrosEntrada.add(eTercero.getEmail1());
			lstParametrosEntrada.add(eTercero.getEmail2());
			lstParametrosEntrada.add(eTercero.getCodigoTipoPersona());
			lstParametrosEntrada.add(eTercero.getCodigoEstadoCivil());
			lstParametrosEntrada.add(eTercero.getCodigoSexo());
			lstParametrosEntrada.add(eTercero.getCodigoComunidad());
			lstParametrosEntrada.add(eTercero.getCodigoBanca());
			lstParametrosEntrada.add(eTercero.getTipoProveedor());
			lstParametrosEntrada.add(eTercero.getTipoAceptante());
			lstParametrosEntrada.add(eTercero.getIndicadorTipoPersona());
			lstParametrosEntrada.add(eTercero.getUsuarioRegistro().getNombreUsuario());

			
			mensaje = objConexion.ejecutaTransaccion(SP_ABACOINLEGAL_UPD_TERCERO, lstParametrosEntrada);
		} catch(Exception objEx) {
			mensaje.setIdMensaje(-1);
			mensaje.setDescMensaje(objEx.getMessage());
			UManejadorLog.error("Acceso: Problemas al registrar.", objEx);
		}
		return mensaje;
	}
	
	public EMensaje modificarSocio(ECliente eCliente) {
		EMensaje mensaje = new EMensaje();
		List<Object> lstParametrosEntrada;
		SimpleDateFormat formato = new SimpleDateFormat("HH:mm:ss");
		
		try {
			lstParametrosEntrada = new ArrayList<Object>();
			lstParametrosEntrada.add(eCliente.getCodigoCliente());
			lstParametrosEntrada.add(eCliente.getCodigoClienteTemporal());
			lstParametrosEntrada.add(eCliente.getCodigoTipoCliente());
			lstParametrosEntrada.add(eCliente.getCodigoTipoPersona());
			lstParametrosEntrada.add(eCliente.getCodigoTipoDocumento());
			lstParametrosEntrada.add(eCliente.getDocumento());
			lstParametrosEntrada.add(eCliente.getRuc());
			lstParametrosEntrada.add(eCliente.getNombreCorto());
			lstParametrosEntrada.add(eCliente.getNombreLargo());
			/*
			lstParametrosEntrada.add(eCliente.getDireccion());
			lstParametrosEntrada.add(eCliente.getCodigoPostal());
			lstParametrosEntrada.add(eCliente.getTelefono());
			lstParametrosEntrada.add(eCliente.getFax());
			lstParametrosEntrada.add(eCliente.getCodigoUbigeo());
			*/
			lstParametrosEntrada.add(eCliente.getUsuarioRegistro().getNombreUsuario());
			lstParametrosEntrada.add(eCliente.getFechaRegistro());
			lstParametrosEntrada.add(formato.format(eCliente.getFechaRegistro()));
			
			mensaje = objConexion.ejecutaTransaccion(SP_ABACOINLEGAL_UPD_SOCIO, lstParametrosEntrada);
		} catch(Exception objEx) {
			mensaje.setIdMensaje(-1);
			mensaje.setDescMensaje(objEx.getMessage());
			UManejadorLog.error("Acceso: Problemas al modificar.", objEx);
		}
		return mensaje;
	}
	
	public EMensaje modificarPostulante(ECliente eCliente) {
		EMensaje mensaje = new EMensaje();
		List<Object> lstParametrosEntrada;
		SimpleDateFormat formato = new SimpleDateFormat("HH:mm:ss");
		
		try {
			lstParametrosEntrada = new ArrayList<Object>();
			lstParametrosEntrada.add(eCliente.getCodigoCliente());
			lstParametrosEntrada.add(eCliente.getCodigoClienteTemporal());
			lstParametrosEntrada.add(eCliente.getCodigoTipoCliente());
			lstParametrosEntrada.add(eCliente.getCodigoTipoPersona());
			lstParametrosEntrada.add(eCliente.getCodigoTipoDocumento());
			lstParametrosEntrada.add(eCliente.getDocumento());
			lstParametrosEntrada.add(eCliente.getRuc());
			lstParametrosEntrada.add(eCliente.getNombreCorto());
			lstParametrosEntrada.add(eCliente.getNombreLargo());
			/*
			lstParametrosEntrada.add(eCliente.getDireccion());
			lstParametrosEntrada.add(eCliente.getCodigoPostal());
			lstParametrosEntrada.add(eCliente.getTelefono());
			lstParametrosEntrada.add(eCliente.getFax());
			lstParametrosEntrada.add(eCliente.getCodigoUbigeo());
			*/
			lstParametrosEntrada.add(eCliente.getUsuarioRegistro().getNombreUsuario());
			lstParametrosEntrada.add(eCliente.getFechaRegistro());
			lstParametrosEntrada.add(formato.format(eCliente.getFechaRegistro()));
			
			mensaje = objConexion.ejecutaTransaccion(SP_ABACOINLEGAL_UPD_POSTULANTE, lstParametrosEntrada);
		} catch(Exception objEx) {
			mensaje.setIdMensaje(-1);
			mensaje.setDescMensaje(objEx.getMessage());
			UManejadorLog.error("Acceso: Problemas al modificar.", objEx);
		}
		return mensaje;
	}
	
	public List<EPersona> listarSocio(EPersonaParametro objEPersonaParam) {
		List<EPersona> lstPersona = new ArrayList<EPersona>();
		EPersona objEPersona = null;
		EDocumentoIdentidad objEDocumentoIdentidad = null;
		ETipoDocumentoPersona objETipoDocumentoPersona = null;
		List<Object> lstParametrosEntrada = null;

		try {

			lstParametrosEntrada = new ArrayList<Object>();
			lstParametrosEntrada.add(objEPersonaParam.getCodPersona());
			lstParametrosEntrada.add(UFuncionesGenerales.convierteCadenaMayuscula(objEPersonaParam.getNombrePersona()));

			ResultSet objResultSet = objConexion.ejecutaConsulta(SP_ABACOINLEGAL_SEL_SOCIO, lstParametrosEntrada, null);

			if (objResultSet != null) {
				while (objResultSet.next()) {
					objEPersona = new EPersona();
					objETipoDocumentoPersona = new ETipoDocumentoPersona();
					objEDocumentoIdentidad = new EDocumentoIdentidad();
					objEPersona.setCodigo(objResultSet.getInt("CODCLI"));
					objETipoDocumentoPersona.setCodigo(UFuncionesGenerales.revisaCadena(objResultSet.getString("TIPDOC")));
					objEDocumentoIdentidad.setTipoDocumento(objETipoDocumentoPersona);
					objEDocumentoIdentidad.setDocumento(UFuncionesGenerales.revisaCadena(objResultSet.getString("DOCUME")));
					objEPersona.setDocumentoIdentidad(objEDocumentoIdentidad);
					objEPersona.setNombreCorte(UFuncionesGenerales.convierteCadenaMayuscula(objResultSet.getString("NOMABV")));
					objEPersona.setNombreLargo(UFuncionesGenerales.convierteCadenaMayuscula(objResultSet.getString("NOMBCL")));
					objEPersona.setNombre(UFuncionesGenerales.convierteCadenaMayuscula(objResultSet.getString("NOMBCL")));
					objEPersona.setClasePersona(UFuncionesGenerales.revisaCadena(objResultSet.getString("CLAPER")));
					
					lstPersona.add(objEPersona);
				}
				objConexion.cierraConsulta(objResultSet);
			}
		} catch (Exception objEx) {
			UManejadorLog.error(
					"Acceso: Error al obtener el listado de Tercero: ", objEx);
		}
		return lstPersona;
	}
	
	public List<EPersona> listarSocioyTercero(int codigo, String descripcion) {
		List<EPersona> lstPersona = new ArrayList<EPersona>();
		EPersona objEPersona = null;
		EDocumentoIdentidad objEDocumentoIdentidad = null;
		ETipoDocumentoPersona objETipoDocumentoPersona = null;
		List<Object> lstParametrosEntrada = null;
		ResultSet objResultSet = null;
		try {

			lstParametrosEntrada = new ArrayList<Object>();
			lstParametrosEntrada.add(codigo);
			lstParametrosEntrada.add(descripcion);

			objResultSet = objConexion.ejecutaConsulta(SP_ABACOINLEGAL_SEL_SOCIOTERCERO, lstParametrosEntrada, null);

			if (objResultSet != null) {
				while (objResultSet.next()) {
					objEPersona = new EPersona();
					objETipoDocumentoPersona = new ETipoDocumentoPersona();
					objEDocumentoIdentidad = new EDocumentoIdentidad();
					objEPersona.setCodigo(objResultSet.getInt("CODCLI"));
					objETipoDocumentoPersona.setCodigo(UFuncionesGenerales.revisaCadena(objResultSet.getString("TIPDOC")));
				/*	objETipoDocumentoPersona.setNombreCorto(UFuncionesGenerales.convierteCadenaMayuscula(objResultSet.getString("DOCUME")));
					objETipoDocumentoPersona.setDescripcion(UFuncionesGenerales.convierteCadenaMayuscula(objResultSet.getString("DOCUME")));*/
					objEDocumentoIdentidad.setTipoDocumento(objETipoDocumentoPersona);
					objEDocumentoIdentidad.setDocumento(UFuncionesGenerales.revisaCadena(objResultSet.getString("DOCUME")));
					objEPersona.setDocumentoIdentidad(objEDocumentoIdentidad);
					objEPersona.setNombreCorte(UFuncionesGenerales.convierteCadenaMayuscula(objResultSet.getString("NOMABV")));
					objEPersona.setNombreLargo(UFuncionesGenerales.convierteCadenaMayuscula(objResultSet.getString("NOMBCL")));
					lstPersona.add(objEPersona);
				}
				objConexion.cierraConsulta(objResultSet);
			}
		} catch (Exception objEx) {
			UManejadorLog.error(
					"Acceso: Error al obtener el listado de Socio: ", objEx);
		}
		return lstPersona;
	}
	
	
	public List<ETercero> listarNotarios(int codigo, String descripcion) {
		List<ETercero> lstTercero = new ArrayList<ETercero>();
		ETercero objETercero = null;
		List<Object> lstParametrosEntrada = null;
		ResultSet objResultSet = null;
		try {

			lstParametrosEntrada = new ArrayList<Object>();
			lstParametrosEntrada.add(codigo);
			lstParametrosEntrada.add(descripcion);

			objResultSet = objConexion.ejecutaConsulta(SP_ABACOINLEGAL_SEL_NOTARIA, lstParametrosEntrada, null);

			if (objResultSet != null) {
				while (objResultSet.next()) {
					objETercero = new ETercero();
					
					objETercero.setCodigoCliente(objResultSet.getInt("CODCLI"));
					objETercero.setCodigoTipoDocumento(UFuncionesGenerales.revisaCadena(objResultSet.getString("TIPDOC")));
					objETercero.setDocumento(UFuncionesGenerales.revisaCadena(objResultSet.getString("DOCUME")));
					objETercero.setNombreCorte(UFuncionesGenerales.revisaCadena(objResultSet.getString("NOMABV")));
					objETercero.setNombreLargo(UFuncionesGenerales.revisaCadena(objResultSet.getString("NOMBCL")));
					/*objETercero.setRuc(UFuncionesGenerales.revisaCadena(objResultSet.getString("NRORUC")));
					objETercero.setDireccion(UFuncionesGenerales.revisaCadena(objResultSet.getString("DIRECC")));
					objETercero.setCodigoUbigeo(objResultSet.getInt("CCIUDA"));
					objETercero.setCodigoZonaGeografica(objResultSet.getInt("ZONGEO"));
					objETercero.setDireccionPostal(UFuncionesGenerales.revisaCadena(objResultSet.getString("DIRECP")));
					objETercero.setCodigoUbigeoPostal(objResultSet.getInt("CCIUDP"));
					objETercero.setCodigoZonaGeograficaPostal(objResultSet.getInt("ZONGEP"));
					objETercero.setTelefono1(UFuncionesGenerales.revisaCadena(objResultSet.getString("TELEFO")));
					objETercero.setAnexo(UFuncionesGenerales.revisaCadena(objResultSet.getString("TELANE")));
					objETercero.setFax(UFuncionesGenerales.revisaCadena(objResultSet.getString("FAX")));
					objETercero.setCodigoTipoPersona(UFuncionesGenerales.revisaCadena(objResultSet.getString("CLAPER")));
					objETercero.setCodigoComunidad(UFuncionesGenerales.revisaCadena(objResultSet.getString("COMUNI")));
					objETercero.setCodigoBanca(UFuncionesGenerales.revisaCadena(objResultSet.getString("BANCA")));
					objETercero.setTipoProveedor(objResultSet.getInt("PROVEE"));
					objETercero.setTipoAceptante(objResultSet.getInt("ACEPTA"));
					objETercero.setCodigoEstadoCivil(objResultSet.getString("ESTCIV"));
					objETercero.setCodigoSexo(objResultSet.getString("SEXO"));
					objETercero.setEmail1(objResultSet.getString("EMAIL1"));
					objETercero.setEmail2(objResultSet.getString("EMAIL2"));*/
					

					lstTercero.add(objETercero);
				}
				objConexion.cierraConsulta(objResultSet);
			}
		} catch (Exception objEx) {
			UManejadorLog.error(
					"Acceso: Error al obtener el Listado de Notarios: ", objEx);
		}
		return lstTercero;
	}
	
	public List<EPersona> listarPostulante(EPersonaParametro objEPersonaParam) {
		List<EPersona> lstPersona = new ArrayList<EPersona>();
		EPersona objEPersona = null;
		EDocumentoIdentidad objEDocumentoIdentidad = null;
		ETipoDocumentoPersona objETipoDocumentoPersona = null;
		List<Object> lstParametrosEntrada = null;

		try {

			lstParametrosEntrada = new ArrayList<Object>();
			lstParametrosEntrada.add(objEPersonaParam.getCodPersona());
			lstParametrosEntrada.add(UFuncionesGenerales.convierteCadenaMayuscula(objEPersonaParam.getNombrePersona()));
			
			ResultSet objResultSet = objConexion.ejecutaConsulta(SP_ABACOINLEGAL_SEL_POSTULANTE, lstParametrosEntrada, null);

			if (objResultSet != null) {
				while (objResultSet.next()) {
					objEPersona = new EPersona();
					objETipoDocumentoPersona = new ETipoDocumentoPersona();
					objEDocumentoIdentidad = new EDocumentoIdentidad();
					objEPersona.setCodigo(objResultSet.getInt("TMPCLI"));
					objETipoDocumentoPersona.setCodigo(UFuncionesGenerales.revisaCadena(objResultSet.getString("TIPDOC")));
					objEDocumentoIdentidad.setTipoDocumento(objETipoDocumentoPersona);
					objEDocumentoIdentidad.setDocumento(UFuncionesGenerales.revisaCadena(objResultSet.getString("DOCUME")));
					objEPersona.setDocumentoIdentidad(objEDocumentoIdentidad);
					objEPersona.setNombreCorte(UFuncionesGenerales.convierteCadenaMayuscula(objResultSet.getString("NOMABV")));
					objEPersona.setNombreLargo(UFuncionesGenerales.convierteCadenaMayuscula(objResultSet.getString("NOMBCL")));
					objEPersona.setNombre(UFuncionesGenerales.convierteCadenaMayuscula(objResultSet.getString("NOMBCL")));
					objEPersona.setClasePersona(UFuncionesGenerales.revisaCadena(objResultSet.getString("CLAPER")));
					
					lstPersona.add(objEPersona);
				}
				objConexion.cierraConsulta(objResultSet);
			}
		} catch (Exception objEx) {
			UManejadorLog.error(
					"Acceso: Error al obtener el listado de Postulante: ", objEx);
		}
		return lstPersona;
	}
	
	public List<EPersona> listarTercero(EPersonaParametro objEPersonaParam, int codigoTipoBusqueda) {
		List<EPersona> lstPersona = new ArrayList<EPersona>();
		EPersona objEPersona = null;
		EDocumentoIdentidad objEDocumentoIdentidad = null;
		ETipoDocumentoPersona objETipoDocumentoPersona = null;
		List<Object> lstParametrosEntrada = null;

		try {

			lstParametrosEntrada = new ArrayList<Object>();
			lstParametrosEntrada.add(objEPersonaParam.getCodPersona());
			lstParametrosEntrada.add(UFuncionesGenerales.convierteCadenaMayuscula(objEPersonaParam.getNombrePersona()));
			lstParametrosEntrada.add(codigoTipoBusqueda);

			ResultSet objResultSet = objConexion.ejecutaConsulta(SP_ABACOINLEGAL_SEL_TERCERO, lstParametrosEntrada, null);

			if (objResultSet != null) {
				while (objResultSet.next()) {
					objEPersona = new EPersona();
					objETipoDocumentoPersona = new ETipoDocumentoPersona();
					objEDocumentoIdentidad = new EDocumentoIdentidad();
					objEPersona.setCodigo(objResultSet.getInt("CODCLI"));
					objETipoDocumentoPersona.setCodigo(UFuncionesGenerales.revisaCadena(objResultSet.getString("TIPDOC")));
					objEDocumentoIdentidad.setTipoDocumento(objETipoDocumentoPersona);
					objEDocumentoIdentidad.setDocumento(UFuncionesGenerales.revisaCadena(objResultSet.getString("DOCUME")));
					objEPersona.setDocumentoIdentidad(objEDocumentoIdentidad);
					objEPersona.setNombreCorte(UFuncionesGenerales.convierteCadenaMayuscula(objResultSet.getString("NOMABV")));
					objEPersona.setNombreLargo(UFuncionesGenerales.convierteCadenaMayuscula(objResultSet.getString("NOMBCL")));
					objEPersona.setNombre(UFuncionesGenerales.convierteCadenaMayuscula(objResultSet.getString("NOMBCL")));
					objEPersona.setClasePersona(UFuncionesGenerales.revisaCadena(objResultSet.getString("CLAPER")));
					
					lstPersona.add(objEPersona);
				}
				objConexion.cierraConsulta(objResultSet);
			}
		} catch (Exception objEx) {
			UManejadorLog.error(
					"Acceso: Error al obtener el listado de Tercero: ", objEx);
		}
		return lstPersona;
	}
	
	public List<EPersona> listarNoSocio(EPersonaParametro objEPersonaParam) {
		List<EPersona> lstPersona = new ArrayList<EPersona>();
		EPersona objEPersona = null;
		EDocumentoIdentidad objEDocumentoIdentidad = null;
		ETipoDocumentoPersona objETipoDocumentoPersona = null;
		List<Object> lstParametrosEntrada = null;

		try {

			lstParametrosEntrada = new ArrayList<Object>();
			lstParametrosEntrada.add(objEPersonaParam.getCodPersona());
			lstParametrosEntrada.add(UFuncionesGenerales.convierteCadenaMayuscula(objEPersonaParam.getNombrePersona()));

			ResultSet objResultSet = objConexion.ejecutaConsulta(SP_ABACOINLEGAL_SEL_NOSOCIO, lstParametrosEntrada, null);

			if (objResultSet != null) {
				while (objResultSet.next()) {
					objEPersona = new EPersona();
					objETipoDocumentoPersona = new ETipoDocumentoPersona();
					objEDocumentoIdentidad = new EDocumentoIdentidad();
					objEPersona.setCodigo(objResultSet.getInt("CODCLI"));
					objETipoDocumentoPersona.setCodigo(UFuncionesGenerales.revisaCadena(objResultSet.getString("TIPDOC")));
					objEDocumentoIdentidad.setTipoDocumento(objETipoDocumentoPersona);
					objEDocumentoIdentidad.setDocumento(UFuncionesGenerales.revisaCadena(objResultSet.getString("DOCUME")));
					objEPersona.setDocumentoIdentidad(objEDocumentoIdentidad);
					objEPersona.setNombreCorte(UFuncionesGenerales.convierteCadenaMayuscula(objResultSet.getString("NOMABV")));
					objEPersona.setNombreLargo(UFuncionesGenerales.convierteCadenaMayuscula(objResultSet.getString("NOMBCL")));
					objEPersona.setNombre(UFuncionesGenerales.convierteCadenaMayuscula(objResultSet.getString("NOMBCL")));
					objEPersona.setClasePersona(UFuncionesGenerales.revisaCadena(objResultSet.getString("CLAPER")));
					
					lstPersona.add(objEPersona);
				}
				objConexion.cierraConsulta(objResultSet);
			}
		} catch (Exception objEx) {
			UManejadorLog.error(
					"Acceso: Error al obtener el listado de no Socio: ", objEx);
		}
		return lstPersona;
	}
	
	public List<EClienteHistorico> listarClienteHistorico(int codigoTipoCliente, int codigoCliente) {
		List<Object> lstParametrosEntrada = null;
		ResultSet oResultSet = null;
		EClienteHistorico oEClienteHistorico = null;
		List<EClienteHistorico> lstClienteHistorico = null;
		
		try {
			lstParametrosEntrada = new ArrayList<Object>();
			lstParametrosEntrada.add(codigoTipoCliente);
			lstParametrosEntrada.add(codigoCliente);

			oResultSet = objConexion.ejecutaConsulta(SP_ABACOINLEGAL_SEL_CLIENTE_HISTORICO, lstParametrosEntrada, null);

			if (oResultSet != null) {
				lstClienteHistorico = new ArrayList<EClienteHistorico>();
				while (oResultSet.next()) {
					oEClienteHistorico = new EClienteHistorico();
					oEClienteHistorico.setNumeroSolicitud(oResultSet.getInt("NROSOL"));
					oEClienteHistorico.setCodigoTipoCliente(oResultSet.getInt("TIPCLI"));
					oEClienteHistorico.setCodigoCliente(oResultSet.getInt("CODCLI"));
					
					oEClienteHistorico.setCodigoTipoDocumento(UFuncionesGenerales.revisaCadena(oResultSet.getString("TIPDOC")));
					oEClienteHistorico.setNumeroDocumento(UFuncionesGenerales.revisaCadena(oResultSet.getString("DOCUME")));
					oEClienteHistorico.setRuc(UFuncionesGenerales.revisaCadena(oResultSet.getString("NRORUC")));
					oEClienteHistorico.setApellidoPaterno(UFuncionesGenerales.revisaCadena(oResultSet.getString("APEPAT")));
					oEClienteHistorico.setApellidoMaterno(UFuncionesGenerales.revisaCadena(oResultSet.getString("APEMAT")));
					oEClienteHistorico.setNombre(UFuncionesGenerales.revisaCadena(oResultSet.getString("NOMBRS")));
					oEClienteHistorico.setNombreLargo(UFuncionesGenerales.revisaCadena(oResultSet.getString("NOMBCS")));
					
					oEClienteHistorico.setDireccionReal(UFuncionesGenerales.revisaCadena(oResultSet.getString("DIRECC")));
					oEClienteHistorico.setCodigoUbigeoReal(oResultSet.getInt("CCIUDA"));
					oEClienteHistorico.setDireccionContractual(UFuncionesGenerales.revisaCadena(oResultSet.getString("DIRECP")));
					oEClienteHistorico.setCodigoUbigeoContractual(oResultSet.getInt("CCIUDP"));
					
					oEClienteHistorico.setCodigoTipoDocumentoConyugue(UFuncionesGenerales.revisaCadena(oResultSet.getString("TDOCON")));
					oEClienteHistorico.setDocumentoConyugue(UFuncionesGenerales.revisaCadena(oResultSet.getString("DOCCON")));
					oEClienteHistorico.setApellidoPaternoConyugue(UFuncionesGenerales.revisaCadena(oResultSet.getString("APEPCY")));
					oEClienteHistorico.setApellidoMaternoConyugue(UFuncionesGenerales.revisaCadena(oResultSet.getString("APEMCY")));
					oEClienteHistorico.setNombreConyugue(UFuncionesGenerales.revisaCadena(oResultSet.getString("NOMBCY")));
					oEClienteHistorico.setNombreLargoConyuge(UFuncionesGenerales.revisaCadena(oResultSet.getString("NOMLCY")));
					
					oEClienteHistorico.setCodigoTipoPersonaJuridica(UFuncionesGenerales.revisaCadena(oResultSet.getString("TIPOPJ")));
					oEClienteHistorico.setMontoCapitalSocialRegistroPublicos(oResultSet.getDouble("CAPSRP"));
					oEClienteHistorico.setMontoCapitalSocialActual(oResultSet.getDouble("CAPSAC"));
					oEClienteHistorico.setCodigoFacultadOperar(UFuncionesGenerales.revisaCadena(oResultSet.getString("FACUOP")));
					oEClienteHistorico.setCodigoTipoSuscripcionPago(UFuncionesGenerales.revisaCadena(oResultSet.getString("TIPSPG")));
					oEClienteHistorico.setNumeroSuscripcionPago(oResultSet.getInt("SUSPAG"));
					oEClienteHistorico.setNumeroAccionistas(oResultSet.getInt("NROACC"));
					oEClienteHistorico.setIndicadorAvalarTercero(UFuncionesGenerales.revisaCadena(oResultSet.getString("AVATER")));
					oEClienteHistorico.setIndicadorGrabarBien(UFuncionesGenerales.revisaCadena(oResultSet.getString("GRABIE")));
					oEClienteHistorico.setDescripcionAvalarTercero(UFuncionesGenerales.revisaCadena(oResultSet.getString("AVATFI")));
					oEClienteHistorico.setObservacionAvalarTercero(UFuncionesGenerales.revisaCadena(oResultSet.getString("OB1AVT")));
					oEClienteHistorico.setObservacionGrabarBien(UFuncionesGenerales.revisaCadena(oResultSet.getString("OB1GRA")));
					oEClienteHistorico.setObservacionConstanciaSocial(UFuncionesGenerales.revisaCadena(oResultSet.getString("DAT1PJ")));
					
					oEClienteHistorico.setCodigoEstadoCivil(UFuncionesGenerales.revisaCadena(oResultSet.getString("CODCIV")));
					oEClienteHistorico.setOficinaRegistral(UFuncionesGenerales.revisaCadena(oResultSet.getString("OFIREG")));
					oEClienteHistorico.setNumeroPartida(UFuncionesGenerales.revisaCadena(oResultSet.getString("NUMPART")));
					oEClienteHistorico.setFechaConstitucion(oResultSet.getDate("FECCONS"));
					oEClienteHistorico.setCodigoNotario(oResultSet.getInt("CODNOT"));
					oEClienteHistorico.setDescripcionNotario(UFuncionesGenerales.revisaCadena(oResultSet.getString("DESCNOT")));
					oEClienteHistorico.setNumeroAcciones(oResultSet.getInt("NUMACC"));
					
					oEClienteHistorico.setCodigoTipoDuracionPartida(oResultSet.getInt("CODTPART"));
					oEClienteHistorico.setRegistroPartida(UFuncionesGenerales.revisaCadena(oResultSet.getString("REGPART")));
					oEClienteHistorico.setDescripcionConstitucion(UFuncionesGenerales.revisaCadena(oResultSet.getString("DESCCONS")));
					oEClienteHistorico.setCodigoTipoValorSuscripcion(oResultSet.getInt("CODTVAL"));
					oEClienteHistorico.setMontoValorNominal(oResultSet.getDouble("MTOVALN"));
					//oEClienteHistorico.setDescripcionAporte(UFuncionesGenerales.revisaCadena(oResultSet.getString("")));
					oEClienteHistorico.setEstatuto(UFuncionesGenerales.revisaCadena(oResultSet.getString("ESTATUTO")));
					oEClienteHistorico.setDescripcionPatrimonio(UFuncionesGenerales.revisaCadena(oResultSet.getString("DESCPAT")));
					oEClienteHistorico.setCodigoTipoNumeracionEstatuto(oResultSet.getInt("CODTNETT"));
					oEClienteHistorico.setNumeracionEstatuto(oResultSet.getInt("NUMETT"));
					oEClienteHistorico.setAsiento(UFuncionesGenerales.revisaCadena(oResultSet.getString("ASIENTO")));
					oEClienteHistorico.setFechaPeriodoInicio(oResultSet.getDate("FECPINI"));
					oEClienteHistorico.setFechaPeriodoVencimiento(oResultSet.getDate("FECPVEN"));
					
					oEClienteHistorico.setFechaRegistro(oResultSet.getDate("FECREG"));
					oEClienteHistorico.setHoraRegistro(oResultSet.getString("HORREG"));
					oEClienteHistorico.setUsuarioRegistro(UFuncionesGenerales.revisaCadena(oResultSet.getString("USUREG")));
					/*
					EUsuario eUsuario = new EUsuario();
					eUsuario.setNombreUsuario(UFuncionesGenerales.revisaCadena(oResultSet.getString("USUREG")));
					oEClienteHistorico.setUsuarioRegistro(eUsuario);
					*/
					
					oEClienteHistorico.setCodigoUbigeoRealH(oResultSet.getInt("@CCIUDA"));
					oEClienteHistorico.setCodigoUbigeoContractualH(oResultSet.getInt("@DIRECP"));
					
					oEClienteHistorico.setValidarCodigoTipoDocumento(UFuncionesGenerales.revisaCadena(oResultSet.getString("TIPDOC")).equals(
							UFuncionesGenerales.revisaCadena(oResultSet.getString("@TIPDOC"))) ? false : true);
					oEClienteHistorico.setValidarNumeroDocumento(UFuncionesGenerales.revisaCadena(oResultSet.getString("DOCUME")).equals(
							UFuncionesGenerales.revisaCadena(oResultSet.getString("@DOCUME"))) ? false : true);
					oEClienteHistorico.setValidarRuc(UFuncionesGenerales.revisaCadena(oResultSet.getString("NRORUC")).equals(
							UFuncionesGenerales.revisaCadena(oResultSet.getString("@NRORUC"))) ? false : true);
					oEClienteHistorico.setValidarApellidoPaterno(UFuncionesGenerales.revisaCadena(oResultSet.getString("APEPAT")).equals(
							UFuncionesGenerales.revisaCadena(oResultSet.getString("@APEPAT"))) ? false : true);
					oEClienteHistorico.setValidarApellidoMaterno(UFuncionesGenerales.revisaCadena(oResultSet.getString("APEMAT")).equals(
							UFuncionesGenerales.revisaCadena(oResultSet.getString("@APEMAT"))) ? false : true);
					oEClienteHistorico.setValidarNombre(UFuncionesGenerales.revisaCadena(oResultSet.getString("NOMBRS")).equals(
							UFuncionesGenerales.revisaCadena(oResultSet.getString("@NOMBRS"))) ? false : true);
					oEClienteHistorico.setValidarNombreLargo(UFuncionesGenerales.revisaCadena(oResultSet.getString("NOMBCS")).equals(
							UFuncionesGenerales.revisaCadena(oResultSet.getString("@NOMBCS"))) ? false : true);
					
					oEClienteHistorico.setValidarDireccionReal(UFuncionesGenerales.revisaCadena(oResultSet.getString("DIRECC")).equals(
							UFuncionesGenerales.revisaCadena(oResultSet.getString("@DIRECC"))) ? false : true);
					oEClienteHistorico.setValidarCodigoUbigeoReal(oResultSet.getInt("CCIUDA") == oResultSet.getInt("@CCIUDA") ? false : true);
					oEClienteHistorico.setValidarDireccionContractual(UFuncionesGenerales.revisaCadena(oResultSet.getString("DIRECP")).equals(
							UFuncionesGenerales.revisaCadena(oResultSet.getString("@DIRECP"))) ? false : true);
					oEClienteHistorico.setValidarCodigoUbigeoContractual(oResultSet.getInt("CCIUDP") == oResultSet.getInt("@CCIUDP") ? false : true);
					
					oEClienteHistorico.setValidarCodigoTipoDocumentoConyugue(UFuncionesGenerales.revisaCadena(oResultSet.getString("TDOCON")).equals(
							UFuncionesGenerales.revisaCadena(oResultSet.getString("@TDOCON"))) ? false : true);
					oEClienteHistorico.setValidarDocumentoConyugue(UFuncionesGenerales.revisaCadena(oResultSet.getString("DOCCON")).equals(
							UFuncionesGenerales.revisaCadena(oResultSet.getString("@DOCCON"))) ? false : true);
					oEClienteHistorico.setValidarApellidoPaternoConyugue(UFuncionesGenerales.revisaCadena(oResultSet.getString("APEPCY")).equals(
							UFuncionesGenerales.revisaCadena(oResultSet.getString("@APEPCY"))) ? false : true);
					oEClienteHistorico.setValidarApellidoMaternoConyugue(UFuncionesGenerales.revisaCadena(oResultSet.getString("APEMCY")).equals(
							UFuncionesGenerales.revisaCadena(oResultSet.getString("@APEMCY"))) ? false : true);
					oEClienteHistorico.setValidarNombreConyugue(UFuncionesGenerales.revisaCadena(oResultSet.getString("NOMBCY")).equals(
							UFuncionesGenerales.revisaCadena(oResultSet.getString("@NOMBCY"))) ? false : true);
					oEClienteHistorico.setValidarNombreLargoConyuge(UFuncionesGenerales.revisaCadena(oResultSet.getString("NOMLCY")).equals(
							UFuncionesGenerales.revisaCadena(oResultSet.getString("@NOMLCY"))) ? false : true);
					
					oEClienteHistorico.setValidarCodigoTipoPersonaJuridica(UFuncionesGenerales.revisaCadena(oResultSet.getString("TIPOPJ")).equals(
							UFuncionesGenerales.revisaCadena(oResultSet.getString("@TIPOPJ"))) ? false : true);
					oEClienteHistorico.setValidarMontoCapitalSocialRegistroPublicos(oResultSet.getDouble("CAPSRP") == oResultSet.getDouble("@CAPSRP") ? false : true);
					oEClienteHistorico.setValidarMontoCapitalSocialActual(oResultSet.getDouble("CAPSAC") == oResultSet.getDouble("@CAPSAC") ? false : true);
					oEClienteHistorico.setValidarCodigoFacultadOperar(UFuncionesGenerales.revisaCadena(oResultSet.getString("FACUOP")).equals(
							UFuncionesGenerales.revisaCadena(oResultSet.getString("@FACUOP"))) ? false : true);
					oEClienteHistorico.setValidarCodigoTipoSuscripcionPago(UFuncionesGenerales.revisaCadena(oResultSet.getString("TIPSPG")).equals(
							UFuncionesGenerales.revisaCadena(oResultSet.getString("@TIPSPG"))) ? false : true);
					oEClienteHistorico.setValidarNumeroSuscripcionPago(oResultSet.getInt("SUSPAG") == oResultSet.getInt("@SUSPAG") ? false : true);
					oEClienteHistorico.setValidarNumeroAccionistas(oResultSet.getInt("NROACC") == oResultSet.getInt("@NROACC") ? false : true);
					oEClienteHistorico.setValidarIndicadorAvalarTercero(UFuncionesGenerales.revisaCadena(oResultSet.getString("AVATER")).equals(
							UFuncionesGenerales.revisaCadena(oResultSet.getString("@AVATER"))) ? false : true);
					oEClienteHistorico.setValidarIndicadorGrabarBien(UFuncionesGenerales.revisaCadena(oResultSet.getString("GRABIE")).equals(
							UFuncionesGenerales.revisaCadena(oResultSet.getString("@GRABIE"))) ? false : true);
					oEClienteHistorico.setValidarDescripcionAvalarTercero(UFuncionesGenerales.revisaCadena(oResultSet.getString("AVATFI")).equals(
							UFuncionesGenerales.revisaCadena(oResultSet.getString("@AVATFI"))) ? false : true);
					oEClienteHistorico.setValidarObservacionAvalarTercero(UFuncionesGenerales.revisaCadena(oResultSet.getString("OB1AVT")).equals(
							UFuncionesGenerales.revisaCadena(oResultSet.getString("@OB1AVT"))) ? false : true);
					oEClienteHistorico.setValidarObservacionGrabarBien(UFuncionesGenerales.revisaCadena(oResultSet.getString("OB1GRA")).equals(
							UFuncionesGenerales.revisaCadena(oResultSet.getString("@OB1GRA"))) ? false : true);
					oEClienteHistorico.setValidarObservacionConstanciaSocial(UFuncionesGenerales.revisaCadena(oResultSet.getString("DAT1PJ")).equals(
							UFuncionesGenerales.revisaCadena(oResultSet.getString("@DAT1PJ"))) ? false : true);
					
					oEClienteHistorico.setValidarCodigoEstadoCivil(UFuncionesGenerales.revisaCadena(oResultSet.getString("CODCIV")).equals(
							UFuncionesGenerales.revisaCadena(oResultSet.getString("@CODCIV"))) ? false : true);
					oEClienteHistorico.setValidarOficinaRegistral(UFuncionesGenerales.revisaCadena(oResultSet.getString("OFIREG")).equals(
							UFuncionesGenerales.revisaCadena(oResultSet.getString("@OFIREG"))) ? false : true);
					oEClienteHistorico.setValidarNumeroPartida(UFuncionesGenerales.revisaCadena(oResultSet.getString("NUMPART")).equals(
							UFuncionesGenerales.revisaCadena(oResultSet.getString("@NUMPART"))) ? false : true);
					oEClienteHistorico.setValidarFechaConstitucion(UFuncionesGenerales.revisaFecha(oResultSet.getDate("FECCONS")).equals(
							UFuncionesGenerales.revisaFecha(oResultSet.getDate("@FECCONS"))) ? false: true);
					oEClienteHistorico.setValidarCodigoNotario(oResultSet.getInt("CODNOT") == oResultSet.getInt("@CODNOT") ? false : true);
					oEClienteHistorico.setValidarDescripcionNotario(UFuncionesGenerales.revisaCadena(oResultSet.getString("DESCNOT")).equals(
							UFuncionesGenerales.revisaCadena(oResultSet.getString("@DESCNOT"))) ? false : true);
					oEClienteHistorico.setValidarNumeroAcciones(oResultSet.getInt("NUMACC") == oResultSet.getInt("@NUMACC") ? false : true);
					
					oEClienteHistorico.setValidarCodigoTipoDuracionPartida(oResultSet.getInt("CODTPART") == oResultSet.getInt("@CODTPART") ? false : true);
					oEClienteHistorico.setValidarRegistroPartida(UFuncionesGenerales.revisaCadena(oResultSet.getString("REGPART")).equals(
							UFuncionesGenerales.revisaCadena(oResultSet.getString("@REGPART"))) ? false : true);
					oEClienteHistorico.setValidarDescripcionConstitucion(UFuncionesGenerales.revisaCadena(oResultSet.getString("DESCCONS")).equals(
							UFuncionesGenerales.revisaCadena(oResultSet.getString("@DESCCONS"))) ? false : true);
					oEClienteHistorico.setValidarCodigoTipoValorSuscripcion(oResultSet.getInt("CODTVAL") == oResultSet.getInt("@CODTVAL") ? false : true);
					oEClienteHistorico.setValidarMontoValorNominal(oResultSet.getDouble("MTOVALN") == oResultSet.getDouble("@MTOVALN") ? false : true);
					//
					oEClienteHistorico.setValidarEstatuto(UFuncionesGenerales.revisaCadena(oResultSet.getString("ESTATUTO")).equals(
							UFuncionesGenerales.revisaCadena(oResultSet.getString("@ESTATUTO"))) ? false : true);
					oEClienteHistorico.setValidarDescripcionPatrimonio(UFuncionesGenerales.revisaCadena(oResultSet.getString("DESCPAT")).equals(
							UFuncionesGenerales.revisaCadena(oResultSet.getString("@DESCPAT"))) ? false : true);
					oEClienteHistorico.setValidarCodigoTipoNumeracionEstatuto(oResultSet.getInt("CODTNETT") == oResultSet.getInt("@CODTNETT") ? false : true);
					oEClienteHistorico.setValidarNumeracionEstatuto(oResultSet.getInt("NUMETT") == oResultSet.getInt("@NUMETT") ? false : true);
					oEClienteHistorico.setValidarAsiento(UFuncionesGenerales.revisaCadena(oResultSet.getString("ASIENTO")).equals(
							UFuncionesGenerales.revisaCadena(oResultSet.getString("@ASIENTO"))) ? false : true);
					oEClienteHistorico.setValidarFechaPeriodoInicio(UFuncionesGenerales.revisaFecha(oResultSet.getDate("FECPINI")).equals(
							UFuncionesGenerales.revisaFecha(oResultSet.getDate("@FECPINI"))) ? false: true);
					oEClienteHistorico.setValidarFechaPeriodoVencimiento(UFuncionesGenerales.revisaFecha(oResultSet.getDate("FECPVEN")).equals(
							UFuncionesGenerales.revisaFecha(oResultSet.getDate("@FECPVEN"))) ? false: true);
					
					lstClienteHistorico.add(oEClienteHistorico);
				}
				objConexion.cierraConsulta(oResultSet);
			}
		} catch (Exception objEx) {
			UManejadorLog.error("Acceso: Error al obtener el listado de cliente historico: ", objEx);
		}
		return lstClienteHistorico;
	}
	
	public ECliente buscarSocio(int codigoCliente) {
		List<Object> lstParametrosEntrada = null;
		ResultSet objResultSet = null;
		ECliente oECliente = null;

		try {
			lstParametrosEntrada = new ArrayList<Object>();
			lstParametrosEntrada.add(codigoCliente);
			objResultSet = objConexion.ejecutaConsulta(SP_ABACOINLEGAL_BUS_SOCIO, lstParametrosEntrada, null);
			if (objResultSet != null) {
				if (objResultSet.next()) {
					oECliente = new ECliente();
					oECliente.setCodigoCliente(objResultSet.getInt("CODCLI"));
					oECliente.setCodigoTipoDocumento(UFuncionesGenerales.revisaCadena(objResultSet.getString("TIPDOC")));
					oECliente.setDocumento(UFuncionesGenerales.revisaCadena(objResultSet.getString("DOCUME")));
					oECliente.setRuc(UFuncionesGenerales.revisaCadena(objResultSet.getString("NRORUC")));
					oECliente.setApellidoPaterno(UFuncionesGenerales.revisaCadena(objResultSet.getString("APEPAT")));
					oECliente.setApellidoMaterno(UFuncionesGenerales.revisaCadena(objResultSet.getString("APEMAT")));
					oECliente.setNombre(UFuncionesGenerales.revisaCadena(objResultSet.getString("NOMBRS")));
					oECliente.setNombreCorto(UFuncionesGenerales.revisaCadena(objResultSet.getString("NOMABV")));
					oECliente.setNombreLargo(UFuncionesGenerales.revisaCadena(objResultSet.getString("NOMBCL")));
					oECliente.setNombreLargo2(UFuncionesGenerales.revisaCadena(objResultSet.getString("NOMBCS")));
					oECliente.setDireccion(UFuncionesGenerales.revisaCadena(objResultSet.getString("DIRECC")));
					oECliente.setCodigoUbigeo(objResultSet.getInt("CCIUDA"));
					oECliente.setTelefono(UFuncionesGenerales.revisaCadena(objResultSet.getString("TELEFO")));
					oECliente.setTelefono2(UFuncionesGenerales.revisaCadena(objResultSet.getString("TELEFB")));
					oECliente.setAnexo(objResultSet.getInt("TELANE"));
					oECliente.setFax(UFuncionesGenerales.revisaCadena(objResultSet.getString("FAX")));
					oECliente.setCodigoTipoPersona(UFuncionesGenerales.revisaCadena(objResultSet.getString("CLAPER")));
					oECliente.setDireccion2(UFuncionesGenerales.revisaCadena(objResultSet.getString("DIRECP")));
					oECliente.setCodigoUbigeo2(objResultSet.getInt("CCIUDP"));
					oECliente.setCodigoTipoDocumentoConyugue(UFuncionesGenerales.revisaCadena(objResultSet.getString("TDOCON")));
					oECliente.setDocumentoConyugue(UFuncionesGenerales.revisaCadena(objResultSet.getString("DOCCON")));
					oECliente.setApellidoPaternoConyugue(UFuncionesGenerales.revisaCadena(objResultSet.getString("APEPCY")));
					oECliente.setApellidoMaternoConyugue(UFuncionesGenerales.revisaCadena(objResultSet.getString("APEMCY")));
					oECliente.setNombreConyugue(UFuncionesGenerales.revisaCadena(objResultSet.getString("NOMBCY")));
					oECliente.setNombreSuperLargoConyugue(UFuncionesGenerales.revisaCadena(objResultSet.getString("NOMLCY")));
					oECliente.setCodigoEstadoCivil(UFuncionesGenerales.revisaCadena(objResultSet.getString("ESTCIV")));
				}
				objConexion.cierraConsulta(objResultSet);
			}
		} catch (Exception objEx) {
			UManejadorLog.error("Acceso: Problemas al obtener.", objEx);
		}
		return oECliente;
	}
	
	public ECliente buscarUsuarioDetalle(EUsuario eUsuario) {
		List<Object> lstParametrosEntrada = null;
		ResultSet objResultSet = null;
		ECliente oECliente = null;

		try {
			lstParametrosEntrada = new ArrayList<Object>();
			lstParametrosEntrada.add(eUsuario.getNombreUsuarioSIAF());
			objResultSet = objConexion.ejecutaConsulta(SP_ABACOINLEGAL_BUS_USUARIO_DETALLE, lstParametrosEntrada, null);
			if (objResultSet != null) {
				if (objResultSet.next()) {
					oECliente = new ECliente();
					oECliente.setCodigoCliente(objResultSet.getInt("CODCLI"));
					oECliente.setCodigoTipoDocumento(UFuncionesGenerales.revisaCadena(objResultSet.getString("TIPDOC")));
					oECliente.setDocumento(UFuncionesGenerales.revisaCadena(objResultSet.getString("DOCUME")));
					oECliente.setApellidoPaterno(UFuncionesGenerales.revisaCadena(objResultSet.getString("APEPAT")));
					oECliente.setApellidoMaterno(UFuncionesGenerales.revisaCadena(objResultSet.getString("APEMAT")));
					oECliente.setNombre(UFuncionesGenerales.revisaCadena(objResultSet.getString("NOMBRS")));
				    oECliente.setNombreSuperLargo(UFuncionesGenerales.revisaCadena(objResultSet.getString("NOMBCS")));
					oECliente.setNombreCorto(UFuncionesGenerales.revisaCadena(objResultSet.getString("NOMABV")));
					oECliente.setNombreLargo(UFuncionesGenerales.revisaCadena(objResultSet.getString("NOMBCL")));
					oECliente.setRuc(UFuncionesGenerales.revisaCadena(objResultSet.getString("NRORUC")));
					oECliente.setDireccion(UFuncionesGenerales.revisaCadena(objResultSet.getString("DIRECC")));
					oECliente.setCodigoUbigeo(objResultSet.getInt("CCIUDA"));
					oECliente.setTelefono(UFuncionesGenerales.revisaCadena(objResultSet.getString("TELEFO")));
					oECliente.setTelefono2(UFuncionesGenerales.revisaCadena(objResultSet.getString("TELEFB")));
					oECliente.setAnexo(objResultSet.getInt("TELANE"));
					oECliente.setFax(UFuncionesGenerales.revisaCadena(objResultSet.getString("FAX")));
					oECliente.setDireccionPostal(UFuncionesGenerales.revisaCadena(objResultSet.getString("DIRECP")));
					oECliente.setCodigoUbigeoPostal(objResultSet.getInt("CCIUDP"));
					oECliente.setCodigoTipoPersona(UFuncionesGenerales.revisaCadena(objResultSet.getString("CLAPER")));
					oECliente.setComunidad(UFuncionesGenerales.revisaCadena(objResultSet.getString("COMUNI")));
					oECliente.setEjecutivoControl(UFuncionesGenerales.revisaCadena(objResultSet.getString("OFICIA")));
					oECliente.setCodigoEstadoCivil(UFuncionesGenerales.revisaCadena(objResultSet.getString("ESTCIV")));
					oECliente.setCodigoGenero(UFuncionesGenerales.revisaCadena(objResultSet.getString("SEXO")));
					oECliente.setCodigoProfesion(objResultSet.getInt("PROFES"));
					oECliente.setDescripcionGenero(UFuncionesGenerales.revisaCadena(objResultSet.getString("DESCSEXO")));
					oECliente.setDescripcionEstadoCivil(UFuncionesGenerales.revisaCadena(objResultSet.getString("DESCESTCIV")));
					oECliente.setDescripcionProfesion(UFuncionesGenerales.revisaCadena(objResultSet.getString("DESCPROF")));
				}
				objConexion.cierraConsulta(objResultSet);
			}
		} catch (Exception objEx) {
			UManejadorLog.error("Acceso: Problemas al obtener.", objEx);
		}
		return oECliente;
	}
	
	public ECliente buscarPostulante(int codigoCliente) {
		List<Object> lstParametrosEntrada = null;
		ResultSet objResultSet = null;
		ECliente oECliente = null;

		try {
			lstParametrosEntrada = new ArrayList<Object>();
			lstParametrosEntrada.add(codigoCliente);
			objResultSet = objConexion.ejecutaConsulta(SP_ABACOINLEGAL_BUS_POSTULANTE, lstParametrosEntrada, null);
			if (objResultSet != null) {
				if (objResultSet.next()) {
					oECliente = new ECliente();
					oECliente.setCodigoCliente(objResultSet.getInt("CODCLI"));
					oECliente.setCodigoTipoDocumento(UFuncionesGenerales.revisaCadena(objResultSet.getString("TIPDOC")));
					oECliente.setDocumento(UFuncionesGenerales.revisaCadena(objResultSet.getString("DOCUME")));
					oECliente.setRuc(UFuncionesGenerales.revisaCadena(objResultSet.getString("NRORUC")));
					oECliente.setApellidoPaterno(UFuncionesGenerales.revisaCadena(objResultSet.getString("APEPAT")));
					oECliente.setApellidoMaterno(UFuncionesGenerales.revisaCadena(objResultSet.getString("APEMAT")));
					oECliente.setNombre(UFuncionesGenerales.revisaCadena(objResultSet.getString("NOMBRS")));
					oECliente.setNombreCorto(UFuncionesGenerales.revisaCadena(objResultSet.getString("NOMABV")));
					oECliente.setNombreLargo(UFuncionesGenerales.revisaCadena(objResultSet.getString("NOMBCL")));
                    oECliente.setNombreLargo2(UFuncionesGenerales.revisaCadena(objResultSet.getString("NOMBCS")));
					oECliente.setDireccion(UFuncionesGenerales.revisaCadena(objResultSet.getString("DIRECC")));
					oECliente.setCodigoUbigeo(objResultSet.getInt("CCIUDA"));
					oECliente.setTelefono(UFuncionesGenerales.revisaCadena(objResultSet.getString("TELEFO")));
					oECliente.setTelefono2(UFuncionesGenerales.revisaCadena(objResultSet.getString("TELEFB")));
					oECliente.setAnexo(objResultSet.getInt("TELANE"));
					oECliente.setFax(UFuncionesGenerales.revisaCadena(objResultSet.getString("FAX")));
					oECliente.setCodigoTipoPersona(UFuncionesGenerales.revisaCadena(objResultSet.getString("CLAPER")));
					oECliente.setDireccion2(UFuncionesGenerales.revisaCadena(objResultSet.getString("DIRECP")));
					oECliente.setCodigoUbigeo2(objResultSet.getInt("CCIUDP"));
					
					oECliente.setCodigoTipoDocumentoConyugue(UFuncionesGenerales.revisaCadena(objResultSet.getString("TDOCON")));
					oECliente.setDocumentoConyugue(UFuncionesGenerales.revisaCadena(objResultSet.getString("DOCCON")));
					oECliente.setApellidoPaternoConyugue(UFuncionesGenerales.revisaCadena(objResultSet.getString("APEPCY")));
					oECliente.setApellidoMaternoConyugue(UFuncionesGenerales.revisaCadena(objResultSet.getString("APEMCY")));
					oECliente.setNombreConyugue(UFuncionesGenerales.revisaCadena(objResultSet.getString("NOMBCY")));
					oECliente.setNombreSuperLargoConyugue(UFuncionesGenerales.revisaCadena(objResultSet.getString("NOMLCY")));
					oECliente.setCodigoEstadoCivil(UFuncionesGenerales.revisaCadena(objResultSet.getString("ESTCIV")));
				}
				objConexion.cierraConsulta(objResultSet);
			}
		} catch (Exception objEx) {
			UManejadorLog.error("Acceso: Problemas al obtener.", objEx);
		}
		return oECliente;
	}
	

	public ETercero buscarTercero(long codigoCliente) {
		List<Object> lstParametrosEntrada = null;
		ResultSet objResultSet = null;
		ETercero oETercero = null;

		try {
			lstParametrosEntrada = new ArrayList<Object>();
			lstParametrosEntrada.add(codigoCliente);
			objResultSet = objConexion.ejecutaConsulta(SP_ABACOINLEGAL_BUS_TERCERO, lstParametrosEntrada, null);
			if (objResultSet != null) {
				if (objResultSet.next()) {
					oETercero = new ETercero();
					oETercero.setCodigoCliente(objResultSet.getInt("CODCLI"));
					oETercero.setCodigoTipoDocumento(UFuncionesGenerales.revisaCadena(objResultSet.getString("TIPDOC")));
					oETercero.setDocumento(UFuncionesGenerales.revisaCadena(objResultSet.getString("DOCUME")));
					oETercero.setNombreCorte(UFuncionesGenerales.revisaCadena(objResultSet.getString("NOMABV")));
					oETercero.setNombreLargo(UFuncionesGenerales.revisaCadena(objResultSet.getString("NOMBCL")));
					oETercero.setRuc(UFuncionesGenerales.revisaCadena(objResultSet.getString("NRORUC")));
					oETercero.setRuc2(UFuncionesGenerales.revisaCadena(objResultSet.getString("NRORU8")));
					oETercero.setSiglas(UFuncionesGenerales.revisaCadena(objResultSet.getString("SIGLAS")));
					oETercero.setDireccion(UFuncionesGenerales.revisaCadena(objResultSet.getString("DIRECC")));
					oETercero.setCodigoUbigeo(objResultSet.getInt("CCIUDA"));
					oETercero.setCodigoZonaGeografica(objResultSet.getInt("ZONGEO"));
					oETercero.setDireccionPostal(UFuncionesGenerales.revisaCadena(objResultSet.getString("DIRECP")));
					oETercero.setCodigoUbigeoPostal(objResultSet.getInt("CCIUDP"));
					oETercero.setCodigoZonaGeograficaPostal(objResultSet.getInt("ZONGEP"));
					oETercero.setTelefono1(UFuncionesGenerales.revisaCadena(objResultSet.getString("TELEFO")));
					oETercero.setTelefono2(UFuncionesGenerales.revisaCadena(objResultSet.getString("TELEFB")));
					oETercero.setAnexo(UFuncionesGenerales.revisaCadena(objResultSet.getString("TELANE")));
					oETercero.setFax(UFuncionesGenerales.revisaCadena(objResultSet.getString("FAX")));
					oETercero.setCodigoCIIU(objResultSet.getString("CODCIU"));
					oETercero.setCodigoTipoPersona(UFuncionesGenerales.revisaCadena(objResultSet.getString("CLAPER")));
					oETercero.setCodigoComunidad(UFuncionesGenerales.revisaCadena(objResultSet.getString("COMUNI")));
					oETercero.setCodigoBanca(UFuncionesGenerales.revisaCadena(objResultSet.getString("BANCA")));
					oETercero.setTipoProveedor(objResultSet.getInt("PROVEE"));
					oETercero.setTipoAceptante(objResultSet.getInt("ACEPTA"));
				}
				objConexion.cierraConsulta(objResultSet);
			}
		} catch (Exception objEx) {
			UManejadorLog.error("Acceso: Problemas al obtener.", objEx);
		}
		return oETercero;
	}
	
	public ECliente buscarNoSocio(int codigoCliente) {
		List<Object> lstParametrosEntrada = null;
		ResultSet objResultSet = null;
		ECliente oECliente = null;

		try {
			lstParametrosEntrada = new ArrayList<Object>();
			lstParametrosEntrada.add(codigoCliente);
			objResultSet = objConexion.ejecutaConsulta(SP_ABACOINLEGAL_BUS_NOSOCIO, lstParametrosEntrada, null);
			if (objResultSet != null) {
				if (objResultSet.next()) {
					oECliente = new ECliente();
					oECliente.setCodigoCliente(objResultSet.getInt("CODCLI"));
					oECliente.setCodigoTipoDocumento(UFuncionesGenerales.revisaCadena(objResultSet.getString("TIPDOC")));
					oECliente.setDocumento(UFuncionesGenerales.revisaCadena(objResultSet.getString("DOCUME")));
					oECliente.setRuc(UFuncionesGenerales.revisaCadena(objResultSet.getString("NRORUC")));
					//oECliente.setApellidoPaterno(UFuncionesGenerales.revisaCadena(objResultSet.getString("APEPAT")));
					//oECliente.setApellidoMaterno(UFuncionesGenerales.revisaCadena(objResultSet.getString("APEMAT")));
					//oECliente.setNombre(UFuncionesGenerales.revisaCadena(objResultSet.getString("NOMBRS")));
					oECliente.setNombreCorto(UFuncionesGenerales.revisaCadena(objResultSet.getString("NOMABV")));
					oECliente.setNombreLargo(UFuncionesGenerales.revisaCadena(objResultSet.getString("NOMBCL")));
					//oECliente.setNombreLargo2(UFuncionesGenerales.revisaCadena(objResultSet.getString("NOMBCS")));
					oECliente.setDireccion(UFuncionesGenerales.revisaCadena(objResultSet.getString("DIRECC")));
					oECliente.setCodigoUbigeo(objResultSet.getInt("CCIUDA"));
					oECliente.setTelefono(UFuncionesGenerales.revisaCadena(objResultSet.getString("TELEFO")));
					oECliente.setTelefono2(UFuncionesGenerales.revisaCadena(objResultSet.getString("TELEFB")));
					oECliente.setAnexo(objResultSet.getInt("TELANE"));
					oECliente.setFax(UFuncionesGenerales.revisaCadena(objResultSet.getString("FAX")));
					oECliente.setCodigoTipoPersona(UFuncionesGenerales.revisaCadena(objResultSet.getString("CLAPER")));
					oECliente.setDireccion2(UFuncionesGenerales.revisaCadena(objResultSet.getString("DIRECP")));
					oECliente.setCodigoUbigeo2(objResultSet.getInt("CCIUDP"));
					/*
					oECliente.setCodigoTipoDocumentoConyugue(UFuncionesGenerales.revisaCadena(objResultSet.getString("TDOCON")));
					oECliente.setDocumentoConyugue(UFuncionesGenerales.revisaCadena(objResultSet.getString("DOCCON")));
					oECliente.setApellidoPaternoConyugue(UFuncionesGenerales.revisaCadena(objResultSet.getString("APEPCY")));
					oECliente.setApellidoMaternoConyugue(UFuncionesGenerales.revisaCadena(objResultSet.getString("APEMCY")));
					oECliente.setNombreConyugue(UFuncionesGenerales.revisaCadena(objResultSet.getString("NOMBCY")));
					oECliente.setNombreSuperLargoConyugue(UFuncionesGenerales.revisaCadena(objResultSet.getString("NOMLCY")));
					*/
				}
				objConexion.cierraConsulta(objResultSet);
			}
		} catch (Exception objEx) {
			UManejadorLog.error("Acceso: Problemas al obtener.", objEx);
		}
		return oECliente;
	}
	
	
	public ETercero buscarTerceroAnexo(long codigoCliente) {
		List<Object> lstParametrosEntrada = null;
		ResultSet objResultSet = null;
		ETercero oETercero = null;

		try {
			lstParametrosEntrada = new ArrayList<Object>();
			lstParametrosEntrada.add(codigoCliente);
			objResultSet = objConexion.ejecutaConsulta(SP_ABACOINLEGAL_BUS_TERCERO_ANEXO, lstParametrosEntrada, null);
			if (objResultSet != null) {
				if (objResultSet.next()) {
					oETercero = new ETercero();
					oETercero.setCodigoCliente(objResultSet.getInt("CODCLI"));
					oETercero.setCodigoTipoDocumento(UFuncionesGenerales.revisaCadena(objResultSet.getString("TIPDOC")));
					oETercero.setDocumento(UFuncionesGenerales.revisaCadena(objResultSet.getString("DOCUME")));
					oETercero.setCodigoTipoPersona(UFuncionesGenerales.revisaCadena(objResultSet.getString("CLAPER")));
					oETercero.setApellidoPaterno(UFuncionesGenerales.revisaCadena(objResultSet.getString("APEPAT")));
					oETercero.setApellidoMaterno(UFuncionesGenerales.revisaCadena(objResultSet.getString("APEMAT")));
					oETercero.setNombres(UFuncionesGenerales.revisaCadena(objResultSet.getString("NOMBRS")));
					oETercero.setNombreLargo(UFuncionesGenerales.revisaCadena(objResultSet.getString("NOMBCL")));
					oETercero.setNombreSuperLargo(UFuncionesGenerales.revisaCadena(objResultSet.getString("NOMBCS")));
				}
				objConexion.cierraConsulta(objResultSet);
			}
		} catch (Exception objEx) {
			UManejadorLog.error("Acceso: Problemas al obtener.", objEx);
		}
		return oETercero;
	}
	
	
	public ETercero buscarCliente_Info_PersonaNatural(long codigoCliente) {
		List<Object> lstParametrosEntrada = null;
		ResultSet objResultSet = null;
		ETercero oETercero = null;

		try {
			lstParametrosEntrada = new ArrayList<Object>();
			lstParametrosEntrada.add(codigoCliente);
			objResultSet = objConexion.ejecutaConsulta(SP_ABACOINLEGAL_BUS_CLIENTE_INFO_PERSONA_NATURAL, lstParametrosEntrada, null);
			if (objResultSet != null) {
				if (objResultSet.next()) {
					oETercero = new ETercero();
					oETercero.setCodigoCliente(objResultSet.getInt("CODCLI"));
					oETercero.setCodigoEstadoCivil(objResultSet.getString("ESTCIV"));
					oETercero.setCodigoSexo(objResultSet.getString("SEXO"));
				}
				objConexion.cierraConsulta(objResultSet);
			}
		} catch (Exception objEx) {
			UManejadorLog.error("Acceso: Problemas al obtener.", objEx);
		}
		return oETercero;
	}
	
	public EClienteConstitucionEmpresa buscarConstitucionEmpresa(long codigoCliente) {
		List<Object> lstParametrosEntrada = null;
		ResultSet objResultSet = null;
		EClienteConstitucionEmpresa oEClienteConstitucionEmpresa = null;

		try {
			lstParametrosEntrada = new ArrayList<Object>();
			lstParametrosEntrada.add(codigoCliente);
			objResultSet = objConexion.ejecutaConsulta(SP_ABACOINLEGAL_BUS_SOCIO_CONSTITUCIONEMPRESA, lstParametrosEntrada, null);
			if (objResultSet != null) {
				if (objResultSet.next()) {
					oEClienteConstitucionEmpresa = new EClienteConstitucionEmpresa();
					oEClienteConstitucionEmpresa.setCodigoCliente(objResultSet.getInt("CODCLI"));
					oEClienteConstitucionEmpresa.setCodigoTipoPersonaJuridica(objResultSet.getInt("NATJUR"));
					oEClienteConstitucionEmpresa.setInscripcionRegistroPublico(UFuncionesGenerales.revisaCadena(objResultSet.getString("INREPU")));
					oEClienteConstitucionEmpresa.setFechaConstitucion(objResultSet.getDate("FECCIA"));
					oEClienteConstitucionEmpresa.setCodigoNotario(objResultSet.getInt("CODNOT"));
					oEClienteConstitucionEmpresa.setDescripcionNotario(UFuncionesGenerales.revisaCadena(objResultSet.getString("NOMNOT")));
					oEClienteConstitucionEmpresa.setOficinaRegistral(UFuncionesGenerales.revisaCadena(objResultSet.getString("OFIREG")));
					oEClienteConstitucionEmpresa.setNumeroAcciones(objResultSet.getInt("ACCION"));
				}
				objConexion.cierraConsulta(objResultSet);
			}
		} catch (Exception objEx) {
			UManejadorLog.error("Acceso: Problemas al obtener.", objEx);
		}
		return oEClienteConstitucionEmpresa;
	}
	
	public EClienteAdicional buscarClienteAdicional(long codigoCliente, int codigoTipoCliente){
		List<Object> lstParametrosEntrada = null;
		ResultSet oResultSet = null;
		EClienteAdicional oEClienteAdicional = null;

		try {
			lstParametrosEntrada = new ArrayList<Object>();
			lstParametrosEntrada.add(codigoCliente);
			lstParametrosEntrada.add(codigoTipoCliente);
			oResultSet = objConexion.ejecutaConsulta(SP_ABACOINLEGAL_BUS_CLIENTE_ADICIONAL, lstParametrosEntrada, null);
			if (oResultSet != null) {
				if (oResultSet.next()) {
					oEClienteAdicional = new EClienteAdicional();
					oEClienteAdicional.setCodigoCliente(oResultSet.getInt("CODCLI"));
					oEClienteAdicional.setCodigoTipoCliente(oResultSet.getInt("TIPCLI"));
					
					oEClienteAdicional.setMontoCapitalSocialRegistroPublicos(oResultSet.getDouble("CAPSRP"));
					oEClienteAdicional.setMontoCapitalSocialActual(oResultSet.getDouble("CAPSAC"));
					oEClienteAdicional.setCodigoFacultadOperar(UFuncionesGenerales.revisaCadena(oResultSet.getString("FACUOP")));
					oEClienteAdicional.setCodigoTipoSuscripcionPago(UFuncionesGenerales.revisaCadena(oResultSet.getString("TIPSPG")));
					oEClienteAdicional.setNumeroSuscripcionPago(oResultSet.getInt("SUSPAG"));
					oEClienteAdicional.setNumeroAcciones(oResultSet.getInt("NROACC"));
					oEClienteAdicional.setIndicadorAvalarTercero(UFuncionesGenerales.revisaCadena(oResultSet.getString("AVATER")));
					oEClienteAdicional.setIndicadorGrabarBien(UFuncionesGenerales.revisaCadena(oResultSet.getString("GRABIE")));
					oEClienteAdicional.setDescripcionAvalarTercero(UFuncionesGenerales.revisaCadena(oResultSet.getString("AVATFI")));
					oEClienteAdicional.setObservacionAvalarTercero(UFuncionesGenerales.revisaCadena(oResultSet.getString("OB1AVT"))+"\n"+
							  UFuncionesGenerales.revisaCadena(oResultSet.getString("OB2AVT"))+"\n"+
							  UFuncionesGenerales.revisaCadena(oResultSet.getString("OB3AVT")));
					oEClienteAdicional.setObservacionGrabarBien(UFuncionesGenerales.revisaCadena(oResultSet.getString("OB1GRA"))+"\n"+
							   UFuncionesGenerales.revisaCadena(oResultSet.getString("OB2GRA"))+"\n"+
							   UFuncionesGenerales.revisaCadena(oResultSet.getString("OB3GRA")));
					oEClienteAdicional.setObservacionConstanciaSocial(UFuncionesGenerales.revisaCadena(oResultSet.getString("DAT1PJ"))+"\n"+
									 UFuncionesGenerales.revisaCadena(oResultSet.getString("DAT2PJ"))+"\n"+
									 UFuncionesGenerales.revisaCadena(oResultSet.getString("DAT3PJ"))+"\n"+
									 UFuncionesGenerales.revisaCadena(oResultSet.getString("DAT4PJ"))+"\n"+
									 UFuncionesGenerales.revisaCadena(oResultSet.getString("DAT5PJ")));
					
					oEClienteAdicional.setCodigoTipoDuracionPartida(oResultSet.getInt("CODTPART"));
					oEClienteAdicional.setRegistroPartida(UFuncionesGenerales.revisaCadena(oResultSet.getString("REGPART")));
					oEClienteAdicional.setDescripcionConstitucion(UFuncionesGenerales.revisaCadena(oResultSet.getString("DESCCONS")));
					oEClienteAdicional.setCodigoTipoValorSuscripcion(oResultSet.getInt("CODTVAL"));
					oEClienteAdicional.setMontoValorNominal(oResultSet.getDouble("MTOVALN"));
					oEClienteAdicional.setEstatuto(UFuncionesGenerales.revisaCadena(oResultSet.getString("ESTATUTO")));
					oEClienteAdicional.setDescripcionPatrimonio(UFuncionesGenerales.revisaCadena(oResultSet.getString("DESCPAT")));
					oEClienteAdicional.setCodigoTipoNumeracionEstatuto(oResultSet.getInt("CODTNETT"));
					oEClienteAdicional.setNumeracionEstatuto(oResultSet.getInt("NUMETT"));
					oEClienteAdicional.setAsiento(UFuncionesGenerales.revisaCadena(oResultSet.getString("ASIENTO")));
					oEClienteAdicional.setFechaPeriodoInicio(UFuncionesGenerales.revisaFecha(oResultSet.getDate("FECPINI")));
					oEClienteAdicional.setFechaPeriodoVencimiento(UFuncionesGenerales.revisaFecha(oResultSet.getDate("FECPVEN")));
				}
				objConexion.cierraConsulta(oResultSet);
			}
		} catch (Exception objEx) {
			UManejadorLog.error("Acceso: Problemas al obtener.", objEx);
		}
		return oEClienteAdicional;
	}
}
