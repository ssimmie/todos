package net.ssimmie.todos.api;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;
import static org.springframework.http.HttpStatus.OK;
import static org.springframework.http.MediaType.APPLICATION_JSON_UTF8;

import java.net.URL;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = RANDOM_PORT)
public class TodosResourceIT {

  @LocalServerPort
  private int port;

  private URL todos;

  @Autowired
  private TestRestTemplate template;

  @Before
  public void setUp() throws Exception {
    this.todos = new URL("http://localhost:" + port + "/todos");
  }

  @Test
  public void getTodos() {
    ResponseEntity<String> response = template.getForEntity(todos.toString(),
        String.class);

    assertThat(response.getStatusCode()).isEqualTo(OK);
    assertThat(response.getHeaders().getContentType()).isEqualTo(APPLICATION_JSON_UTF8);
    assertThat(response.getBody()).isEqualTo("[{\"title\":\"Paint room\"},{\"title\":\"Order shelves\"}]");
  }
}
