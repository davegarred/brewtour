var LoginButton = React.createClass({
	render: function() {
		return (
				<button type="button" className="btn btn-primary btn-sm navbar-btn" data-toggle="modal" data-target="#loginModal">Sign
				in</button>
				)
	}
});
var LogoutButton = React.createClass({
	render: function() {
		return (
				<div>
				<button type="button" className="btn btn-default btn-sm navbar-btn dropdown-toggle" data-toggle="dropdown" aria-haspopup="true"
					aria-expanded="false">
				{this.props.user.screenName}
				</button>
				<ul className="dropdown-menu">
				<li className="disabled"><a href="#">Account Settings</a></li>
				<li className="disabled"><a href="#">Change Password</a></li>
				<li role="separator" className="divider"></li>
				<li><a href="#" ng-click="logout()">Logout</a></li>
				</ul>
				</div>
		)
	}
});


var Header = React.createClass({
	componentDidMount: function() {
		$.ajax({
			url: '../user',
			dataType: 'json',
			cache: false,
			success: function(data) {
				this.setState({user: data});
			}.bind(this),
			error: function(xhr,status,err) {
				console.error(this.props.url, status, err.toString());
			}.bind(this)
		})
	},
	getInitialState: function() {
		var testUser = {screenName	: "Dave G"};
		return {
			user: testUser
		};
	},
	render: function() {
		var loginButton;
		if(this.state.user) {
			loginButton = <LogoutButton user={this.state.user} />;
		} else {
			loginButton = <LoginButton />;
		}
		return (
				<nav className="navbar navbar-default" >
				<div className="container-fluid">
				<div className="navbar-header">
				<button type="button" className="navbar-toggle collapsed" data-toggle="collapse" data-target="#bs-example-navbar-collapse-1" aria-expanded="false">
				<span className="sr-only">Toggle navigation</span>
				<span className="icon-bar"></span>
				<span className="icon-bar"></span>
				<span className="icon-bar"></span>
				</button>
				<a className="navbar-brand" href="#">Seattle Beer Tour</a>
				{loginButton}

				</div>
				</div>
				</nav>
		)
	}
});

// <nav class="navbar navbar-default" style="margin-bottom: 0px;">
// <ul class="dropdown-menu" style={{margin-top: -5px;}}>
window.Header = Header;