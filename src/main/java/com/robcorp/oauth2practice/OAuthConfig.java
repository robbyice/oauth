package com.robcorp.oauth2practice;

class OAuthConfig {

}

//@Configuration
//@EnableResourceServer
//public class OAuthConfig {
//
//    @Bean
//    public ResourceServerConfigurerAdapter configureResourceServer() {
//        return new ResourceServerConfigurerAdapter() {
//            @Override
//            public void configure(ResourceServerSecurityConfigurer resources) throws Exception {
//                super.configure(resources);
//                resources.resourceId("resourceId");
//                resources.tokenExtractor(new BearerTokenExtractor());
//            }
//
//            @Override
//            public void configure(HttpSecurity http) throws Exception {
//                super.configure(http);
//
////                http.csrf().disable();
//            }
//        };
//    }
//}
