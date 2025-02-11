# Histórias de Usuário - Sistema de Matrículas

## História 1: Cadastro de usuário (Aluno ou Professor)
**Como um(a)** novo usuário do sistema  
**Eu quero** me cadastrar no sistema de matrículas com minhas informações pessoais  
**Para que** eu possa acessar e utilizar as funcionalidades do sistema.  

### Critérios de Aceitação:
- O usuário deve fornecer um nome, um e-mail e uma senha válida.
- O sistema deve permitir identificar se o usuário é aluno ou professor.
- Dados de login e senha devem ser validados antes do acesso.

---

## História 2: Login no sistema
**Como um** usuário autenticado  
**Eu quero** fazer login no sistema utilizando meu nome de usuário e senha  
**Para que** eu possa acessar minhas informações e realizar ações relacionadas ao meu perfil.  

### Critérios de Aceitação:
- O sistema deve validar a combinação de nome de usuário e senha.
- Em caso de falha de autenticação, uma mensagem de erro deve ser exibida.
- O acesso só será permitido se o usuário estiver previamente cadastrado.

---

## História 3: Realizar matrícula em disciplinas
**Como um** aluno  
**Eu quero** me matricular em até 4 disciplinas obrigatórias e 2 disciplinas optativas  
**Para que** eu possa organizar meu semestre acadêmico de acordo com as regras da universidade.  

### Critérios de Aceitação:
- O sistema deve listar todas as disciplinas disponíveis para matrícula.
- O aluno pode escolher até 4 disciplinas obrigatórias e 2 optativas.
- O sistema deve garantir que disciplinas com 60 alunos estejam fechadas para novas matrículas.
- O sistema deve cancelar a disciplina caso o número de alunos for inferior a 3.
- O aluno so podera realizar matricula durante o periodo para efetuar matriculas.

---

## História 4: Cancelar matrícula em disciplina
**Como um** aluno  
**Eu quero** cancelar a matrícula em uma disciplina antes do término do período de matrícula  
**Para que** eu possa ajustar meu plano de estudos caso necessário.  

### Critérios de Aceitação:
- O sistema deve listar todas as disciplinas em que o aluno está matriculado.
- O aluno deve poder cancelar a matrícula dentro do período de inscrição.
- Uma mensagem de confirmação deve ser exibida após a ação.

---

## História 5: Ver lista de alunos matriculados
**Como um** professor  
**Eu quero** visualizar a lista de alunos matriculados nas minhas disciplinas  
**Para que** eu possa acompanhar quem está inscrito e planejar minhas aulas.  

### Critérios de Aceitação:
- O professor deve poder selecionar a disciplina desejada.
- O sistema deve listar os alunos matriculados com seus nomes e IDs.
- O sistema deve mostrar a quantidade total de alunos inscritos na disciplina.

---

## História 6: Notificação ao sistema de cobranças
**Como o** sistema de matrículas  
**Eu quero** notificar o sistema de cobranças quando um aluno concluir a matrícula  
**Para que** o aluno possa ser cobrado pelas disciplinas matriculadas.  

### Critérios de Aceitação:
- A notificação deve ocorrer após a conclusão do processo de matrícula.
- O sistema de cobranças deve receber o número de créditos e disciplinas matriculadas.

---
