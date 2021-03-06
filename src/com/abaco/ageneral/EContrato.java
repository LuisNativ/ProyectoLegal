package com.abaco.ageneral;

import java.util.Date;
import java.util.List;

import lombok.Data;

import com.abaco.entidad.EUsuario;

public @Data class EContrato {
	
	/*Data de F7401*/
	private long numeroContrato;
	private int codigoMonedaPagare;
	private int codigoCliente;
	private int codigoAsociado;
	private String firmaConyugue; // SI / NO
	private String nombreSocioLargo; 
	private String nombreSocioCorto;
	private String codigoTipoContrato;
	private int codigoCompania;
	private long codigoRepresentante;
	private int codigoServicio;
	private int numeroOperacion;
	private int numeroLista;
	private double importeAprobado;
	private double importeAsignado;
	private double importeRecuperado;
	private double importeGarantizado;
	private double tasaInteresGenerica;
	private double tasaInteresMostrada;
	private int codigoPeriodo;
	private int plazoDias;
	private Date fechaRegistroContrato;
	private String horaRegistroContrato;
	private String usuarioRegistroContrato;
	private Date fechaEmisor;
	private String horaEmisor;
	private String usuarioEmisor;
	private int codigoFirmado;  // SI/NO
	private Date fechaFirmado;
	private String horaFirmado;
	private String usuarioFirmado;
	private Date fechaUltModificacion;
	private String horaUltModificacion;
	private String usuarioUltModificacion;
	private int ubicacionDocumento;
	private Date fechaUbicacion;
	private String horaUbicacion;
	private String usuarioUbicacion;
	private int codigoAsignadoDeudas;
	private Date fechaUltAsignacion;
	private String horaUltAsignacion;
	private String usuarioUltAsignacion;
	private int codigoEstadoDocumento;
	private Date fechaEstadoDocumento;
	private String horaEstadoDocumento;
	private String usuarioEstadoDocumento;
	private int codigoEstadoAnterior;
	private Date fechaEstadoAnterior;
	private String horaEstadoAnterior;
	private String usuarioEstadoAnterior;
	private int numeroPagare;
	private int ultimaClausulaAdicional;
	private int correlativoClausula;
	private int indicadorArchivoLogico;
	private EUsuario usuarioRegistro;
	
	//Adicional
	private long codigoSolicitud;
	private int tipoPlantilla;
	private int monedaPlantilla;
	private int tipovariante;
	private int indicadorConsulta;

	

}
