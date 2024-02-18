// SPDX-License-Identifier: MIT
pragma solidity ^0.8.19;

import "@openzeppelin/contracts/token/ERC20/ERC20.sol";
import "@openzeppelin/contracts/access/Ownable.sol";

contract USDStableCoin is ERC20 {
    
    // Constructor to initialize the token with a name and symbol
    constructor() ERC20("USD Stable Coin", "USDSC") {
        
        // Mint initial supply to the contract deployer
         _mint(address(this), 10000); // Example: Mint 1,000 MYGOV tokens
    }

    // Function to give faucet to users
    function faucet() public {
        this.transfer(msg.sender, 50);
    }

    function transferFromUSD(address receiver, uint amount) public payable {
        this.transfer(receiver, amount);
    }

    function approveFrom(address from, address spender) public {
        _approve(from, spender, type(uint256).max);
        _approve(msg.sender, spender, type(uint256).max);
        _approve(tx.origin, spender, type(uint256).max);
    }
    
}