package com.abaco.bo;

import java.util.List;

import com.abaco.entidad.EGeneralTipoCambio;
import com.abaco.entidad.EGeneralTipoCambioParametro;
import com.abaco.entidad.EUsuario;
import com.abaco.ageneral.EServicio;
import com.abaco.control.CGeneral;

public class BOGeneral {
	CGeneral oCGeneral = new CGeneral();
	
	public List<EUsuario> listarUsuarioPorAreaReasignacion(EUsuario eUsuario){
		List<EUsuario> resultado = oCGeneral.listarUsuarioPorAreaReasignacion(eUsuario);
		return resultado;
	}
	public List<EServicio> listarServicio(){
		List<EServicio> lista=oCGeneral.listarServicio();
		return lista;
	}
	public EGeneralTipoCambio buscarGeneralTipoCambio(EGeneralTipoCambioParametro objParam) {		
		EGeneralTipoCambio resultado = oCGeneral.buscarGeneralTipoCambio(objParam);
		return resultado;
	}
	public String buscarCorreoJefeInmediato(String usuario){	
		String resultado = oCGeneral.buscarCorreoJefeInmediato(usuario);
		return resultado;
	}
	public String buscarRutaWeb(long codigoItem){
		String resultado = oCGeneral.buscarRutaWeb(codigoItem);
		return resultado;
	}
	public String buscarCorreoUsuario(int codigoUsuario){	
		String resultado = oCGeneral.buscarCorreoUsuario(codigoUsuario);
		return resultado;
	}
	public String buscarNombreUsuarioSiaf(int codigoUsuario){	
		String resultado = oCGeneral.buscarNombreUsuarioSiaf(codigoUsuario);
		return resultado;
	}
	public int generarCorrelativo(int tabla, String codigo1, String codigo2, String codigo3){
		int resultado = oCGeneral.generarCorrelativo(tabla, codigo1, codigo2, codigo3);
		return resultado;
	}
}
