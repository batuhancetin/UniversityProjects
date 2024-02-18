import { ethers } from "./ethers-5.6.esm.min.js";
import { myGovTokenAbi, myGovTokenContractAddress, usdStableCoinAbi, usdStableCoinContractAddress } from "./constants.js";

// Connect Button
const connectButton = document.getElementById("connectButton");
const connectMessage = document.getElementById("connectMessage");

// Get Balance of MyGovToken
const getBalanceOfMyGovTokenButton = document.getElementById("getBalanceOfMyGovTokenButton");
const getBalanceOfMyGovTokenMessage = document.getElementById("getBalanceOfMyGovTokenMessage");

// Get Balance of USD Stable Coin
const getBalanceOfUSDStableCoinButton = document.getElementById("getBalanceOfUSDStableCoinButton");
const getBalanceOfUSDStableCoinMessage = document.getElementById("getBalanceOfUSDStableCoinMessage");

// Donate MyGovToken
const donateMyGovTokenAmount = document.getElementById("donateMyGovTokenAmount");
const donateMyGovTokenButton = document.getElementById("donateMyGovTokenButton");
const donateMyGovTokenMessage = document.getElementById("donateMyGovTokenMessage");

// Donate USD Stable Coins
const donateUSDAmount = document.getElementById("donateUSDAmount");
const donateUSDButton = document.getElementById("donateUSDButton");
const donateUSDMessage = document.getElementById("donateUSDMessage");

// Get Survey Information
const surveyId = document.getElementById("surveyId");
const getSurveyInfoButton = document.getElementById("getSurveyInfoButton");
const getSurveyInfoMessage = document.getElementById("getSurveyInfoMessage");

// Get Survey Owner
const surveyOwnerSurveyId = document.getElementById("surveyOwnerSurveyId");
const getSurveyOwnerButton = document.getElementById("getSurveyOwnerButton");
const getSurveyOwnerMessage = document.getElementById("getSurveyOwnerMessage");

// Get Survey Results
const surveyResultsSurveyId = document.getElementById("surveyResultsSurveyId");
const getSurveyResultsButton = document.getElementById("getSurveyResultsButton");
const getSurveyResultsMessage = document.getElementById("getSurveyResultsMessage");

// Get Is Project Funded
const projectFundedId = document.getElementById("projectFundedId");
const getIsProjectFundedButton = document.getElementById("getIsProjectFundedButton");
const getIsProjectFundedMessage = document.getElementById("getIsProjectFundedMessage");

// Get Project Next Payment
const projectNextPaymentId = document.getElementById("projectNextPaymentId");
const getProjectNextPaymentButton = document.getElementById("getProjectNextPaymentButton");
const getProjectNextPaymentMessage = document.getElementById("getProjectNextPaymentMessage");

// Get Project Owner
const projectOwnerProjectId = document.getElementById("projectOwnerProjectId");
const getProjectOwnerButton = document.getElementById("getProjectOwnerButton");
const getProjectOwnerMessage = document.getElementById("getProjectOwnerMessage");

// Get Project Info
const projectInfoId = document.getElementById("projectInfoId");
const getProjectInfoButton = document.getElementById("getProjectInfoButton");
const getProjectInfoMessage = document.getElementById("getProjectInfoMessage");

// Get Number of Project Proposals
const getNoOfProjectProposalsButton = document.getElementById("getNoOfProjectProposalsButton");
const getNoOfProjectProposalsMessage = document.getElementById("getNoOfProjectProposalsMessage");

// Get Number of Funded Projects
const getNoOfFundedProjectsButton = document.getElementById("getNoOfFundedProjectsButton");
const getNoOfFundedProjectsMessage = document.getElementById("getNoOfFundedProjectsMessage");

// Get USD Received by Project
const usdReceivedProjectId = document.getElementById("usdReceivedProjectId");
const getUSDReceivedByProjectButton = document.getElementById("getUSDReceivedByProjectButton");
const getUSDReceivedByProjectMessage = document.getElementById("getUSDReceivedByProjectMessage");

// Get Number of Surveys
const getNoOfSurveysButton = document.getElementById("getNoOfSurveysButton");
const getNoOfSurveysMessage = document.getElementById("getNoOfSurveysMessage");

// Faucet
const myGovTokenFaucetButton = document.getElementById("myGovTokenFaucetButton");
const usdStableCoinFaucetMessage = document.getElementById("usdStableCoinFaucetMessage");

// Faucet
const usdStableCoinFaucetButton = document.getElementById("usdStableCoinFaucetButton");
const myGovTokenFaucetMessage = document.getElementById("myGovTokenFaucetMessage");

// Submit Survey
const submitSurveyIpfs = document.getElementById("submitSurveyIpfs");
const submitSurveyDeadline = document.getElementById("submitSurveyDeadline");
const submitSurveyNumChoices = document.getElementById("submitSurveyNumChoices");
const submitSurveyAtMostChoice = document.getElementById("submitSurveyAtMostChoice");
const submitSurveyButton = document.getElementById("submitSurveyButton");
const submitSurveyMessage = document.getElementById("submitSurveyMessage");

// Take Survey
const takeSurveyId = document.getElementById("takeSurveyId");
const takeSurveyChoices = document.getElementById("takeSurveyChoices");
const takeSurveyButton = document.getElementById("takeSurveyButton");
const takeSurveyMessage = document.getElementById("takeSurveyMessage");

// Submit Project Proposal
const submitProjectIpfs = document.getElementById("submitProjectIpfs");
const submitProjectDeadline = document.getElementById("submitProjectDeadline");
const submitProjectAmounts = document.getElementById("submitProjectAmounts");
const submitProjectSchedule = document.getElementById("submitProjectSchedule");
const submitProjectProposalButton = document.getElementById("submitProjectProposalButton");
const submitProjectProposalMessage = document.getElementById("submitProjectProposalMessage");

// Reserve Project Grant
const reserveGrantProjectId = document.getElementById("reserveGrantProjectId");
const reserveProjectGrantButton = document.getElementById("reserveProjectGrantButton");
const reserveProjectGrantMessage = document.getElementById("reserveProjectGrantMessage");

// Withdraw Project Payment
const withdrawPaymentProjectId = document.getElementById("withdrawPaymentProjectId");
const withdrawProjectPaymentButton = document.getElementById("withdrawProjectPaymentButton");
const withdrawProjectPaymentMessage = document.getElementById("withdrawProjectPaymentMessage");

// Delegate Vote
const delegateVoteMemberAddress = document.getElementById("delegateVoteMemberAddress");
const delegateVoteProjectId = document.getElementById("delegateVoteProjectId");
const delegateVoteToButton = document.getElementById("delegateVoteToButton");
const delegateVoteToMessage = document.getElementById("delegateVoteToMessage");

// Vote for Project Proposal
const voteForProjectId = document.getElementById("voteForProjectId");
const voteForProjectChoice = document.getElementById("voteForProjectChoice");
const voteForProjectProposalButton = document.getElementById("voteForProjectProposalButton");
const voteForProjectProposalMessage = document.getElementById("voteForProjectProposalMessage");

// Vote for Project Payment
const voteForPaymentProjectId = document.getElementById("voteForPaymentProjectId");
const voteForPaymentChoice = document.getElementById("voteForPaymentChoice");
const voteForProjectPaymentButton = document.getElementById("voteForProjectPaymentButton");
const voteForProjectPaymentMessage = document.getElementById("voteForProjectPaymentMessage");

// Transfer from USD
const transferUSDReceiver = document.getElementById("transferUSDReceiver");
const transferUSDAmount = document.getElementById("transferUSDAmount");
const transferUSDButton = document.getElementById("transferUSDButton");
const transferUSDMessage = document.getElementById("transferUSDMessage");

//=================================================================================================================================================================
// Connect Button
connectButton.onclick = connect;

// Get Balance of MyGovToken
getBalanceOfMyGovTokenButton.onclick = getBalanceOfMyGovToken;

// Get Balance of USD Stable Coin
getBalanceOfUSDStableCoinButton.onclick = getBalanceOfUSDStableCoin;

// Donate MyGovToken
donateMyGovTokenButton.onclick = () => {
   const amount = parseFloat(donateMyGovTokenAmount.value);
   donateMyGovToken(amount);
};

// Donate USD Stable Coins
donateUSDButton.onclick = () => {
   const amount = parseInt(donateUSDAmount.value);
   donateUSD(amount);
};

// Get Survey Information
getSurveyInfoButton.onclick = () => {
   const id = parseInt(surveyId.value);
   getSurveyInfo(id);
};

// Get Survey Owner
getSurveyOwnerButton.onclick = () => {
   const id = parseInt(surveyOwnerSurveyId.value);
   getSurveyOwner(id);
};

// Get Survey Results
getSurveyResultsButton.onclick = () => {
   const id = parseInt(surveyResultsSurveyId.value);
   getSurveyResults(id);
};

// Get Is Project Funded
getIsProjectFundedButton.onclick = () => {
   const id = parseInt(projectFundedId.value);
   getIsProjectFunded(id);
};

// Get Project Next Payment
getProjectNextPaymentButton.onclick = () => {
   const id = parseInt(projectNextPaymentId.value);
   getProjectNextPayment(id);
};

// Get Project Owner
getProjectOwnerButton.onclick = () => {
   const id = parseInt(projectOwnerProjectId.value);
   getProjectOwner(id);
};

// Get Project Info
getProjectInfoButton.onclick = () => {
   const id = parseInt(projectInfoId.value);
   getProjectInfo(id);
};

// Get Number of Project Proposals
getNoOfProjectProposalsButton.onclick = getNoOfProjectProposals;

// Get Number of Funded Projects
getNoOfFundedProjectsButton.onclick = getNoOfFundedProjects;

// Get USD Received by Project
getUSDReceivedByProjectButton.onclick = () => {
   const id = parseInt(usdReceivedProjectId.value);
   getUSDReceivedByProject(id);
};

// Get Number of Surveys
getNoOfSurveysButton.onclick = getNoOfSurveys;

// Faucet
myGovTokenFaucetButton.onclick = myGovTokenFaucet;

// Faucet
usdStableCoinFaucetButton.onclick = usdStableCoinFaucet;

// Submit Survey
submitSurveyButton.onclick = () => {
   const ipfsHash = submitSurveyIpfs.value;
   const deadline = parseInt(submitSurveyDeadline.value);
   const numChoices = parseInt(submitSurveyNumChoices.value);
   const atMostChoice = parseInt(submitSurveyAtMostChoice.value);
   submitSurvey(ipfsHash, deadline, numChoices, atMostChoice);
};

// Take Survey
takeSurveyButton.onclick = () => {
   const id = parseInt(takeSurveyId.value);
   const choices = takeSurveyChoices.value.split(",").map((choice) => choice.trim());
   takeSurvey(id, choices);
};

// Submit Project Proposal
submitProjectProposalButton.onclick = () => {
   const ipfsHash = submitProjectIpfs.value;
   const deadline = parseInt(submitProjectDeadline.value);
   const amounts = submitProjectAmounts.value.split(",").map((amount) => parseInt(amount.trim()));
   const schedule = submitProjectSchedule.value.split(",").map((time) => parseInt(time.trim()));
   submitProjectProposal(ipfsHash, deadline, amounts, schedule);
};

// Reserve Project Grant
reserveProjectGrantButton.onclick = () => {
   const id = parseInt(reserveGrantProjectId.value);
   reserveProjectGrant(id);
};

// Withdraw Project Payment
withdrawProjectPaymentButton.onclick = () => {
   const id = parseInt(withdrawPaymentProjectId.value);
   withdrawProjectPayment(id);
};

// Delegate Vote
delegateVoteToButton.onclick = () => {
   const memberAddress = delegateVoteMemberAddress.value;
   const projectId = parseInt(delegateVoteProjectId.value);
   delegateVoteTo(memberAddress, projectId);
};

// Vote for Project Proposal
voteForProjectProposalButton.onclick = () => {
   const id = parseInt(voteForProjectId.value);
   const choice = voteForProjectChoice.value.toLowerCase() === "true";
   voteForProjectProposal(id, choice);
};

// Vote for Project Payment
voteForProjectPaymentButton.onclick = () => {
   const id = parseInt(voteForPaymentProjectId.value);
   const choice = voteForPaymentChoice.value.toLowerCase() === "true";
   voteForProjectPayment(id, choice);
};

transferUSDButton.onclick = () => {
   const receiver = transferUSDReceiver.value;
   const amount = parseInt(transferUSDAmount.value);
   transferFromUSD(receiver, amount);
};

//=================================================================================================================================================================
const provider = new ethers.providers.Web3Provider(window.ethereum);
const signer = provider.getSigner(); // how we are getting the account from the metamask
const myGovTokenContract = new ethers.Contract(myGovTokenContractAddress, myGovTokenAbi, signer);
const usdStableCoinContract = new ethers.Contract(usdStableCoinContractAddress, usdStableCoinAbi, signer);

async function connect() {
   // to account
   if (typeof window.ethereum !== "ethereum") {
      try {
         await window.ethereum.request({ method: "eth_requestAccounts" });
         connectButton.innerHTML = "CONNECTED!!!";
         const accounts = await window.ethereum.request({
            method: "eth_accounts",
         });
         connectMessage.innerHTML = `Connected to ${accounts[0]}`;
      } catch (er) {
         connectButton.innerHTML = "NOT CONNECTED!!!";
         connectMessage.innerHTML = `Connection error: ${er}`;      
      }
      
   }
}

async function getBalanceOfMyGovToken() {
   try {
      const balance = await myGovTokenContract.balanceOf(signer.getAddress());

      getBalanceOfMyGovTokenMessage.innerHTML = `MyGovToken Balance: ${balance}`;
   }
   catch (error) {
      getBalanceOfMyGovTokenMessage.innerHTML = `Error getting the MyGovToken balance: ${error}`;
   }
}

async function getBalanceOfUSDStableCoin() {
   try {
      const balance = await usdStableCoinContract.balanceOf(signer.getAddress());

      getBalanceOfUSDStableCoinMessage.innerHTML = `USD Stable Coin Balance: ${balance}`;
   }
   catch (error) {
      getBalanceOfUSDStableCoinMessage.innerHTML = `Error getting the USD Stable Coin balance: ${error}`;
   }
}


async function donateMyGovToken(amount) {
   try {
      const tx = await myGovTokenContract.donateMyGovToken(amount);

      // Wait for the transaction to be mined
      const receipt = await tx.wait();
      donateMyGovTokenMessage.innerHTML = "Donation successful.";
      console.log(`Donation successful. Transaction receipt: ${receipt}`); 
   } catch (error) {
      donateMyGovTokenMessage.innerHTML = "Error donating MyGov tokens.";
      console.log(`Error donating MyGov tokens: ${error}`);
   }
}

async function donateUSD(amount) {
   try {
      const gasLimit = 200000;
      const tx = await myGovTokenContract.donateUSD(amount, { gasLimit });

      // Wait for the transaction to be mined
      const receipt = await tx.wait();
      donateUSDMessage.innerHTML = "Donation successful.";
      console.log(`Donation successful. Transaction receipt: ${receipt}`); 
   } catch (error) {
      donateUSDMessage.innerHTML = "Error donating USD tokens.";
      console.log(`Error donating USD tokens: ${error}`);   }
}

async function getSurveyInfo(surveyId) {
   try {
      const result = await myGovTokenContract.getSurveyInfo(surveyId);

      const [ipfshash, surveydeadline, numchoices, atmostchoice] = result;

      getSurveyInfoMessage.innerHTML = `Survey Information: <br> IPFS Hash: ${ipfshash} <br> Survey Deadline: ${surveydeadline} <br> Number of Choices: ${numchoices} <br> At Most Choice: ${atmostchoice}`;

   } catch (error) {
      getSurveyInfoMessage.innerHTML = `Error Getting Survey Info: ${error}`;
   }
}

async function getSurveyOwner(surveyId) {
   try {
      const result = await myGovTokenContract.getSurveyOwner(surveyId);

      getSurveyOwnerMessage.innerHTML = `Survey Owner: ${result}`;
   } catch (error) {
      getSurveyOwnerMessage.innerHTML = `Error Getting Survey Owner: ${error}`;
   }
}

async function getSurveyResults(surveyId) {
   try {
      const result = await myGovTokenContract.getSurveyResults(surveyId);

      // The result will be an array with the values returned by the function
      const [numtaken, results] = result;

      getSurveyResultsMessage.innerHTML = `Survey Results: <br> Number of Taken: ${numtaken} <br> Results: ${results}`;
   } catch (error) {
      getSurveyResultsMessage.innerHTML = `Error Getting Survey Results: ${error}`;
   }
}

async function getIsProjectFunded(projectId) {
   try {
      const result = await myGovTokenContract.getIsProjectFunded(projectId);
      getIsProjectFundedMessage.innerHTML = `Is Project Funded: ${result}`;
   } catch (error) {
      getIsProjectFundedMessage.innerHTML = `Error checking if the project is funded: ${error}`;
   }
}

async function getProjectNextPayment(projectId) {
   try {
      const result = await myGovTokenContract.getProjectNextPayment(projectId);
      getProjectNextPaymentMessage.innerHTML = `Next Payment Amount: ${result}`;
   } catch (error) {
      getProjectNextPaymentMessage.innerHTML = `Error getting the next payment amount for the project: ${error}`;
   }
}

async function getProjectOwner(projectId) {
   try {
      const result = await myGovTokenContract.getProjectOwner(projectId);
      getProjectOwnerMessage.innerHTML = `Project Owner: ${result}`;
   } catch (error) {
      getProjectOwnerMessage.innerHTML = `Error getting the project owner: ${error}`;
   }
}

async function getProjectInfo(activityId) {
   try {
      const result = await myGovTokenContract.getProjectInfo(activityId);
      
      const [ipfshash, votedeadline, paymentamounts, payschedule] = result;
      getProjectInfoMessage.innerHTML = `Project Information: <br> IPFS Hash: ${ipfshash} <br> Vote Deadline: ${votedeadline} <br> Payment Amounts: ${paymentamounts} <br> Pay Schedule: ${payschedule}`;
   } catch (error) {
      getProjectInfoMessage.innerHTML = `Error getting project information: ${error}`;
   }
}

async function getNoOfProjectProposals() {
   try {
      const result = await myGovTokenContract.getNoOfProjectProposals();
      getNoOfProjectProposalsMessage.innerHTML = `Number of Project Proposals: ${result}`;
   } catch (error) {
      getNoOfProjectProposalsMessage.innerHTML = `Error getting the number of project proposals: ${error}`;
   }
}

async function getNoOfFundedProjects() {
   try {
      const result = await myGovTokenContract.getNoOfFundedProjects();
      getNoOfFundedProjectsMessage.innerHTML = `Number of Funded Projects: ${result}`;
   } catch (error) {
      getNoOfFundedProjectsMessage.innerHTML = `Error getting the number of funded projects: ${error}`;
   }
}

async function getUSDReceivedByProject(projectId) {
   try {
      const result = await myGovTokenContract.getUSDReceivedByProject(projectId);
      getUSDReceivedByProjectMessage.innerHTML = `USD Received by Project: ${result}`;
   } catch (error) {
      getUSDReceivedByProjectMessage.innerHTML = `Error getting USD received by the project: ${error}`;
   }
}

async function getNoOfSurveys() {
   try {
      const result = await myGovTokenContract.getNoOfSurveys();
      getNoOfSurveysMessage.innerHTML = `Number of Surveys: ${result}`;
   } catch (error) {
      getNoOfSurveysMessage.innerHTML = `Error getting the number of surveys: ${error}`;
   }
}

async function myGovTokenFaucet() {
   try {
      const faucetTx = await myGovTokenContract.faucet();

      // Wait for the transaction to be mined
      const receipt = await faucetTx.wait();
      myGovTokenFaucetMessage.innerHTML = "Faucet successful.";
      console.log("Faucet successful. Transaction receipt:", receipt);
   } catch (error) {
      myGovTokenFaucetMessage.innerHTML = "Error using the faucet.";
      console.log(`Error while using the faucet:${error.message}`);
   }
}

async function submitSurvey(ipfshash, surveydeadline, numchoices, atmostchoice) {
   try {
      const submitSurveyTx = await myGovTokenContract.submitSurvey(ipfshash, surveydeadline, numchoices, atmostchoice);

      // Wait for the transaction to be mined
      const receipt = await submitSurveyTx.wait();
      submitSurveyMessage.innerHTML = "Survey submitted successfully.";
      console.log("Survey submitted successfully. Transaction receipt:", receipt);
   } catch (error) {
      submitSurveyMessage.innerHTML = "Error submitting survey.";
      console.error("Error submitting survey:", error);
   }
}

async function takeSurvey(surveyId, choices) {
   try {
      const takeSurveyTx = await myGovTokenContract.takeSurvey(surveyId, choices);

      // Wait for the transaction to be mined
      const receipt = await takeSurveyTx.wait();
      takeSurveyMessage.innerHTML = "Survey taken successfully.";
      console.log("Survey taken successfully. Transaction receipt:", receipt);
   } catch (error) {
      takeSurveyMessage.innerHTML = "Error taking the survey.";
      console.error("Error taking the survey:", error);
   }
}

async function submitProjectProposal(ipfshash, votedeadline, paymentamounts, payschedule) {
   try {
      const submitProjectTx = await myGovTokenContract.submitProjectProposal(ipfshash, votedeadline, paymentamounts, payschedule);

      // Wait for the transaction to be mined
      const receipt = await submitProjectTx.wait();
      submitProjectProposalMessage.innerHTML = "Project proposal submitted successfully.";
      console.log("Project proposal submitted successfully. Transaction receipt:", receipt);
   } catch (error) {
      submitProjectProposalMessage.innerHTML = "Error submitting project proposal.";
      console.error("Error submitting project proposal:", error);
   }
}

async function reserveProjectGrant(projectId) {
   try {
      const reserveGrantTx = await myGovTokenContract.reserveProjectGrant(projectId);

      // Wait for the transaction to be mined
      const receipt = await reserveGrantTx.wait();

      reserveProjectGrantMessage.innerHTML = "Project grant reserved successfully.";
      console.log("Project grant reserved successfully.", receipt);
   } catch (error) {
      reserveProjectGrantMessage.innerHTML = "Error reserving project grant.";
      console.error("Error reserving project grant:", error);
   }
}

async function withdrawProjectPayment(projectId) {
   try {
      const withdrawPaymentTx = await myGovTokenContract.withdrawProjectPayment(projectId);

      // Wait for the transaction to be mined
      const receipt = await withdrawPaymentTx.wait();

      withdrawProjectPaymentMessage.innerHTML = "Project payment withdrawn successfully.";
      console.log("Project payment withdrawn successfully.", receipt);
   } catch (error) {
      withdrawProjectPaymentMessage.innerHTML = "Error withdrawing project payment.";
      console.error("Error withdrawing project payment:", error);
   }
}

async function delegateVoteTo(memberAddress, projectId) {
   try {
      const delegateVoteTx = await myGovTokenContract.delegateVoteTo(memberAddress, projectId);

      // Wait for the transaction to be mined
      await delegateVoteTx.wait();

      delegateVoteToMessage.innerHTML = "Vote delegated successfully.";
   } catch (error) {
      delegateVoteToMessage.innerHTML = "Error delegating vote.";
      console.error("Error delegating vote:", error);
   }
}

async function voteForProjectProposal(projectId, choice) {
   try {
      const voteForProjectTx = await myGovTokenContract.voteForProjectProposal(projectId, choice);

      // Wait for the transaction to be mined
      await voteForProjectTx.wait();

      voteForProjectProposalMessage.innerHTML = "Vote for project proposal submitted successfully.";
   } catch (error) {
      voteForProjectProposalMessage.innerHTML = "Error voting for project proposal.";
      console.error("Error voting for project proposal:", error);
   }
}

async function voteForProjectPayment(projectId, choice) {
   try {
      const voteForPaymentTx = await myGovTokenContract.voteForProjectPayment(projectId, choice);

      // Wait for the transaction to be mined
      await voteForPaymentTx.wait();

      voteForProjectPaymentMessage.innerHTML = "Vote for project payment submitted successfully.";
   } catch (error) {
      voteForProjectPaymentMessage.innerHTML = "Error voting for project payment.";
      console.error("Error voting for project payment:", error);
   }
}

async function usdStableCoinFaucet() {
   try {
      const transaction = await usdStableCoinContract.faucet();
      const receipt = await transaction.wait();
      usdStableCoinFaucetMessage.innerHTML = "Faucet successful.";
      console.log(`Successfully obtained USDStableCoin. Receipt: ${receipt}`);
   } catch (error) {
      usdStableCoinFaucetMessage.innerHTML = "Error using the faucet.";
      console.log(`An error occurred during usdStableCoinFaucet: ${error}`);
   }
}

async function transferFromUSD(receiver, amount) {
   try {
      const transaction = await usdStableCoinContract.transferFromUSD(receiver, amount);

      // Wait for the transaction to be mined
      await transaction.wait();

      transferUSDMessage.innerHTML = `Transfer successful! ${amount} USD sent to ${receiver}`;
   } catch (error) {
      transferUSDMessage.innerHTML = "Error transferring USD.";
      console.error("Error:", error.message);
   }
}
