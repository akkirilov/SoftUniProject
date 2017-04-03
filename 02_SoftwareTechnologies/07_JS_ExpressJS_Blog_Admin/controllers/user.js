const User = require('mongoose').model('User');
const Role = require('mongoose').model('Role');
const passport = require('passport');
const encryption = require('./../utilities/encryption');
//import {fullName} from 'User';
//const {FirstName, LastName}=User;
module.exports = {
    registerGet:(req, res) =>{
        res.render('user/register');
    },

    registerPost:(req, res) =>{
        let registerArgs = req.body;

        User.findOne({email: registerArgs.email}).then(user => {
            let errorMsg = '';
            if(user) {
                errorMsg = 'User with the same email exist!';
            } else if(registerArgs.password !== registerArgs.repeatedPassword) {
                errorMsg = 'Passwords do not match!';
            }

            if(errorMsg){
                registerArgs.error = errorMsg;
                res.render('user/register', registerArgs);
                return;
            }

            let role = Role.findOne({name: 'User'}).then(role => {
                let salt = encryption.generateSalt();
                let passwordHash = encryption.hashPassword(registerArgs.password, salt);
                let userObj = {
                    email: registerArgs.email,
                    fullName: registerArgs.fullName,
                    passwordHash: passwordHash,
                    articles: [],
                    roles: [],
                    salt: salt
                };
                userObj.roles.push(role);
                User.create(userObj).then(user =>{
                    let {_id} = user;
                    role.users.push(_id);
                    role.save();
                    req.logIn(user, err => {
                        if (err) {
                            registerArgs.error = err.message;
                            res.render('user/register', registerArgs);
                            return;
                        }

                        res.redirect('/');
                    });
                });
            });
        });
    },

    loginGet: (req, res) => {
        res.render('user/login');
    },

    loginPost: (req, res) => {
        let loginArgs = req.body;
        let res12 = User.findOne({email:loginArgs.email}).then(user => {
            if (!user || !user.authenticate(loginArgs.password)) {
                let errorMsg = 'Either username or password is invalid!';
                loginArgs.error = errorMsg;
                res.render('user/login', loginArgs);
                return;
            }

            req.logIn(user, (err) => {
                if (err) {
                    console.log(err);
                    res.redirect('user/login', {error: err.message});
                    return;
                }
                let returnUrl ='/';
                if(req.session.returnUrl){
                    returnUrl = req.session.returnUrl;
                    delete req.session.returnUrl;
                }
                res.redirect(returnUrl);
            })
        });
        console.log(res12);
    },

    logout: (req, res) => {
        req.logOut();
        res.redirect('/');
    }
};