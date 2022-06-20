package com.example.appsecurityfirst.config;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * @EnableWebSecurity   xafsizlik jihatidan
 * @Configuration       ham bean qilib  beradi class ichida bean yaratib ha beradi
 * @EnableGlobalMethodSecurity(prePostEnabled = true) PreAuthorize ishlatish uchun CONTROLLIRDAGI
 */
@EnableWebSecurity
@Configuration
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

    /**
     *  BUNI YOZGANIMIZDAN KEYIN APPLICATION.PROPERTIES NI ICHIDAGI PASSWORD AND NAME DELETE QILISH KK
     *  <br>
     *   CHUNKI IN MEMORY AUTHENTICATION DAN FOYDALANIDK
     */

    // 3. ish
    @Override // userlarni vaqtincha saqlap turish va sozlash uchun
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {

        // 4.ish
        // 1-usul Roles   LAVOZIM BOYICHA  // DAVOMI CONTROLLERDA
//        auth
//                .inMemoryAuthentication()//SHU VAQTINCHALIK SAQLAYDI
//                .withUser("director").password(passwordEncoder().encode("director")).roles("DIRECTOR")
//                .and()
//                .withUser("manager").password(passwordEncoder().encode("manager")).roles("MANAGER")
//                .and()
//                .withUser("user").password(passwordEncoder().encode("user")).roles("USER");


        // 2-usul Privileges  HUQUQLAR BOYICHA //// DAVOMI CONTROLLERDA
        auth
                .inMemoryAuthentication()//SHU VAQTINCHALIK SAQLAYDI
                .withUser("director1").password(passwordEncoder().encode("director1")).roles("DIRECTOR").authorities("READ_ALL_PRODUCT","ADD_PRODUCT","EDIT_PRODUCT","DELETE_PRODUCT","READ_ONE_PRODUCT")
                .and()
                .withUser("director2").password(passwordEncoder().encode("director2")).roles("DIRECTOR").authorities("READ_ALL_PRODUCT","ADD_PRODUCT","EDIT_PRODUCT","READ_ONE_PRODUCT")
                .and()
                .withUser("manager").password(passwordEncoder().encode("manager")).roles("MANAGER").authorities("READ_ALL_PRODUCT","ADD_PRODUCT","EDIT_PRODUCT")
                .and()
                .withUser("user").password(passwordEncoder().encode("user")).roles("USER").authorities("READ_ALL_PRODUCT");


    }

    @Override // 1. ish
    protected void configure(HttpSecurity http) throws Exception {

        // 2. ish
        http
                .csrf().disable()      //
                .authorizeRequests()   // Requestga ruxsat ber
                .anyRequest()          // Har qanday requestga ruhsat ber
                .authenticated()       // Authentifikatsiya qil ya'ni tekshirib keyin otqiz
                .and()                 //
                .httpBasic();          // httpBasic qilasan.  httpBasic ga otasan  postmenda ishlash uchun
                                       // bularni yozmasak formBasic BILAN KIRSA BOLARDI
                                       // DAVOMI CONTROLLERDA LAVOZIMLARNI YOZISH



    }

    /**
     * @Bean qilishimiz kk unda ishlamaydi <br>
     * bean qilishimiz uchun configuration ni ichida bolishi kk
     * @return passwordni korinmas kodlab beradi chunki u korinmasligi kk <br>
     * returnda enoding qilib beradi
     */
    // 5. ish
    @Bean
    PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();

    }
}
