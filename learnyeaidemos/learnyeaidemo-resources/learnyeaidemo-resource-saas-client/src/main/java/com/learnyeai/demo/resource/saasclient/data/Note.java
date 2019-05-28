package com.learnyeai.demo.resource.saasclient.data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import org.hibernate.annotations.GenericGenerator;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.learnyeai.demo.resource.saasclient.details.NoteDetails;

import cn.jovanyframework.cloud.saasclient.resource.ScenePrincipal;

@Entity
public class Note implements NoteDetails {

	@Id
	@GeneratedValue(generator="jpa-uuid") 
	@GenericGenerator(name = "jpa-uuid", strategy = "uuid")
	@Column(length = 32)
	private String id;

	private String namespace;
	private String target;

	@Column(length = 200)
	private String content;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	@JsonIgnore
	public String getNamespace() {
		return namespace;
	}

	public void setNamespace(String namespace) {
		this.namespace = namespace;
	}

	@JsonIgnore
	public String getTarget() {
		return target;
	}

	public ScenePrincipal getScene() {
		return new ScenePrincipal(namespace, target);
	}

	public void setTarget(String target) {
		this.target = target;
	}

}
