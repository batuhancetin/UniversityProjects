// SPDX-License-Identifier: MIT
pragma solidity ^0.8.19;

import "@openzeppelin/contracts/token/ERC20/ERC20.sol";
import "./USDStableCoin.sol";

contract MyGovToken is ERC20 {

    // Coin fees for submit survey or project proposal
    uint surveyUSDFee =  5; 
    uint surveyMyGovFee =  2; 
    uint projectUSDFee =  50;
    uint projectMyGovFee = 5;

    uint numOfFundedProjects = 0;

    // Reference to the USD Stable Coin Contract
    USDStableCoin usdStableCoinContract;

    // Number of members in the system
    uint numberOfMembers = 0;

    // The amount of reserved USD for granted projects
    uint reservedUSD = 0;

    // Constructor to initialize the token with a name and symbol
    constructor(uint tokensupply, address usdStableCoinAddress) ERC20("MyGov Token", "MYGOV") {

        usdStableCoinContract = USDStableCoin(usdStableCoinAddress);
        
        // Mint initial supply to the contract deployer
        _mint(address(this), tokensupply); // Example: Mint 1,000 MYGOV tokens
        _approve(msg.sender, address(this), type(uint256).max);
        _approve(msg.sender, address(usdStableCoinContract), type(uint256).max);
        _approve(address(this), msg.sender, type(uint256).max);
        _approve(address(this), address(usdStableCoinContract), type(uint256).max);
        _approve(address(usdStableCoinContract), msg.sender, type(uint256).max);
        _approve(address(usdStableCoinContract), address(this), type(uint256).max);
        usdStableCoinContract.approveFrom(msg.sender, address(this));
        usdStableCoinContract.approveFrom(address(this), msg.sender);
        usdStableCoinContract.approveFrom(address(usdStableCoinContract), address(this));
        usdStableCoinContract.approveFrom(address(usdStableCoinContract), msg.sender);
    }

    // Struct representing a survey
    struct Survey {
        string ipfsHash;
        address owner;
        uint deadline;
        uint numChoices;
        uint atMostChoice;
        uint numTaken;
        uint [] results;
        mapping(address => bool) participants;  

    }

    // Struct representing a user
    struct User {
        address userAddress;
        bool fauceted; // Shows user get faucet or not
        mapping(uint => address) delegation; // key is project id, value is address of the delegate
        uint lockedUntil; // timestampt that holds the time user's token is locked
        mapping(uint => uint) weight; // vote weight, key holds projectid value holds the vote weight
    }

    struct ProjectProposal {
        address projectOwner;
        string ipfsHash;
        uint deadline;
        uint[] paymentAmounts;
        uint[] paySchedule;
        bool isFunded; // shows project is funded or not
        mapping(address => bool) voters; // voting status of users
        mapping(address => bool) paymentvoters; // payment voting status of users
        uint vote; // proposal true vote count
        uint votepayment; // payment true vote count
        bool[] withdrawed; // withdraw status of payments
        

    }

    Survey[] public surveys;
    ProjectProposal[] public proposals;
    mapping(address => User) public users;  



    // Function for users to donate MyGov tokens to the contract
    function donateMyGovToken(uint amount) public payable {
        require(balanceOf(msg.sender) >= amount, "Insufficient amount of MyGovToken");
        transfer(address(this), amount);
        if (balanceOf(msg.sender) == 0 && numberOfMembers > 0){
            numberOfMembers --;
        }
    }

    // Function for users to donate USD Stable Coins to the contract
    function donateUSD(uint amount) public payable {
        require(usdStableCoinContract.balanceOf(msg.sender) >= amount, "Insufficient amount of USD Stable Coin");
        usdStableCoinContract.transferFrom(msg.sender, address(usdStableCoinContract), amount);
    }


    // Function to get survey results
    function getSurveyResults(uint surveyid) public view returns(uint numtaken, uint[] memory results) {
        numtaken = surveys[surveyid].numTaken;
        results = surveys[surveyid].results;
        return (numtaken, results);
    }

    // Function to get survey information
    function getSurveyInfo(uint surveyid) public view returns(string memory ipfshash, uint surveydeadline,uint numchoices, uint atmostchoice) {
        ipfshash = surveys[surveyid].ipfsHash;
        surveydeadline = surveys[surveyid].deadline;
        numchoices = surveys[surveyid].numChoices;
        atmostchoice = surveys[surveyid].atMostChoice;
        return (ipfshash, surveydeadline, numchoices, atmostchoice);
    } 

    // Function to get the owner of a survey
    function getSurveyOwner(uint surveyid) public view returns(address surveyowner) {
        surveyowner = surveys[surveyid].owner;
        return surveyowner;
    }

    // Function to check if a project is funded
    function getIsProjectFunded(uint projectid) public view returns(bool funded) {
        funded = proposals[projectid].isFunded;
        return funded;
    }

    // Function to get the next payment amount for a project
    function getProjectNextPayment(uint projectid) public view returns(int next) {
        ProjectProposal storage project = proposals[projectid];
        for (uint i = 0; i < project.paySchedule.length; i++) 
        {
            if (block.timestamp < project.paySchedule[i]) {
                next = int(project.paymentAmounts[i]);
                break;
            }
        }
        return next;

    }
    
    // Function to get the owner of a project
    function getProjectOwner(uint projectid) public view returns(address projectowner) {
        projectowner = proposals[projectid].projectOwner;
        return projectowner;
    }

    // Function to get project information
    function getProjectInfo(uint activityid) public view returns(string memory ipfshash, uint votedeadline, uint[] memory paymentamounts, uint[] memory payschedule) {
        ipfshash = proposals[activityid].ipfsHash;
        votedeadline = proposals[activityid].deadline;
        paymentamounts = proposals[activityid].paymentAmounts;
        payschedule = proposals[activityid].paySchedule;
        ipfshash = proposals[activityid].ipfsHash;
        return (ipfshash, votedeadline, paymentamounts, payschedule);
    }

    // Function to get the number of project proposals
    function getNoOfProjectProposals() public view returns(uint numproposals) {
        numproposals = proposals.length;
        return numproposals;
    }

    // Function to get the number of funded projects
    function getNoOfFundedProjects() public view returns(uint numfunded) {
        numfunded = numOfFundedProjects;
        return numfunded;
    }

    // Function to get the USD received by a project
    function getUSDReceivedByProject (uint projectid) public view returns(uint amount) {
        amount = 0;
        ProjectProposal storage project = proposals[projectid];
        for (uint i = 0; i < project.paymentAmounts.length; i++) 
        {
            if (project.withdrawed[i] == false) {
                break;
            }
            amount += project.paymentAmounts[i];
        }
        return amount;
    }

    // Function to get the number of surveys
    function getNoOfSurveys() public view returns(uint numsurveys) {
        numsurveys = surveys.length;
        return numsurveys;
    }

    // Faucet function to distribute MyGov token to users
    function faucet() public {
        require(users[msg.sender].fauceted == false, "User got already the faucet.");        
        this.transfer(msg.sender, 1);
        users[msg.sender].fauceted = true;
        numberOfMembers ++;
    }

    // Function to submit survey
    function submitSurvey(string memory ipfshash,uint surveydeadline,uint numchoices, uint atmostchoice) public payable returns (uint surveyid) {
        require(balanceOf(msg.sender) > 0, "Users must be member to submit survey.");
        require(balanceOf(msg.sender) >= surveyMyGovFee, "There isn't enough amount of MyGovToken to submit survey.");
        require(usdStableCoinContract.balanceOf(msg.sender) >= surveyUSDFee, "There isn't enough amount of USD to submit survey.");
        transfer(address(this), surveyMyGovFee);
        usdStableCoinContract.transferFrom(msg.sender, address(usdStableCoinContract), surveyUSDFee);
        // Survey initialization
        surveys.push();
        surveyid = surveys.length - 1;
        Survey storage newSurvey = surveys[surveyid];
        newSurvey.ipfsHash = ipfshash;
        newSurvey.deadline = surveydeadline;
        newSurvey.numChoices = numchoices;
        newSurvey.atMostChoice = atmostchoice;
        newSurvey.owner = msg.sender;
        newSurvey.numTaken = 0;
        newSurvey.results = new uint[](numchoices);

        if (balanceOf(msg.sender) == 0) {
            numberOfMembers --;
        }

        return surveyid;
    }

    // Function to take survey
    function takeSurvey(uint surveyid, uint [] memory choices) public {
        Survey storage _survey = surveys[surveyid];
        require(balanceOf(msg.sender) > 0, "Users must be member to take survey.");
        require(_survey.deadline > block.timestamp, "Deadline is passed.");
        require(_survey.participants[msg.sender] == false, "User has already taken the survey.");
        require(_survey.atMostChoice >= choices.length, "User chose too much option.");
        // Answer survey questions
        for (uint i = 0; i < choices.length; i ++) {
            uint choice = choices[i];
            _survey.results[choice] ++;
        }
        _survey.numTaken ++;
        _survey.participants[msg.sender] = true;
    }

    // Function to submit project proposal
    function submitProjectProposal(string memory ipfshash, uint votedeadline, uint [] memory paymentamounts, uint [] memory payschedule) public payable returns (uint projectid) {
        require(balanceOf(msg.sender) > 0, "Users must be member to submit project proposal.");
        require(balanceOf(msg.sender) >= projectMyGovFee, "There isn't enough amount of MyGovToken to propose project.");
        require(usdStableCoinContract.balanceOf(msg.sender) >= projectUSDFee, "There isn't enough amount of USD to propose project.");
        transfer(address(this), projectMyGovFee);
        usdStableCoinContract.transferFrom(msg.sender, address(usdStableCoinContract), projectUSDFee);
        // Project Proposal initialization
        proposals.push();
        projectid = proposals.length - 1;
        ProjectProposal storage project = proposals[projectid];
        project.ipfsHash = ipfshash;
        project.deadline = votedeadline;
        project.isFunded = false;
        project.paymentAmounts = paymentamounts;
        project.paySchedule = payschedule;
        project.projectOwner = msg.sender;
        project.vote = 0;
        project.votepayment = 0;
        project.withdrawed = new bool[](paymentamounts.length);

        if (balanceOf(msg.sender) == 0) {
            numberOfMembers --;
        }
        for (uint i = 0; i < project.paymentAmounts.length; i++) 
        {
            project.withdrawed[i] = false;
        }
        return projectid;
    }

    // Function to reserve project grant
    function reserveProjectGrant(uint projectid) public {
        ProjectProposal storage project = proposals[projectid];
        require(project.isFunded == false, "Project is already funded");
        require(msg.sender == project.projectOwner, "User is not the project owner.");
        require(project.deadline > block.timestamp, "Deadline is passed.");
        require(project.vote * 10 >= numberOfMembers, "Insufficient vote");
        uint funding = 0; // Total amount of funding
        
        for (uint i = 0; i < project.paymentAmounts.length; i ++) 
        {
            funding += project.paymentAmounts[i];
        }
        uint required = funding + reservedUSD;

        require(usdStableCoinContract.balanceOf(address(usdStableCoinContract)) >= required, "Insufficient amount of USD to grant");
        reservedUSD += funding;
        project.isFunded = true;
        numOfFundedProjects ++;
    }

    // Function to withdraw project payment
    function withdrawProjectPayment(uint projectid) public payable {
        ProjectProposal storage project = proposals[projectid];
        require(project.isFunded, "Project must be funded.");
        require(msg.sender == project.projectOwner, "User is not the project owner.");
        if (project.votepayment * 100 >= numberOfMembers) {
            // Withdraw the payments that schedule is previous from the current time
            for (uint i = 0; i < project.paySchedule.length; i++) 
            {
                if (project.withdrawed[i] == false && project.paySchedule[i] < block.timestamp) {
                    require(project.paymentAmounts[i] <= reservedUSD, "Insufficient reserved.");
                    usdStableCoinContract.transferFromUSD(msg.sender, project.paymentAmounts[i]);
                    reservedUSD -= project.paymentAmounts[i];
                    project.withdrawed[i] = true;
                }
            }
        } else {
            project.isFunded = false; // Project lost its funding
        }
        
    }

    // Function to delegate vote
    function delegateVoteTo(address memberaddr,uint projectid) public {
        ProjectProposal storage project = proposals[projectid];
        User storage user = users[msg.sender];
        User storage delegate = users[memberaddr];
        require(project.voters[msg.sender] == false, "User already voted.");
        require(project.voters[memberaddr] == false, "Delegated user already voted.");
        require(msg.sender != memberaddr, "Self delegation is not allowed.");
        require(balanceOf(msg.sender) > 0, "User must be a member.");
        require(balanceOf(memberaddr) > 0, "Delegate must be a member.");
        require(user.delegation[projectid] == address(0), "User already delegated his/her vote.");
        require(delegate.delegation[projectid] == address(0), "Delegate already delegated his/her vote.");
        user.delegation[projectid] = memberaddr;
        // initialize delegate weight or increment
        if  (user.weight[projectid] == 0) {
            user.weight[projectid] = 1;
        }
        if (delegate.weight[projectid] == 0) {
            delegate.weight[projectid] = 1 + user.weight[projectid];
        } else {
            delegate.weight[projectid] += user.weight[projectid];
        }
        user.weight[projectid] = 0;
        user.lockedUntil = project.deadline; // locked user's token until deadline of project
    }

    // Function to vote for project proposal 
    function voteForProjectProposal(uint projectid,bool choice) public {
        require(balanceOf(msg.sender) > 0, "Voter must be a member.");
        ProjectProposal storage project = proposals[projectid];
        User storage user = users[msg.sender];
        require(project.voters[msg.sender] == false, "User already voted.");
        require(user.delegation[projectid] == address(0), "User already delegated his/her vote.");
        require(block.timestamp > user.lockedUntil, "User's vote is locked until deadline of another project.");
        require(block.timestamp < project.deadline, "Deadline of the proposal is passed.");
        if (choice == true) {
            if (user.weight[projectid] == 0) {
                user.weight[projectid] = 1;
            }
            project.vote += user.weight[projectid]; // increment vote count with weight of the user
        }
        project.voters[msg.sender] = true;
    }

    // Function to vote for project payment
    function voteForProjectPayment(uint projectid,bool choice) public {
        require(balanceOf(msg.sender) > 0, "Voter must be a member.");
        ProjectProposal storage project = proposals[projectid];
        User storage user = users[msg.sender];
        require(project.paymentvoters[msg.sender] == false, "User already voted.");
        require(user.delegation[projectid] == address(0), "User already delegated his/her vote.");
        require(block.timestamp > user.lockedUntil, "User's vote is locked until deadline of another project.");
        require(block.timestamp < project.deadline, "Deadline of the proposal is passed.");
        if (choice == true) {
            if (user.weight[projectid] == 0) {
                user.weight[projectid] = 1;
            }
            project.votepayment += user.weight[projectid]; // increment payment vote count with weight of the user
        }
        project.paymentvoters[msg.sender] = true;
    }


}
