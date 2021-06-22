const ce = React.createElement;
const csrfToken = document.getElementById("csrfToken").value;
const validateRoute = document.getElementById("validateRoute").value;
const createRoute = document.getElementById("createRoute").value;

class LoginMain extends React.Component {
    constructor(props) {
        super(props);
        this.state = { loggedIn: false };
    }

    render() {
        if (this.state.loggedIn) {
            return ce(UserProfile, { doLogout: () => this.setState( { loggedIn: false})});
        } else {
            return ce(LoginComponent, { doLogin: () => this.setState( { loggedIn: true})});
        }
    }
}

class LoginComponent extends React.Component {
    constructor(props) {
        super(props);
        this.state = {
            loginName: "",
            loginPass: "",
            createName: "",
            createPass: "",
            createEmail: "",
            createEthId: "",
            loginMessage: "",
            createMessage: ""
        };
    }

    render() {
        return ce('div', null,
            ce('h2', null, 'Login:'),
            ce('br'),
            'Username: ',
            ce('input', {type: "text", id: "loginName", value: this.state.loginName, onChange: e => this.changerHandler(e)}),
            ce('br'),
            'Password: ',
            ce('input', {type: "password", id: "loginPass", value: this.state.loginPass, onChange: e => this.changerHandler(e)}),
            ce('br'),
            ce('button', {onClick: e => this.login(e)}, 'Login'),
            ce('span', {id: "login-message"}, this.state.loginMessage),
            ce('h2', null, 'Create User:'),
            ce('br'),
            'Username: ',
            ce('input', {type: "text", id: "createName", value: this.state.createName, onChange: e => this.changerHandler(e)}),
            ce('br'),
            'Password: ',
            ce('input', {type: "password", id: "createPass", value: this.state.createPass, onChange: e => this.changerHandler(e)}),
            ce('br'),
            'Email: ',
            ce('input', {type: "text", id: "createEmail", value: this.state.createEmail, onChange: e => this.changerHandler(e)}),
            ce('br'),
            'ETH ID: ',
            ce('input', {type: "text", id: "createEthId", value: this.state.createEthId, onChange: e => this.changerHandler(e)}),
            ce('br'),
            ce('button', {onClick: e => this.createUser(e)}, 'Create User'),
            ce('span', {id: "create-message"}, this.state.createMessage)
        );
    }

    changerHandler(e) {
        this.setState({ [e.target['id']]: e.target.value });
    }

    login(e) {
        const username = this.state.loginName;
        const password = this.state.loginPass;
        fetch(validateRoute, {
            method: 'POST',
            headers: {'Content-Type': 'application/json', 'Csrf-Token': csrfToken },
            body: JSON.stringify({ username, password })
        }).then(res => res.json()).then(data => {
            if(data) {
                this.props.doLogin();
            } else {
                this.setState({ loginMessage: "Login Failed" });
            }
        });
    }

    createUser() {
        const username = this.state.createName;
        const password = this.state.createPass;
        const email = this.state.createEmail;
        const ethId = this.state.createEthId;
        fetch(createRoute, {
            method: 'POST',
            headers: {'Content-Type': 'application/json', 'Csrf-Token': csrfToken },
            body: JSON.stringify({ username, password, email, ethId })
        }).then(res => {
            res.json()
        }).then(data => {
            if(data) {
                this.props.doLogin();
            } else {
                this.setState({ createMessage: "User Creation Failed"});
            }
        });
    }
}

class UserProfile extends React.Component {
    constructor(props) {
        super(props);
        this.state = {
            nothing: ""
        };
    }
    render() {
        return (
            ce('h2', null, 'User Profile')
        )
    }
}


ReactDOM.render(
    ce(LoginMain, null, null),
    document.getElementById('react-root')
);