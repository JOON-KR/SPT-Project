package com.sptp.dawnary.member.domain;

import java.util.HashSet;
import java.util.Set;

import com.sptp.dawnary.follow.domain.Follow;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "MEMBER")
@Setter
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Member {

	@Id
	@GeneratedValue
	@Column(name = "member_id")
	private Long id;

	@Column(name = "email", length = 50, updatable = false, unique = true)
	private String email;

	@Column(name = "password", nullable = false)
	private String password;

	@Column(name = "name", nullable = false)
	private String name;

	@Column(name = "image_path")
	private String imagePath;

	@Enumerated(EnumType.STRING)
	@Column(name = "ROLE", nullable = false)
	private RoleType role;

	@OneToMany(mappedBy = "following")
	private Set<Follow> followers = new HashSet<>();

	@OneToMany(mappedBy = "follower")
	private Set<Follow> followings = new HashSet<>();

	public void updatePassword(String password) {
		this.password = password;
	}

	public void updateName(String name) {
		this.name = name;
	}

	public void updateImagePath(String imagePath) {
		this.imagePath = imagePath;
	}
}
