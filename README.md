# susie-project

# Schedule

<table>
	<thead>
		<tr>
			<th>Week</th>
			<th>Liżewski Kacper</th>
			<th>Maciąg Patryk</th>
			<th>Latocha Tobiasz</th>
		</tr>
	</thead>
	<tbody>
	    <tr>
	        <td>1</td>
	        <td colspan=3>Wybór technologii oraz języków programowania</td>
	    </tr>
	    <tr>
	        <td>2</td>
	        <td colspan=3>Wybór odpowiedniej architektury oprogramowania</td>
	    </tr>
	    <tr>
	        <td>3</td>
	        <td>Opracowanie mechanizmu uwierzytelniania i autoryzacji opartego na serwerze autoryzacyjnym Keycloak (OIDC, OAuth2.0)</td>
	        <td>Przygotowanie wstępnego projektu aplikacji oraz jej funkcjonalności</td>
	        <td>Przygotowanie wstępnych szablonów komponentów oraz routingu</td>
	    </tr>
	    <tr>
	        <td>4</td>
	        <td>Konteneryzacja serwera autoryzacyjnego Keycloak</td>
	        <td>Przygotowanie modułu sieciowego oraz modułu do cache’owania zapytań</td>
	        <td>Implementacja rejestracji oraz logowania </td>
	    </tr>
	    <tr>
	        <td>5</td>
	        <td>Implementacja interfejsu komunikacji między resource server oraz authorisation server</td>
	        <td>Przygotowanie modułu do przetrzymywania wrażliwych danych takich jak Token /Refresh token z możliwością rozszerzenia komponentu o inne dane</td>
	        <td>Implementacja Integracji z API oraz zarządzaniem danymi</td>
	    </tr>
	    <tr>
	        <td>6</td>
	        <td>Mapowanie struktury bazy danych na klasy encji i połączenie ich odpowiednimi relacjami, konteneryzacja relacyjnej bazy danych Postgres</td>
	        <td>Przygotowanie komponentu do zarządzanie dostępem do API</td>
	        <td>Przygotowanie kanban board oraz komponentu backlog</td>
	    </tr>
	    <tr>
	        <td>7</td>
	        <td>Zaplanowanie API dla aplikacji </td>
	        <td>Implementacja ekranu powitalnego, rejestracji orazlogowania</td>
	        <td>Przygotowanie komponentu dashboard </td>
	    </tr>
	    <tr>
	        <td>8</td>
	        <td>Implementacja API w technologii SpringBoot Web</td>
	        <td>Implementacja ekranu boards, backlog oraz dashboard</td>
	        <td>Implementacja podstawowej nawigacji i layout aplikacji</td>
	    </tr>
	    <tr>
	        <td>9</td>
	        <td>Integracja API resource server i authorisation server</td>
	        <td>Implementacja ekranu home wraz z widokami typu formularz</td>
	        <td>Ostateczna stylizacja aplikacji </td>
	    </tr>
	    <tr>
	        <td>10</td>
	        <td colspan=3>Proces testowania oprogramowania (testy jednostkowe oraz manualne)</td>
	    </tr>
    </tbody>
<tbody>

## Getting started

To make it easy for you to get started with GitLab, here's a list of recommended next steps.

Already a pro? Just edit this README.md and make it your own. Want to make it easy? [Use the template at the bottom](#editing-this-readme)!

## Add your files

- [ ] [Create](https://docs.gitlab.com/ee/user/project/repository/web_editor.html#create-a-file) or [upload](https://docs.gitlab.com/ee/user/project/repository/web_editor.html#upload-a-file) files
- [ ] [Add files using the command line](https://docs.gitlab.com/ee/gitlab-basics/add-file.html#add-a-file-using-the-command-line) or push an existing Git repository with the following command:

```
cd existing_repo
git remote add origin https://gitlab.com/klizewski/susie-project.git
git branch -M main
git push -uf origin main
```

## Integrate with your tools

- [ ] [Set up project integrations](https://gitlab.com/klizewski/susie-project/-/settings/integrations)

## Collaborate with your team

- [ ] [Invite team members and collaborators](https://docs.gitlab.com/ee/user/project/members/)
- [ ] [Create a new merge request](https://docs.gitlab.com/ee/user/project/merge_requests/creating_merge_requests.html)
- [ ] [Automatically close issues from merge requests](https://docs.gitlab.com/ee/user/project/issues/managing_issues.html#closing-issues-automatically)
- [ ] [Enable merge request approvals](https://docs.gitlab.com/ee/user/project/merge_requests/approvals/)
- [ ] [Set auto-merge](https://docs.gitlab.com/ee/user/project/merge_requests/merge_when_pipeline_succeeds.html)

## Test and Deploy

Use the built-in continuous integration in GitLab.

- [ ] [Get started with GitLab CI/CD](https://docs.gitlab.com/ee/ci/quick_start/index.html)
- [ ] [Analyze your code for known vulnerabilities with Static Application Security Testing(SAST)](https://docs.gitlab.com/ee/user/application_security/sast/)
- [ ] [Deploy to Kubernetes, Amazon EC2, or Amazon ECS using Auto Deploy](https://docs.gitlab.com/ee/topics/autodevops/requirements.html)
- [ ] [Use pull-based deployments for improved Kubernetes management](https://docs.gitlab.com/ee/user/clusters/agent/)
- [ ] [Set up protected environments](https://docs.gitlab.com/ee/ci/environments/protected_environments.html)

***

# Editing this README

When you're ready to make this README your own, just edit this file and use the handy template below (or feel free to structure it however you want - this is just a starting point!). Thank you to [makeareadme.com](https://www.makeareadme.com/) for this template.

## Suggestions for a good README
Every project is different, so consider which of these sections apply to yours. The sections used in the template are suggestions for most open source projects. Also keep in mind that while a README can be too long and detailed, too long is better than too short. If you think your README is too long, consider utilizing another form of documentation rather than cutting out information.

## Name
Choose a self-explaining name for your project.

## Description
Let people know what your project can do specifically. Provide context and add a link to any reference visitors might be unfamiliar with. A list of Features or a Background subsection can also be added here. If there are alternatives to your project, this is a good place to list differentiating factors.

## Badges
On some READMEs, you may see small images that convey metadata, such as whether or not all the tests are passing for the project. You can use Shields to add some to your README. Many services also have instructions for adding a badge.

## Visuals
Depending on what you are making, it can be a good idea to include screenshots or even a video (you'll frequently see GIFs rather than actual videos). Tools like ttygif can help, but check out Asciinema for a more sophisticated method.

## Installation
Within a particular ecosystem, there may be a common way of installing things, such as using Yarn, NuGet, or Homebrew. However, consider the possibility that whoever is reading your README is a novice and would like more guidance. Listing specific steps helps remove ambiguity and gets people to using your project as quickly as possible. If it only runs in a specific context like a particular programming language version or operating system or has dependencies that have to be installed manually, also add a Requirements subsection.

## Usage
Use examples liberally, and show the expected output if you can. It's helpful to have inline the smallest example of usage that you can demonstrate, while providing links to more sophisticated examples if they are too long to reasonably include in the README.

## Support
Tell people where they can go to for help. It can be any combination of an issue tracker, a chat room, an email address, etc.

## Roadmap
If you have ideas for releases in the future, it is a good idea to list them in the README.

## Contributing
State if you are open to contributions and what your requirements are for accepting them.

For people who want to make changes to your project, it's helpful to have some documentation on how to get started. Perhaps there is a script that they should run or some environment variables that they need to set. Make these steps explicit. These instructions could also be useful to your future self.

You can also document commands to lint the code or run tests. These steps help to ensure high code quality and reduce the likelihood that the changes inadvertently break something. Having instructions for running tests is especially helpful if it requires external setup, such as starting a Selenium server for testing in a browser.

## Authors and acknowledgment
Show your appreciation to those who have contributed to the project.

## License
For open source projects, say how it is licensed.

## Project status
If you have run out of energy or time for your project, put a note at the top of the README saying that development has slowed down or stopped completely. Someone may choose to fork your project or volunteer to step in as a maintainer or owner, allowing your project to keep going. You can also make an explicit request for maintainers.
