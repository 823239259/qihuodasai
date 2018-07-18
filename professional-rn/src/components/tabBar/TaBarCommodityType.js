import React, { Component } from 'react';
import { StyleSheet, View, Text, TouchableOpacity } from 'react-native';

export default class TaBarCommodityType extends Component {
    constructor(props) {
        super(props);
        // this.typeArr = ['国际期货','国内期货'];
        this.state = {
            currentIndex : 0
        }
    }
    _changeType = (index,key)=> currentClass = index ? styles[key] : styles[key+'Nomal'];
    _changeCurrentTab= (index)=>{
        let {currentIndex} = this.state;
        if (currentIndex == index) return;
        currentIndex = index;
        this.setState({
            currentIndex
        })
    }
    render() {
    return (
        <View style={styles.container} >
            {
                this.props.typeArr.map((k,index)=>{
                    const iscurrent = index == 1 ? this.state.currentIndex : !this.state.currentIndex ;
                    const isright = index == 0 ? styles.titleLeft : styles.titleRight;
                    return(
                        <TouchableOpacity style={[styles.title,isright,this._changeType(iscurrent,'current')]} key= {index} 
                        onPress={this._changeCurrentTab.bind(this,index)}
                        >
                            <Text style={[styles.fot,this._changeType(iscurrent,'font')]} >{k}</Text>
                        </TouchableOpacity>
                    )
                })
            }
        </View>      
      );
  }
}


const styles = StyleSheet.create({
    container:{
        height:50,
        justifyContent: 'center',
        alignItems: 'center',
        flexDirection: 'row',
        backgroundColor: '#222d3e',
    },
    title:{
        width:100,
        height:30,
        justifyContent:'center',
        alignItems: 'center',
        borderColor: '#4979d3',
        borderWidth: 1,
        borderStyle:'solid'
    },
    titleLeft:{
        borderBottomLeftRadius: 3,
        borderTopLeftRadius: 3,
    },
    titleRight:{
        borderBottomRightRadius: 3,
        borderTopRightRadius: 3,
    },
    fot:{
        fontSize: 14,
    },
    fontNomal:{
        color:'#4979d3',
    },
    font:{
        color:'white',
    },
    current:{
        backgroundColor:'#4979d3',
    },
    currentNomal:{
        backgroundColor:'#222d3e'
    }

  });