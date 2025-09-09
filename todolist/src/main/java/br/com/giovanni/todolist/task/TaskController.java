package br.com.giovanni.todolist.task;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import br.com.giovanni.todolist.utils.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/tasks")

public class TaskController {

    @Autowired
    private ITaskRepository taskRepository;


    @PostMapping("/")
    public ResponseEntity create(@RequestBody TaskModel taskModel, HttpServletRequest request){
        var idUser=request.getAttribute("idUser");
        taskModel.setIdUser((UUID) idUser);


        var currentDate = LocalDateTime.now();
        //01/09/2025-atual
        //05/09/2025-inicio
        if (currentDate.isAfter(taskModel.getStartAt()) || currentDate.isAfter(taskModel.getEndAt())){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("A data de inicio/ data de termino deve ser maior que a data atual");
        }
        if (taskModel.getStartAt().isAfter(taskModel.getEndAt())){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("A data de inicio deve ser menos que a data de termino");
        }

        var task= this.taskRepository.save(taskModel);
        return  ResponseEntity.status(HttpStatus.OK).body(task);

    }

    @GetMapping("/")
    public List<TaskModel> list(HttpServletRequest request){
        var idUser=request.getAttribute("idUser");

        var tasks =this.taskRepository.findByIdUser((UUID) idUser);
        return tasks;

    }

    //http:localhost/tasks/
    @PutMapping("/{id}")
    public ResponseEntity update(@RequestBody TaskModel taskModel, @PathVariable UUID id,HttpServletRequest request) {

        var task= this.taskRepository.findById(id).orElse(null);

        if (task==null){
            return  ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Tarefa nao encontrada ");

        }

        var idUser=request.getAttribute("idUser");
        if(!task.getIdUser().equals(idUser)){
            return  ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Usuario sem permissao");

        }

        Utils.copyNonNullProperties(taskModel,task);

        var taskUpdate= this.taskRepository.save(task);
        return ResponseEntity.ok().body(this.taskRepository.save(taskUpdate));


    }
}
