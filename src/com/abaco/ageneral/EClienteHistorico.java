package com.abaco.ageneral;

import java.util.Date;
import java.util.List;

import com.abaco.entidad.EUsuario;

import lombok.Data;

public @Data class EClienteHistorico {
	private long numeroSolicitud;
	private int codigoTipoCliente;
	private int codigoCliente;
	
	//Datos Persona
	private String codigoTipoDocumento;
	private String numeroDocumento;
	private String ruc;
	private String apellidoPaterno;
	private String apellidoMaterno;
	private String nombre;
	private String nombreLargo;
	
	//Datos Direcciones
	private int codigoUbigeoReal;
	private String direccionReal;
	private int codigoUbigeoContractual;
	private String direccionContractual;
	
	//Datos Conyugue
	private String codigoTipoDocumentoConyugue;
	private String documentoConyugue;
	private String apellidoPaternoConyugue;
	private String apellidoMaternoConyugue;
	private String nombreConyugue;
	private String nombreLargoConyuge;
	
	//Datos PJ
	private String codigoTipoPersonaJuridica;
	private double montoCapitalSocialRegistroPublicos;
	private double montoCapitalSocialActual;
	private String codigoFacultadOperar;
	private String codigoTipoSuscripcionPago;
	private int numeroSuscripcionPago;
	private int numeroAccionistas;
	private String indicadorAvalarTercero;
	private String indicadorGrabarBien;
	private String descripcionAvalarTercero;
	private String observacionAvalarTercero;
	private String observacionGrabarBien;
	private String observacionConstanciaSocial;
	
	private String codigoEstadoCivil;
	private String oficinaRegistral;
	private String numeroPartida;
	private Date fechaConstitucion;
	private int codigoNotario;
	private String descripcionNotario;
	private int numeroAcciones;
	
	private int codigoTipoDuracionPartida;
	private String registroPartida;
	private String descripcionConstitucion;
	private int codigoTipoValorSuscripcion;
	private double montoValorNominal;
	private String descripcionAporte;
	private String estatuto;
	private String descripcionPatrimonio;
	private int codigoTipoNumeracionEstatuto;
	private int numeracionEstatuto;
	private String asiento;
	private Date fechaPeriodoInicio;
	private Date fechaPeriodoVencimiento;
	
	private Date fechaRegistro;
	private String horaRegistro;
	private String usuarioRegistro;
	//private EUsuario usuarioRegistro;
	
	//Datos Direcciones Historico
	private int codigoUbigeoRealH;
	private int codigoUbigeoContractualH;
	
	//Para Historico
	private boolean validarCodigoTipoDocumento;
	private boolean validarNumeroDocumento;
	private boolean validarRuc;
	private boolean validarApellidoPaterno;
	private boolean validarApellidoMaterno;
	private boolean validarNombre;
	private boolean validarNombreLargo;
	private boolean validarCodigoUbigeoReal;
	private boolean validarDireccionReal;
	private boolean validarCodigoUbigeoContractual;
	private boolean validarDireccionContractual;
	private boolean validarCodigoTipoDocumentoConyugue;
	private boolean validarDocumentoConyugue;
	private boolean validarApellidoPaternoConyugue;
	private boolean validarApellidoMaternoConyugue;
	private boolean validarNombreConyugue;
	private boolean validarNombreLargoConyuge;
	private boolean validarCodigoTipoPersonaJuridica;
	private boolean validarMontoCapitalSocialRegistroPublicos;
	private boolean validarMontoCapitalSocialActual;
	private boolean validarCodigoFacultadOperar;
	private boolean validarCodigoTipoSuscripcionPago;
	private boolean validarNumeroSuscripcionPago;
	private boolean validarNumeroAccionistas;
	private boolean validarIndicadorAvalarTercero;
	private boolean validarIndicadorGrabarBien;
	private boolean validarDescripcionAvalarTercero;
	private boolean validarObservacionAvalarTercero;
	private boolean validarObservacionGrabarBien;
	private boolean validarObservacionConstanciaSocial;
	private boolean validarCodigoEstadoCivil;
	private boolean validarOficinaRegistral;
	private boolean validarNumeroPartida;
	private boolean validarFechaConstitucion;
	private boolean validarCodigoNotario;
	private boolean validarDescripcionNotario;
	private boolean validarNumeroAcciones;
	private boolean validarCodigoTipoDuracionPartida;
	private boolean validarRegistroPartida;
	private boolean validarDescripcionConstitucion;
	private boolean validarCodigoTipoValorSuscripcion;
	private boolean validarMontoValorNominal;
	private boolean validarDescripcionAporte;
	private boolean validarEstatuto;
	private boolean validarDescripcionPatrimonio;
	private boolean validarCodigoTipoNumeracionEstatuto;
	private boolean validarNumeracionEstatuto;
	private boolean validarAsiento;
	private boolean validarFechaPeriodoInicio;
	private boolean validarFechaPeriodoVencimiento;
}
