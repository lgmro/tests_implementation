# Implementação de testes unitários e de integração

## Integrantes:
1. [Enzo Oliveira](https://github.com/enzomatos01) 
2. [Euller Costa](https://github.com/EullerSC) 
3. [Isaac Lessa](https://github.com/Isoco1)
4. [Lucas Gabriel](https://github.com/lgmro)
5. [Luis Carlos](https://github.com/Luis1988xp)

## Informações úteis:
- O objetivo principal desta atividade foi realizar testes unitário e testes de integração em uma api sring boot, na qual a mesma possui uma integração com a api do github e a implementação através do CommandLineRunner de cadastro de setores.

## Requisitos
1. Tenha o Java instalado.
2. [Baixe ou clone a api](https://github.com/lgmro/tests_implementation/).

## Como rodar esse projeto localmente
1. Rode a api em sua ide de preferência.

O projeto será iniciado na porta 8090. Poderá usar a seguinte url para teste da integração com o gihub: `localhost:8090/github/user/'Nome do usuario no github'`;

No terminal você verá o seguinte menu para criação, edição e exlucão de setores:
<div align = "center">
    <img src= "https://github.com/lgmro/SD_ProjetoSocket/assets/84135761/7fab082b-87c7-4322-9d45-12bd4415a11e" width = "800px"/>
</div>

## Testes
1. No projeto terá a pasta 'src/test' aonde ficam localizados os testes de unidade e a pasta 'src/integrationTests' aonde ficam localizados os testes de integração. Conforme imagem abaixo: 
<div align = "center">
    <img src= "https://github.com/lgmro/SD_ProjetoSocket/assets/84135761/80c93abe-459d-4bce-8c84-fbb2e1a106b2" width = "800px"/>
</div>

## Tasks do gradle
1. Para rodar todos os testes (unitários e de integração) e gerar um report no jacoco (obs.: os testes de integração não aumentam o coverage no jacoco) rode a seguinte tarefa pelo terminal: **./gradlew checkTestAndGenerateReport**.
2. Para rodar somente os testes unitário use: **./gradlew test**;
3. Para rodar somente os testes de integração use: **./gradlew integrationTests**.
4. Os reports do jacoco estarão localizados em: 'build/reports/test/html/index.html'. Conforme imagem abaixo: 
<div align = "center">
    <img src= "https://github.com/lgmro/tests_implementation/assets/84135761/ac93fdeb-d2b4-41ff-a005-f9f9133fdfc3" width = "800px"/>
</div>

