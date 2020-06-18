package br.ce.wcaquino.taskbackend.controller;

import java.time.LocalDate;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import br.ce.wcaquino.taskbackend.model.Task;
import br.ce.wcaquino.taskbackend.repo.TaskRepo;
import br.ce.wcaquino.taskbackend.utils.ValidationException;

public class TaskControllerTest {

	@Mock
	private TaskRepo taskRepo;	//cria todos os mocks
	
	@InjectMocks
	private TaskController controller;  //os mocks que estiverem criados nesse contexto, sejam injetados nessa classe (Faz a instancia do controller)
	
	@Before
	public void setup() {			//executado antes de cada teste
		MockitoAnnotations.initMocks(this);
	}
	
	@Test
	public void naoDeveSalvarTarefaSemDescricao(){
		Task todo = new Task();
		todo.setDueDate(LocalDate.now());
		try {
			controller.save(todo);   //controller vai salvar a tarefa
			Assert.fail("Não deveria chegar nesse ponto");
		} catch (ValidationException e) {
			Assert.assertEquals("Fill the task description", e.getMessage()); //verifica se a excessão lançada é realmente a esperada.
		}   
	}
	
	@Test
	public void naoDeveSalvarTarefaSemData() {
		Task todo = new Task();
		todo.setTask("Descricao");
		//todo.setDueDate(LocalDate.now());
		try {
			controller.save(todo);   //controller vai salvar a tarefa
			Assert.fail("Não deveria chegar nesse ponto");
		} catch (ValidationException e) {
			Assert.assertEquals("Fill the due date", e.getMessage()); //verifica se a excessão lançada é realmente a esperada.
		}   
	}
	
	@Test
	public void naoDeveSalvarTarefaComDataPassada() {
		Task todo = new Task();
		todo.setTask("Descricao");
		todo.setDueDate(LocalDate.of(2010, 01, 01)); 
		try {
			controller.save(todo);   //controller vai salvar a tarefa
			Assert.fail("Não deveria chegar nesse ponto");
		} catch (ValidationException e) {
			Assert.assertEquals("Due date must not be in past", e.getMessage()); //verifica se a excessão lançada é realmente a esperada.
		}   
	}
	
	@Test
	public void deveSalvarTarefaComSucesso() throws ValidationException {
		Task todo = new Task();
		todo.setTask("Descricao");
		todo.setDueDate(LocalDate.now());
		controller.save(todo);
		
		Mockito.verify(taskRepo).save(todo);   //pedi para o mockito verificar se o TaskRepo foi invocada no método salvar, enviando o método todo
	}
}
