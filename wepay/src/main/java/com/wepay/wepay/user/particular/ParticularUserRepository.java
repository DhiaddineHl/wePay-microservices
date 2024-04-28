package com.wepay.wepay.user.particular;

import com.wepay.wepay.user.particular.ParticularUser;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ParticularUserRepository extends JpaRepository<ParticularUser, Integer> {


}
