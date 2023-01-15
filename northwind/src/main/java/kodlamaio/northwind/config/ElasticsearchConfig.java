package kodlamaio.northwind.config;
//
//import org.elasticsearch.client.RestHighLevelClient;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.data.elasticsearch.client.ClientConfiguration;
//import org.springframework.data.elasticsearch.client.RestClients;
//import org.springframework.data.elasticsearch.config.AbstractElasticsearchConfiguration;
//import org.springframework.data.elasticsearch.repository.config.EnableElasticsearchRepositories;
//
//@Configuration
//@EnableElasticsearchRepositories(basePackages = "tr.gov.tubitak.stt.controller")
//public class ElasticsearchConfig extends AbstractElasticsearchConfiguration {
//
//    @Value("${service.elasticsearch.host-port}")
//    private String ELASTICSEARCH_HOST_PORT;
//
//    @Bean
//    public RestHighLevelClient elasticsearchClient() {
//        final ClientConfiguration clientConfiguration =
//                ClientConfiguration.builder().connectedTo(ELASTICSEARCH_HOST_PORT).build();
//        return RestClients.create(clientConfiguration).rest();
//    }
//}
