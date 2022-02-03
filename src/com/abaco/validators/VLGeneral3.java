package com.abaco.validators;

import java.io.Serializable;

import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.component.UIComponent;
import javax.faces.component.UIInput;
import javax.faces.context.FacesContext;
import javax.faces.validator.ValidatorException;
import javax.inject.Inject;
import javax.inject.Named;

import com.abaco.ageneral.MBRegistroOperacionSolicitudCredito;
import com.abaco.negocio.util.UFuncionesGenerales;

@ManagedBean(name = "vlgeneral3")
@Named
@RequestScoped
public class VLGeneral3 implements Serializable{
	
	public void validarDocumento(FacesContext context, UIComponent component, Object value)
		throws ValidatorException{
		String texto = (String)value;
		String mensajeRequerido = ((UIInput)component).getRequiredMessage();
		
		if(texto != null){
	        if(mensajeRequerido.equals("validaRuc")){ //Valida Tipo Documento Ruc
	        	if(!UFuncionesGenerales.validateRuc(texto)) {
		        	FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR,"Mensaje","Campo Inválido");
		        	throw new ValidatorException(msg);
	        	}
	        }else if(mensajeRequerido.equals("validaDni")){ //Valida Tipo Documento Dni y Libreta Electoral
	        	if(!UFuncionesGenerales.validateDni(texto)) {
		        	FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR,"Mensaje","Campo Inválido");
		        	throw new ValidatorException(msg);
	        	}
			}
		}
	}
}
