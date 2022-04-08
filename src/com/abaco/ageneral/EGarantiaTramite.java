package com.abaco.ageneral;

import java.util.Date;

import lombok.Data;

import com.abaco.entidad.EUsuario;
import com.abaco.negocio.util.UFuncionesGenerales;
import com.abaco.negocio.util.UConstante.UTipoGarantia;

public @Data class EGarantiaTramite {
	/* Correspondiente a la tabla F9212 */
	private int nroAsiento;
	private int codigoServicio;
	private long codigoGarantia;
	private int codigoMoneda;
	private int codigoEtapaTramite;
	private int numeroHojaIngresoLegal;
	
	private Date fechaIngreso;
	private int evaluacionDocumento;
	private Date fechaElaboracionContrato;
	private String usuarioElaboracionContrato;
	private Date fechaFirmaContrato;
	private Date fechaIngresoNotaria;
	private Date fechaLegalizacionFirma;
	private int numeroKardex;
	private int codigoNotario;
	private Date fechaIngresoRegistro;
	private Date fechaVigenciaAsiento;
	private String observacion1;
	private String observacion2;
	private Date fechaObservacion;
	private Date fechaTacha;
	private Date fechaInscripcion;
	private String fichaInscripcion;
	private String tomoInscripcion;
	private String ciudadInscripcion;
	
	private String descripcionObservacionBloqueo1;
	private String descripcionObservacionBloqueo2;
	private Date fechaIngresoRegistroB;
	private Date fechaVigenciaAsientoB;
	private Date fechaObservacionB;
	private Date fechaTachaB;
	private Date fechaInscripcionB;
	private String fichaInscripcionB;
	private String tomoInscripcionB;
	private String ciudadInscripcionB;

	//Adicionl
	private String descripcionNotario;
	private Date fechaRegistro;
	private String horaRegistro;
	private EUsuario usuarioRegistro;
	private int tipoGarantia;
	private int secuenciaHistorica;
	private String tituloA;
	private String tituloB;
	
	
	//Para Historico
	private boolean validarNumeroHojaIngresoLegal;
	private boolean validarFechaIngreso;
	private boolean validarEvaluacionDocumento;
	private boolean validarFechaElaboracionContrato;
	private boolean validarUsuarioElaboracionContrato;
	private boolean validarFechaFirmaContrato;
	private boolean validarFechaIngresoNotaria;
	private boolean validarFechaLegalizacionFirma;
	private boolean validarNumeroKardex;
	private boolean validarCodigoNotario;
	private boolean validarFechaIngresoRegistro;
	private boolean validarFechaVigenciaAsiento;
	private boolean validarObservacion1;
	private boolean validarObservacion2;
	private boolean validarFechaObservacion;
	private boolean validarFechaTacha;
	private boolean validarFechaInscripcion;
	private boolean validarFichaInscripcion;
	private boolean validarTomoInscripcion;
	private boolean validarCiudadInscripcion;
	private boolean validarDescripcionObservacionBloqueo1;
	private boolean validarDescripcionObservacionBloqueo2;
	private boolean validarFechaIngresoRegistroB;
	private boolean validarFechaVigenciaAsientoB;
	private boolean validarFechaObservacionB;
	private boolean validarFechaTachaB;
	private boolean validarFechaInscripcionB;
	private boolean validarFichaInscripcionB;
	private boolean validarTomoInscripcionB;
	private boolean validarCiudadInscripcionB;
	private boolean validarTituloA;
	private boolean validarTituloB;
	
	public boolean validarCampos(int tipoGarantia){
		if(tipoGarantia == UTipoGarantia.PREDIO){
			if(fechaIngreso == null && fechaElaboracionContrato == null && evaluacionDocumento == 0 && fechaFirmaContrato == null &&
			   fechaIngresoNotaria == null && codigoNotario== 0 && numeroKardex == 0 && fechaIngresoRegistro == null && fechaObservacion==null &&
			   fechaTacha== null && fechaInscripcion== null && fechaIngresoRegistroB == null && fechaObservacionB==null && fechaTachaB==null && fechaInscripcionB==null &&
			   !UFuncionesGenerales.convierteCadenaABoolean(usuarioElaboracionContrato) && !UFuncionesGenerales.convierteCadenaABoolean(descripcionNotario) && 
			   !UFuncionesGenerales.convierteCadenaABoolean(observacion1) && !UFuncionesGenerales.convierteCadenaABoolean(observacion2) && 
			   !UFuncionesGenerales.convierteCadenaABoolean(fichaInscripcion) && !UFuncionesGenerales.convierteCadenaABoolean(tomoInscripcion) && 
			   !UFuncionesGenerales.convierteCadenaABoolean(tituloA) && !UFuncionesGenerales.convierteCadenaABoolean(ciudadInscripcion) &&
			   !UFuncionesGenerales.convierteCadenaABoolean(descripcionObservacionBloqueo1) && !UFuncionesGenerales.convierteCadenaABoolean(descripcionObservacionBloqueo2) && 
			   !UFuncionesGenerales.convierteCadenaABoolean(fichaInscripcionB) && !UFuncionesGenerales.convierteCadenaABoolean(tomoInscripcionB) && 
			   !UFuncionesGenerales.convierteCadenaABoolean(tituloB) && !UFuncionesGenerales.convierteCadenaABoolean(ciudadInscripcionB) ){
				return false;
			}else{
				return true;
			}
			
		}else{
			if(fechaIngreso == null && fechaElaboracionContrato == null && evaluacionDocumento == 0 && fechaFirmaContrato == null &&
			   fechaIngresoNotaria == null && codigoNotario== 0 && numeroKardex == 0 && fechaIngresoRegistro == null && fechaObservacion==null &&
			   fechaTacha== null && fechaInscripcion== null &&
			   !UFuncionesGenerales.convierteCadenaABoolean(usuarioElaboracionContrato) && !UFuncionesGenerales.convierteCadenaABoolean(descripcionNotario) && 
			   !UFuncionesGenerales.convierteCadenaABoolean(observacion1) && !UFuncionesGenerales.convierteCadenaABoolean(observacion2) && 
			   !UFuncionesGenerales.convierteCadenaABoolean(fichaInscripcion) && !UFuncionesGenerales.convierteCadenaABoolean(tomoInscripcion) && 
			   !UFuncionesGenerales.convierteCadenaABoolean(tituloA) && !UFuncionesGenerales.convierteCadenaABoolean(ciudadInscripcion)){
				return false;
			}else{
				return true;
			}
		}
		
	}
	

}
